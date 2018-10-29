package io.revze.footballmatchschedule.view.matchdetail

import com.google.gson.Gson
import io.revze.footballmatchschedule.api.ApiRepository
import io.revze.footballmatchschedule.api.Endpoint
import io.revze.footballmatchschedule.api.response.MatchDetailResponse
import io.revze.footballmatchschedule.api.response.TeamDetailResponse
import io.revze.footballmatchschedule.util.CoroutineContextProvider
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MatchDetailPresenter(private val view: MatchDetailView,
                           private val apiRepository: ApiRepository,
                           private val gson: Gson,
                           private val context: CoroutineContextProvider = CoroutineContextProvider()) {
    fun getMatchDetail(id: String) {
        view.showLoading()

        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository.doRequest(Endpoint().MATCH_DETAIL_URL + id), MatchDetailResponse::class.java)
            }

            view.showMatchDetail(data.await().matchDetail[0])
            view.hideLoading()
        }
    }

    fun getTeamDetail(id: String, type: String) {
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository.doRequest(Endpoint().TEAM_DETAIL_URL + id), TeamDetailResponse::class.java)
            }

            if (type.equals("home")) view.showHomeTeamLogo(data.await().teamDetail[0].teamBadge)
            else view.showAwayTeamLogo(data.await().teamDetail[0].teamBadge)
        }
    }
}