package com.example.pillz.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pillz.R

class FollowUpsFragment : Fragment() {
    companion object {
        open fun newInstance(): FollowUpsFragment {
            return FollowUpsFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.follow_ups_frag, container, false)
        return view
    }
}

