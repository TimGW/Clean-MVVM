package com.timgortworst.cleanarchitecture.domain.model.response

import androidx.annotation.Keep

@Keep
sealed class ErrorEntity {
    abstract val throwable: Throwable?

    data class DatabaseError(override val throwable: Throwable? = null) : ErrorEntity()
    data class NetworkException(override val throwable: Throwable? = null) : ErrorEntity()
    data class Unknown(override val throwable: Throwable? = null) : ErrorEntity()

    sealed class HttpErrors : ErrorEntity() {
        data class ResourceForbidden(override val throwable: Throwable? = null) : HttpErrors()
        data class ResourceNotFound(override val throwable: Throwable? = null) : HttpErrors()
        data class InternalServerError(override val throwable: Throwable? = null) : HttpErrors()
        data class BadGateWay(override val throwable: Throwable? = null) : HttpErrors()
    }
}