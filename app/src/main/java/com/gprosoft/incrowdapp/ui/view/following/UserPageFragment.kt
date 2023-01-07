package com.gprosoft.incrowdapp.ui.view.following

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gprosoft.incrowdapp.R
import com.gprosoft.incrowdapp.data.model.Evento
import com.gprosoft.incrowdapp.data.model.UsuarioModel
import com.gprosoft.incrowdapp.data.model.UsuarioProvider
import com.gprosoft.incrowdapp.data.network.MyApiEndpointInterface
import com.gprosoft.incrowdapp.ui.view.MainActivity2
import com.gprosoft.incrowdapp.ui.view.profile.myeventsfollowing.EventsApuntadoAdapter
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserPageFragment : Fragment() {
    private lateinit var mCtx : Context
    lateinit var list : List<Evento>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bundle : Bundle? = this.arguments
        var user : UsuarioModel = bundle?.getSerializable("usuario") as UsuarioModel
        mCtx = requireContext()
        val view = inflater.inflate(R.layout.fragment_user_page, container, false)
        val username = view.findViewById<TextView>(R.id.user_username)
        val name = view.findViewById<TextView>(R.id.textView5)
        val email = view.findViewById<TextView>(R.id.textView6)
        val rate = view.findViewById<RatingBar>(R.id.user_rate)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerEvents2)
        val label = view.findViewById<TextView>(R.id.label_events)
        label.text = "Events created by" + " " + user.username
        list = listOf()
        recyclerView.layoutManager = LinearLayoutManager(context)
        getList(recyclerView, user)
        username.text = user.username
        name.text = user.name
        rate.rating = user.valoracion!!
        email.text = user.email

        rate.setOnRatingBarChangeListener { ratingBar, rating, _ -> rate.rating =
            user.valoracion!!
        }

        val retrofit = Retrofit.Builder()
            .baseUrl(MyApiEndpointInterface.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(MyApiEndpointInterface::class.java)

        val btn_salir = view.findViewById<ImageView>(R.id.salir_de_evento)
        btn_salir.setOnClickListener{
            startActivity(requireActivity().intent)
            requireActivity().finish()
            requireActivity().overridePendingTransition(0,0)
        }

        val buttonAdd = view.findViewById<ImageView>(R.id.buttonAdd)
        buttonAdd.setOnClickListener() {
            service.anadirAmigo(UsuarioProvider.usuarioModel.username, user.username).enqueue(object :
                Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    Toast.makeText(activity, user.username + " added as friend" , Toast.LENGTH_SHORT).show()
                    val bundle = Bundle()
                    bundle.putSerializable("usuario", user)
                    val activity = view.context as AppCompatActivity
                    val transit = FriendPageFragment()
                    transit.arguments = bundle
                    activity.supportFragmentManager.beginTransaction()
                        .replace(R.id.container, transit)
                        .addToBackStack(null)
                        .commit()
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(activity,  "Server error following user" , Toast.LENGTH_SHORT).show()
                }
            })
        }

        return view
    }

    private fun getList(recyclerView: RecyclerView?, user : UsuarioModel) {
        val retrofit = Retrofit.Builder()
            .baseUrl(MyApiEndpointInterface.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(MyApiEndpointInterface::class.java)
        service.eventoUsuario(user.username).enqueue(object : Callback<List<Evento>> {
            override fun onResponse(call: Call<List<Evento>>, response: Response<List<Evento>>) {
                if(!response.body().isNullOrEmpty()) {
                    list = response.body()!!
                    recyclerView?.adapter = EventsApuntadoAdapter(list)
                }
            }

            override fun onFailure(call: Call<List<Evento>>, t: Throwable) {
                Toast.makeText(activity,  "Server error receiving the events" , Toast.LENGTH_SHORT).show()
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