package com.pedroroig.appskeleton.data.network

import com.pedroroig.appskeleton.data.network.model.NetworkGithubRepo
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubNetworkApi {

    @GET("users/{username}/repos")
    suspend fun getUserRepositories(
        @Path("username") username: String
    ): List<NetworkGithubRepo>
}