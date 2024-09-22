package com.pedroroig.appskeleton.domain

import com.pedroroig.appskeleton.domain.model.GithubRepo

fun mockReposSeveral() = listOf(
    GithubRepo(1, "Repo 1", "Description 1", "Kotlin", 100, 50),
    GithubRepo(2, "Repo 2", "Description 2", "Java", 200, 100),
    GithubRepo(3, "Repo 3", null, "Python", 50, 20),
    GithubRepo(4, "Repo 4", "Description 4", "Swift", 150, 75),
    GithubRepo(5, "Repo 5", "Description 5", null, 300, 150)
)