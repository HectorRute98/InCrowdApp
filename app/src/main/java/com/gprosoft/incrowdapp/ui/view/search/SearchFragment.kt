package com.gprosoft.incrowdapp.ui.view.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gprosoft.incrowdapp.R
import com.gprosoft.incrowdapp.data.model.Evento
import com.gprosoft.incrowdapp.data.model.UsuarioModel
import com.gprosoft.incrowdapp.data.model.UsuarioProvider
import com.gprosoft.incrowdapp.data.network.MyApiEndpointInterface
import com.gprosoft.incrowdapp.databinding.FragmentSearchBinding
import com.gprosoft.incrowdapp.ui.view.following.FriendsAdapter
import com.gprosoft.incrowdapp.ui.view.profile.myeventsfollowing.EventsApuntadoAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchFragment : Fragment() {
    lateinit var listE : List<Evento>
    lateinit var listU : List<UsuarioModel>
    lateinit var sublistE : MutableList<Evento>
    lateinit var sublistU : MutableList<UsuarioModel>
    lateinit var mCtx : Context
    lateinit var mRecyclerView : RecyclerView
    var showUsers = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        val searchView = view.findViewById<SearchView>(R.id.searchview)
        val filterButton = view.findViewById<ImageView>(R.id.filter_button)
        val searchLabel = view.findViewById<TextView>(R.id.TextView)
        mRecyclerView = view.findViewById(R.id.recyclerFriends)
        mCtx = requireContext()
        listE = listOf()
        listU = listOf()
        sublistE = mutableListOf()
        sublistU = mutableListOf()
        mRecyclerView.layoutManager = LinearLayoutManager(context)
        getUsersList()
        getEventsList()

        filterButton.setOnClickListener() {
            if (showUsers) {
                showUsers = false
                searchLabel.text = "Search Events"
                mRecyclerView.layoutManager = LinearLayoutManager(context)
                mRecyclerView.adapter = EventsApuntadoAdapter(sublistE)
            } else {
                showUsers = true
                searchLabel.text = "Search Friends"
                mRecyclerView.layoutManager = LinearLayoutManager(context)
                mRecyclerView.adapter = FriendsAdapter(listU)
            }
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String): Boolean {
                if (showUsers) {
                    val sublist = listU.filter {
                        it.username?.contains(p0, ignoreCase = true) ?: false
                    }
                    mRecyclerView.adapter = FriendsAdapter(sublist)

                } else {
                    val sublist = sublistE.filter {
                        it.nombre.contains(p0, ignoreCase = true)
                    }
                    mRecyclerView.adapter = EventsApuntadoAdapter(sublist)

                }
                return true
            }
        })

        return view
    }

    private fun getEventsList() {
        val retrofit = Retrofit.Builder()
            .baseUrl(MyApiEndpointInterface.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(MyApiEndpointInterface::class.java)
        service.eventoList().enqueue(object : Callback<List<Evento>> {
            override fun onResponse(call: Call<List<Evento>>, response: Response<List<Evento>>) {
                if(response.body() != null) {
                    listE = response.body()!!
                    sublistE.clear()
                    sublistE = listE.filter {
                        val elementName = when (it) {
                            is Evento -> it.organizador
                            else -> throw IllegalArgumentException("Element is not a String or Person")
                        }
                        elementName != UsuarioProvider.usuarioModel.username
                    } as MutableList<Evento>
                    mRecyclerView.adapter = EventsApuntadoAdapter(sublistE)
                }
            }

            override fun onFailure(call: Call<List<Evento>>, t: Throwable) {
                Toast.makeText(activity,  "Server error receiving the events" , Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getUsersList() {
        val retrofit = Retrofit.Builder()
            .baseUrl(MyApiEndpointInterface.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(MyApiEndpointInterface::class.java)
        service.userList().enqueue(object : Callback<List<UsuarioModel>> {
            override fun onResponse(call: Call<List<UsuarioModel>>, response: Response<List<UsuarioModel>>) {
                if(response.body() != null) {
                    listU = response.body()!!
                    listU = listU.filterIndexed { i, u -> i >= 0 && i <= listU.lastIndex && u.username != UsuarioProvider.usuarioModel.username }
                }
            }

            override fun onFailure(call: Call<List<UsuarioModel>>, t: Throwable) {
                Toast.makeText(activity,  "Server error receiving the users" , Toast.LENGTH_SHORT).show()
            }
        })
    }
}