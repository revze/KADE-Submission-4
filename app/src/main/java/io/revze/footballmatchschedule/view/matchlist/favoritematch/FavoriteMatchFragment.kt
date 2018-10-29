package io.revze.footballmatchschedule.view.matchlist.favoritematch

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

import io.revze.footballmatchschedule.R
import io.revze.footballmatchschedule.db.database
import io.revze.footballmatchschedule.model.FavoriteMatch
import io.revze.footballmatchschedule.util.gone
import io.revze.footballmatchschedule.util.visible
import kotlinx.android.synthetic.main.fragment_favorite_match.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.support.v4.onRefresh

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [FavoriteMatchFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [FavoriteMatchFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class FavoriteMatchFragment : Fragment() {

    private var favoriteMatches: MutableList<FavoriteMatch> = mutableListOf()
    private lateinit var favoriteMatchAdapter: FavoriteMatchAdapter
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var rvFavoriteMatch: RecyclerView
    private lateinit var layoutEmptyFavoriteMatch: LinearLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_match, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipeRefresh = sr_favorite_match
        rvFavoriteMatch = rv_favorite_match
        favoriteMatchAdapter = FavoriteMatchAdapter(requireContext(), favoriteMatches)
        rvFavoriteMatch.layoutManager = LinearLayoutManager(context)
        rvFavoriteMatch.adapter = favoriteMatchAdapter
        layoutEmptyFavoriteMatch = layout_empty_favorite_match

        swipeRefresh.onRefresh {
            showFavoriteMatch()
        }

        showFavoriteMatch()
    }

    private fun showFavoriteMatch() {
        favoriteMatches.clear()
        context?.database?.use {
            swipeRefresh.isRefreshing = false
            val resullt = select(FavoriteMatch.TABLE_NAME)
            val favoriteMatch = resullt.parseList(classParser<FavoriteMatch>())
            if (favoriteMatch.size > 0) {
                favoriteMatches.addAll(favoriteMatch)
                favoriteMatchAdapter.notifyDataSetChanged()
                rvFavoriteMatch.visible()
                layoutEmptyFavoriteMatch.gone()
            }
            else{
                rvFavoriteMatch.gone()
                layoutEmptyFavoriteMatch.visible()
            }
        }
    }
}
