package woowacourse.shopping

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RetrofitService {
    @POST("products/")
    fun getProducts(): Call<List<Product>>

    @GET("products/{productId}")
    fun getProduct(
        @Path("productId") productId: Int
    ): Call<Product>
}
