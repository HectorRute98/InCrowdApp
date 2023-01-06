package com.gprosoft.incrowdapp.ui.view.event

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gprosoft.incrowdapp.R
import com.gprosoft.incrowdapp.data.model.Mensaje

class MensajeAdapter(private val eventsList: List<Mensaje>) : RecyclerView.Adapter<MensajeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MensajeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MensajeViewHolder(layoutInflater.inflate(R.layout.chat_row, parent, false))
    }

    override fun onBindViewHolder(holder: MensajeViewHolder, position: Int) {
        val item = eventsList[position]
        holder.render(item)

    }

    override fun getItemCount(): Int {
        return eventsList.size
    }
}