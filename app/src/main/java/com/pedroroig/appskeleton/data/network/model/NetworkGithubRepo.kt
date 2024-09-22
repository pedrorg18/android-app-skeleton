package com.pedroroig.appskeleton.data.network.model

data class NetworkGithubRepo(
    val id: Int,
    val name: String,
    val description: String?,
    val language: String?,
    val stargazersCount: Int,
    val forksCount: Int
)