package com.timgortworst.cleanarchitecture.domain.usecase

import com.timgortworst.cleanarchitecture.domain.model.state.State
import kotlinx.coroutines.flow.Flow

// Multiple Values Requests
interface FlowUseCase<in Params, out T> {
    fun execute(params: Params) : Flow<State<T>>
}