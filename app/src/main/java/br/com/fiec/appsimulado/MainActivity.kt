package br.com.fiec.appsimulado

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.com.fiec.appsimulado.ui.theme.AppSimuladoTheme

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.fiec.appsimulado.databinding.ActivityAlunoBinding
import br.com.fiec.appsimulado.databinding.ActivityMainBinding
import br.com.fiec.appsimulado.ui.theme.Aluno
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

class MainActivity : ComponentActivity() {

    private lateinit var binding: ActivityMainBinding
    private var fotoUriSelecionada: Uri? = null
    private var fotoBitmap: Bitmap? = null
    private var tempCameraUri: Uri? = null

    private val localAdapter = SimpleAlunoAdapter()
    private val remoteAdapter = SimpleAlunoAdapter()

    // Instanciação dos Repositórios e ViewModel via Factory
    private val viewModel: AlunoViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val db = AppDatabase.getDatabase(applicationContext)
                val localRepo = AlunoLocalRepository(db.alunoDao())

                val retrofit = Retrofit.Builder()
                    .baseUrl("https://api.exemplo.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                val apiService = retrofit.create(AlunoApiService::class.java)
                val remoteRepo = AlunoRemoteRepository(apiService)

                @Suppress("UNCHECKED_CAST")
                return AlunoViewModel(remoteRepo, localRepo) as T
            }
        }
    }

    // Launchers de Câmera, Galeria e Permissões
    private val galleryLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { carregarBitmap(it) }
    }

    private val cameraLauncher = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success: Boolean ->
        if (success && tempCameraUri != null) {
            carregarBitmap(tempCameraUri!!)
        }
    }

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) abrirCamera()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerViews()
        setupListeners()
        observeViewModel()

        viewModel.carregarAlunosLocais()
        viewModel.carregarAlunosRemotos()
    }

    private fun setupRecyclerViews() {
        binding.rvLocais.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = localAdapter
        }
        binding.rvRemotos.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = remoteAdapter
        }
    }

    private fun setupListeners() {
        binding.btnGaleria.setOnClickListener {
            galleryLauncher.launch("image/*")
        }

        binding.btnCamera.setOnClickListener {
            val permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                abrirCamera()
            } else {
                permissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }

        binding.btnSalvarLocal.setOnClickListener {
            val nome = binding.etNome.text.toString()
            val email = binding.etEmail.text.toString()
            val foto = fotoUriSelecionada?.toString() ?: ""

            viewModel.salvarLocal(AlunoEntity(nome = nome, email = email, foto = foto))
        }

        binding.btnSalvarRemoto.setOnClickListener {
            val nome = binding.etNome.text.toString()
            val email = binding.etEmail.text.toString()
            val foto = fotoUriSelecionada?.toString() ?: ""

            viewModel.salvarRemoto(Aluno(nome = nome, email = email, foto = foto))
        }
    }

    private fun carregarBitmap(uri: Uri) {
        fotoUriSelecionada = uri
        fotoBitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            ImageDecoder.decodeBitmap(ImageDecoder.createSource(contentResolver, uri))
        } else {
            @Suppress("DEPRECATION")
            MediaStore.Images.Media.getBitmap(contentResolver, uri)
        }

        // Exibe a imagem no Canvas de prévia
        binding.composeCanvas.setContent {
            FotoPreviewCanvas(bitmap = fotoBitmap)
        }
    }

    private fun abrirCamera() {
        val tempFile = File.createTempFile("photo_", ".jpg", cacheDir).apply {
            createNewFile()
            deleteOnExit()
        }
        tempCameraUri = FileProvider.getUriForFile(
            this,
            "$packageName.fileprovider",
            tempFile
        )
        tempCameraUri?.let { cameraLauncher.launch(it) }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.alunosLocais.collectLatest { lista ->
                localAdapter.submitList(lista.map { "${it.nome} (${it.email})" })
            }
        }

        lifecycleScope.launch {
            viewModel.alunosRemotos.collectLatest { lista ->
                remoteAdapter.submitList(lista.map { "${it.nome} (${it.email})" })
            }
        }
    }
}

// Adapter simples para renderizar as listas de texto
class SimpleAlunoAdapter : RecyclerView.Adapter<SimpleAlunoAdapter.ViewHolder>() {
    private var items = emptyList<String>()

    fun submitList(newItems: List<String>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false)
        return ViewHolder(view as TextView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = items[position]
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)
}