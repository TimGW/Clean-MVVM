package com.timgortworst.cleanarchitecture.domain.model.state

sealed class Result<T>(
    val data: T? = null,
    val error: ErrorEntity? = null
) {
    class Success<T>(data: T) : Result<T>(data)
    class Loading<T>(data: T? = null) : Result<T>(data)
    class Error<T>(error: ErrorEntity? = null, data: T? = null) : Result<T>(data, error)
}