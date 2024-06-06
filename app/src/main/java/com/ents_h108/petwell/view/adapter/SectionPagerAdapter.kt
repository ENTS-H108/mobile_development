package com.ents_h108.petwell.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ents_h108.petwell.view.main.articleTypes.ListArticleFragment
import com.ents_h108.petwell.view.main.articleTypes.ListPromoFragment

class SectionsPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = ListPromoFragment()
            1 -> fragment = ListArticleFragment()
        }
        return fragment as Fragment
    }

}