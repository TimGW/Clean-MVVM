package com.timgortworst.cleanarchitecture.domain.model.state

sealed class Result<T>(
    val data: T? = null,
    val error: ErrorType? = null
) {
    class Success<T>(data: T) : Result<T>(data)
    class Loading<T>(data: T? = null) : Result<T>(data)
    class Error<T>(error: ErrorType? = null, data: T? = null) : Result<T>(data, error)

    sealed class ErrorType {
        data class DatabaseError(val throwable: Throwable? = null) : ErrorType()
        data class IOError(val throwable: Throwable? = null) : ErrorType()
        data class HttpError(val throwable: Throwable? = null, val statusCode: Int) : ErrorType()
        data class Unknown(val throwable: Throwable? = null) : ErrorType()
    }
}