package io.revze.footballmatchschedule.view.matchlist.favoritematch

import android.content.Context
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.revze.footballmatchschedule.R
import io.revze.footballmatchschedule.model.FavoriteMatch
import io.revze.footballmatchschedule.view.matchdetail.MatchDetailActivity
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_row_match.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class FavoriteMatchAdapter(private val context: Context, private val favoriteMatches: List<FavoriteMatch>): RecyclerView.Adapter<FavoriteMatchAdapter.FavoriteMatchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMatchViewHolder {
        return FavoriteMatchViewHolder(LayoutInflater.from(context).inflate(R.layout.item_row_match, parent, false))
    }

    override fun onBindViewHolder(holder: FavoriteMatchViewHolder, position: Int) {
        holder.bindItems(favoriteMatches[position])
    }

    override fun getItemCount() = favoriteMatches.size

    inner class FavoriteMatchViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindItems(favoriteMatch: FavoriteMatch) {
            tv_date.text = dateConverter(favoriteMatch.dateEvent)
            tv_home_team.text = favoriteMatch.homeTeam
            favoriteMatch.homeScore.let { tv_home_score.text = it }
            tv_away_team.text = favoriteMatch.awayTeam
            favoriteMatch.awayScore.let { tv_away_score.text = it }
            itemView.onClick {
                context.startActivity<MatchDetailActivity>(MatchDetailActivity.EVENT_ID_KEY to favoriteMatch.eventId)
            }
        }

        fun dateConverter(date: String): String {
            try {
                val sourceDF = SimpleDateFormat("yyyy-MM-dd", Locale.US)
                val parseSrcDate = sourceDF.parse(date)
                val finalDF = SimpleDateFormat("EEE, dd MMM yyyy", Locale.US)
                return finalDF.format(parseSrcDate)
            }
            catch (e: ParseException) {
                return date
            }
        }
    }
}
