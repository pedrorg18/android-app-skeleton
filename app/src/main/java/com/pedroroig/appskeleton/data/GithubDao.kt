package com.pedroroig.appskeleton.data

import com.pedroroig.appskeleton.domain.model.GithubRepo

interface GithubDao {
    suspend fun getUserRepositories(username: String): List<GithubRepo>
}