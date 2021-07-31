package com.timgortworst.cleanarchitecture.data.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MoshiNetwork

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MoshiDefault