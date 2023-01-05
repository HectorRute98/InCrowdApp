package com.gprosoft.incrowdapp.ui.view.profile.modify_profile

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.gprosoft.incrowdapp.R
import com.gprosoft.incrowdapp.data.model.RespuestaModel
import com.gprosoft.incrowdapp.data.model.RespuestaProvider
import com.gprosoft.incrowdapp.data.model.UsuarioModel
import com.gprosoft.incrowdapp.data.model.UsuarioProvider
import com.gprosoft.incrowdapp.databinding.FragmentModifyProfileBinding
import com.gprosoft.incrowdapp.ui.components.DialogFragmentLoading
import com.gprosoft.incrowdapp.ui.view.MainActivity
import com.gprosoft.incrowdapp.ui.view.MainActivity2

class ModifyProfileFragment : Fragment() {

    private var _binding : FragmentModifyProfileBinding? = null
    private val binding get() = _binding!!
    private val modifyProfileViewModel: ModifyProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentModifyProfileBinding.inflate(inflater,container,false)
        val datos = UsuarioProvider.usuarioModel
        binding.ETUserName.hint = datos.username
        binding.ETName.hint = datos.name ?: datos.name
        binding.ETEmail.hint = datos.email ?: datos.email
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var dialog = DialogFragmentLoading()

        binding.buttonSaveChanges.setOnClickListener {
            guardar_cambios(1)
        }

        binding.buttonCloseSesion.setOnClickListener {
            salir_eliminar()
        }

        binding.buttonDeleteAccount.setOnClickListener {
            guardar_cambios(2)
        }

        modifyProfileViewModel.isLoading.observe(requireActivity(), Observer {
            if(it){
                dialog.show(requireActivity().supportFragmentManager,"customDialog")
            }else{
                dialog.dismiss()
            }
        })

        modifyProfileViewModel.respuestaModel.observe(requireActivity(), Observer { currentRespuesta ->
            if(currentRespuesta.success == true){
                findNavController().navigate(R.id.navigation_home)
                onDetach()
                Toast.makeText(requireContext(),
                    "Changes saved succesfully", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(requireContext(),
                    currentRespuesta.success.toString() + " " + currentRespuesta.message + " " + currentRespuesta.status, Toast.LENGTH_SHORT).show()
            }

        })

        modifyProfileViewModel.respuestaModel2.observe(requireActivity(), Observer { currentRespuesta2 ->
            println(currentRespuesta2.message + " " + currentRespuesta2.success)
            println("DIME QUE LLEGO POR AQUI")
            if(currentRespuesta2.success == true) {
                println("DIME QUE LLEGO POR AQUI 23232")
                salir_eliminar()
            }
        })


    }

    private fun salir_eliminar() {
        println("QUE SI QUE SI QUE ENTRO AQUI")
        UsuarioProvider.usuarioModel = UsuarioModel(null,null,null,null,null)
        RespuestaProvider.respuesta = RespuestaModel(null,null,null)
        val intent = Intent(activity, MainActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

    private fun guardar_cambios(x:Int) {
        val builder = AlertDialog.Builder(activity)
        val vista = layoutInflater.inflate(R.layout.dialogpassword, null)
        builder.setView(vista)
        val dialog = builder.create()
        dialog.show()
        val cajaPassword = vista.findViewById<EditText>(R.id.password)
        val btnConf: Button = vista.findViewById(R.id.btnConfirmar)
        var z = Toast.makeText(activity, "Incorrect Password", Toast.LENGTH_SHORT)

        btnConf.setOnClickListener {
            z.cancel()
            if (cajaPassword.text.toString() == UsuarioProvider.usuarioModel.password) {
                dialog.hide()
                if(x == 1){
                    modifyProfileViewModel.saveChanges(binding.ETName, binding.ETUserName,
                        binding.ETEmail, binding.ETPassword)
                }else if( x == 2){
                    println("entro al caso correcto")
                    modifyProfileViewModel.deleteAccount()
                }
            } else {
                //z.cancel()
                z = Toast.makeText(activity, "Incorrect Password", Toast.LENGTH_SHORT)
                z.show()
            }
        }
    }

    override fun onAttach(context: Context) {
        (activity as MainActivity2).hideBottomNavigation()
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
        (activity as MainActivity2).showBottomNavigation()
    }



}