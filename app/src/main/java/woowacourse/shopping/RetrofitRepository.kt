package woowacourse.shopping

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object RetrofitRepository {
    private val retrofitService = RetrofitClient.retrofitService

    fun getProducts(callback: (Result<List<Product>>) -> Unit) {
        retrofitService.getProducts().enqueue(
            object : Callback<List<Product>> {
                override fun onResponse(
                    call: Call<List<Product>>,
                    response: Response<List<Product>>
                ) {
                    if (response.isSuccessful) {
                        val body = response.body()
                        if (body != null) {
                            callback(Result.success(body))
                        } else {
                            callback(Result.failure(Throwable("body is null!")))
                        }
                    } else {
                        callback(Result.failure(Throwable("Is not successful")))
                    }
                }

                override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                    callback(Result.failure(t))
                }
            }
        )
    }

    fun getProduct(productId: Int): Product {
        return retrofitService.getProduct(productId).execute().body()!!
    }
}
