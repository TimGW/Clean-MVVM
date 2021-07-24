package com.timgortworst.cleanarchitecture.domain.model.state

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val errorEntity: ErrorEntity? = null) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
}


