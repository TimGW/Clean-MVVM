package com.timgortworst.cleanarchitecture.domain.model.state

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val errorEntity: ErrorEntity? = null) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
}

// TODO pass cached data with loading and error states
//sealed class Resource<T>(
//    val data: T? = null,
//    val message: String? = null
//) {
//    class Success<T>(data: T) : Resource<T>(data)
//    class Loading<T>(data: T? = null) : Resource<T>(data)
//    class Error<T>(val errorEntity: ErrorEntity? = null, data: T? = null) : Resource<T>(data, errorEntity?.message)
//}