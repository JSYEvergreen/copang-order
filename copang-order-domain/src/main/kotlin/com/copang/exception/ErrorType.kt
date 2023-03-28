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
    ;
}

