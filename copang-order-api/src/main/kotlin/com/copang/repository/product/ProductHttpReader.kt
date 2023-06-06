package com.copang.repository.product

import com.copang.product.Product
import com.copang.product.ProductReader
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Repository
import org.springframework.web.client.RestTemplate

@Repository
class ProductHttpReader(
    private val copangRestTemplate: RestTemplate,
    @Value("http://localhost:8080/product") private val productServerUrl: String,
) : ProductReader {

    override fun readAllIn(productIds: List<Long>): List<Product> {
//        val response: ProductsHttpResponse = ResponseEntityUtils.getOrThrows(
//            className = "ProductHttpRepository",
//            methodName = "getProductsByIdsIn",
//            errorMessage = "상품 정보 조회 실패.",
//            errorType = ErrorType.PRODUCT_SERVER_ERROR,
//            apiCall = {
//                copangRestTemplate.exchange<ApiResponse<ProductsHttpResponse>>(
//                    "$productServerUrl/info/search",
//                    HttpMethod.POST,
//                    HttpEntity(
//                        ProductsHttpRequest(
//                            productIds = productIds,
//                        )
//                    ),
//                )
//            }
//        )!!
//        return response.content.map { it.toDomain() }
        return listOf()
    }
}
