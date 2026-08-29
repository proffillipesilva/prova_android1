package br.com.fiec.appsimulado

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class AlunoViewModel(
    private val localRepository: AlunoLocalRepository,
    private val remoteRepository: AlunoRemoteRepository
) : ViewModel() {

    private val _alunos = MutableLiveData<List<Aluno>>()
    val alunos: LiveData<List<Aluno>> = _alunos

    fun carregarAlunos() {
        viewModelScope.launch {
            try {
                // Tenta carregar da API remota
                val alunosRemotos = remoteRepository.getAlunos()
                _alunos.value = alunosRemotos

                // Cache local no Room
                alunosRemotos.forEach { localRepository.insertAluno(it) }
            } catch (e: Exception) {
                // Em caso de falha na rede, recupera do Room local
                val alunosLocais = localRepository.getAlunos()
                _alunos.value = alunosLocais
            }
        }
    }

    fun salvarAluno(aluno: Aluno) {
        viewModelScope.launch {
            try {
                remoteRepository.insertAluno(aluno)
                localRepository.insertAluno(aluno)
                carregarAlunos()
            } catch (e: Exception) {
                localRepository.insertAluno(aluno)
            }
        }
    }
}