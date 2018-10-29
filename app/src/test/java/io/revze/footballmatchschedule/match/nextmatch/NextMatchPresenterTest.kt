package io.revze.footballmatchschedule.match.nextmatch

import com.google.gson.Gson
import io.revze.footballmatchschedule.TestContextProvider
import io.revze.footballmatchschedule.api.ApiRepository
import io.revze.footballmatchschedule.api.Endpoint
import io.revze.footballmatchschedule.api.response.NextMatchResponse
import io.revze.footballmatchschedule.model.NextMatch
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class NextMatchPresenterTest {
    @Mock
    private lateinit var view: NextMatchView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    private lateinit var presenter: NextMatchPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = NextMatchPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun testGetNextMatchList() {
        val nextMatches: MutableList<NextMatch> = mutableListOf()
        val response = NextMatchResponse(nextMatches)

        `when`(gson.fromJson(apiRepository.doRequest(Endpoint().NEXT_EVENT_URL),
                NextMatchResponse::class.java
        )).thenReturn(response)

        presenter.getNextMatchList()

        verify(view).showLoading()
        verify(view).showNextMatchList(nextMatches)
        verify(view).hideLoading()
    }
}