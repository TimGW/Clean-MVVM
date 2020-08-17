package com.timgortworst.cleanarchitecture.domain.usecase

// One-shot Requests
interface SuspendUseCase<in Params, out T> {
    suspend fun execute(params: Params) : T
}