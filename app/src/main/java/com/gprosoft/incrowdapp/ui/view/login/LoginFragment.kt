package com.gprosoft.incrowdapp.ui.view.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.gprosoft.incrowdapp.R
import com.gprosoft.incrowdapp.data.model.UsuarioProvider
import com.gprosoft.incrowdapp.databinding.FragmentLoginBinding
import com.gprosoft.incrowdapp.ui.components.DialogFragmentLoading
import com.gprosoft.incrowdapp.ui.view.MainActivity2
import com.gprosoft.incrowdapp.ui.view.reset.ResetFragment


class LoginFragment : Fragment() {

    private var _binding : FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val loginViewModel:LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var dialog = DialogFragmentLoading()

        loginViewModel.respuestaModel.observe(requireActivity(), Observer { currentRespuesta ->
            if(currentRespuesta.success == true){
                val intent = Intent(activity, MainActivity2::class.java)
                startActivity(intent)
                activity?.finish()
                Toast.makeText(requireContext(),
                    "Welcome "+UsuarioProvider.usuarioModel.username, Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(requireContext(),
                    "Invalid credentials" , Toast.LENGTH_SHORT).show()
            }
        })

        loginViewModel.isLoading.observe(requireActivity(), Observer {
            if(it){
                dialog.show(requireActivity().supportFragmentManager,"customDialog")
            }else{
                dialog.dismiss()
            }
        })

        binding.buttonLog.setOnClickListener {
            loginViewModel.comprobarLogin(binding.usernameLogin.text.toString(), binding.passwordLogin.text.toString())
        }

        binding.buttonGoReset.setOnClickListener {
            val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentContainer, ResetFragment())
            fragmentTransaction.commit()
        }

    }

}