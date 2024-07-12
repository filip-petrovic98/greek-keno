package com.mozzartbet.greekkeno.core.data.repository.di

import com.mozzartbet.greekkeno.core.data.repository.draws.DrawsRepository
import com.mozzartbet.greekkeno.core.data.repository.draws.IDrawsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataModule {

    @Binds
    abstract fun bindDrawsRepository(
        drawsRepository: DrawsRepository
    ): IDrawsRepository
}
