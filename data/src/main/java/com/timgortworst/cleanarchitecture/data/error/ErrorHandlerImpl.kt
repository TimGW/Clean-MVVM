package com.timgortworst.cleanarchitecture.data.error

import com.timgortworst.cleanarchitecture.domain.model.state.ErrorEntity
import com.timgortworst.cleanarchitecture.domain.model.state.ErrorHandler
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ErrorHandlerImpl @Inject constructor() : ErrorHandler {

    override fun getError(throwable: Throwable): ErrorEntity {
        return when (throwable) {
            is IOException -> ErrorEntity.IOError(throwable)
            is HttpException -> ErrorEntity.HttpError(throwable, throwable.code())
            else -> ErrorEntity.Unknown(throwable)
        }
    }

    override fun getApiError(statusCode: Int, throwable: Throwable?): ErrorEntity {
        return ErrorEntity.HttpError(throwable, statusCode)
    }
}