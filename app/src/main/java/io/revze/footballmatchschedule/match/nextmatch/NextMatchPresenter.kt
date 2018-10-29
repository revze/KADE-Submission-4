package io.revze.footballmatchschedule.match.nextmatch

import com.google.gson.Gson
import io.revze.footballmatchschedule.api.ApiRepository
import io.revze.footballmatchschedule.api.Endpoint
import io.revze.footballmatchschedule.api.response.NextMatchResponse
import io.revze.footballmatchschedule.util.CoroutineContextProvider
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class NextMatchPresenter(private val view: NextMatchView,
                         private val apiRepository: ApiRepository,
                         private val gson: Gson,
                         private val context: CoroutineContextProvider = CoroutineContextProvider()) {
    fun getNextMatchList() {
        view.showLoading()

        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository.doRequest(Endpoint().NEXT_EVENT_URL), NextMatchResponse::class.java)
            }

            view.showNextMatchList(data.await().nextMatch)
            view.hideLoading()
        }
     }
}