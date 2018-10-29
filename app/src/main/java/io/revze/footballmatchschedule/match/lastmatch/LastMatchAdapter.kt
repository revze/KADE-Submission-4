package io.revze.footballmatchschedule.match.lastmatch

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.revze.footballmatchschedule.R
import io.revze.footballmatchschedule.model.LastMatch
import io.revze.footballmatchschedule.view.matchdetail.MatchDetailActivity
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_row_match.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import java.text.ParseException
import java.text.SimpleDateFormat
import org.jetbrains.anko.startActivity
import java.util.*

class LastMatchAdapter(private val context: Context?, private val lastMatches: List<LastMatch>) : RecyclerView.Adapter<LastMatchAdapter.LastMatchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LastMatchViewHolder {
        return LastMatchViewHolder(LayoutInflater.from(context).inflate(R.layout.item_row_match, parent, false))
    }

    override fun onBindViewHolder(holder: LastMatchViewHolder, position: Int) {
        holder.bindItems(lastMatches[position])
    }

    override fun getItemCount(): Int = lastMatches.size

    inner class LastMatchViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindItems(lastMatch: LastMatch) {
            tv_date.text = dateConverter(lastMatch.dateEvent)
            tv_home_team.text = lastMatch.homeTeam
            tv_home_score.text = lastMatch.homeScore
            tv_away_team.text = lastMatch.awayTeam
            tv_away_score.text = lastMatch.awayScore
            itemView.onClick {
                context?.startActivity<MatchDetailActivity>(MatchDetailActivity.EVENT_ID_KEY to lastMatch.eventId)
            }
        }

        fun dateConverter(date: String) : String {
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
