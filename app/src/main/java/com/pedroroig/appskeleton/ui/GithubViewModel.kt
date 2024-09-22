package com.pedroroig.appskeleton.ui

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.savedstate.SavedStateRegistryOwner
import com.pedroroig.appskeleton.domain.model.repository.GithubRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GithubViewModel(
    private val repository: GithubRepository
) : ViewModel() {

    private val searchAction: (String) -> Unit = { fetchUserRepositories(it) }

    private val _uiState = MutableStateFlow(initialState())
    val uiState: StateFlow<UiModel> = _uiState

    private val _uiEffects = MutableSharedFlow<String>()
    val uiEffects: SharedFlow<String> = _uiEffects

    private fun fetchUserRepositories(username: String) {
        if (username.isNotEmpty()) {
            viewModelScope.launch {
                _uiState.value = loadingState()
                delay(1000)
                try {
                    repository.getUserRepositories(username)
                        .collect { repos ->
                            _uiState.value = UiModel(repos, false, searchAction)
                        }
                } catch (e: Exception) {
                    _uiState.value = initialState()
                    e.printStackTrace()
                    _uiEffects.emit("There was an error when fetching the data")
                }
            }
        }
    }

    private fun initialState() = UiModel(emptyList(), false, searchAction)

    private fun loadingState() = UiModel(emptyList(), true, searchAction)

    companion object {
        fun provideFactory(
            repository: GithubRepository,
            owner: SavedStateRegistryOwner,
            defaultArgs: Bundle? = null,
        ): AbstractSavedStateViewModelFactory =
            object : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    key: String,
                    modelClass: Class<T>,
                    handle: SavedStateHandle
                ): T {
                    return GithubViewModel(repository) as T
                }
            }
    }
}