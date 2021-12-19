package iuh.doancuoiki.api

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import iuh.doancuoiki.repository.Repository
import iuh.doancuoiki.views.APIViewModel

class APIViewModelFactory (
    private val repository : Repository
): ViewModelProvider.Factory{
    override fun<T : ViewModel?> create(modelClass: Class<T>) :T{
        return APIViewModel(repository) as T
    }
}