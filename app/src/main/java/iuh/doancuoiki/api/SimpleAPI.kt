package iuh.doancuoiki.api

import iuh.doancuoiki.objects.Recommend
import retrofit2.Response
import retrofit2.http.GET

interface SimpleAPI {
    @GET("/")
    suspend fun getRecommend(): Response<List<Recommend>>
}