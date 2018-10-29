package io.revze.footballmatchschedule.match.lastmatch


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.google.gson.Gson

import io.revze.footballmatchschedule.R
import io.revze.footballmatchschedule.api.ApiRepository
import io.revze.footballmatchschedule.model.LastMatch
import io.revze.footballmatchschedule.util.gone
import io.revze.footballmatchschedule.util.visible
import kotlinx.android.synthetic.main.fragment_last_match.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class LastMatchFragment : Fragment(), LastMatchView {

    private lateinit var lastMatchPresenter: LastMatchPresenter
    private lateinit var rvLastMatch: RecyclerView
    private lateinit var layoutLoading: LinearLayout
    private var lastMatches: MutableList<LastMatch> = mutableListOf()
    private lateinit var lastMatchAdapter: LastMatchAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_last_match, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val apiRepository = ApiRepository()
        val gson = Gson()
        lastMatchPresenter = LastMatchPresenter(this, apiRepository, gson)
        rvLastMatch = rv_last_match
        layoutLoading = layout_loading
        lastMatchAdapter = LastMatchAdapter(context, lastMatches)
        rvLastMatch.layoutManager = LinearLayoutManager(context)
        rvLastMatch.adapter = lastMatchAdapter

        lastMatchPresenter.getLastMatchList()
    }

    override fun showLoading() {
        rvLastMatch.gone()
        layoutLoading.visible()
    }

    override fun hideLoading() {
        rvLastMatch.visible()
        layoutLoading.gone()
    }

    override fun showLastMatchList(data: List<LastMatch>) {
        lastMatches.clear()
        lastMatches.addAll(data)
        lastMatchAdapter.notifyDataSetChanged()
    }
}
