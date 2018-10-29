package io.revze.footballmatchschedule.api

import java.net.URL

class ApiRepository {
    fun doRequest(url: String) = URL(url).readText()
}