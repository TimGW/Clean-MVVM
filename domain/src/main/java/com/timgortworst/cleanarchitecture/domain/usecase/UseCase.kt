package com.timgortworst.cleanarchitecture.domain.usecase

interface UseCase<in Params, out T> {
    fun execute(params: Params): T
}