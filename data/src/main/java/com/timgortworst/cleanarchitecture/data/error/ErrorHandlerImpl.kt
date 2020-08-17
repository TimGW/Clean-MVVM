package com.timgortworst.cleanarchitecture.data.error

import com.timgortworst.cleanarchitecture.domain.model.state.ErrorEntity
import com.timgortworst.cleanarchitecture.domain.model.state.ErrorHandler
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection

class ErrorHandlerImpl : ErrorHandler {

    override fun getError(throwable: Throwable): ErrorEntity {
        return when (throwable) {
            is IOException -> ErrorEntity.NetworkException(throwable)
            is HttpException -> getError(throwable.code(), throwable)
            else -> ErrorEntity.Unknown(throwable)
        }
    }

    override fun getError(statusCode: Int, throwable: Throwable?): ErrorEntity {
        return when(statusCode) {
            HttpURLConnection.HTTP_NOT_FOUND -> ErrorEntity.HttpErrors.ResourceNotFound(throwable)
            HttpURLConnection.HTTP_FORBIDDEN -> ErrorEntity.HttpErrors.ResourceForbidden(throwable)
            HttpURLConnection.HTTP_INTERNAL_ERROR -> ErrorEntity.HttpErrors.InternalServerError(throwable)
            HttpURLConnection.HTTP_BAD_GATEWAY -> ErrorEntity.HttpErrors.BadGateWay(throwable)
            else -> ErrorEntity.Unknown()
        }
    }
}