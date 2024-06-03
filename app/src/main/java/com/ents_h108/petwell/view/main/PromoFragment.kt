package com.ents_h108.petwell.view.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import com.ents_h108.petwell.R
import com.ents_h108.petwell.databinding.FragmentPromoBinding
import com.ents_h108.petwell.view.adapter.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class PromoFragment : Fragment() {
    private var _binding: FragmentPromoBinding? = null
    private val binding get() = _binding!!

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
        _binding = FragmentPromoBinding.inflate(inflater, container, false)
        val view = binding.root

        // Set up ViewPager and TabLayout
        val sectionsPagerAdapter = SectionsPagerAdapter(requireActivity())
        binding.viewPager.adapter = sectionsPagerAdapter

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        // Hide ActionBar elevation
        requireActivity().actionBar?.elevation = 0f

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}