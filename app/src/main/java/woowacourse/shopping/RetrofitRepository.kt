package woowacourse.shopping

class RetrofitRepository {
    private val retrofitService = RetrofitClient.retrofitService

    fun getProducts(): List<Product> {
        return retrofitService.getProducts().execute().body()!!
    }

    fun getProduct(productId: Int): Product {
        return retrofitService.getProduct(productId).execute().body()!!
    }
}
