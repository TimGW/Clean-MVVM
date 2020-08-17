package com.timgortworst.cleanarchitecture.data.error

import androidx.annotation.Keep
import com.timgortworst.cleanarchitecture.domain.model.response.ErrorEntity
import com.timgortworst.cleanarchitecture.domain.model.response.ErrorHandler
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection

@Keep
class ErrorHandlerImpl : ErrorHandler {

    override fun getError(throwable: Throwable): ErrorEntity {
        return when (throwable) {
            is IOException -> ErrorEntity.NetworkException(throwable)
            is HttpException -> getError(throwable.code())
            else -> ErrorEntity.Unknown(throwable)
        }
    }

    override fun getError(statusCode: Int): ErrorEntity {
        return when(statusCode) {
            HttpURLConnection.HTTP_NOT_FOUND -> ErrorEntity.HttpErrors.ResourceNotFound()
            HttpURLConnection.HTTP_FORBIDDEN -> ErrorEntity.HttpErrors.ResourceForbidden()
            HttpURLConnection.HTTP_INTERNAL_ERROR -> ErrorEntity.HttpErrors.InternalServerError()
            HttpURLConnection.HTTP_BAD_GATEWAY -> ErrorEntity.HttpErrors.BadGateWay()
            else -> ErrorEntity.Unknown()
        }
    }
}