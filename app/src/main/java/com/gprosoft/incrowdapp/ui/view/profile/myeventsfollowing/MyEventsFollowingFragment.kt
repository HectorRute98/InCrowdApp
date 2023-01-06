package com.gprosoft.incrowdapp.ui.view.profile.myeventsfollowing

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gprosoft.incrowdapp.R
import com.gprosoft.incrowdapp.data.model.Evento
import com.gprosoft.incrowdapp.data.model.UsuarioProvider
import com.gprosoft.incrowdapp.data.network.MyApiEndpointInterface
import com.gprosoft.incrowdapp.ui.components.DialogFragmentLoading
import com.gprosoft.incrowdapp.ui.view.MainActivity2
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyEventsFollowingFragment : Fragment() {
    lateinit var lista : List<Evento>
    lateinit var contexto : Context
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my_events_following, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerEvents2)
        contexto = requireContext()
        recyclerView.layoutManager = LinearLayoutManager(context)
        conseguir_lista(recyclerView)
        return view
    }

    private fun conseguir_lista(recyclerView: RecyclerView?) {
        var dialog = DialogFragmentLoading()
        dialog.show(requireActivity().supportFragmentManager,"customDialog")
        val retrofit = Retrofit.Builder()
            .baseUrl(MyApiEndpointInterface.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(MyApiEndpointInterface::class.java)
        service.eventosApuntados(UsuarioProvider.usuarioModel.username).enqueue(object : Callback<List<Evento>> {
            override fun onResponse(call: Call<List<Evento>>, response: Response<List<Evento>>) {
                if(!response.body().isNullOrEmpty()){
                    dialog.dismiss()
                    lista = response.body()!!
                    recyclerView?.adapter = EventsApuntadoAdapter(lista)
                }else{
                    dialog.dismiss()
                }
            }
            override fun onFailure(call: Call<List<Evento>>, t: Throwable) {
                println("ERROR AL RECIBIR LA LISTA DE EVENTOS DONDE SE HA APUNTADO EL USUARIO")
                dialog.dismiss()
            }
        })
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