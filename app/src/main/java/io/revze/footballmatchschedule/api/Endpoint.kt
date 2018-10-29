package io.revze.footballmatchschedule.api

import io.revze.footballmatchschedule.Env

class Endpoint {
    val LAST_EVENT_URL = Env().BASE_URL + "eventspastleague.php?id=" + Env().LEAGUE_ID
    val NEXT_EVENT_URL = Env().BASE_URL + "eventsnextleague.php?id=" + Env().LEAGUE_ID
    val MATCH_DETAIL_URL = Env().BASE_URL + "lookupevent.php?id="
    val TEAM_DETAIL_URL = Env().BASE_URL + "lookupteam.php?id="
}