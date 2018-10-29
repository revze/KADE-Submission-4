package io.revze.footballmatchschedule.match.lastmatch

import com.google.gson.Gson
import io.revze.footballmatchschedule.api.ApiRepository
import io.revze.footballmatchschedule.api.Endpoint
import io.revze.footballmatchschedule.api.response.LastMatchResponse
import io.revze.footballmatchschedule.util.CoroutineContextProvider
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class LastMatchPresenter(private val view: LastMatchView,
                         private val apiRepository: ApiRepository,
                         private val gson: Gson,
                         private val context: CoroutineContextProvider = CoroutineContextProvider()) {
    fun getLastMatchList() {
        view.showLoading()

        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository.doRequest(Endpoint().LAST_EVENT_URL), LastMatchResponse::class.java)
            }

            view.showLastMatchList(data.await().lastMatch)
            view.hideLoading()
        }
    }
}