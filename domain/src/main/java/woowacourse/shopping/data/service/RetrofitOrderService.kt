package woowacourse.shopping.data.service

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import woowacourse.shopping.model.Order
import woowacourse.shopping.model.OrderList
import woowacourse.shopping.model.OrderResponse
import woowacourse.shopping.model.PostOrderRequest

interface RetrofitOrderService {
    @GET("cart-items/checkout")
    fun getOrderList(
        @Header("Authorization") token: String,
        @Query("ids") cartItemIds: String
    ): Call<OrderList>

    @GET("orders")
    fun getOrders(
        @Header("Authorization") token: String
    ): Call<OrderResponse>

    @GET("orders/{id}")
    fun getOrder(
        @Header("Authorization") token: String,
        @Path("id") id: Long
    ): Call<Order>

    @POST("orders")
    fun postOrder(
        @Header("Authorization") token: String,
        @Body request: PostOrderRequest
    ): Call<Unit>
}
