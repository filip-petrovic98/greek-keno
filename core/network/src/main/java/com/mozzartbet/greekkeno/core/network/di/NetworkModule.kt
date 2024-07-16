package com.mozzartbet.greekkeno.core.network.di

import com.mozzartbet.greekkeno.core.network.apiservice.DrawsApiService
import com.mozzartbet.greekkeno.core.network.datasource.DrawsDataSource
import com.mozzartbet.greekkeno.core.network.datasource.IDrawsDatasource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
internal abstract class NetworkModule {

    @Binds
    abstract fun bindDrawsRepository(
        drawsDataSource: DrawsDataSource
    ): IDrawsDatasource

    companion object {
        private const val BASE_URL = "https://api.opap.gr/"

        @Provides
        @Singleton
        fun provideOkHttpClient(): OkHttpClient {
            return OkHttpClient.Builder().build()
        }

        @Provides
        @Singleton
        fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
        }

        @Provides
        @Singleton
        fun provideDrawsApiService(retrofit: Retrofit): DrawsApiService {
            return retrofit.create(DrawsApiService::class.java)
        }
    }
}
