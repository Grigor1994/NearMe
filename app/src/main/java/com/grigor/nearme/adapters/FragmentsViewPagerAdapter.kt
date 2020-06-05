package com.grigor.nearme.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter


class FragmentsViewPagerAdapter(fragmentManager: Fragment) :
    FragmentStateAdapter(fragmentManager) {
    private val fragmentList: MutableList<Fragment> = ArrayList()
    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }

    fun addFragment(fragment: Fragment) {
        fragmentList.add(fragment)
    }
}
