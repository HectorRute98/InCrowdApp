package com.gprosoft.incrowdapp.ui.view.profile.myeventsfollowing

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gprosoft.incrowdapp.R
import com.gprosoft.incrowdapp.data.model.Evento

class EventsApuntadoViewHolder(view: View): RecyclerView.ViewHolder(view) {

    val nameBook = view.findViewById<TextView>(R.id.titulo)
    val autor = view.findViewById<TextView>(R.id.autor)

    fun render(eventModel: Evento){
        nameBook.text = eventModel.nombre
        autor.text = eventModel.organizador
    }
}