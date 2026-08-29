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

    // Fonte de dados única (Single Source of Truth) vinda do Room
    val alunos: StateFlow<List<Aluno>> = localRepository.alunos
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Sincroniza a lista remota com o banco de dados local
    fun fetchAlunos() {
        viewModelScope.launch {
            try {
                val remoteAlunos = remoteRepository.getAlunos()
                remoteAlunos.forEach { aluno ->
                    localRepository.insertAluno(aluno)
                }
            } catch (e: Exception) {
                // Tratar falha de conexão / erro da API
            }
        }
    }

    // Insere no servidor remoto e atualiza a base local
    fun insertAluno(aluno: Aluno) {
        viewModelScope.launch {
            try {
                val novoAluno = remoteRepository.insertAluno(aluno)
                localRepository.insertAluno(novoAluno)
            } catch (e: Exception) {
                // Tratar falha no envio
            }
        }
    }
}