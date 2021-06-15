package com.timgortworst.cleanarchitecture.domain.model.state

sealed class ErrorEntity {
    abstract val message: String

    data class DatabaseError(val throwable: Throwable? = null, override val message: String) : ErrorEntity()
    data class NetworkException(val throwable: Throwable? = null, override val message: String) : ErrorEntity()
    data class Unknown(val throwable: Throwable? = null, override val message: String) : ErrorEntity()

    sealed class HttpErrors : ErrorEntity() {
        data class ResourceForbidden(override val message: String) : HttpErrors()
        data class ResourceNotFound(override val message: String) : HttpErrors()
        data class InternalServerError(override val message: String) : HttpErrors()
        data class BadGateWay(override val message: String) : HttpErrors()
    }
}