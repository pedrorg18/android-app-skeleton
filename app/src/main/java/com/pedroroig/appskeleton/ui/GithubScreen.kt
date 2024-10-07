package com.pedroroig.appskeleton.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pedroroig.appskeleton.domain.mockReposSeveral
import com.pedroroig.appskeleton.domain.model.GithubRepo

@Composable
fun GithubScreen(viewModel: GithubViewModel = hiltViewModel()) {
    val uiModel by viewModel.uiState.collectAsStateWithLifecycle()
    GithubScreenContent(
        uiModel,
        viewModel::fetchUserRepositories,
        viewModel::onDismissError,
    )
}

@Composable
private fun GithubScreenContent(
    uiModel: UiModel,
    searchAction: (String) -> Unit,
    onDismissError: () -> Unit,
) {
    Box {
        RepoList(uiModel, searchAction)
        if (uiModel.error != null) {
            ErrorDialog(
                "There was an error fetching the repositories",
                "Ok",
                onConfirm = { onDismissError() },
                onDismiss = { onDismissError() },
            )
        }
        if (uiModel.isLoading) {
            LoadingScreen()
        }
    }
}

@Composable
private fun RepoList(uiModel: UiModel, searchAction: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        var username by remember { mutableStateOf("") }
        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("GitHub Username") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            searchAction(username)
        }) {
            Text("Fetch Repositories")
        }
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(uiModel.repos) { repo ->
                GithubRepoItem(repo)
            }
        }
    }
}

@Composable
private fun GithubRepoItem(repo: GithubRepo) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = repo.name, style = MaterialTheme.typography.headlineSmall)
            Text(
                text = repo.description ?: "No description",
                style = MaterialTheme.typography.bodyMedium
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Language: ${repo.language ?: "Unknown"}")
                Text(text = "Stars: ${repo.stargazersCount}")
            }
            Text(text = "Forks: ${repo.forksCount}")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GithubScreenPopulatedPreview() {
    GithubScreenContent(
        UiModel(
            mockReposSeveral(),
            false,
        ),
        emptyLambdaString(),
        emptyLambda(),
    )
}

@Preview(showBackground = true)
@Composable
private fun GithubScreenEmptyPreview() {
    GithubScreenContent(
        UiModel(
            emptyList(),
            false,
        ),
        emptyLambdaString(),
        emptyLambda(),
    )
}

@Preview(showBackground = true)
@Composable
private fun GithubScreenLoadingPreview() {
    GithubScreenContent(
        UiModel(
            emptyList(),
            true,
        ),
        emptyLambdaString(),
        emptyLambda(),
    )
}

private fun emptyLambdaString(): (String) -> Unit = {}

private fun emptyLambda(): () -> Unit = {}
