package io.revze.footballmatchschedule.model

class FavoriteMatch(
        val id: Long?,
        val eventId: String,
        val homeTeam: String,
        val awayTeam: String,
        val homeScore: String?,
        val awayScore: String?,
        val dateEvent: String
) {
    companion object {
        const val TABLE_NAME: String = "TABLE_FAVORITE_MATCH"
        const val ID: String = "ID_"
        const val EVENT_ID: String = "EVENT_ID"
        const val HOME_TEAM_NAME: String = "HOME_TEAM_NAME"
        const val AWAY_TEAM_NAME: String = "AWAY_TEAM_NAME"
        const val HOME_TEAM_SCORE: String = "HOME_TEAM_SCORE"
        const val AWAY_TEAM_SCORE: String = "AWAY_TEAM_SCORE"
        const val DATE_EVENT: String = "DATE_EVENT"
    }
}