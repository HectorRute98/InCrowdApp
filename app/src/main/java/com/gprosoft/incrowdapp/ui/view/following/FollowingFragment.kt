package com.gprosoft.incrowdapp.ui.view.following

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gprosoft.incrowdapp.R
import com.gprosoft.incrowdapp.data.model.UsuarioModel
import com.gprosoft.incrowdapp.data.model.UsuarioProvider
import com.gprosoft.incrowdapp.data.network.MyApiEndpointInterface
import com.gprosoft.incrowdapp.databinding.FragmentFollowingBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FollowingFragment : Fragment() {

    lateinit var list : List<UsuarioModel>
    lateinit var sublist : MutableList<UsuarioModel>
    lateinit var mCtx : Context
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_following,container,false)
        val searchView = view.findViewById<SearchView>(R.id.searchview_friends)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerFriends)
        mCtx = requireContext()
        list = listOf()
        sublist = mutableListOf()
        recyclerView.layoutManager = LinearLayoutManager(context)
        getList(recyclerView)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                val sublist = list.filter {
                    it.username?.contains(newText, ignoreCase = true) ?: false
                }
                recyclerView.adapter = FriendsAdapter(sublist)
                return true
            }

        })
        recyclerView.adapter = FriendsAdapter(sublist)
        return view
    }

    private fun getList(recyclerView: RecyclerView?) {
        val retrofit = Retrofit.Builder()
            .baseUrl(MyApiEndpointInterface.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(MyApiEndpointInterface::class.java)
        service.amigosUsuario(UsuarioProvider.usuarioModel.username).enqueue(object : Callback<List<UsuarioModel>> {
            override fun onResponse(call: Call<List<UsuarioModel>>, response: Response<List<UsuarioModel>>) {
                if(response.body() != null) {
                    list = response.body()!!
                    recyclerView?.adapter = FriendsAdapter(list)
                }
            }

            override fun onFailure(call: Call<List<UsuarioModel>>, t: Throwable) {
                println("HA OCURRIDO UN ERROR")
            }
        })
    }
}