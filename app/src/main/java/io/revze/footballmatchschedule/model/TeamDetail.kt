package io.revze.footballmatchschedule.model

import com.google.gson.annotations.SerializedName

class TeamDetail (
        @SerializedName("strTeamBadge")
        var teamBadge: String?
)