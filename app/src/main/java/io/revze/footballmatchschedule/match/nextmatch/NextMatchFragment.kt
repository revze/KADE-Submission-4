package io.revze.footballmatchschedule.match.nextmatch


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
import io.revze.footballmatchschedule.model.NextMatch
import io.revze.footballmatchschedule.util.gone
import io.revze.footballmatchschedule.util.visible
import kotlinx.android.synthetic.main.fragment_next_match.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class NextMatchFragment : Fragment(), NextMatchView {

    private lateinit var nextMatchPresenter: NextMatchPresenter
    private lateinit var rvNextMatch: RecyclerView
    private lateinit var layoutLoading: LinearLayout
    private var nextMatches: MutableList<NextMatch> = mutableListOf()
    private lateinit var nextMatchAdapter: NextMatchAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_next_match, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val apiRepository = ApiRepository()
        val gson = Gson()
        nextMatchPresenter = NextMatchPresenter(this, apiRepository, gson)
        rvNextMatch = rv_next_match
        layoutLoading = layout_loading
        nextMatchAdapter = NextMatchAdapter(context, nextMatches)
        rvNextMatch.layoutManager = LinearLayoutManager(context)
        rvNextMatch.adapter = nextMatchAdapter

        nextMatchPresenter.getNextMatchList()
    }

    override fun showLoading() {
        rvNextMatch.gone()
        layoutLoading.visible()
    }

    override fun hideLoading() {
        rvNextMatch.visible()
        layoutLoading.gone()
    }

    override fun showNextMatchList(data: List<NextMatch>) {
        nextMatches.clear()
        nextMatches.addAll(data)
        nextMatchAdapter.notifyDataSetChanged()
    }
}
