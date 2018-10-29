package io.revze.footballmatchschedule.api.response

import com.google.gson.annotations.SerializedName
import io.revze.footballmatchschedule.model.MatchDetail
import io.revze.footballmatchschedule.model.TeamDetail

class TeamDetailResponse(@SerializedName("teams") val teamDetail: List<TeamDetail>)