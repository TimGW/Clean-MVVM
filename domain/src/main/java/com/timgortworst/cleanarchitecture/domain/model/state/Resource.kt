package com.timgortworst.cleanarchitecture.domain.model.state

sealed class Resource<T>(
    val data: T? = null,
    val error: ErrorEntity? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(error: ErrorEntity? = null, data: T? = null) : Resource<T>(data, error)
}