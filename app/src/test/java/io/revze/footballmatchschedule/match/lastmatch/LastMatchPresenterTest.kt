package io.revze.footballmatchschedule.match.lastmatch

import com.google.gson.Gson
import io.revze.footballmatchschedule.api.ApiRepository
import io.revze.footballmatchschedule.api.Endpoint
import io.revze.footballmatchschedule.api.response.LastMatchResponse
import io.revze.footballmatchschedule.model.LastMatch
import io.revze.footballmatchschedule.TestContextProvider
import org.junit.Test

import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class LastMatchPresenterTest {

    @Mock
    private lateinit var view: LastMatchView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    private lateinit var presenter: LastMatchPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = LastMatchPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun testGetLastMatchList() {
        val lastMatches: MutableList<LastMatch> = mutableListOf()
        val response = LastMatchResponse(lastMatches)

        `when`(gson.fromJson(apiRepository.doRequest(Endpoint().LAST_EVENT_URL),
                LastMatchResponse::class.java
        )).thenReturn(response)

        presenter.getLastMatchList()

        verify(view).showLoading()
        verify(view).showLastMatchList(lastMatches)
        verify(view).hideLoading()
    }
}