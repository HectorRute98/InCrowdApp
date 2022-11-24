package com.gprosoft.incrowdapp.ui.components

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.gprosoft.incrowdapp.R
import com.gprosoft.incrowdapp.databinding.FragmentDialogLoadingBinding
import com.gprosoft.incrowdapp.databinding.FragmentLoginBinding

class DialogFragmentLoading : DialogFragment() {

    private var _binding : FragmentDialogLoadingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDialogLoadingBinding.inflate(inflater,container,false)
        return binding.root
    }

}