package com.copang.exception

enum class ErrorType(
    val errorCode: String,
    val message: String,
) {
    /**
     * 주문 관련 에러 : 30000~
     */


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
    ;
}

