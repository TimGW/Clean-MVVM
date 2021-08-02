package com.timgortworst.cleanarchitecture.data.error

import com.timgortworst.cleanarchitecture.domain.model.state.ErrorEntity
import com.timgortworst.cleanarchitecture.domain.model.state.ErrorHandler
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection
import javax.inject.Inject

class ErrorHandlerImpl @Inject constructor() : ErrorHandler {

    // TODO REMOVE HARDCODED STRINGS
    override fun getError(throwable: Throwable): ErrorEntity {
        return when (throwable) {
            is IOException -> ErrorEntity.NetworkException(throwable, "a network error occurred")
            is HttpException -> getApiError(throwable, throwable.code(), "a server error occurred")
            else -> ErrorEntity.Unknown(throwable, "an error occurred")
        }
    }

    override fun getApiError(throwable: Throwable?, statusCode: Int, message: String): ErrorEntity {
        return when(statusCode) {
            HttpURLConnection.HTTP_NOT_FOUND -> ErrorEntity.HttpErrors.ResourceNotFound(message)
            HttpURLConnection.HTTP_FORBIDDEN -> ErrorEntity.HttpErrors.ResourceForbidden(message)
            HttpURLConnection.HTTP_INTERNAL_ERROR -> ErrorEntity.HttpErrors.InternalServerError(message)
            HttpURLConnection.HTTP_BAD_GATEWAY -> ErrorEntity.HttpErrors.BadGateWay(message)
            else -> ErrorEntity.Unknown(throwable, message)
        }
    }
}