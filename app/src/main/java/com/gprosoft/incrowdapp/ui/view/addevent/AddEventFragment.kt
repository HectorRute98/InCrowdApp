package com.gprosoft.incrowdapp.ui.view.addevent

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.gprosoft.incrowdapp.R
import com.gprosoft.incrowdapp.databinding.FragmentAddEventBinding
import com.gprosoft.incrowdapp.databinding.FragmentProfileBinding
import com.gprosoft.incrowdapp.ui.components.DialogFragmentLoading
import com.gprosoft.incrowdapp.ui.view.profile.ProfileViewModel

class AddEventFragment : Fragment() {

    private var _binding : FragmentAddEventBinding? = null
    private val binding get() = _binding!!
    private val addEventViewModel: AddEventViewModel by viewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddEventBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var dialog = DialogFragmentLoading()

    }

}