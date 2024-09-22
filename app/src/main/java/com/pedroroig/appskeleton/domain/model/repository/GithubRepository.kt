package com.pedroroig.appskeleton.domain.model.repository

import com.pedroroig.appskeleton.domain.model.GithubRepo
import kotlinx.coroutines.flow.Flow

interface GithubRepository {
    fun getUserRepositories(username: String): Flow<List<GithubRepo>>
}