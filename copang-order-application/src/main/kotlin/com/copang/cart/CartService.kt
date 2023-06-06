package com.copang.cart

import com.copang.auth.UserInfo
import com.copang.common.exception.CopangException
import com.copang.common.exception.ErrorType
import com.copang.common.utils.AuthUtils
import com.copang.common.utils.SafeMap
import com.copang.common.utils.toSafeMap
import com.copang.product.Product
import com.copang.product.ProductReader
import org.springframework.stereotype.Service

@Service
class CartService(
    private val cartRepository: CartRepository,
    private val productReader: ProductReader,
) {
    // TODO : 파라미터로 buyerInfo 받기
    fun getAllCarts(): List<Cart> {
        val buyerInfo: UserInfo = AuthUtils.getUserInfo()
        val initialCart: List<Cart> = cartRepository.getAllActiveByBuyerId(buyerId = buyerInfo.id)
        if (initialCart.isEmpty()) {
            return emptyList()
        }

        val productIds = initialCart.map { it.product.id }
        val productsMap: SafeMap<Long, Product> = productReader.readAllIn(productIds).toSafeMap { it.id }
        return initialCart.map {
            it.filledOf(
                buyerInfo = buyerInfo,
                product = productsMap[it.product.id].quantityOf(quantity = it.product.quantity),
            )
        }
    }

    fun addCart(buyer: UserInfo, productId: Long, quantity: Int) {
        val existCart = cartRepository.getActiveCartByBuyerIdAndProductId(buyer.id, productId)
        if (existCart.isEmpty()) {
            val product = getProductCartAddable(productId, quantity)
            cartRepository.addCart(
                cart = Cart.empty().filledOf(
                    buyerInfo = buyer,
                    product = product.quantityOf(quantity),
                )
            )
            return
        }
        cartRepository.updateCart(
            cart = existCart.filledOf(
                buyerInfo = buyer,
                product = existCart.product.addQuantity(quantity),
            )
        )
    }

    private fun getProductCartAddable(productId: Long, quantity: Int): Product {
        val products: List<Product> = productReader.readAllIn(listOf(productId))
        if (products.isEmpty()) {
            throw CopangException(ErrorType.NOT_EXIST_PRODUCT_ERROR)
        }
        val product = products[0]
        if (product.quantity < quantity) {
            throw CopangException(
                errorType = ErrorType.PRODUCT_QUANTITY_EXCEEDED_ERROR,
                message = "상품의 재고가 모자라서 장바구니에 담을 수 없습니다.",
            )
        }
        return product
    }

    fun updateCart(buyer: UserInfo, cartId: Long, quantity: Int) {
        val existCart = cartRepository.getActiveByIdAndBuyerIdOrThrows(cartId = cartId, buyerId = buyer.id)
        getProductCartAddable(
            productId = existCart.product.id,
            quantity = quantity,
        )

        cartRepository.updateCart(
            cart = existCart.filledOf(
                buyerInfo = buyer,
                product = existCart.product.quantityOf(quantity)
            )
        )
    }

    fun deleteCart(buyer: UserInfo, cartId: Long) {
        val existCart = cartRepository.getActiveByIdAndBuyerIdOrThrows(cartId = cartId, buyerId = buyer.id)
        cartRepository.deleteCart(
            cart = existCart,
        )
    }

    fun createOrder(cart: Cart) {
        cartRepository.createOrder(cart)
    }
}
