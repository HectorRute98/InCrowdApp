package com.gprosoft.incrowdapp.ui.view.reset

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.gprosoft.incrowdapp.R
import com.gprosoft.incrowdapp.databinding.FragmentRegisterBinding
import com.gprosoft.incrowdapp.databinding.FragmentResetBinding
import com.gprosoft.incrowdapp.ui.components.DialogFragmentLoading
import com.gprosoft.incrowdapp.ui.view.register.RegisterViewModel

class ResetFragment : Fragment() {
    private var _binding : FragmentResetBinding? = null
    private val binding get() = _binding!!
    private val resetViewModel: ResetViewModel by viewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResetBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var dialog = DialogFragmentLoading()

        resetViewModel.respuestaModel.observe(requireActivity(), Observer { currentRespuesta ->
            Toast.makeText(requireContext(),
                currentRespuesta.success.toString() + " " + currentRespuesta.message + " " + currentRespuesta.status, Toast.LENGTH_SHORT).show()
        })

        resetViewModel.isLoading.observe(requireActivity(), Observer {
            if(it){
                dialog.show(requireActivity().supportFragmentManager,"customDialog")
            }else{
                dialog.dismiss()
            }
        })

        binding.buttonSendReset.setOnClickListener {
            resetViewModel.comprobarReset(binding.emailReset.text.toString())
        }
    }
}