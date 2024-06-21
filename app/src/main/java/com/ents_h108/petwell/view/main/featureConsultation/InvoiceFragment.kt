package com.ents_h108.petwell.view.main.featureConsultation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import coil.load
import com.ents_h108.petwell.R
import com.ents_h108.petwell.data.model.Doctor
import com.ents_h108.petwell.databinding.FragmentInvoiceBinding
import com.ents_h108.petwell.databinding.FragmentTabularCatBinding
import com.ents_h108.petwell.utils.Result
import com.ents_h108.petwell.view.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class InvoiceFragment : Fragment() {
    private lateinit var doctor: Doctor
    private lateinit var binding: FragmentInvoiceBinding
    private val viewModel: MainViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInvoiceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: InvoiceFragmentArgs by navArgs()
        doctor = args.dokterName

        binding.btnConsultation.setOnClickListener {
            openWhatsApp(doctor.name)
        }
        setupUI()
    }

    private fun setupUI() {
       binding.apply {
           tvDoctorName.text = doctor.name
           tvCategory.text = doctor.type
           tvConsultationFee.text = doctor.price
           ivProfile.load(doctor.profpict)

       }
        viewModel.fetchUserProfile().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Success -> {
                    binding.apply {
                        tvOrdererName.text = result.data.username
                        tvOrdererEmail.text = result.data.email
                    }
                }
                is Result.Error -> {}
                is Result.Loading -> {}
            }
        }

    }

    private fun openWhatsApp(doctorName: String) {
        val phoneNumber = "6281228375433"
        val message = "Start chat with $doctorName about a consultation."
        val url = "https://wa.me/$phoneNumber?text=${Uri.encode(message)}"
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
        }
        startActivity(intent)
    }

}