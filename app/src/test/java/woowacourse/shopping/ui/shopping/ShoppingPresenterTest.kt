package woowacourse.shopping.ui.shopping

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.data.repository.CartRepository
import woowacourse.shopping.data.repository.ProductRepository
import woowacourse.shopping.data.repository.RecentRepository
import woowacourse.shopping.mapper.toUIModel
import woowacourse.shopping.model.CartProduct
import woowacourse.shopping.model.CartProducts
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.RecentProduct

class ShoppingPresenterTest {

    private lateinit var view: ShoppingContract.View
    private lateinit var presenter: ShoppingContract.Presenter
    private lateinit var productRepository: ProductRepository
    private lateinit var recentRepository: RecentRepository
    private lateinit var cartRepository: CartRepository

    private val fakeProduct: Product = Product(
        1,
        "[사미헌] 갈비탕",
        12000,
        "https://search4.kakaocdn.net/argon/656x0_80_wr/KjhZ1Chrw9p"
    )

    private val fakeRecentProduct: RecentProduct = RecentProduct(
        1,
        "[사미헌] 갈비탕",
        12000,
        "https://search4.kakaocdn.net/argon/656x0_80_wr/KjhZ1Chrw9p"
    )

    private val fakeCartProduct: CartProduct = CartProduct(
        1,
        1,
        fakeProduct
    )

    private val fakeProducts = List(10) { fakeProduct }
    private val fakeRecentProducts = List(10) { fakeRecentProduct }
    private val fakeCartProducts = CartProducts(List(10) { fakeCartProduct })

    private val fakeCartCounts = fakeCartProducts.toUIModel()
        .associateBy { it.id }
        .mapValues { it.value.count }

    @Before
    fun setUp() {
        view = mockk()
        productRepository = mockk()
        recentRepository = mockk()
        cartRepository = mockk()
        presenter = ShoppingPresenter(view, productRepository, recentRepository, cartRepository)
    }

    @Test
    fun `초기 상품들을 세팅한다`() {
        // given
        mockCartGetAll()
        mockProductGetNext()
        every { recentRepository.getRecent(10) } returns fakeRecentProducts

        every { view.setRecentProducts(any()) } returns Unit
        every { view.addMoreProducts(any()) } returns Unit
        every { view.setCartProducts(any()) } returns Unit

        // when
        presenter.setUpProducts()

        // then
        verify(exactly = 1) {
            view.setRecentProducts(fakeRecentProducts.map { it.toUIModel() })
        }
        verify(exactly = 1) {
            view.addMoreProducts(fakeProducts.map { it.toUIModel() })
        }
        verify(exactly = 1) {
            view.setCartProducts(fakeCartCounts)
        }
    }

    @Test
    fun `상품들을 추가로 전달한다`() {
        // given
        mockProductGetNext()
        every { view.addMoreProducts(any()) } returns Unit

        // when
        presenter.setUpNextProducts()

        // then
        verify(exactly = 1) { view.addMoreProducts(fakeProducts.map { it.toUIModel() }) }
    }

    @Test
    fun `최근 본 상품들을 전달한다`() {
        // given
        every { recentRepository.getRecent(10) } returns fakeRecentProducts
        every { view.setRecentProducts(any()) } returns Unit

        // when
        presenter.setUpRecentProducts()

        // then
        verify(exactly = 1) {
            view.setRecentProducts(fakeRecentProducts.map { it.toUIModel() })
        }
    }

    @Test
    fun `상품들에 장바구니 숫자를 반영한다`() {
        // given
        mockCartGetAll()
        every { view.setCartProducts(any()) } returns Unit

        // when
        presenter.setUpCartCounts()

        // then
        verify(exactly = 1) {
            view.setCartProducts(fakeCartCounts)
        }
    }

    @Test
    fun `상품의 개수를 장바구니 저장한다`() {
        // given
        mockCartGetAll()
        mockProductGetNext()
        mockCartUpdateCountWithProductId()
        every { cartRepository.getTotalSelectedCount() } returns 10
        every { view.setToolbar(any()) } returns Unit

        // when
        presenter.updateItemCount(1, 10)

        // then
        verify(exactly = 1) { view.setToolbar(10) }
    }

    @Test
    fun `총 장바구니 상품 개수를 반영한다`() {
        // given
        mockCartGetAll()
        every { view.setCartProducts(any()) } returns Unit
        every { view.setToolbar(any()) } returns Unit

        // when
        presenter.setUpCartCounts()

        // then
        verify(exactly = 1) {
            view.setCartProducts(fakeCartCounts)
        }
    }

    @Test
    fun `선택한 상품의 상세페이지로 이동한다`() {
        // given
        mockProductFindById()
        every { view.navigateToProductDetail(any()) } returns Unit

        // when
        presenter.navigateToItemDetail(fakeProduct.toUIModel().id)

        // then
        verify(exactly = 1) { view.navigateToProductDetail(fakeProduct.toUIModel()) }
    }

    private fun mockProductGetNext() {
        val successSlot = slot<(Result<List<Product>>) -> Unit>()
        every {
            productRepository.getNext(any(), capture(successSlot))
        } answers {
            successSlot.captured.invoke(Result.success(fakeProducts))
        }
    }

    private fun mockProductFindById() {
        val successSlot = slot<(Result<Product>) -> Unit>()
        every {
            productRepository.findById(any(), capture(successSlot))
        } answers {
            successSlot.captured.invoke(Result.success(fakeProduct))
        }
    }

    private fun mockCartGetAll() {
        val successSlot = slot<(Result<CartProducts>) -> Unit>()
        every {
            cartRepository.getAll(capture(successSlot))
        } answers {
            successSlot.captured.invoke(Result.success(fakeCartProducts))
        }
    }

    private fun mockCartUpdateCountWithProductId() {
        val successSlot = slot<(Result<Int>) -> Unit>()
        every {
            cartRepository.updateCountWithProductId(1, any(), capture(successSlot))
        } answers {
            successSlot.captured.invoke(Result.success(10))
        }
    }
}
