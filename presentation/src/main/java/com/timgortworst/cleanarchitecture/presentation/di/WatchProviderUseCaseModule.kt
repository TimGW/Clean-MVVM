package com.timgortworst.cleanarchitecture.presentation.di

import com.timgortworst.cleanarchitecture.domain.model.state.Result
import com.timgortworst.cleanarchitecture.domain.model.watchprovider.WatchProvider
import com.timgortworst.cleanarchitecture.domain.model.watchprovider.WatchProviderRegion
import com.timgortworst.cleanarchitecture.domain.usecase.SuspendUseCase
import com.timgortworst.cleanarchitecture.domain.usecase.UseCase
import com.timgortworst.cleanarchitecture.domain.usecase.watchprovider.GetWatchProviderRegionsUseCase
import com.timgortworst.cleanarchitecture.domain.usecase.watchprovider.GetWatchProvidersMovieUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow

@Module
@InstallIn(ViewModelComponent::class)
abstract class WatchProviderUseCaseModule {

    @Binds
    @ViewModelScoped
    abstract fun provideGetWatchProviderRegionsUseCase(
        getWatchProviderRegionsUseCase: GetWatchProviderRegionsUseCase
    ): UseCase<Unit, Flow<Result<List<WatchProviderRegion>?>>>

    @Binds
    @ViewModelScoped
    abstract fun provideGetWatchProviderMovieUseCase(
        getWatchProvidersMovieUseCase: GetWatchProvidersMovieUseCase
    ): SuspendUseCase<GetWatchProvidersMovieUseCase.Params, Result<List<WatchProvider>>>
}