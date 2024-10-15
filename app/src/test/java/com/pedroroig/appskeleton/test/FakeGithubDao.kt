package com.pedroroig.appskeleton.test

import com.pedroroig.appskeleton.data.GithubDao
import com.pedroroig.appskeleton.domain.model.GithubRepo

class FakeGithubDao : GithubDao {

    private val repositories = mutableListOf<GithubRepo>()

    override suspend fun getUserRepositories(username: String): List<GithubRepo> {
        return repositories
    }

    fun addRepositories(repos: List<GithubRepo>) {
        repositories.addAll(repos)
    }
}