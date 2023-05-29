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
            content = productsHttpRequest.productIds.map {
                ProductsBodyHttpResponse(
                    productId = it,
                    productCode = "ABC$it",
                    productName = "My Product$it",
                    productDescription = "This is a sample product$it",
                    productInformation = "Some product information$it",
                    productQuantity = 10,
                    productCost = 5000,
                    productIsSale = true,
                    productSellerId = 5678
                )
            }
        )
    )
}
