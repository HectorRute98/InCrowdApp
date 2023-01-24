package com.gprosoft.incrowdapp.ui.view.event

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gprosoft.incrowdapp.R
import com.gprosoft.incrowdapp.data.model.Evento
import com.gprosoft.incrowdapp.data.model.UsuarioModel
import com.gprosoft.incrowdapp.data.network.MyApiEndpointInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ListaParticipantesFragment : Fragment() {
    lateinit var lista: List<UsuarioModel>
    lateinit var contexto: Context
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.lista_participantes_admin, container, false)
        val bundle : Bundle? = this.getArguments()
        var evento : Evento = bundle?.getSerializable("evento") as Evento
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerParticipantes)
        contexto = requireContext()
        recyclerView.layoutManager = LinearLayoutManager(context)
        conseguir_lista(recyclerView,evento)


        return view
    }

    private fun conseguir_lista(recyclerView: RecyclerView?, evento: Evento) {

        val retrofit = Retrofit.Builder()
            .baseUrl(MyApiEndpointInterface.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(MyApiEndpointInterface::class.java)
        println("Tratando de conseguir la lista")
        service.participantes(evento.nombre).enqueue(object :
            Callback<List<UsuarioModel>> {
            override fun onResponse(call: Call<List<UsuarioModel>>, response: Response<List<UsuarioModel>>) {
                if (!response.body().isNullOrEmpty()) {
                    lista = response.body()!!
                    println("ha llegado la lista de participantes")
                    recyclerView?.adapter = ParticipantesAdapter(lista, evento.nombre, true)
                }
            }

            override fun onFailure(call: Call<List<UsuarioModel>>, t: Throwable) {
                println("ERROR AL RECIBIR LA LISTA DE PARTICIPANTES DEL EVENTO")
            }
        })
    }
}