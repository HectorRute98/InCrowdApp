package com.gprosoft.incrowdapp.ui.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gprosoft.incrowdapp.R
import com.gprosoft.incrowdapp.data.model.Evento
import com.gprosoft.incrowdapp.data.model.UsuarioProvider
import com.gprosoft.incrowdapp.data.network.MyApiEndpointInterface
import com.gprosoft.incrowdapp.databinding.FragmentHomeBinding
import com.gprosoft.incrowdapp.ui.components.DialogFragmentLoading
import com.gprosoft.incrowdapp.ui.view.profile.myeventsfollowing.EventsApuntadoAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment() {

    lateinit var lista : List<Evento>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val recyclerView = view?.findViewById<RecyclerView>(R.id.recyclerEvents2)
        if (recyclerView != null) {
            recyclerView.layoutManager = LinearLayoutManager(context)
        }
        if (recyclerView != null) {
            generar_lista(recyclerView)
        }

        val btn = view.findViewById<ImageView>(R.id.buttonInfo)

        btn.setOnClickListener {
            println(UsuarioProvider.usuarioModel.password)
            findNavController().navigate(R.id.navigation_info)
        }
        return view
    }

    private fun generar_lista(recyclerView: RecyclerView) {
        var dialog = DialogFragmentLoading()
        dialog.show(requireActivity().supportFragmentManager,"customDialog")

        val retrofit = Retrofit.Builder()
            .baseUrl(MyApiEndpointInterface.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(MyApiEndpointInterface::class.java)

        service.eventosRandom(UsuarioProvider.usuarioModel.username).enqueue(object :
            Callback<List<Evento>> {
            override fun onResponse(call: Call<List<Evento>>, response: Response<List<Evento>>) {
                if(!response.body().isNullOrEmpty()){
                    dialog.dismiss()
                    lista = response.body()!!
                    recyclerView.adapter = EventsApuntadoAdapter(lista)
                }else{
                    println("no he recibido nada")
                    dialog.dismiss()
                }
            }
            override fun onFailure(call: Call<List<Evento>>, t: Throwable) {
                Toast.makeText(activity,  "Server error receiving the events followed" , Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
        })

    }

}