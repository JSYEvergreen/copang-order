package com.copang.cart

import com.copang.common.exception.CopangException
import com.copang.common.exception.ErrorType
import com.copang.common.utils.AuthUtils
import com.copang.mock.CartTestData
import com.copang.mock.ProductTestData
import com.copang.mock.UserInfoTestData
import com.copang.product.Product
import com.copang.product.ProductRepository
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.matchers.shouldBe
import io.mockk.*
import org.junit.jupiter.api.Test

class CartServiceTest {
    private val cartRepository = mockk<CartRepository>()
    private val productRepository = mockk<ProductRepository>()
    private val sut = CartService(cartRepository, productRepository)

    @Test
    fun `전체 카트 정보를 가져올 때 유저 정보가 없으면 에러가 발생한다`() {
        // given
        AuthUtils.clearUserInfo()

        // when
        val actualException = shouldThrowExactly<CopangException> { sut.getAllCarts() }

        // then
        actualException.errorCode shouldBe ErrorType.NOT_EXIST_USER_INFO.errorCode
        actualException.message shouldBe ErrorType.NOT_EXIST_USER_INFO.message
    }

    @Test
    fun `전체 카트 정보를 가져올 때 카트가 비어있으면 상품 정보 가져오지 않고 빈 리스트가 응답된다`() {
        // given
        val givenUserInfo = UserInfoTestData.userInfo()
        AuthUtils.setUserInfo(givenUserInfo)
        every { cartRepository.getAllActiveByBuyerId(any()) } returns emptyList()

        // when
        val actualResult: List<Cart> = sut.getAllCarts()

        // then
        actualResult shouldBe emptyList()
        verify(exactly = 1) { cartRepository.getAllActiveByBuyerId(any()) }
        verify(exactly = 0) { productRepository.getProductsByIdsIn(any()) }
    }

    @Test
    fun `전체 카트 정보를 가져올 때 유저 정보와 카트 정보, 상품 정보를 취합해서 응답한다`() {
        // given
        val givenUserInfo = UserInfoTestData.userInfo()
        AuthUtils.setUserInfo(givenUserInfo)

        val givenCarts: List<Cart> = listOf(
            CartTestData.cart(
                id = 1L,
                buyerInfo = givenUserInfo,
                product = ProductTestData.product(id = 1L, quantity = 2)
            ),
            CartTestData.cart(
                id = 2L,
                buyerInfo = givenUserInfo,
                product = ProductTestData.product(id = 2L, quantity = 3)
            )
        )
        every { cartRepository.getAllActiveByBuyerId(any()) } returns givenCarts

        val givenProducts: List<Product> = listOf(
            ProductTestData.product(id = 1L, code = "code1", quantity = 2),
            ProductTestData.product(id = 2L, code = "code2", quantity = 3),
        )
        every { productRepository.getProductsByIdsIn(any()) } returns givenProducts

        // when
        val actualResult: List<Cart> = sut.getAllCarts()

        // then
        actualResult.size shouldBe 2
        verify(exactly = 1) { cartRepository.getAllActiveByBuyerId(any()) }
        verify(exactly = 1) { productRepository.getProductsByIdsIn(any()) }
    }

    @Test
    fun `장바구니에 상품을 추가하는데 존재하지 않는 상품이라면 에러가 발생한다`() {
        // given
        val givenUserInfo = UserInfoTestData.userInfo()
        val givenProductId = 1L
        val givenQuantity = 200
        every {
            cartRepository.getActiveCartByBuyerIdAndProductId(
                buyerId = givenUserInfo.id,
                productId = givenProductId,
            )
        } returns Cart.empty()

        every { productRepository.getProductsByIdsIn(any()) } returns emptyList()

        // when
        val actualThrownException = shouldThrowExactly<CopangException> {
            sut.addCart(
                buyer = givenUserInfo,
                productId = givenProductId,
                quantity = givenQuantity,
            )
        }

        // then
        verify(exactly = 1) { cartRepository.getActiveCartByBuyerIdAndProductId(any(), any()) }
        verify(exactly = 1) { productRepository.getProductsByIdsIn(any()) }
        verify(exactly = 0) { cartRepository.addCart(any()) }

        actualThrownException.errorCode shouldBe ErrorType.NOT_EXIST_PRODUCT_ERROR.errorCode
    }

    @Test
    fun `장바구니에 상품을 추가하는데 상품의 재고보다 더 많은 개수만큼 추가하려고 하면 에러가 발생한다`() {
        // given
        val givenUserInfo = UserInfoTestData.userInfo()
        val givenProductId = 1L
        val givenQuantity = 200
        every {
            cartRepository.getActiveCartByBuyerIdAndProductId(
                buyerId = givenUserInfo.id,
                productId = givenProductId,
            )
        } returns Cart.empty()

        val givenProducts: List<Product> = listOf(
            ProductTestData.product(id = 1L, code = "code1", quantity = givenQuantity - 10),
        )
        every { productRepository.getProductsByIdsIn(any()) } returns givenProducts

        // when
        val actualThrownException = shouldThrowExactly<CopangException> {
            sut.addCart(
                buyer = givenUserInfo,
                productId = givenProductId,
                quantity = givenQuantity,
            )
        }

        // then
        verify(exactly = 1) { cartRepository.getActiveCartByBuyerIdAndProductId(any(), any()) }
        verify(exactly = 1) { productRepository.getProductsByIdsIn(any()) }
        verify(exactly = 0) { cartRepository.addCart(any()) }

        actualThrownException.errorCode shouldBe ErrorType.PRODUCT_QUANTITY_EXCEEDED_ERROR.errorCode
    }

    @Test
    fun `장바구니에 상품을 추가하는데 기존 장바구니에 해당 상품이 없다면 주어진 개수만큼 상품을 추가한다`() {
        // given
        val givenUserInfo = UserInfoTestData.userInfo()
        val givenProductId = 1L
        val givenQuantity = 2
        every {
            cartRepository.getActiveCartByBuyerIdAndProductId(
                buyerId = givenUserInfo.id,
                productId = givenProductId,
            )
        } returns Cart.empty()

        val givenProducts: List<Product> = listOf(
            ProductTestData.product(id = 1L, code = "code1", quantity = 2),
        )
        every { productRepository.getProductsByIdsIn(any()) } returns givenProducts
        every { cartRepository.addCart(any()) } just Runs

        // when
        sut.addCart(
            buyer = givenUserInfo,
            productId = givenProductId,
            quantity = givenQuantity,
        )

        // then
        verify(exactly = 1) { cartRepository.getActiveCartByBuyerIdAndProductId(any(), any()) }
        verify(exactly = 1) { productRepository.getProductsByIdsIn(any()) }

        val capturingSlog = slot<Cart>()
        verify(exactly = 1) { cartRepository.addCart(cart = capture(capturingSlog)) }
        val cart: Cart = capturingSlog.captured
        cart.product.quantity shouldBe givenQuantity
    }

    @Test
    fun `장바구니에 상품을 추가하는데 이미 장바구니에 같은 상품이 있다면 장바구니에 담긴 상품 개수를 늘린다`() {
        // given
        val givenUserInfo = UserInfoTestData.userInfo()
        val givenProductId = 1L
        val givenQuantity = 2
        val givenExistCart = CartTestData.cart(
            id = 1L,
            product = ProductTestData.product(id = 1L, quantity = 1)
        )
        every {
            cartRepository.getActiveCartByBuyerIdAndProductId(
                buyerId = givenUserInfo.id,
                productId = givenProductId,
            )
        } returns givenExistCart

        every { cartRepository.updateCart(any()) } just Runs

        // when
        sut.addCart(
            buyer = givenUserInfo,
            productId = givenProductId,
            quantity = givenQuantity,
        )

        // then
        verify(exactly = 1) { cartRepository.getActiveCartByBuyerIdAndProductId(any(), any()) }

        val capturingSlog = slot<Cart>()
        verify(exactly = 1) { cartRepository.updateCart(cart = capture(capturingSlog)) }
        val cart: Cart = capturingSlog.captured
        cart.product.quantity shouldBe givenExistCart.product.quantity + givenQuantity
    }
}
