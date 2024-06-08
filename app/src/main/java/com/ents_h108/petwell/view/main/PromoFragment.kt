package com.ents_h108.petwell.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.ents_h108.petwell.R
import com.ents_h108.petwell.databinding.FragmentPromoBinding
import com.ents_h108.petwell.view.adapter.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class PromoFragment : Fragment() {
    private lateinit var binding: FragmentPromoBinding
    private val args: PromoFragmentArgs by navArgs()

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPromoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
        selectTab(args.tabPosition)
        hideActionBarElevation()
    }

    private fun setupViewPager() {
        val sectionsPagerAdapter = SectionsPagerAdapter(requireActivity())
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun selectTab(position: Int) {
        binding.viewPager.setCurrentItem(position, false)
    }

    private fun hideActionBarElevation() {
        requireActivity().actionBar?.elevation = 0f
    }
}
