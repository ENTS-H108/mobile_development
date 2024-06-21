package com.ents_h108.petwell.view.main.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ents_h108.petwell.R
import com.ents_h108.petwell.databinding.FragmentHelpBinding


class HelpFragment : Fragment() {
   private lateinit var binding:FragmentHelpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentHelpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnChat.setOnClickListener {
            openWhatsApp()
        }

    }

    fun openWhatsApp() {
        val phoneNumber = "6281228375433" // Ganti dengan nomor WhatsApp yang sesuai
        val message = "Hai, saya butuh bantuan terkait aplikasi ini."
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("https://wa.me/$phoneNumber?text=${Uri.encode(message)}")
        startActivity(intent)
    }
}