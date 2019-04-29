package com.example.pillz.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pillz.R

class RemindersFragment : Fragment() {
    companion object {
        open fun newInstance(): RemindersFragment {
            return RemindersFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.reminders_frag, container, false)
        return view
    }
}

