package com.pedroroig.appskeleton.ui

import com.pedroroig.appskeleton.domain.model.GithubRepo

data class UiModel(
    val repos: List<GithubRepo>,
    val isLoading: Boolean,
)