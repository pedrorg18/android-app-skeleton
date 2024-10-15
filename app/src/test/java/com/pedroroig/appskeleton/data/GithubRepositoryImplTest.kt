package com.pedroroig.appskeleton.data

import com.pedroroig.appskeleton.domain.mockReposSeveral
import com.pedroroig.appskeleton.domain.model.GithubRepo
import com.pedroroig.appskeleton.test.FakeGithubDao
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GithubRepositoryImplTest {

    private val githubDao = FakeGithubDao()
    private val githubRepository = GithubRepositoryImpl(githubDao)

    @Test
    fun `WHEN dao returns repos THEN repository returns them`() = runTest {
        githubDao.addRepositories(mockReposSeveral())

        githubRepository.getUserRepositories("testuser")
            .collect {
                repos -> assertEquals(mockReposSeveral(), repos)
            }
    }

    @Test
    fun `WHEN dao returns empty THEN repository returns empty`() = runTest {
        githubRepository.getUserRepositories("testuser")
            .collect {
                    repos -> assertEquals(emptyList<GithubRepo>(), repos)
            }
    }

}