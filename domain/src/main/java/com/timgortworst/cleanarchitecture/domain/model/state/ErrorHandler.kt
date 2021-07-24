package com.timgortworst.cleanarchitecture.domain.model.state

interface ErrorHandler {
    fun getError(throwable: Throwable): ErrorEntity
    fun getApiError(throwable: Throwable? = null, statusCode: Int, message: String): ErrorEntity
}