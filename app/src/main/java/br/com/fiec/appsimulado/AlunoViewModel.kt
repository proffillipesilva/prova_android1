package com.example.alunoapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.alunoapp.data.repository.AlunoLocalRepository
import com.example.alunoapp.data.repository.AlunoRemoteRepository
import com.example.alunoapp.model.Aluno
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel que une o AlunoLocalRepository (Room) e o AlunoRemoteRepository
 * (Retrofit/API). A tela sempre observa os dados locais (fonte única de
 * verdade); sincronizarComServidor() busca a lista remota e grava no Room,
 * e adicionarAluno() salva local e tenta enviar pro servidor.
 */
class AlunoViewModel(
    private val localRepository: AlunoLocalRepository,
    private val remoteRepository: AlunoRemoteRepository
) : ViewModel() {

    private val _alunos = MutableStateFlow<List<Aluno>>(emptyList())
    val alunos: StateFlow<List<Aluno>> = _alunos.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _erro = MutableStateFlow<String?>(null)
    val erro: StateFlow<String?> = _erro.asStateFlow()

    init {
        observarAlunosLocais()
    }

    private fun observarAlunosLocais() {
        viewModelScope.launch {
            localRepository.getAlunos().collect { lista ->
                _alunos.value = lista
            }
        }
    }

    /** Busca a lista de alunos no servidor e atualiza o banco local (Room). */
    fun sincronizarComServidor() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val alunosRemotos = remoteRepository.getAlunos()
                localRepository.insertAlunos(alunosRemotos)
                _erro.value = null
            } catch (e: Exception) {
                _erro.value = "Erro ao buscar alunos do servidor: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    /** Salva localmente e tenta enviar o novo aluno para o servidor. */
    fun adicionarAluno(nome: String, curso: String, foto: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val novoAluno = Aluno(nome = nome, curso = curso, foto = foto)
            try {
                localRepository.insertAluno(novoAluno)
                val alunoSalvoNoServidor = remoteRepository.insertAluno(novoAluno)
                localRepository.insertAluno(alunoSalvoNoServidor)
                _erro.value = null
            } catch (e: Exception) {
                _erro.value = "Aluno salvo localmente, mas houve erro ao enviar ao servidor: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun limparErro() {
        _erro.value = null
    }
}

/** Factory necessária pois o AlunoViewModel recebe dependências no construtor. */
class AlunoViewModelFactory(
    private val localRepository: AlunoLocalRepository,
    private val remoteRepository: AlunoRemoteRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AlunoViewModel::class.java)) {
            return AlunoViewModel(localRepository, remoteRepository) as T
        }
        throw IllegalArgumentException("ViewModel desconhecida: ${modelClass.name}")
    }
}
