package com.timgortworst.cleanarchitecture.domain.usecase

import kotlinx.coroutines.flow.Flow

interface FlowUseCase<in Params, out T> {
    fun execute(params: Params): Flow<T>
}