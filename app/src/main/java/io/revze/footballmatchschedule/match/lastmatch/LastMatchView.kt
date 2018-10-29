package io.revze.footballmatchschedule.match.lastmatch

import io.revze.footballmatchschedule.model.LastMatch

interface LastMatchView {
    fun showLoading()
    fun hideLoading()
    fun showLastMatchList(data: List<LastMatch>)
}