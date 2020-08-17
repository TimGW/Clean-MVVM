package com.timgortworst.cleanarchitecture.domain.model.response

interface ErrorHandler {
    fun getError(throwable: Throwable): ErrorEntity
    fun getError(statusCode: Int): ErrorEntity
}