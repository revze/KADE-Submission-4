package io.revze.footballmatchschedule.model

import com.google.gson.annotations.SerializedName

class LastMatch (
        @SerializedName("idEvent")
        var eventId: String,

        @SerializedName("strHomeTeam")
        var homeTeam: String,

        @SerializedName("strAwayTeam")
        var awayTeam: String,

        @SerializedName("intHomeScore")
        var homeScore: String,

        @SerializedName("intAwayScore")
        var awayScore: String,

        @SerializedName("dateEvent")
        var dateEvent: String
)