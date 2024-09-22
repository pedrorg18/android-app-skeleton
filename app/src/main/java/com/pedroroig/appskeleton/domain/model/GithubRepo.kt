package com.pedroroig.appskeleton.domain.model

data class GithubRepo(
    val id: Int,
    val name: String,
    val description: String?,
    val language: String?,
    val stargazersCount: Int,
    val forksCount: Int
)