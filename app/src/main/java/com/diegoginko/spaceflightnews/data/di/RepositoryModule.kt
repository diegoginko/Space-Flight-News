package com.diegoginko.spaceflightnews.data.di

import com.diegoginko.spaceflightnews.data.repository.SFNRepositoryImpl
import com.diegoginko.spaceflightnews.domain.repository.SFNRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindSFNRepository(
        sfnRepositoryImpl: SFNRepositoryImpl
    ): SFNRepository
}
