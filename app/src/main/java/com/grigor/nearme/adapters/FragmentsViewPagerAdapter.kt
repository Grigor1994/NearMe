package com.grigor.nearme.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.grigor.nearme.BlankFragment2
import com.grigor.nearme.HomeChildFragment


//private val TAB_TITLES = arrayOf(
//    R.string.tab_text_me,
//    R.string.tab_text_friends,
//    R.string.tab_text_staff
//)
/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
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
