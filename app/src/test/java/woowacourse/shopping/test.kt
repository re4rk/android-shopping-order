package woowacourse.shopping

import org.junit.Test

class test {
    @Test
    fun testGetProducts() {
        val retrofitRepository = RetrofitRepository()
        val products = retrofitRepository.getProducts()
        println(products)
    }

    @Test
    fun testGetProduct() {
        val retrofitRepository = RetrofitRepository()
        val product = retrofitRepository.getProduct(1)
        println(product)
    }
}
