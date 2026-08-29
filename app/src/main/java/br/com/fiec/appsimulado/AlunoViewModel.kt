package br.com.fiec.appsimulado

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AlunoViewModel(
    private val remoteRepo: AlunoRemoteRepository,
    private val localRepo: AlunoLocalRepository
) : ViewModel() {

    private val _alunosLocais = MutableStateFlow<List<AlunoEntity>>(emptyList())
    val alunosLocais: StateFlow<List<AlunoEntity>> = _alunosLocais.asStateFlow()

    private val _alunosRemotos = MutableStateFlow<List<Aluno>>(emptyList())
    val alunosRemotos: StateFlow<List<Aluno>> = _alunosRemotos.asStateFlow()

    fun carregarAlunosLocais() {
        viewModelScope.launch {
            localRepo.getAlunosLocais().collect { lista ->
                _alunosLocais.value = lista
            }
        }
    }

    fun carregarAlunosRemotos() {
        viewModelScope.launch {
            try {
                val response = remoteRepo.getAlunosRemotos()
                if (response.isSuccessful) {
                    _alunosRemotos.value = response.body() ?: emptyList()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun salvarLocal(aluno: AlunoEntity) {
        viewModelScope.launch {
            localRepo.salvarLocal(aluno)
        }
    }

    fun salvarRemoto(aluno: Aluno) {
        viewModelScope.launch {
            try {
                remoteRepo.salvarRemoto(aluno)
                carregarAlunosRemotos()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}