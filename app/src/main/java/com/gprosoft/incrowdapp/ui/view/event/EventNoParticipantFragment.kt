package com.gprosoft.incrowdapp.ui.view.event

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.gprosoft.incrowdapp.R
import com.gprosoft.incrowdapp.data.model.Evento
import com.gprosoft.incrowdapp.data.model.UsuarioProvider
import com.gprosoft.incrowdapp.data.network.MyApiEndpointInterface
import com.gprosoft.incrowdapp.ui.view.MainActivity2
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EventNoParticipantFragment : Fragment() {
    lateinit var mCtx: Context

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_event_no_participant, container, false)
        mCtx = requireContext()
        val bundle : Bundle? = this.getArguments()
        val evento : Evento = bundle?.getSerializable("evento") as Evento
        setup(view, evento)

        val btn_salir = view.findViewById<ImageView>(R.id.salir_de_evento2)
        btn_salir.setOnClickListener{
            startActivity(requireActivity().intent)
            requireActivity().finish()
            requireActivity().overridePendingTransition(0,0)
        }

        val btn_join_evento = view.findViewById<ImageView>(R.id.btn_join_event2)
        btn_join_evento.setOnClickListener {
            val retrofit = Retrofit.Builder()
                .baseUrl(MyApiEndpointInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val service = retrofit.create(MyApiEndpointInterface::class.java)

            service.anadirParticipante(evento.nombre, UsuarioProvider.usuarioModel.username).enqueue(object :
                Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.body() != null) {
                        val bundle = Bundle()
                        bundle.putSerializable("evento",evento)
                        val activity = view.context as AppCompatActivity
                        val event = EventParticipantFragment()
                        event.arguments = bundle
                        activity.supportFragmentManager.beginTransaction()
                            .replace(R.id.container,event)
                            .addToBackStack(null)
                            .commit()
                    } else {
                        showAlert()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(activity,  "Server error joining event" , Toast.LENGTH_SHORT).show()
                }
            })
        }

        return view
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(mCtx)
        builder.setTitle("Error")
        builder.setMessage("Error joining the event")
        builder.setPositiveButton("Ok", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun setup(view: View, evento: Evento) {
        val nom_event = view.findViewById<TextView>(R.id.nombre_evento)
        val nom_creador = view.findViewById<TextView>(R.id.nombre_creador)
        val fecha = view.findViewById<TextView>(R.id.fecha_evento)
        val hora = view.findViewById<TextView>(R.id.hora_evento)
        val descripcion = view.findViewById<TextView>(R.id.descripcion_evento)
        val categoria = view.findViewById<TextView>(R.id.categoria_evento)

        nom_event.hint = evento.nombre
        nom_creador.hint = evento.organizador
        fecha.hint = evento.fecha
        hora.hint = evento.hora
        descripcion.hint = evento.descripcion
        categoria.hint = evento.categoria
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