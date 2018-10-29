package io.revze.footballmatchschedule.match.nextmatch

import io.revze.footballmatchschedule.model.NextMatch

interface NextMatchView {
    fun showLoading()
    fun hideLoading()
    fun showNextMatchList(data: List<NextMatch>)
}