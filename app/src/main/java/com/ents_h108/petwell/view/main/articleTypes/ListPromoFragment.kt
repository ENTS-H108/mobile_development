package com.ents_h108.petwell.view.main.articleTypes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ents_h108.petwell.data.model.Article
import com.ents_h108.petwell.databinding.FragmentListPromoBinding
import com.ents_h108.petwell.utils.Result
import com.ents_h108.petwell.utils.Utils
import com.ents_h108.petwell.view.adapter.PromoAdapter
import com.ents_h108.petwell.view.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListPromoFragment : Fragment() {

    private lateinit var binding: FragmentListPromoBinding
    private lateinit var promoAdapter: PromoAdapter
    private val viewModel: MainViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentListPromoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observePromo()
    }

    private fun setupRecyclerView() {
        promoAdapter = PromoAdapter(object : PromoAdapter.OnItemClickListener {
            override fun onItemClick(item: Article) {
                findNavController().navigate(ListPromoFragmentDirections.actionListPromoFragmentToDetailPromoArticleFragment2())
            }
        })
        binding.rvListPromo.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = promoAdapter
        }
    }

    private fun observePromo() {
        viewModel.getContent("promo").observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.historyLoading.visibility = View.VISIBLE
                    binding.rvListPromo.visibility = View.GONE
                }
                is Result.Success -> {
                    binding.historyLoading.visibility = View.GONE
                    binding.rvListPromo.visibility = View.VISIBLE
                    promoAdapter.submitData(lifecycle, result.data)
                }
                is Result.Error -> {
                    binding.historyLoading.visibility = View.GONE
                    binding.rvListPromo.visibility = View.GONE
                    Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
