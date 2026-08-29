package br.com.fiec.appsimulado
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.fiec.appsimulado.Aluno
import br.com.fiec.appsimulado.AlunoLocalRepository
import br.com.fiec.appsimulado.AlunoRemoteRepository
import br.com.fiec.appsimulado.AlunoViewModel
import coil.compose.AsyncImage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Inicializa o banco de dados e a API
        val database = AppDatabase.getDatabase(this)
        val localRepository = AlunoLocalRepository(database.alunoDao())
        val remoteRepository = AlunoRemoteRepository(RetrofitClient.apiService)

        // 2. Passa os repositórios na ordem exata esperada pelo construtor da AlunoViewModel
        val viewModel = AlunoViewModel(localRepository, remoteRepository)

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TelaAlunos(viewModel)
                }
            }
        }
    }
}

@Composable
fun TelaAlunos(viewModel: AlunoViewModel) {
    val alunos by viewModel.alunosState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Button(
            onClick = {
                val novoAluno = Aluno(
                    nome = "Aluno Teste ${alunos.size + 1}",
                    foto = "https://via.placeholder.com/150"
                )
                viewModel.cadastrarAluno(novoAluno)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Adicionar Aluno de Teste")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(alunos) { aluno ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Row(modifier = Modifier.padding(16.dp)) {
                        AsyncImage(
                            model = aluno.foto,
                            contentDescription = "Foto do Aluno",
                            modifier = Modifier.size(50.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = aluno.nome,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        }
    }
}