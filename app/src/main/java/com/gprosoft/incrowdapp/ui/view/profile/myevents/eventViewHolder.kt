package com.gprosoft.incrowdapp.ui.view.profile.myevents

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gprosoft.incrowdapp.R
import com.gprosoft.incrowdapp.data.model.Evento

class eventsViewHolder(view: View): RecyclerView.ViewHolder(view) {

    val nameBook = view.findViewById<TextView>(R.id.titulo)
    val autor = view.findViewById<TextView>(R.id.autor)
    val button21 : Button = view.findViewById(R.id.button5)

    fun render(eventModel: Evento){
        nameBook.text = eventModel.nombre
        autor.text = eventModel.organizador
    }
}