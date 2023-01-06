package com.gprosoft.incrowdapp.ui.view.profile.myevents

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.gprosoft.incrowdapp.R
import com.gprosoft.incrowdapp.data.model.Evento
import com.gprosoft.incrowdapp.data.network.MyApiEndpointInterface
import com.gprosoft.incrowdapp.data.model.UsuarioProvider
import com.gprosoft.incrowdapp.ui.components.DialogFragmentLoading
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_modify_event, container, false)
        val bundle : Bundle? = this.getArguments()
        var evento : Evento = bundle?.getSerializable("evento") as Evento
        setup(view , evento)
        return view
    }


    fun setup(view:View,evento: Evento) {

        val eventName = view.findViewById<TextView>(R.id.event_name)
        val eventCap = view.findViewById<TextView>(R.id.event_cap)
        val eventCat = view.findViewById<TextView>(R.id.event_cat)
        val eventHour = view.findViewById<TextView>(R.id.event_hour)
        val eventDate = view.findViewById<TextView>(R.id.event_date)
        val eventDesc = view.findViewById<TextView>(R.id.event_desc)
        val buttonInfo2 = view.findViewById<ImageView>(R.id.buttonInfo2)
        val buttonCancel = view.findViewById<Button>(R.id.buttonCancel)
        val buttonDeleteAccount = view.findViewById<Button>(R.id.buttonDeleteAccount)


        eventName.hint = evento.nombre
        eventCap.hint = evento.aforo.toString()
        eventCat.hint = evento.categoria
        eventDate.hint = evento.fecha
        eventHour.hint = evento.hora
        eventDesc.hint = evento.descripcion

        buttonInfo2.setOnClickListener{
            if(
                eventName.text.isEmpty() &&
                eventDate.text.isEmpty() &&
                eventDesc.text.isEmpty() &&
                eventCat.text.isEmpty() &&
                eventCap.text.isEmpty() &&
                eventHour.text.isEmpty()) {
                Toast.makeText(requireContext(),
                    "No has introducido ningún dato que cambiar", Toast.LENGTH_SHORT).show()
            } else {
                guardar_cambios(1,evento,eventCap,eventName,eventDate,eventHour,eventDesc,eventCat)
            }

        }

        buttonCancel.setOnClickListener {
            startActivity(requireActivity().intent)
            requireActivity().finish()
            requireActivity().overridePendingTransition(0,0)
        }

        buttonDeleteAccount.setOnClickListener {
            guardar_cambios(2,evento,eventCap,eventName,eventDate,eventHour,eventDesc,eventCat)
        }

    }

    private fun guardar_cambios(
        x: Int,
        evento: Evento,
        eventCap: TextView,
        eventName: TextView,
        eventDate: TextView,
        eventHour: TextView,
        eventDesc: TextView,
        eventCat: TextView
    ) {
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

                    if(!eventCap.text.isNullOrEmpty()){
                        evento.aforo = eventCap.text.toString().toInt()
                    }

                    if(!eventCat.text.isNullOrEmpty()){
                        evento.categoria = eventCat.text.toString()
                    }

                    if(!eventDesc.text.isNullOrEmpty()){
                        evento.descripcion = eventDesc.text.toString()
                    }

                    if(!eventDate.text.isNullOrEmpty()){
                        evento.fecha = eventDate.text.toString()
                    }

                    if(!eventHour.text.isNullOrEmpty()){
                        evento.hora = eventHour.text.toString()
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

    /*override fun onAttach(context: Context) {
        (activity as MainActivity2).hideBottomNavigation()
        super.onAttach(context)
    }*/

    /*override fun onDetach() {
        super.onDetach()
        (activity as MainActivity2).showBottomNavigation()
    }*/


}