package br.com.fiec.appsimulado

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiec.appsimulado.ui.theme.Aluno
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
            localRepo.alunos.collect { lista ->
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
            localRepo.salvar(aluno)
        }
    }

    fun salvarRemoto(aluno: Aluno) {
        viewModelScope.launch {
            try {
                remoteRepo.salvar(aluno)
                carregarAlunosRemotos()
            } catch (e: Exception) {
                // ignora, app não deve crashar por causa da API fake
            }
        }
    }
}