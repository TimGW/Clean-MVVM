package com.timgortworst.cleanarchitecture.domain.model.state

// TODO refactor to result class
sealed class ErrorEntity {
    data class DatabaseError(val throwable: Throwable? = null) : ErrorEntity()
    data class IOError(val throwable: Throwable? = null) : ErrorEntity()
    data class HttpError(val throwable: Throwable? = null, val statusCode: Int) : ErrorEntity()
    data class Unknown(val throwable: Throwable? = null) : ErrorEntity()
}