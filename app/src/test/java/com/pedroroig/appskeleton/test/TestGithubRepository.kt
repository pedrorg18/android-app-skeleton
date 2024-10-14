package com.pedroroig.appskeleton.test

import com.pedroroig.appskeleton.domain.model.GithubRepo
import com.pedroroig.appskeleton.domain.repository.GithubRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow

class TestGithubRepository : GithubRepository {

    var fail = false
    private var flow = MutableSharedFlow<List<GithubRepo>>()

    override fun getUserRepositories(username: String): Flow<List<GithubRepo>> =
        if (fail) {
            flow {
                throw RuntimeException("Test error")
            }
        } else flow

    suspend fun emit(reposList: List<GithubRepo>) = flow.emit(reposList)
}