package woowacourse.shoppoing.data.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.data.remoteDataSourceImpl.CartRemoteDataSourceImpl
import woowacourse.shopping.model.CartProduct
import woowacourse.shopping.utils.RetrofitUtil
import woowacourse.shopping.utils.mockWebServer.CartMockWeb

class CartRemoteDataSourceImplTest {
    private lateinit var mockWebServer: CartMockWeb
    private lateinit var remoteCartDataSource: CartRemoteDataSourceImpl

    @Before
    fun setUp() {
        // given
        mockWebServer = CartMockWeb()
        RetrofitUtil.url = mockWebServer.url
        remoteCartDataSource = CartRemoteDataSourceImpl()
    }

    @Test
    fun `장바구니 목록을 가져온다`() {
        // when
        var lock = true
        var cartProducts: List<CartProduct> = emptyList()
        remoteCartDataSource.getAll { result ->
            result.onSuccess { cartProducts = it }
                .onFailure { e -> throw e }
            lock = false
        }
        while (lock) { Thread.sleep(100) }

        // then
        assertThat(cartProducts).hasSize(7)
        cartProducts.forEachIndexed { i, it ->
            assertThat(it.id).isEqualTo(i)
            assertThat(it.quantity).isEqualTo(i + 1)
            assertThat(it.product.name).isEqualTo("치킨$i")
            assertThat(it.product.price).isEqualTo(10000)
            assertThat(it.product.imageUrl).isEqualTo(
                "https://search4.kakaocdn.net/argon/656x0_80_wr/KjhZ1Chrw9p"
            )
        }
    }

    @Test
    fun `장바구니에 상품을 추가한다`() {
        // when
        var lock = true
        var count: Int? = null
        remoteCartDataSource.postItem(1) { result ->
            result.onSuccess { count = 1 }
                .onFailure { e -> throw e }
            lock = false
        }
        while (lock) { Thread.sleep(100) }

        // then
        assertThat(count).isEqualTo(1)
    }

    @Test
    fun `장바구니의 상품 개수를 변경한다`() {
        // when
        var lock = true
        var count: Int? = null
        remoteCartDataSource.patchItemQuantity(1, 10) { result ->
            result.onSuccess { count = 10 }
                .onFailure { e -> throw e }
            lock = false
        }
        while (lock) { Thread.sleep(100) }

        // then
        assertThat(count).isEqualTo(10)
    }

    @Test
    fun `장바구니에서 상품을 제거한다`() {
        // when
        var lock = true
        var count: Int? = null
        remoteCartDataSource.deleteItem(1) { result ->
            result.onSuccess { count = 0 }
                .onFailure { e -> throw e }
            lock = false
        }
        while (lock) { Thread.sleep(100) }

        // then
        assertThat(count).isEqualTo(0)
    }
}
