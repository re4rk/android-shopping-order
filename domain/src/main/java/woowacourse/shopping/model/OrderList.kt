package woowacourse.shopping.model

data class OrderList(
    val cartItems: List<CartProduct>,
    val totalPrice: Int,
    val currentPoints: Int,
    val earnedPoints: Int,
    val availablePoints: Int
)
