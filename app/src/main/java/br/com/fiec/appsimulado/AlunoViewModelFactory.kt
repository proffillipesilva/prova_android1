package br.com.fiec.appsimulado

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.fiec.appsimulado.ui.theme.AlunoViewModel

class AlunoViewModelFactory(
    private val localRepository: AlunoLocalRepository,
    private val remoteRepository: AlunoRemoteRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AlunoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AlunoViewModel(localRepository, remoteRepository) as T
        }
        throw IllegalArgumentException("Classe de ViewModel desconhecida: ${modelClass.name}")
    }
}