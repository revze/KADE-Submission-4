package io.revze.footballmatchschedule.api.response

import com.google.gson.annotations.SerializedName
import io.revze.footballmatchschedule.model.MatchDetail

class MatchDetailResponse(@SerializedName("events") val matchDetail: List<MatchDetail>)