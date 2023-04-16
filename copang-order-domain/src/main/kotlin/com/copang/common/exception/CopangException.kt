package com.copang.common.exception

import java.lang.RuntimeException

class CopangException(
    message: String = "",
    val errorCode: String = "",
    cause: Throwable? = null,
) : RuntimeException(message, cause) {

    constructor(
        errorType: ErrorType,
        cause: Throwable? = null,
    ) : this(
        message = errorType.message,
        errorCode = errorType.errorCode,
        cause = cause,
    )

    constructor(
        errorType: ErrorType,
        cause: Throwable? = null,
        message: String = "",
    ) : this(
        message = message.ifBlank { errorType.message },
        errorCode = errorType.errorCode,
        cause = cause,
    )
}
