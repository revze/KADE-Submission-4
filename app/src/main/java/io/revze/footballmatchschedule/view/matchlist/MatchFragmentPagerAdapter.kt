package io.revze.footballmatchschedule.view.matchlist

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import io.revze.footballmatchschedule.R
import io.revze.footballmatchschedule.view.matchlist.favoritematch.FavoriteMatchFragment
import io.revze.footballmatchschedule.match.lastmatch.LastMatchFragment
import io.revze.footballmatchschedule.match.nextmatch.NextMatchFragment

class MatchFragmentPagerAdapter(private var context: Context, private var fragmentManager: FragmentManager)
    : FragmentPagerAdapter(fragmentManager) {

    override fun getCount() = 3

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return LastMatchFragment()
            1 -> return NextMatchFragment()
            else -> return FavoriteMatchFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> return context.getString(R.string.last_match_fragment_title)
            1 -> return context.getString(R.string.next_match_fragment_title)
            else -> return context.getString(R.string.favorite_match_fragment_title)
        }
    }
}