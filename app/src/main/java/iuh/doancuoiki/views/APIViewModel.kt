package iuh.doancuoiki.views

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import iuh.doancuoiki.objects.Recommend
import iuh.doancuoiki.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class APIViewModel (private val repository: Repository) : ViewModel() {
    val myResponse : MutableLiveData<Response<List<Recommend>>> = MutableLiveData()
    fun getRecommend(){
        viewModelScope.launch {
            val response = repository.getRecommend()
            myResponse.value = response
        }
    }
}