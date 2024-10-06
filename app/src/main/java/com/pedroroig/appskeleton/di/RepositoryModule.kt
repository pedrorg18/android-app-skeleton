package com.pedroroig.appskeleton.di

import com.pedroroig.appskeleton.data.GithubDao
import com.pedroroig.appskeleton.data.GithubRepositoryImpl
import com.pedroroig.appskeleton.domain.repository.GithubRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun provideGithubRepository(githubDao: GithubDao): GithubRepository =
        GithubRepositoryImpl(githubDao)
}