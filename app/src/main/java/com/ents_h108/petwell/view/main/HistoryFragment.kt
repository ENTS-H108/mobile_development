package com.ents_h108.petwell.view.main

import com.ents_h108.petwell.view.adapter.HistoryAdapter
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.recyclerview.widget.LinearLayoutManager
import com.ents_h108.petwell.data.model.History
import com.ents_h108.petwell.data.repository.UserPreferences
import com.ents_h108.petwell.databinding.FragmentHistoryBinding
import com.ents_h108.petwell.utils.Result
import com.ents_h108.petwell.view.viewmodel.MainViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.koin.androidx.viewmodel.ext.android.viewModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")

class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding
    private val viewModel: MainViewModel by viewModel()

    private val historyAdapter = HistoryAdapter(object : HistoryAdapter.OnItemClickListener {
        override fun onItemClick(item: History) {
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupUI()
    }

    private fun setupUI() {
        val petActive = runBlocking {
            UserPreferences.getInstance(requireActivity().dataStore).getPetActive().first()
        }
        if (petActive != null) {
            viewModel.getPet(petActive).observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Result.Loading -> {}
                    is Result.Success -> {
                        binding.tvName.text = result.data.name
                        binding.tvGender.text = result.data.species
                        historyAdapter.submitList(result.data.history)
                    }
                    is Result.Error -> {
                        Log.d("HomeFragment", "Pet Name: ${result.error}")
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvHistory.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = historyAdapter
        }
        binding.rvHistory.visibility = View.VISIBLE
    }
}
