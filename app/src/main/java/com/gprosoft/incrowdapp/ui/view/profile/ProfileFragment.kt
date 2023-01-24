package com.gprosoft.incrowdapp.ui.view.profile

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.gprosoft.incrowdapp.R
import com.gprosoft.incrowdapp.data.model.UsuarioProvider
import com.gprosoft.incrowdapp.databinding.FragmentProfileBinding
import com.gprosoft.incrowdapp.databinding.FragmentResetBinding
import com.gprosoft.incrowdapp.ui.components.DialogFragmentLoading
import com.gprosoft.incrowdapp.ui.view.reset.ResetViewModel

class ProfileFragment : Fragment() {

    private var _binding : FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater,container,false)
        val datos = UsuarioProvider.usuarioModel
        binding.textView4.text = datos.username
        binding.textView5.text = datos.name
        binding.textView6.text = datos.email
        binding.profileRate.rating = datos.valoracion!!
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonModifyAccount.setOnClickListener {
            findNavController().navigate(R.id.navigation_modifyprofile)
        }

        binding.button.setOnClickListener {
            findNavController().navigate(R.id.navigation_myevents)
        }

        binding.button2.setOnClickListener {
            findNavController().navigate(R.id.navigation_myeventsfollowing)
        }


    }

}