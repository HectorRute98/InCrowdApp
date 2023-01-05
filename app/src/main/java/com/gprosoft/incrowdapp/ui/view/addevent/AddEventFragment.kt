package com.gprosoft.incrowdapp.ui.view.addevent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.gprosoft.incrowdapp.data.model.*
import com.gprosoft.incrowdapp.data.network.MyApiEndpointInterface
import com.gprosoft.incrowdapp.databinding.FragmentAddEventBinding
import com.gprosoft.incrowdapp.ui.components.DialogFragmentLoading
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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

        binding.buttonInfo.setOnClickListener{
            if(
                binding.eventName.text.isEmpty() ||
                binding.eventDate.text.isEmpty() ||
                binding.eventDesc.text.isEmpty() ||
                binding.eventCat.text.isEmpty() ||
                binding.eventCap.text.isEmpty() ||
                binding.eventHour.text.isEmpty()) {
                    Toast.makeText(requireContext(),
                        "FALTAN DATOS", Toast.LENGTH_SHORT).show()
            } else {
                dialog.show(requireActivity().supportFragmentManager,"customDialog")

                val retrofit = Retrofit.Builder()
                    .baseUrl(MyApiEndpointInterface.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                val service = retrofit.create(MyApiEndpointInterface::class.java)

                val evento = Evento(
                    binding.eventName.text.toString(),
                    binding.eventDesc.text.toString(),
                    binding.eventDate.text.toString(),
                    binding.eventHour.text.toString(),
                    false,
                    binding.eventCap.text.toString().toInt(),
                    binding.eventCat.text.toString(), UsuarioProvider.usuarioModel.username)

                println(evento.nombre)

                service.createEvento(evento).enqueue(object : Callback<RespuestaModel> {
                    override fun onResponse(call: Call<RespuestaModel>, response: Response<RespuestaModel>) {
                        val respuesta: RespuestaModel = response.body()!!
                        if(respuesta.success == true) {
                            dialog.dismiss()
                            Toast.makeText(activity,  binding.eventName.text.toString() + " " + "has been created" , Toast.LENGTH_LONG).show()
                            binding.eventName.text = null
                            binding.eventDesc.text = null
                            binding.eventDate.text = null
                            binding.eventHour.text = null
                            binding.eventCap.text = null
                            binding.eventCat.text = null
                        } else {
                            dialog.dismiss()
                            Toast.makeText(activity,  "This event already exists" , Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<RespuestaModel>, t: Throwable) {
                        Toast.makeText(activity,  "Server error creating event" , Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }
                })
            }
        }
    }
}