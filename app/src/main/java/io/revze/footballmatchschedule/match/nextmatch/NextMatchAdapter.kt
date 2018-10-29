package io.revze.footballmatchschedule.match.nextmatch

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.revze.footballmatchschedule.R
import io.revze.footballmatchschedule.model.NextMatch
import io.revze.footballmatchschedule.view.matchdetail.MatchDetailActivity
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_row_match.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class NextMatchAdapter(private val context: Context?, private val nextMatches: List<NextMatch>) : RecyclerView.Adapter<NextMatchAdapter.NextMatchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NextMatchViewHolder {
        return NextMatchViewHolder(LayoutInflater.from(context).inflate(R.layout.item_row_match, parent, false))
    }

    override fun onBindViewHolder(holder: NextMatchViewHolder, position: Int) {
        holder.bindItems(nextMatches[position])
    }

    override fun getItemCount(): Int = nextMatches.size

    inner class NextMatchViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindItems(nextMatch: NextMatch) {
            tv_date.text = dateConverter(nextMatch.dateEvent)
            tv_home_team.text = nextMatch.homeTeam
            nextMatch.homeScore.let { tv_home_score.text = it }
            tv_away_team.text = nextMatch.awayTeam
            nextMatch.awayScore.let { tv_away_score.text = it }
            itemView.onClick {
                context?.startActivity<MatchDetailActivity>(MatchDetailActivity.EVENT_ID_KEY to nextMatch.eventId)
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
