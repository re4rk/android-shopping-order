package woowacourse.shopping.ui.cart

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import woowacourse.shopping.data.repository.CartRepository
import woowacourse.shopping.data.repository.ProductRepository
import woowacourse.shopping.mapper.toUIModel
import woowacourse.shopping.model.CartProduct
import woowacourse.shopping.model.PageUIModel
import woowacourse.shopping.model.Product

class CartPresenterTest {

    private lateinit var view: CartContract.View
    private lateinit var presenter: CartContract.Presenter
    private lateinit var cartRepository: CartRepository
    private lateinit var productRepository: ProductRepository

    private val fakeProduct = Product(
        1,
        "aa",
        1,
        "https://search4.kakaocdn.net/argon/656x0_80_wr/KjhZ1Chrw9p"
    )

    private val fakeCartProduct = CartProduct(
        1,
        1,
        fakeProduct
    )

    private val fakeCartProducts = List(10) { fakeCartProduct }

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        view = mockk()
        cartRepository = mockk(relaxed = true)
        productRepository = mockk(relaxed = true)
        every { cartRepository.getPage(any(), any()) } answers { Result.success(fakeCartProducts) }

        presenter = CartPresenter(view, cartRepository)
    }

    @Test
    fun `화면의 초기값들을 설정한다`() {
        // given
        every { cartRepository.getTotalPrice() } returns 12000
        every { cartRepository.getTotalCheckedCount() } returns 10
        every { cartRepository.hasNextPage() } returns true
        every { cartRepository.hasPrevPage() } returns true

        every { view.setPage(any(), any()) } answers { nothing }

        // when
        presenter.fetchCartProducts()

        // then
        verify(exactly = 1) {
            view.setPage(
                fakeCartProducts.toUIModel(),
                PageUIModel(pageNext = true, pagePrev = true, pageNumber = 1)
            )
        }
        assertEquals(presenter.totalPrice.value, 12000)
        assertEquals(presenter.checkedCount.value, 10)
        assertEquals(presenter.allCheck.value, true)
    }

    @Test
    fun `현재 페이지의 상품들을 모두 선택한다`() {
        // given
        every { cartRepository.getTotalPrice() } returns 12000
        every { cartRepository.getTotalCheckedCount() } returns 10
        every { cartRepository.hasNextPage() } returns true
        every { cartRepository.hasPrevPage() } returns true
        every { cartRepository.updateAllChecked(any()) } answers { nothing }

        every { view.setPage(any(), any()) } answers { nothing }

        // when
        presenter.fetchCartProducts()
        presenter.setUpProductsCheck(true)

        // then
        verify(exactly = 1) {
            cartRepository.updateAllChecked(any())
        }
        verify(exactly = 1) {
            view.setPage(
                fakeCartProducts.toUIModel(),
                PageUIModel(pageNext = true, pagePrev = true, pageNumber = 1)
            )
        }
    }

    @Test
    fun `다음 페이지로 넘어가면 새로 불러온다`() {
        // given
        every { cartRepository.getTotalPrice() } returns 12000
        every { cartRepository.getTotalCheckedCount() } returns 10
        every { cartRepository.hasNextPage() } returns true
        every { cartRepository.hasPrevPage() } returns true

        every { view.setPage(any(), any()) } answers { nothing }

        // when
        presenter.fetchCartProducts()
        presenter.moveToPageNext()

        // then
        verify(exactly = 1) {
            view.setPage(
                fakeCartProducts.toUIModel(),
                PageUIModel(pageNext = true, pagePrev = true, pageNumber = 2)
            )
        }
        assertEquals(presenter.allCheck.value, true)
    }

    @Test
    fun `이전 페이지로 가면 새로 불러온다`() {
        // given
        every { cartRepository.getTotalPrice() } returns 12000
        every { cartRepository.getTotalCheckedCount() } returns 10
        every { cartRepository.hasNextPage() } returns true
        every { cartRepository.hasPrevPage() } returns true

        every { view.setPage(any(), any()) } answers { nothing }

        // when
        presenter.fetchCartProducts()
        presenter.moveToPagePrev()

        // then
        verify(exactly = 1) {
            view.setPage(
                fakeCartProducts.toUIModel(),
                PageUIModel(pageNext = true, pagePrev = true, pageNumber = 0)
            )
        }
        assertEquals(presenter.allCheck.value, true)
    }

    @Test
    fun `아이템의 개수를 변경한다`() {
        // given
        every { cartRepository.updateCountWithProductId(any(), any()) }
            .answers { Result.success(10) }

        every { cartRepository.hasNextPage() } returns true
        every { cartRepository.hasPrevPage() } returns true
        every { cartRepository.getTotalPrice() } returns 12000
        every { cartRepository.getTotalCheckedCount() } returns 10
        every { view.setPage(any(), any()) } answers { nothing }

        // when
        presenter.fetchCartProducts()
        presenter.updateItemCount(1, 0)

        // then
        verify(exactly = 2) { view.setPage(any(), any()) }
    }

    @Test
    fun `아이템의 체크를 변경한다`() {
        // given
        every { cartRepository.updateChecked(any(), any()) } returns Unit
        every { cartRepository.hasNextPage() } returns true
        every { cartRepository.hasPrevPage() } returns true
        every { cartRepository.getTotalPrice() } returns 12000
        every { cartRepository.getTotalCheckedCount() } returns 10
        every { view.setPage(any(), any()) } answers { nothing }

        // when
        presenter.fetchCartProducts()
        presenter.updateItemCheck(1, true)

        // then
        verify(exactly = 1) { cartRepository.updateChecked(1, true) }
        verify(exactly = 1) { view.setPage(any(), any()) }
    }

    @Test
    fun `장바구니에 담긴 상품을 삭제한다`() {
        // given
        every {
            cartRepository.updateCountWithProductId(any(), any())
        } answers { Result.success(0) }
        every { cartRepository.hasNextPage() } returns true
        every { cartRepository.hasPrevPage() } returns true
        every { cartRepository.getTotalPrice() } returns 12000
        every { cartRepository.getTotalCheckedCount() } returns 10
        every { view.setPage(any(), any()) } answers { nothing }

        // when
        presenter.fetchCartProducts()
        presenter.updateItemCount(1, 0)

        // then
        verify(exactly = 2) { view.setPage(any(), any()) }
    }

    @Test
    fun `상세 페이지로 이동한다`() {
        every { cartRepository.getAll() } answers { Result.success(fakeCartProducts) }

        every { view.navigateToItemDetail(any()) } answers { nothing }

        // when
        presenter.navigateToItemDetail(fakeProduct.toUIModel().id)

        // then
        verify(exactly = 1) { view.navigateToItemDetail(fakeProduct.toUIModel().id) }
    }

    @Test
    fun `현재 페이지 인덱스를 반환한다`() {
        // given
        val index = 5

        // when
        val presenter = CartPresenter(view, cartRepository, index)

        // then
        assertEquals(presenter.getPageIndex(), index)
    }
}
