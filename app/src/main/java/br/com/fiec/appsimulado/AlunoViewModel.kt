package br.com.fiec.appsimulado

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AlunoViewModel(
    private val remoteRepo: AlunoRemoteRepository,
    private val localRepo: AlunoLocalRepository
) : ViewModel() {

    private val _alunosLocais = MutableStateFlow<List<AlunoEntity>>(emptyList())
    val alunosLocais: StateFlow<List<AlunoEntity>> = _alunosLocais

    private val _alunosRemotos = MutableStateFlow<List<Aluno>>(emptyList())
    val alunosRemotos: StateFlow<List<Aluno>> = _alunosRemotos

    fun carregarAlunosLocais() {
        viewModelScope.launch {
            localRepo.listarTodos().collect { lista ->
                _alunosLocais.value = lista
            }
        }
    }

    fun carregarAlunosRemotos() {
        viewModelScope.launch {
            try {
                _alunosRemotos.value = remoteRepo.getAlunos()
            } catch (e: Exception) {
                _alunosRemotos.value = emptyList()
            }
        }
    }

    fun salvarLocal(aluno: AlunoEntity) {
        viewModelScope.launch {
            localRepo.inserir(aluno)
        }
    }

    fun salvarRemoto(aluno: Aluno) {
        viewModelScope.launch {
            try {
                remoteRepo.insertAluno(aluno)
                carregarAlunosRemotos()
            } catch (e: Exception) {
                // falha ao salvar remoto — pode expor um estado de erro se quiser
            }
        }
    }
}