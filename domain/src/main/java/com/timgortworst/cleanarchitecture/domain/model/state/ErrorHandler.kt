package com.timgortworst.cleanarchitecture.domain.model.state

interface ErrorHandler {
    fun getError(throwable: Throwable): ErrorEntity
    fun getError(statusCode: Int, throwable: Throwable? = null): ErrorEntity
    fun getMessage(errorEntity: ErrorEntity): String
}