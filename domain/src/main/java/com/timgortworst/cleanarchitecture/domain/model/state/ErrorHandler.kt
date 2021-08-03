package com.timgortworst.cleanarchitecture.domain.model.state

interface ErrorHandler {
    fun getError(throwable: Throwable): ErrorEntity
    fun getApiError(statusCode: Int, throwable: Throwable? = null): ErrorEntity
}