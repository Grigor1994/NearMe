package com.grigor.nearme.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.grigor.nearme.BlankFragment2
import com.grigor.nearme.HomeChildFragment
import com.grigor.nearme.R
import com.grigor.nearme.adapters.FragmentsViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        childFragmentManager
        setUpViewPager2(view_pager)
        tabLayoutMediatorSettings()
    }

    private fun setUpViewPager2(viewPager: ViewPager2) {

        val fragmentAdapter = FragmentsViewPagerAdapter(this)
        fragmentAdapter.addFragment(HomeChildFragment())
        fragmentAdapter.addFragment(BlankFragment2())
        viewPager.adapter = fragmentAdapter
        viewPager.isUserInputEnabled = false
    }

    private fun tabLayoutMediatorSettings() {
        TabLayoutMediator(tabLayout, view_pager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "ME"
                }
                1 -> {
                    tab.text = "FRIENDS"
                }
            }
        }.attach()
    }
}

