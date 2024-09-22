package com.pedroroig.appskeleton.data.network

import com.pedroroig.appskeleton.data.GithubDao
import com.pedroroig.appskeleton.data.network.model.NetworkGithubRepo
import com.pedroroig.appskeleton.domain.model.GithubRepo

class GithubNetworkDao(private val apiService: GithubNetworkApi) : GithubDao {

    override suspend fun getUserRepositories(username: String): List<GithubRepo> {
        return apiService.getUserRepositories(username)
            .map { networkGithubRepo ->
                networkGithubRepo.toGithubRepo()
            }
    }
}

private fun NetworkGithubRepo.toGithubRepo() =
    GithubRepo(
        id = id,
        name = name,
        description = description,
        language = language,
        stargazersCount = stargazersCount,
        forksCount = forksCount,
    )