package br.com.fiec.appsimulado

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AlunoViewModel(
    private val localRepository: AlunoLocalRepository,
    private val remoteRepository: AlunoRemoteRepository
) : ViewModel() {

    // Estado que gerencia a lista de alunos exibida na tela do Jetpack Compose
    private val _alunosState = MutableStateFlow<List<Aluno>>(emptyList())
    val alunosState: StateFlow<List<Aluno>> = _alunosState.asStateFlow()

    // Estado para monitorar se o app está carregando dados
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        carregarAlunos()
    }

    // Função que busca dados tanto da API quanto do Banco Local
    fun carregarAlunos() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // 1. Tenta buscar da API (Remoto)
                val alunosRemotos = remoteRepository.fetchAlunos()

                if (alunosRemotos.isNotEmpty()) {
                    _alunosState.value = alunosRemotos

                    // Sincroniza salvando os dados da API no Banco de Dados Local (Room)
                    alunosRemotos.forEach { aluno ->
                        localRepository.insertAluno(aluno)
                    }
                }
            } catch (e: Exception) {
                // 2. Se a API falhar (ex: sem internet), busca o que estiver salvo localmente no Room
                val alunosLocais = localRepository.getAlunos()
                _alunosState.value = alunosLocais
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Função para inserir um aluno em ambos os locais
    fun cadastrarAluno(aluno: Aluno) {
        viewModelScope.launch {
            try {
                // Salva na API
                remoteRepository.insertAlunoRemote(aluno)
            } catch (e: Exception) {
                // Tratar erro de rede se necessário
            } finally {
                // Sempre salva localmente para garantir a persistência offline
                localRepository.insertAluno(aluno)
                // Atualiza a lista da tela
                carregarAlunos()
            }
        }
    }
}