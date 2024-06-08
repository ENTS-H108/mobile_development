package com.ents_h108.petwell.view.main.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentEditPetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        arguments?.let {
            pet = EditPetFragmentArgs.fromBundle(it).pet
        }
        setupPetData()
    }

    private fun setupViews() {
        val spinnerPetType: Spinner = binding.spinnerPetType
        ArrayAdapter.createFromResource(requireContext(), R.array.pet_types, android.R.layout.simple_spinner_item)
            .apply {
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerPetType.adapter = this
            }
    }

    private fun setupPetData() {
        binding.apply {
            if (pet == null) {
                binding.btnDeletePet.visibility = View.GONE
                binding.saveBtn.text = getString(R.string.add_pet_btn)
                binding.saveBtn.setOnClickListener {
                    mainViewModel.addPet(etPetName.text.toString(), binding.spinnerPetType.selectedItem.toString())
                        .observe(viewLifecycleOwner) { handleResult(it) }
                }
            } else {
                pet?.let { pet ->
                    binding.imgPet.load(pet.name)
                    etPetName.setText(pet.name)
                    binding.saveBtn.setOnClickListener {
                        mainViewModel.editPet(pet.id, etPetName.text.toString(), binding.spinnerPetType.selectedItem.toString())
                            .observe(viewLifecycleOwner) { handleResult(it) }
                    }
                    binding.btnDeletePet.setOnClickListener {
                        mainViewModel.deletePet(pet.id).observe(viewLifecycleOwner) { handleResult(it) }
                    }
                }
            }
        }
    }

    private fun handleResult(result: Result<*>) {
        binding.loading.visibility = when (result) {
            is Result.Loading -> View.VISIBLE
            else -> View.GONE
        }
        val message = when (result) {
            is Result.Success -> "Success"
            is Result.Error -> result.error
            else -> null
        }
        message?.let { context?.let { ctx -> Utils.showToast(ctx, it) } }
        findNavController().navigate(EditPetFragmentDirections.actionEditPetFragmentToProfileFragment())
    }
}
