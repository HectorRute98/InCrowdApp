package com.gprosoft.incrowdapp.ui.view.event

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gprosoft.incrowdapp.R
import com.gprosoft.incrowdapp.data.model.Mensaje

class MensajeViewHolder(view: View): RecyclerView.ViewHolder(view) {

    val usuario = view.findViewById<TextView>(R.id.usuario_name)
    val mensaje = view.findViewById<TextView>(R.id.mensaje)

    fun render(mensaje_usuario: Mensaje){
        usuario.text = mensaje_usuario.autor
        mensaje.text = mensaje_usuario.texto
    }
}