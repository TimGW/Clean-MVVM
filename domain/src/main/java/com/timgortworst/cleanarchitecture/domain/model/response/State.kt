package com.timgortworst.cleanarchitecture.domain.model.response

sealed class State<out T> {
    data class Success<out T>(val data: T) : State<T>()
    data class Error(val errorEntity: ErrorEntity) : State<Nothing>()
    object Loading : State<Nothing>()
}


