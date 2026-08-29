package br.com.fiec.appsimulado

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Extra (não pedido, mas necessário): como AlunoViewModel tem parâmetros no
 * construtor, precisa de uma Factory pra ser criado com "by viewModels { ... }".
 */
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
