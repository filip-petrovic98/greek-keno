package com.mozzartbet.greekkeno.core.common.di

import com.mozzartbet.greekkeno.core.common.di.coroutine.DefaultDispatcher
import com.mozzartbet.greekkeno.core.common.di.coroutine.IoDispatcher
import com.mozzartbet.greekkeno.core.common.di.coroutine.MainDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
abstract class CoreModule {

    companion object {

        @Provides
        @DefaultDispatcher
        fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

        @Provides
        @IoDispatcher
        fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

        @Provides
        @MainDispatcher
        fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main
    }
}
