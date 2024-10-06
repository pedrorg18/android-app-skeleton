package com.pedroroig.appskeleton.data

import com.pedroroig.appskeleton.domain.model.GithubRepo
import com.pedroroig.appskeleton.domain.repository.GithubRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(
    private val githubDao: GithubDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : GithubRepository {

    override fun getUserRepositories(username: String): Flow<List<GithubRepo>> =
        flow {
            emit(githubDao.getUserRepositories(username))
        }.flowOn(ioDispatcher)
}

