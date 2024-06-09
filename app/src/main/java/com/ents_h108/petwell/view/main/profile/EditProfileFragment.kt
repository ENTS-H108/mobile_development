package com.ents_h108.petwell.view.main.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.activity.result.contract.ActivityResultContracts
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import coil.load
import com.ents_h108.petwell.data.repository.UserPreferences
import com.ents_h108.petwell.databinding.FragmentEditProfileBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.first

private const val IMAGE_TYPE = "image/*"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")

class EditProfileFragment : Fragment() {

    private lateinit var binding: FragmentEditProfileBinding
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    private val getImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let { binding.imgAvatar.load(uri) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnEditPicture.setOnClickListener { getImage.launch(IMAGE_TYPE) }
        coroutineScope.launch {
            val username = UserPreferences.getInstance(requireActivity().dataStore).getUsername().first()
            binding.etEdtUsername.setText(username)
        }
    }
}