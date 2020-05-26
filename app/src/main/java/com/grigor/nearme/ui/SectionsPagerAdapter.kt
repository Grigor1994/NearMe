package com.grigor.nearme.ui

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.grigor.nearme.R
import com.grigor.nearme.ui.home.HomeFragment


private val TAB_TITLES = arrayOf(
    R.string.tab_text_me,
    R.string.tab_text_friends,
    R.string.tab_text_staff
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fragmentManager: FragmentManager) :
    FragmentPagerAdapter(fragmentManager) {

    /**
     * getItem is called to instantiate the fragment for the given page.
     */
    override fun getItem(position: Int): Fragment {
      return HomeFragment()
    }

    /**
     * Set our tabs titles.
     */
    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    /**
     * This method will returns 3 tabs.
     */
    override fun getCount(): Int {
        return 3
    }
}
