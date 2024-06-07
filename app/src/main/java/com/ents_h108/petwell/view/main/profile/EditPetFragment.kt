package com.ents_h108.petwell.view.main.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ents_h108.petwell.R
import com.ents_h108.petwell.databinding.FragmentEditPetBinding


class EditPetFragment : Fragment() {
    private lateinit var binding: FragmentEditPetBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditPetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        arguments?.let {
//            val args = EditPetFragmentArgs.fromBundle(it)
//            petItem = args.petItem
//        }


        binding.imgPet
        binding.tvRace
        binding.etPetName
    }

}