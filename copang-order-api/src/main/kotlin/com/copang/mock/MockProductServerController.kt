package com.copang.mock

import com.copang.common.ApiResponse
import com.copang.repository.product.ProductsBodyHttpResponse
import com.copang.repository.product.ProductsHttpRequest
import com.copang.repository.product.ProductsHttpResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@RestController
class MockProductServerController {
    @PostMapping("/product/info/search")
    fun foo(@RequestBody productsHttpRequest: ProductsHttpRequest) = ApiResponse.success(
        ProductsHttpResponse(
            content = listOf(
                ProductsBodyHttpResponse(
                    productId = 1234,
                    productCode = "ABC123",
                    productName = "My Product",
                    productDescription = "This is a sample product",
                    productInformation = "Some product information",
                    productQuantity = 10,
                    productCost = 5000,
                    productIsSale = true,
                    productSellerId = 5678
                ),
            )
        )
    )
}
