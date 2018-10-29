package io.revze.footballmatchschedule.api.response

import com.google.gson.annotations.SerializedName
import io.revze.footballmatchschedule.model.LastMatch
import io.revze.footballmatchschedule.model.NextMatch

class NextMatchResponse(@SerializedName("events") val nextMatch: List<NextMatch>)