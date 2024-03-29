package com.copang.repository.product

import com.copang.common.ApiResponse
import com.copang.common.exception.ErrorType
import com.copang.common.utils.ResponseEntityUtils
import com.copang.product.Product
import com.copang.product.ProductRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Repository
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange

@Repository
class ProductHttpRepository(
    private val copangRestTemplate: RestTemplate,
    @Value("\${copang.servers.product}") private val productServerUrl: String,
) : ProductRepository {

    override fun getProductsByIdsIn(productIds: List<Long>): List<Product> {
        val response: ProductsHttpResponse = ResponseEntityUtils.getOrThrows(
            className = "ProductHttpRepository",
            methodName = "getProductsByIdsIn",
            errorMessage = "상품 정보 조회 실패.",
            errorType = ErrorType.PRODUCT_SERVER_ERROR,
            apiCall = {
                copangRestTemplate.exchange<ApiResponse<ProductsHttpResponse>>(
                    "$productServerUrl/info/search",
                    HttpMethod.POST,
                    HttpEntity(
                        ProductsHttpRequest(
                            productIds = productIds,
                        )
                    ),
                )
            }
        )!!
        return response.content.map { it.toDomain() }
    }
}
