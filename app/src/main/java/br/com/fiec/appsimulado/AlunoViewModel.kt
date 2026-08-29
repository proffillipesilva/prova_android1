package br.com.fiec.appsimulado

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AlunoViewModel(
    private val localRepository: AlunoLocalRepository,
    private val remoteRepository: AlunoRemoteRepository
) : ViewModel() {

    val alunos: StateFlow<List<Aluno>> = localRepository.alunos
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    init {
        refreshAlunos()
    }

    private fun refreshAlunos() {
        viewModelScope.launch {
            try {
                val remoteData = remoteRepository.getAlunos()
                localRepository.insertAll(remoteData)
            } catch (e: Exception) {
                // Log error or handle failure
            }
        }
    }

    fun adicionarAluno(nome: String, foto: String) {
        val novoAluno = Aluno(nome = nome, foto = foto)
        viewModelScope.launch {
            try {
                val savedAluno = remoteRepository.insertAluno(novoAluno)
                localRepository.insert(savedAluno)
            } catch (e: Exception) {
                localRepository.insert(novoAluno)
            }
        }
    }
}