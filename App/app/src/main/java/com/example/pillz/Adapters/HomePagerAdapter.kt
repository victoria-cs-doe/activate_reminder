package com.example.pillz.Adapters

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.pillz.Fragments.FollowUpsFragment
import com.example.pillz.Fragments.HomeFragment
import com.example.pillz.Fragments.RemindersFragment
import com.example.pillz.R

class HomePagerAdapter(fragmentManager: FragmentManager, private val context: Context) :
    FragmentPagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return HomeFragment.newInstance()
            1 -> return RemindersFragment.newInstance()
            else -> return FollowUpsFragment.newInstance()
        }
    }

    override fun getCount(): Int {
        return (3)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> context.resources.getString(R.string.homeFragment)
            1 -> context.resources.getString(R.string.remindersFragment)
            else -> context.resources.getString(R.string.followUpsFragment)
        }
    }
}