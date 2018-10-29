package io.revze.footballmatchschedule.view.matchdetail

import android.database.sqlite.SQLiteConstraintException
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.ScrollView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import io.revze.footballmatchschedule.R
import io.revze.footballmatchschedule.api.ApiRepository
import io.revze.footballmatchschedule.db.database
import io.revze.footballmatchschedule.model.FavoriteMatch
import io.revze.footballmatchschedule.model.MatchDetail
import io.revze.footballmatchschedule.util.gone
import io.revze.footballmatchschedule.util.visible
import kotlinx.android.synthetic.main.activity_match_detail.*
import org.jetbrains.anko.contentView
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class MatchDetailActivity : AppCompatActivity(), MatchDetailView {

    companion object {
        val EVENT_ID_KEY = "event_id"
    }

    private lateinit var matchDetailPresenter: MatchDetailPresenter
    private lateinit var svMatchDetail: ScrollView
    private lateinit var layoutLoading: LinearLayout
    private lateinit var addToFavoriteMenuItem: MenuItem
    private lateinit var currentMatch: MatchDetail
    private lateinit var id: String
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_detail)

        if (intent != null) id = intent.getStringExtra(EVENT_ID_KEY)
        val apiRepository = ApiRepository()
        val gson = Gson()
        matchDetailPresenter = MatchDetailPresenter(this, apiRepository, gson)
        svMatchDetail = sv_match_detail
        layoutLoading = layout_loading
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        favoriteState()
        matchDetailPresenter.getMatchDetail(id)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.add_to_favorite -> {
                if (isFavorite) removeFromFavorite()
                else addToFavorite()

                isFavorite = !isFavorite
                setFavorite()

                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showLoading() {
        svMatchDetail.gone()
        layoutLoading.visible()
    }

    override fun hideLoading() {
        svMatchDetail.visible()
        layoutLoading.gone()
    }

    override fun showMatchDetail(data: MatchDetail) {
        currentMatch = data

        matchDetailPresenter.getTeamDetail(data.homeTeamId, "home")
        matchDetailPresenter.getTeamDetail(data.awayTeamId, "away")
        tv_date.text = dateConverter(data.dateEvent)
        tv_home_team.text = data.homeTeam
        tv_away_team.text = data.awayTeam
        data.homeScore.let { tv_home_score.text = it }
        data.awayScore.let { tv_away_score.text = it }
        data.homeGoalDetails.let { tv_home_goal_details.text = it }
        data.awayGoalDetails.let { tv_away_goal_details.text = it }
        data.homeShots.let { tv_home_shots.text = it }
        data.awayShots.let { tv_away_shots.text = it }
        data.homeLineupGoalkeeper.let { tv_home_goal_keeper.text = it }
        data.awayLineupGoalkeeper.let { tv_away_goal_keeper.text = it }
        data.homeLineupDefense.let { tv_home_defender.text = it }
        data.awayLineupDefense.let { tv_away_defender.text = it }
        data.homeLineupMidfield.let { tv_home_midfield.text = it }
        data.awayLineupMidfield.let { tv_away_midfield.text = it }
        data.homeLineupForward.let { tv_home_forward.text = it }
        data.awayLineupForward.let { tv_away_forward.text = it }
        data.homeLineupSubstitutes.let { tv_home_substitutes.text = it }
        data.awayLineupSubstitutes.let { tv_away_substitutes.text = it }
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

    override fun showHomeTeamLogo(logo: String?) {
        Picasso.get().load(logo).into(iv_home_team)
    }

    override fun showAwayTeamLogo(logo: String?) {
        Picasso.get().load(logo).into(iv_away_team)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        addToFavoriteMenuItem = menu.getItem(0)
        setFavorite()
        return super.onCreateOptionsMenu(menu)
    }

    private fun addToFavorite() {
        try {
            if (this::currentMatch.isInitialized) {
                database.use {
                    insert(FavoriteMatch.TABLE_NAME,
                            FavoriteMatch.EVENT_ID to id,
                            FavoriteMatch.HOME_TEAM_NAME to currentMatch.homeTeam,
                            FavoriteMatch.HOME_TEAM_SCORE to currentMatch.homeScore,
                            FavoriteMatch.AWAY_TEAM_NAME to currentMatch.awayTeam,
                            FavoriteMatch.AWAY_TEAM_SCORE to currentMatch.awayScore,
                            FavoriteMatch.DATE_EVENT to currentMatch.dateEvent)
                }
                contentView?.snackbar(getString(R.string.success_added_favorite_match))
            }
        }
        catch (e: SQLiteConstraintException) {
            contentView?.snackbar(e.localizedMessage)
        }
    }

    private fun removeFromFavorite() {
        try {
            database.use {
                delete(FavoriteMatch.TABLE_NAME, "(EVENT_ID = {id})",
                        "id" to id)
            }

            contentView?.snackbar(getString(R.string.success_removed_favorite_match))
        }
        catch (e: SQLiteConstraintException) {
            contentView?.snackbar(e.localizedMessage)
        }
    }

    private fun setFavorite() {
        if (isFavorite) addToFavoriteMenuItem.icon = ContextCompat.getDrawable(this, R.drawable.ic_added_to_favorites)
        else addToFavoriteMenuItem.icon = ContextCompat.getDrawable(this, R.drawable.ic_add_to_favorites)
    }

    private fun favoriteState() {
        database.use {
            val result = select(FavoriteMatch.TABLE_NAME)
                    .whereArgs("(EVENT_ID = {id})",
                            "id" to id)
            val favoriteMatch = result.parseList(classParser<FavoriteMatch>())
            if (!favoriteMatch.isEmpty()) isFavorite = true
        }
    }
}
