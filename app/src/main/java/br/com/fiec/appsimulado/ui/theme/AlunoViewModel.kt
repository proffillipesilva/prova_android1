package br.com.fiec.appsimulado.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiec.appsimulado.Aluno
import br.com.fiec.appsimulado.AlunoLocalRepository
import br.com.fiec.appsimulado.AlunoRemoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AlunoViewModel(
    private val localRepository: AlunoLocalRepository,
    private val remoteRepository: AlunoRemoteRepository
) : ViewModel() {

    val alunos: StateFlow<List<Aluno>> = localRepository.getAlunos()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _uiState = MutableStateFlow<AlunoUiState>(AlunoUiState.Idle)
    val uiState: StateFlow<AlunoUiState> = _uiState

    init {
        sincronizarAlunos()
    }

    fun sincronizarAlunos() {
        viewModelScope.launch {
            _uiState.value = AlunoUiState.Loading
            remoteRepository.getAlunos()
                .onSuccess { alunosRemotos ->
                    alunosRemotos.forEach { localRepository.insertAluno(it) }
                    _uiState.value = AlunoUiState.Idle
                }
                .onFailure { erro ->
                    _uiState.value = AlunoUiState.Error(erro.message ?: "Erro ao sincronizar alunos")
                }
        }
    }

    fun insertAluno(aluno: Aluno) {
        viewModelScope.launch {
            localRepository.insertAluno(aluno)

            remoteRepository.insertAluno(aluno)
                .onFailure { erro ->
                    _uiState.value = AlunoUiState.Error(
                        erro.message ?: "Erro ao enviar aluno para o servidor"
                    )
                }
        }
    }
}

sealed class AlunoUiState {
    object Idle : AlunoUiState()
    object Loading : AlunoUiState()
    data class Error(val mensagem: String) : AlunoUiState()
}