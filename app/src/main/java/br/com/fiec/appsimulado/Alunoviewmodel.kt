package br.com.fiec.appsimulado

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AlunoViewModel(
    private val localRepository: AlunoLocalRepository,
    private val remoteRepository: AlunoRemoteRepository
) : ViewModel() {

    private val _alunosLocais = MutableStateFlow<List<Aluno>>(emptyList())
    val alunosLocais: StateFlow<List<Aluno>> = _alunosLocais

    private val _alunosRemotos = MutableStateFlow<List<Aluno>>(emptyList())
    val alunosRemotos: StateFlow<List<Aluno>> = _alunosRemotos

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    /** O Room é reativo: essa coleta fica "ligada" e a lista se atualiza sozinha a cada insert. */
    fun carregarAlunosLocais() {
        viewModelScope.launch {
            localRepository.getAlunos().collectLatest { lista ->
                _alunosLocais.value = lista
            }
        }
    }

    /** A API não é reativa como o Room, por isso precisa buscar sob demanda. */
    fun carregarAlunosRemotos() {
        viewModelScope.launch {
            try {
                _alunosRemotos.value = remoteRepository.getAlunos()
            } catch (e: Exception) {
                _error.value = "Erro ao buscar alunos remotos: ${e.message}"
            }
        }
    }

    fun salvarLocal(aluno: AlunoEntity) {
        viewModelScope.launch {
            try {
                localRepository.insertAluno(aluno.toAluno())
            } catch (e: Exception) {
                _error.value = "Erro ao salvar aluno local: ${e.message}"
            }
        }
    }

    fun salvarRemoto(aluno: Aluno) {
        viewModelScope.launch {
            try {
                remoteRepository.insertAluno(aluno)
                carregarAlunosRemotos() // recarrega pra já vir com o item novo
            } catch (e: Exception) {
                _error.value = "Erro ao salvar aluno remoto: ${e.message}"
            }
        }
    }
}