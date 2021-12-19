package iuh.doancuoiki.repository

import iuh.doancuoiki.api.RetrofitInstance
import retrofit2.Response
import iuh.doancuoiki.objects.Recommend

class Repository {
    suspend fun getRecommend(): Response<List<Recommend>> {
        return RetrofitInstance.api.getRecommend()
    }

}