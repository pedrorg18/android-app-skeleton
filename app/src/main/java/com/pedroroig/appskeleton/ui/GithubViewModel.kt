package com.pedroroig.appskeleton.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pedroroig.appskeleton.domain.repository.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GithubViewModel @Inject constructor(
    private val repository: GithubRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(initialState())
    val uiState: StateFlow<UiModel> = _uiState.asStateFlow()

    fun fetchUserRepositories(username: String) {
        if (username.isNotEmpty()) {
            viewModelScope.launch {
                setLoadingState()
                try {
                    repository.getUserRepositories(username)
                        .collect { repos ->
                            _uiState.value = UiModel(repos, false)
                        }
                } catch (e: Exception) {
                    _uiState.value = initialState()
                    e.printStackTrace()
                    setErrorState()
                }
            }
        }
    }

    fun onDismissError() {
        _uiState.value = uiState.value.copy(
            error = null
        )
    }

    private fun initialState() = UiModel(emptyList(), false)

    private fun setErrorState() {
        _uiState.value = uiState.value.copy(
            error = "There was an error when fetching the data"
        )
    }

    private fun setLoadingState() {
        _uiState.value = uiState.value.copy(
            isLoading = true
        )
    }
}