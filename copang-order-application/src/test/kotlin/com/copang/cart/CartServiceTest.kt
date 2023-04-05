package com.copang.cart

import com.copang.common.exception.CopangException
import com.copang.common.exception.ErrorType
import com.copang.mock.CartTestData
import com.copang.mock.ProductTestData
import com.copang.mock.UserInfoTestData
import com.copang.product.Product
import com.copang.product.ProductRepository
import com.copang.common.utils.AuthUtils
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
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
}
