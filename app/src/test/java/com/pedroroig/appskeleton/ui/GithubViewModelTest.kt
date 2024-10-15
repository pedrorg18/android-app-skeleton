package com.pedroroig.appskeleton.ui

import com.pedroroig.appskeleton.domain.mockReposSeveral
import com.pedroroig.appskeleton.domain.model.GithubRepo
import com.pedroroig.appskeleton.test.FakeGithubRepository
import com.pedroroig.appskeleton.utils.MainDispatcherRule
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GithubViewModelTest {

    private lateinit var viewModel: GithubViewModel
    private var repository: FakeGithubRepository = FakeGithubRepository()
    // So the main dispatcher tasks are run immediately, instead of being queued
    private val mainDispatcher = UnconfinedTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(mainDispatcher)

    @Before
    fun setup() {
        viewModel = GithubViewModel(repository)
    }

    @Test
    fun `GIVEN initial screen THEN state not loading and empty list`() = runTest {
        assertEquals(initialState(), viewModel.uiState.value)
    }

    @Test
    fun `WHEN data is fetched THEN state loading and empty list`() = runTest {
        viewModel.fetchUserRepositories("testuser")

        assertEquals(loadingState(), viewModel.uiState.value)
    }

    @Test
    fun `WHEN data arrives THEN state not loading and display list`() = runTest {
        val expectedRepos = mockReposSeveral()

        viewModel.fetchUserRepositories("testuser")
        repository.emit(expectedRepos)

        assertEquals(displayState(expectedRepos), viewModel.uiState.value)
    }

    @Test
    fun `WHEN there is an error THEN state with error`() = runTest {
        repository.fail = true

        viewModel.fetchUserRepositories("testuser")

        assertEquals(errorState(), viewModel.uiState.value)
    }

    private fun initialState() = UiModel(emptyList(), false)

    private fun loadingState() = UiModel(emptyList(), true)

    private fun displayState(repos: List<GithubRepo>) = UiModel(repos, false)

    private fun errorState() =
        UiModel(emptyList(), false, "There was an error when fetching the data")
}