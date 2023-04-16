package com.copang.common.exception

enum class ErrorType(
    val errorCode: String,
    val message: String,
) {
    /**
     * 주문 관련 에러 : 30000~
     */
    ORDER_SERVER_ERROR("30000", "주문 서버 에러가 발생했습니다."),
    REQUEST_PARAMETER_ERROR("30001", "요청 파라미터가 잘못되었습니다."),
    NOT_EXIST_CART_ERROR("30002", "장바구니 정보가 존재하지 않습니다."),

    /**
     * 유저 관련 에러 : 31000~
     */
    NOT_EXIST_TOKEN_ERROR("31000", "유저 access token이 존재하지 않습니다."),
    NOT_EXIST_USER_INFO("31001", "유저 정보가 존재하지 않습니다."),
    AUTH_SERVER_ERROR("31002", "유저 정보 서버 api 호출에 실패했습니다."),

    /**
     * 상품 관련 에러 : 32000~
     */
    PRODUCT_SERVER_ERROR("32000", "상품 서버 api 호출에 실패했습니다."),
    NOT_EXIST_PRODUCT_ERROR("32001", "상품 정보를 찾을 수 없습니다."),
    PRODUCT_QUANTITY_EXCEEDED_ERROR("32002", "상품의 재고가 모자릅니다."),
    ;
}

