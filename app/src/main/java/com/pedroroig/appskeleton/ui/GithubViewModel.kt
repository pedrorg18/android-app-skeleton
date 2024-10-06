package com.pedroroig.appskeleton.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pedroroig.appskeleton.domain.repository.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GithubViewModel @Inject constructor(
    private val repository: GithubRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(initialState())
    val uiState: StateFlow<UiModel> = _uiState

    // TODO Manage errors
    private val _uiEffects = MutableSharedFlow<String>()
    val uiEffects: SharedFlow<String> = _uiEffects

    fun fetchUserRepositories(username: String) {
        if (username.isNotEmpty()) {
            viewModelScope.launch {
                _uiState.value = loadingState()
                delay(1000)
                try {
                    repository.getUserRepositories(username)
                        .collect { repos ->
                            _uiState.value = UiModel(repos, false)
                        }
                } catch (e: Exception) {
                    _uiState.value = initialState()
                    e.printStackTrace()
                    _uiEffects.emit("There was an error when fetching the data")
                }
            }
        }
    }

    private fun initialState() = UiModel(emptyList(), false)

    private fun loadingState() = UiModel(emptyList(), true)
}