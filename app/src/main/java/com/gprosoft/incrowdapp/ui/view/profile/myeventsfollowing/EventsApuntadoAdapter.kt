package com.gprosoft.incrowdapp.ui.view.profile.myeventsfollowing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.gprosoft.incrowdapp.R
import com.gprosoft.incrowdapp.data.model.Evento
import com.gprosoft.incrowdapp.data.model.RespuestaModel
import com.gprosoft.incrowdapp.data.model.UsuarioProvider
import com.gprosoft.incrowdapp.data.network.MyApiEndpointInterface
import com.gprosoft.incrowdapp.ui.view.event.EventNoParticipantFragment
import com.gprosoft.incrowdapp.ui.view.event.EventParticipantFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EventsApuntadoAdapter(private val eventsList: List<Evento>) : RecyclerView.Adapter<EventsApuntadoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsApuntadoViewHolder {
        println("He intentado completar")
        val layoutInflater = LayoutInflater.from(parent.context)
        return EventsApuntadoViewHolder(layoutInflater.inflate(R.layout.item_evento, parent, false))
    }

    override fun onBindViewHolder(holder: EventsApuntadoViewHolder, position: Int) {
        val item = eventsList[position]
        holder.render(item)
        holder.itemView.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v : View){
                val retrofit = Retrofit.Builder()
                    .baseUrl(MyApiEndpointInterface.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                val service = retrofit.create(MyApiEndpointInterface::class.java)
                service.esParticipante(item.nombre,UsuarioProvider.usuarioModel.username).enqueue(object : Callback<RespuestaModel> {
                    override fun onResponse(call: Call<RespuestaModel>, response: Response<RespuestaModel>) {
                        if (response.body()?.success == true) {
                            val bundle = Bundle()
                            bundle.putSerializable("evento",item)
                            val activity = v.context as AppCompatActivity
                            var event = EventParticipantFragment()
                            event.arguments = bundle
                            activity.supportFragmentManager.beginTransaction()
                                .replace(R.id.container,event)
                                .addToBackStack(null)
                                .commit()
                        }else{
                            val bundle = Bundle()
                            bundle.putSerializable("evento",item)
                            val activity = v.context as AppCompatActivity
                            var event = EventNoParticipantFragment()
                            event.arguments = bundle
                            activity.supportFragmentManager.beginTransaction()
                                .replace(R.id.container,event)
                                .addToBackStack(null)
                                .commit()
                        }
                    }

                    override fun onFailure(call: Call<RespuestaModel>, t: Throwable) {
                        Toast.makeText(v.context,  "Server error receiving the list of participants " , Toast.LENGTH_SHORT).show()
                    }
                })

                /*val bundle = Bundle()
                bundle.putSerializable("evento",item)
                val activity = v.context as AppCompatActivity
                var event = EventParticipantFragment()
                event.arguments = bundle
                activity.supportFragmentManager.beginTransaction()
                    .replace(R.id.container,event)
                    .addToBackStack(null)
                    .commit()*/
            }
        })
    }

    override fun getItemCount(): Int {
        return eventsList.size
    }

}
