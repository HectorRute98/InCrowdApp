package com.gprosoft.incrowdapp.ui.view.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.gprosoft.incrowdapp.R
import com.gprosoft.incrowdapp.databinding.FragmentLoginBinding
import com.gprosoft.incrowdapp.databinding.FragmentRegisterBinding
import com.gprosoft.incrowdapp.ui.components.DialogFragmentLoading
import com.gprosoft.incrowdapp.ui.view.login.LoginFragment
import com.gprosoft.incrowdapp.ui.view.login.LoginViewModel
import com.gprosoft.incrowdapp.ui.view.reset.ResetFragment

class RegisterFragment : Fragment() {
    private var _binding : FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val registerViewModel: RegisterViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var dialog = DialogFragmentLoading()

        registerViewModel.respuestaModel.observe(requireActivity(), Observer { currentRespuesta ->
            if(currentRespuesta.success == true){
                Toast.makeText(requireContext(),
                    binding.usernameRegister.text.toString() + " has been registered", Toast.LENGTH_SHORT).show()
                val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.fragmentContainer, LoginFragment())
                fragmentTransaction.commit()
            }else{
                Toast.makeText(requireContext(),
                    "Invalid data", Toast.LENGTH_SHORT).show()
            }

        })

        registerViewModel.isLoading.observe(requireActivity(), Observer {
            if(it){
                dialog.show(requireActivity().supportFragmentManager,"customDialog")
            }else{
                dialog.dismiss()
            }
        })

        binding.buttonConfirmRegister.setOnClickListener {
            registerViewModel.comprobarRegister(binding.usernameRegister.text.toString(), binding.passwordRegister.text.toString(),
                                                binding.nameRegister.text.toString() , binding.emailRegister.text.toString())
        }

    }
}