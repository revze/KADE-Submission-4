package io.revze.footballmatchschedule.api.response

import com.google.gson.annotations.SerializedName
import io.revze.footballmatchschedule.model.LastMatch

class LastMatchResponse(@SerializedName("events") val lastMatch: List<LastMatch>)