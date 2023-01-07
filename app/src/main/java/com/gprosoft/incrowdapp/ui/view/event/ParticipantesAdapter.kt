package com.gprosoft.incrowdapp.ui.view.event

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.gprosoft.incrowdapp.R
import com.gprosoft.incrowdapp.data.model.RespuestaModel
import com.gprosoft.incrowdapp.data.model.UsuarioModel
import com.gprosoft.incrowdapp.data.network.MyApiEndpointInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ParticipantesAdapter(
    private val participantList: List<UsuarioModel>,
    val nombre: String,
    val isOwner: Boolean
) : RecyclerView.Adapter<ParticipantViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParticipantViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return if (isOwner) {
            ParticipantViewHolder(layoutInflater.inflate(R.layout.item_lista_participantes, parent, false))
        } else {
            ParticipantViewHolder(layoutInflater.inflate(R.layout.participants_row_item, parent, false))
        }
    }

    override fun onBindViewHolder(holder: ParticipantViewHolder, position: Int) {
        val item = participantList[position]
        holder.render(item)
        if (isOwner) {
            holder.btn.setOnClickListener(object: View.OnClickListener {
                override fun onClick(v : View){
                    mover(item)
                    holder.btn2?.visibility = VISIBLE
                    holder.btn2?.isClickable = false
                    holder.btn.isClickable = false
                    Toast.makeText(v.context,
                        item.username + "has been removed", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    override fun getItemCount(): Int {
        return participantList.size
    }

    private fun mover(item: UsuarioModel) {
        val retrofit = Retrofit.Builder()
            .baseUrl(MyApiEndpointInterface.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(MyApiEndpointInterface::class.java)
        service.deleteParticipante(nombre , item.username).enqueue(object :
            Callback<RespuestaModel> {
            override fun onResponse(
                call: Call<RespuestaModel>,
                response: Response<RespuestaModel>
            ) {

            }

            override fun onFailure(call: Call<RespuestaModel>, t: Throwable) {

            }
        })

    }

    /*private fun goUserPage(v : View, user : Usuario) {
        val bundle = Bundle()
        bundle.putSerializable("usuario", user)
        val activity = v.context as AppCompatActivity
        val transit = UserPageFragment()
        transit.arguments = bundle
        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.containerView, transit)
            .addToBackStack(null)
            .commit()
    }

    private fun goFriendPage(v : View, user : Usuario) {
        val bundle = Bundle()
        bundle.putSerializable("usuario", user)
        val activity = v.context as AppCompatActivity
        val transit = FriendPageFragment()
        transit.arguments = bundle
        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.containerView, transit)
            .addToBackStack(null)
            .commit()
    }*/


}