package com.gprosoft.incrowdapp.ui.view.profile.myevents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.gprosoft.incrowdapp.R
import com.gprosoft.incrowdapp.data.model.Evento
import com.gprosoft.incrowdapp.data.model.EventoProvider

class eventsAdapter(private val eventsList: List<Evento>) : RecyclerView.Adapter<eventsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): eventsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return eventsViewHolder(layoutInflater.inflate(R.layout.item_modificar_evento, parent, false))
    }

    override fun onBindViewHolder(holder: eventsViewHolder, position: Int) {
        val item = eventsList[position]
        holder.render(item)
        holder.button21.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v : View){
                EventoProvider.eventoModel = item
                //findNavController().navigate(R.id.navigation_modifyprofile)
                val bundle = Bundle()
                bundle.putSerializable("evento",item)
                val activity = v.context as AppCompatActivity
                val modify = ModifyEventFragment()
                modify.arguments = bundle
                activity.supportFragmentManager.beginTransaction()
                    .replace(R.id.container,modify)
                    .addToBackStack(null)
                    .commit()
            }
        })

        /*holder.itemView.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v : View){
                val bundle = Bundle()
                bundle.putSerializable("evento",item)
                val activity = v.context as AppCompatActivity
                val event = eventParticipant()
                event.arguments = bundle
                activity.supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container,event)
                    .addToBackStack(null)
                    .commit()
            }
        }) */
    }

    override fun getItemCount(): Int {
        return eventsList.size
    }
}