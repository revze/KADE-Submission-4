package io.revze.footballmatchschedule.match.adapter

import android.content.Context
import android.view.View
import io.revze.footballmatchschedule.model.FavoriteMatch
import io.revze.footballmatchschedule.view.matchlist.favoritematch.FavoriteMatchAdapter
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class FavoriteMatchAdapterTest {
    @Mock
    private lateinit var favoriteMatchAdapter: FavoriteMatchAdapter

    @Mock
    private lateinit var context: Context

    @Mock
    private lateinit var view: View

    private val list: MutableList<FavoriteMatch> = mutableListOf()

    @Before
    fun test() {
        MockitoAnnotations.initMocks(this)
        favoriteMatchAdapter = FavoriteMatchAdapter(context, list)
    }

    @Test
    fun testDateConverter() {
        val actualDate = favoriteMatchAdapter.FavoriteMatchViewHolder(view).dateConverter("2018-05-19")
        assertEquals("Sat, 19 May 2018", actualDate)
    }
}