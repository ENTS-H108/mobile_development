package com.ents_h108.petwell.view.main.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.navigation.fragment.findNavController
import coil.load
import com.ents_h108.petwell.R
import com.ents_h108.petwell.data.model.Pet
import com.ents_h108.petwell.databinding.FragmentEditPetBinding
import com.ents_h108.petwell.utils.Result
import com.ents_h108.petwell.utils.Utils
import com.ents_h108.petwell.view.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class EditPetFragment : Fragment() {
    private lateinit var binding: FragmentEditPetBinding
    private val mainViewModel: MainViewModel by viewModel()

    private var pet: Pet? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditPetBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        startof dropdown
        // Inisialisasi spinner
        val spinnerPetType: Spinner = view.findViewById(R.id.spinner_pet_type)

        // Membuat adapter untuk spinner
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.pet_types,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerPetType.adapter = adapter
        }

//        end of dropdown
        arguments?.let {
            pet = EditPetFragmentArgs.fromBundle(it).pet
        }

        binding.apply {
            if (pet == null) {
                binding.btnDeletePet.visibility = View.GONE
                binding.saveBtn.text = getString(R.string.add_pet_btn)
                binding.saveBtn.setOnClickListener {
                    val selectedPetType = spinnerPetType.selectedItem.toString()
                    mainViewModel.addPet(etPetName.text.toString(), selectedPetType)
                        .observe(viewLifecycleOwner) { result ->
                            when (result) {
                                is Result.Loading -> {
                                    binding.loading.visibility = View.VISIBLE
                                }

                                is Result.Success -> {
                                    binding.loading.visibility = View.GONE
                                    context?.let { Utils.showToast(it, "Succes") }
                                    findNavController().navigate(EditPetFragmentDirections.actionEditPetFragmentToProfileFragment())

                                }

                                is Result.Error -> {
                                    binding.loading.visibility = View.GONE
                                    context?.let { Utils.showToast(it, result.error) }
                                }
                            }
                        }
                }
            } else {
                pet?.let { pet1 ->
                    binding.imgPet.load(pet1.name)
//                    binding.etEdtRas.setText(pet1.species)
                    binding.etPetName.setText(pet1.name)
                    binding.saveBtn.setOnClickListener {
                        mainViewModel.editPet(
                            pet!!.id,
                            etPetName.text.toString(),
                            spinnerPetType.selectedItem.toString()
                        ).observe(viewLifecycleOwner) { result ->
                            when (result) {
                                is Result.Loading -> {
                                    binding.loading.visibility = View.VISIBLE
                                }

                                is Result.Success -> {
                                    binding.loading.visibility = View.GONE
                                    context?.let { Utils.showToast(it, "Succes") }
                                    findNavController().navigate(EditPetFragmentDirections.actionEditPetFragmentToProfileFragment())

                                }

                                is Result.Error -> {
                                    binding.loading.visibility = View.GONE
                                    context?.let { Utils.showToast(it, result.error) }
                                }
                            }
                        }
                    }
                    binding.btnDeletePet.setOnClickListener {
                        mainViewModel.deletePet(pet!!.id).observe(viewLifecycleOwner) { result ->
                            when (result) {
                                is Result.Loading -> {
                                    binding.loading.visibility = View.VISIBLE
                                }

                                is Result.Success -> {
                                    binding.loading.visibility = View.GONE
                                    context?.let { Utils.showToast(it, "Succes") }
                                    findNavController().navigate(EditPetFragmentDirections.actionEditPetFragmentToProfileFragment())

                                }

                                is Result.Error -> {
                                    binding.loading.visibility = View.GONE
                                    context?.let { Utils.showToast(it, result.error) }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}