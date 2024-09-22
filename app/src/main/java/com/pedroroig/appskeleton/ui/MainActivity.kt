package com.pedroroig.appskeleton.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.pedroroig.appskeleton.data.GithubRepositoryImpl
import com.pedroroig.appskeleton.data.network.GithubNetworkDao
import com.pedroroig.appskeleton.data.network.RetrofitGithubNetwork
import com.pedroroig.appskeleton.ui.theme.AppSkeletonTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val viewModel: GithubViewModel by viewModels {
        GithubViewModel.provideFactory(
            GithubRepositoryImpl(
                GithubNetworkDao(
                    RetrofitGithubNetwork.networkApi
                )
            ),
            this,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    enableEdgeToEdge()
                    setContent {
                        AppSkeletonTheme {
                            GithubScreen(
                                uiModel = uiState,
                                modifier = Modifier.padding(top = 24.dp))
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiEffects.collect { errorMessage ->
                    with(this@MainActivity) {
                        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }
}