package br.com.fiec.appsimulado

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

class MainActivity : ComponentActivity() {

    private val viewModel: AlunoViewModel by viewModels {
        val database = AppDatabase.getDatabase(applicationContext)
        val localRepository = AlunoLocalRepository(database.alunoDao())
        val remoteRepository = AlunoRemoteRepository(RetrofitClient.alunoApiService)
        AlunoViewModelFactory(localRepository, remoteRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AlunoFormScreen(viewModel)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlunoFormScreen(viewModel: AlunoViewModel) {
    var nome by remember { mutableStateOf("") }
    var fotoUri by remember { mutableStateOf<Uri?>(null) }
    val alunos by viewModel.alunos.collectAsState()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        fotoUri = uri
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Cadastro de Alunos") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = nome,
                onValueChange = { nome = it },
                label = { Text("Nome do Aluno") },
                modifier = Modifier.fillMaxWidth()
            )

            if (fotoUri != null) {
                AsyncImage(
                    model = fotoUri,
                    contentDescription = "Preview da Foto",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Button(
                onClick = {
                    launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Selecionar Foto")
            }

            Button(
                onClick = {
                    if (nome.isNotBlank() && fotoUri != null) {
                        viewModel.adicionarAluno(nome, fotoUri.toString())
                        nome = ""
                        fotoUri = null
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = nome.isNotBlank() && fotoUri != null
            ) {
                Text("Salvar Aluno")
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(alunos) { aluno ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            AsyncImage(
                                model = aluno.foto,
                                contentDescription = "Foto de ${aluno.nome}",
                                modifier = Modifier.size(64.dp),
                                contentScale = ContentScale.Crop
                            )
                            Column {
                                Text(text = aluno.nome, style = MaterialTheme.typography.titleMedium)
                                Text(text = "ID: ${aluno.id}", style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                }
            }
        }
    }
}