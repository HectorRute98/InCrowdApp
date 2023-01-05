package com.gprosoft.incrowdapp.ui.view.profile.myevents

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.gprosoft.incrowdapp.R
import com.gprosoft.incrowdapp.data.model.Evento
import com.gprosoft.incrowdapp.data.network.MyApiEndpointInterface
import com.gprosoft.incrowdapp.data.model.UsuarioProvider
import com.gprosoft.incrowdapp.databinding.FragmentModifyEventBinding
import com.gprosoft.incrowdapp.ui.components.DialogFragmentLoading
import com.gprosoft.incrowdapp.ui.view.MainActivity2
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ModifyEventFragment : Fragment() {

    private var _binding: FragmentModifyEventBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentModifyEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle : Bundle? = this.getArguments()
        var evento : Evento = bundle?.getSerializable("evento") as Evento
        val nom_principal = evento.nombre
        setup(evento)

        binding.buttonInfo2.setOnClickListener{
            if(
                binding.eventName.text.isEmpty() &&
                binding.eventDate.text.isEmpty() &&
                binding.eventDesc.text.isEmpty() &&
                binding.eventCat.text.isEmpty() &&
                binding.eventCap.text.isEmpty() &&
                binding.eventHour.text.isEmpty()) {
                Toast.makeText(requireContext(),
                    "No has introducido ningún dato que cambiar", Toast.LENGTH_SHORT).show()
            } else {
                guardar_cambios(1,evento)
            }

        }

        binding.buttonDeleteAccount.setOnClickListener {
            guardar_cambios(2,evento)
        }

    }

    private fun guardar_cambios(x:Int,evento: Evento) {
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

                    if(!binding.eventCap.text.isNullOrEmpty()){
                        evento.aforo = binding.eventCap.text.toString().toInt()
                    }

                    if(!binding.eventCat.text.isNullOrEmpty()){
                        evento.categoria = binding.eventCat.text.toString()
                    }

                    if(!binding.eventDesc.text.isNullOrEmpty()){
                        evento.descripcion = binding.eventDesc.text.toString()
                    }

                    if(!binding.eventDate.text.isNullOrEmpty()){
                        evento.fecha = binding.eventDate.text.toString()
                    }

                    if(!binding.eventHour.text.isNullOrEmpty()){
                        evento.hora = binding.eventHour.text.toString()
                    }

                    modificar_evento(evento)

                }else if( x == 2){

                    eliminar_evento(evento)

                }
            } else {
                //z.cancel()
                z = Toast.makeText(activity, "Incorrect Password", Toast.LENGTH_SHORT)
                z.show()
            }
        }
    }

    private fun eliminar_evento(evento: Evento) {
        var dialog = DialogFragmentLoading()
        dialog.show(requireActivity().supportFragmentManager,"customDialog")
        val retrofit = Retrofit.Builder()
            .baseUrl(MyApiEndpointInterface.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(MyApiEndpointInterface::class.java)

        service.deleteEvento(evento.nombre).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                dialog.dismiss()
                Toast.makeText(requireContext(),
                    "Evento eliminado exitosamente", Toast.LENGTH_SHORT).show()
                startActivity(requireActivity().intent)
                requireActivity().finish()
                requireActivity().overridePendingTransition(0,0)
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                dialog.dismiss()
                Toast.makeText(requireContext(),
                    "Evento no eliminado, intentalo de nuevo", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun modificar_evento(evento: Evento) {
        var dialog = DialogFragmentLoading()
        dialog.show(requireActivity().supportFragmentManager,"customDialog")
        val MEDIA_TYPE_JSON: MediaType? = MediaType.parse("application/json; charset=utf-8")
        val retrofit = Retrofit.Builder()
            .baseUrl(MyApiEndpointInterface.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(MyApiEndpointInterface::class.java)

        val J = JSONObject()
        J.put("nombre", evento.nombre)
        J.put("descripcion", evento.descripcion)
        J.put("fecha", evento.fecha)
        J.put("hora", evento.hora)
        J.put("esPublico",false)
        J.put("aforo",evento.aforo)
        J.put("categoria", evento.categoria)
        J.put("organizador", evento.organizador)

        val body: RequestBody = RequestBody.create(MEDIA_TYPE_JSON, J.toString())

        service.updateEvento(evento.nombre,body).enqueue(object : Callback<Evento> {
            override fun onResponse(call: Call<Evento>, response: Response<Evento>) {
                if(response.body() != null){
                    dialog.dismiss()
                    Toast.makeText(requireContext(),
                        "Evento modificado exitosamente", Toast.LENGTH_LONG).show()
                    startActivity(requireActivity().intent)
                    requireActivity().finish()
                    requireActivity().overridePendingTransition(0,0)
                }else{
                    dialog.dismiss()
                    Toast.makeText(requireContext(),
                        "Error", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Evento>, t: Throwable) {
                dialog.dismiss()
                println("ERROR AL MODIFICAR EVENTO")
            }
        })

    }

    private fun setup(evento: Evento) {
        binding.eventName.hint = evento.nombre
        binding.eventCap.hint = evento.aforo.toString()
        binding.eventCat.hint = evento.categoria
        binding.eventDate.hint = evento.fecha
        binding.eventHour.hint = evento.hora
        binding.eventDesc.hint = evento.descripcion
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