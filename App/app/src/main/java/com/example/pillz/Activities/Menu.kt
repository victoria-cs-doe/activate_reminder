package com.example.pillz.Activities

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.pillz.Adapters.HomePagerAdapter
import com.example.pillz.Models.User
import com.example.pillz.Network.Endpoints
import retrofit2.Call
import com.example.pillz.R
import retrofit2.Callback
import retrofit2.Response

class Menu : AppCompatActivity() {

    private val tag = "TAG_MENU"
    private lateinit var viewPager: ViewPager
    private lateinit var tabs: TabLayout
    private val restAPI: Endpoints by lazy { Endpoints.createRetrofitInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        setupViews()
        setupPager()
        getUsers()

    }

    private fun getUsers() {
        val call = restAPI.getUsers()

        call.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>?, response: Response<List<User>>?) {
                response?.let {
                    it.body()?.let {
                        for (item in it) {
                            Log.d(tag,
                                    "${item.firstName} - ${item.lastName} - ${item.email} - ${item.password}\n")
                        }
                    }

                }
            }

            override fun onFailure(call: Call<List<User>>?, t: Throwable?) {
            }
        })

    }

    private fun setupViews() {
        viewPager = findViewById(R.id.viewPager)
        tabs = findViewById(R.id.tabs)
    }

    private fun setupPager() {
        val fragmentAdapter = HomePagerAdapter(supportFragmentManager, this)
        viewPager.adapter = fragmentAdapter
        tabs.setupWithViewPager(viewPager)
    }
}
