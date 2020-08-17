package com.timgortworst.cleanarchitecture.data.di

import com.timgortworst.cleanarchitecture.data.BuildConfig
import com.timgortworst.cleanarchitecture.data.network.AuthHeaderInterceptor
import com.timgortworst.cleanarchitecture.data.network.RemoteDataSourceMovie
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    factory { provideAuthHeaderInterceptor() }
    factory { provideOkHttpClient(get()) }
    factory { provideMovieEndPoint(get()) }
    single { provideRetrofit(get()) }
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create()).build()
}

fun provideOkHttpClient(authInterceptor: AuthHeaderInterceptor): OkHttpClient {
    return OkHttpClient().newBuilder().addInterceptor(authInterceptor).build()
}

fun provideAuthHeaderInterceptor(): AuthHeaderInterceptor {
    return AuthHeaderInterceptor(BuildConfig.API_KEY)
}

fun provideMovieEndPoint(retrofit: Retrofit): RemoteDataSourceMovie = retrofit.create(RemoteDataSourceMovie::class.java)
