package com.timgortworst.cleanarchitecture.data.di

import android.content.Context
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.timgortworst.cleanarchitecture.data.BuildConfig
import com.timgortworst.cleanarchitecture.data.error.ErrorHandlerImpl
import com.timgortworst.cleanarchitecture.data.local.*
import com.timgortworst.cleanarchitecture.data.remote.AuthHeaderInterceptor
import com.timgortworst.cleanarchitecture.domain.model.state.ErrorHandler
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * Module for all globally required dependencies
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    abstract fun bindErrorHandler(errorHandlerImpl: ErrorHandlerImpl): ErrorHandler

    @Binds
    abstract fun provideAuthHeaderInterceptor(
        authInterceptor: AuthHeaderInterceptor
    ): Interceptor

    companion object {

        @Provides
        fun providesSharedPreferences(
            sharedPrefManager: SharedPrefManager,
            @ApplicationContext context: Context
        ): SharedPrefs = SharedPrefs(sharedPrefManager, context)

        @Provides
        @Singleton
        fun providesRoomDb(
            @ApplicationContext context: Context,
            @MoshiDefault moshi: Moshi
        ) = Room.databaseBuilder(context, AppDatabase::class.java, "tmdb")
            .addTypeConverter(TypeConverterMovie(moshi))
            .addTypeConverter(TypeConverterTvShow(moshi))
            .fallbackToDestructiveMigration()
            .build()

        @Provides
        @Singleton
        fun provideRetrofit(
            okHttpClient: OkHttpClient,
            @MoshiNetwork moshi: Moshi
        ): Retrofit {
            return Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
        }

        @Provides
        fun provideOkHttpClient(interceptor: Interceptor): OkHttpClient {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val builder = OkHttpClient().newBuilder().addInterceptor(interceptor)
            if (BuildConfig.DEBUG) builder.addInterceptor(loggingInterceptor)

            return builder.build()
        }

        @Provides
        @MoshiDefault
        fun provideMoshi(
            builder: Moshi.Builder
        ) = builder.build()

        @Provides
        fun provideMoshiBuilder() = Moshi.Builder()
    }
}
