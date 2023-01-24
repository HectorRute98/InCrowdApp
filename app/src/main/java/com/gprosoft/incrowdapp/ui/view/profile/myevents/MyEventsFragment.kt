package com.gprosoft.incrowdapp.ui.view.profile.myevents

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gprosoft.incrowdapp.R
import com.gprosoft.incrowdapp.data.model.Evento
import com.gprosoft.incrowdapp.data.network.MyApiEndpointInterface
import com.gprosoft.incrowdapp.data.model.UsuarioProvider
import com.gprosoft.incrowdapp.ui.components.DialogFragmentLoading
import com.gprosoft.incrowdapp.ui.view.MainActivity2
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyEventsFragment : Fragment() {
    lateinit var lista: List<Evento>
    lateinit var contexto: Context
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my_events,container,false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerEvents)
        contexto = requireContext()
        var dialog = DialogFragmentLoading()
        recyclerView.layoutManager = LinearLayoutManager(context)
        conseguir_lista(recyclerView,dialog)

        return view
    }

    private fun conseguir_lista(recyclerView: RecyclerView?, dialog: DialogFragmentLoading) {

        dialog.show(requireActivity().supportFragmentManager,"customDialog")

        val retrofit = Retrofit.Builder()
            .baseUrl(MyApiEndpointInterface.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(MyApiEndpointInterface::class.java)
        service.eventoUsuario(UsuarioProvider.usuarioModel.username).enqueue(object : Callback<List<Evento>> {
            override fun onResponse(call: Call<List<Evento>>, response: Response<List<Evento>>) {
                if (!response.body().isNullOrEmpty()) {
                    dialog.dismiss()
                    lista = response.body()!!
                    recyclerView?.adapter = eventsAdapter(lista)
                }else{
                    dialog.dismiss()
                }
            }

            override fun onFailure(call: Call<List<Evento>>, t: Throwable) {
                Toast.makeText(activity,  "Server error receiving the events" , Toast.LENGTH_SHORT).show()
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