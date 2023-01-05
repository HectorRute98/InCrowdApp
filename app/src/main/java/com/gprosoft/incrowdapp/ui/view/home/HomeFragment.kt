package com.gprosoft.incrowdapp.ui.view.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.gprosoft.incrowdapp.R
import com.gprosoft.incrowdapp.data.model.UsuarioProvider
import com.gprosoft.incrowdapp.databinding.FragmentHomeBinding
import com.gprosoft.incrowdapp.databinding.FragmentLoginBinding
import com.gprosoft.incrowdapp.ui.components.DialogFragmentLoading
import com.gprosoft.incrowdapp.ui.view.MainActivity2
import com.gprosoft.incrowdapp.ui.view.home.info.InfoFragment
import com.gprosoft.incrowdapp.ui.view.login.LoginViewModel
import com.gprosoft.incrowdapp.ui.view.reset.ResetFragment

class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var dialog = DialogFragmentLoading()

        binding.buttonInfo.setOnClickListener {
            println(UsuarioProvider.usuarioModel.password)
            findNavController().navigate(R.id.navigation_info)
        }

    }

}