package com.ents_h108.petwell.view.main.featureConsultation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ents_h108.petwell.databinding.FragmentConsultationBinding
import com.ents_h108.petwell.view.adapter.ConsultationAdapter
import com.ents_h108.petwell.data.model.Doctor
import com.ents_h108.petwell.utils.Result
import com.ents_h108.petwell.view.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ConsultationFragment : Fragment() {

    private lateinit var binding: FragmentConsultationBinding
    private lateinit var consultationAdapter: ConsultationAdapter
    private val viewModel: MainViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentConsultationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        populateDummyData()
    }

    private fun setupRecyclerView() {
        consultationAdapter = ConsultationAdapter(object : ConsultationAdapter.OnItemClickListener {
            override fun onItemClick(item: Doctor) {
            }

            override fun onBtnClick(item: Doctor) {
                findNavController().navigate(ConsultationFragmentDirections.actionConsultationFragmentToPaymentFragment(item))
            }
        })

        binding.rvListPersonChat.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = consultationAdapter
        }
    }

    private fun populateDummyData() {
        viewModel.getAllDoctor().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> binding.rvListPersonChat.visibility = View.GONE
                is Result.Error -> binding.rvListPersonChat.visibility = View.GONE
                is Result.Success -> {
                    binding.rvListPersonChat.visibility = View.VISIBLE
                    consultationAdapter.submitList(result.data)
                }
            }
        }
    }
}
