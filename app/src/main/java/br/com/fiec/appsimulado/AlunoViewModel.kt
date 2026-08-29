package br.com.fiec.appsimulado

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AlunoViewModel(
    private val remoteRepository: AlunoRemoteRepository,
    private val localRepository: AlunoLocalRepository
) : ViewModel() {

    private val _alunosLocais = MutableStateFlow<List<AlunoEntity>>(emptyList())
    val alunosLocais: StateFlow<List<AlunoEntity>> = _alunosLocais.asStateFlow()

    private val _alunosRemotos = MutableStateFlow<List<Aluno>>(emptyList())
    val alunosRemotos: StateFlow<List<Aluno>> = _alunosRemotos.asStateFlow()

    fun carregarAlunosLocais() {
        viewModelScope.launch {
            _alunosLocais.value = localRepository.getAlunosEntities()
        }
    }

    fun carregarAlunosRemotos() {
        viewModelScope.launch {
            try {
                _alunosRemotos.value = remoteRepository.getAlunos()
            } catch (e: Exception) {
                _alunosRemotos.value = emptyList()
            }
        }
    }

    fun salvarLocal(aluno: AlunoEntity) {
        viewModelScope.launch {
            localRepository.insertEntity(aluno)
            carregarAlunosLocais()
        }
    }

    fun salvarRemoto(aluno: Aluno) {
        viewModelScope.launch {
            try {
                remoteRepository.insertAluno(aluno)
            } catch (e: Exception) {
                // sem internet ou API fora do ar — ainda assim recarrega a lista
            }
            carregarAlunosRemotos()
        }
    }
}