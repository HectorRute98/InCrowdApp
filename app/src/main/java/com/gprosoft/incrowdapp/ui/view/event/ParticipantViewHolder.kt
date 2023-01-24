package com.gprosoft.incrowdapp.ui.view.event

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.gprosoft.incrowdapp.R
import com.gprosoft.incrowdapp.data.model.UsuarioModel

class ParticipantViewHolder(view: View): RecyclerView.ViewHolder(view) {

    val name = view.findViewById<TextView>(R.id.item_name)
    val btn : ImageView = view.findViewById(R.id.btn_expulsar21)
    val btn2 : ImageView ?= view.findViewById(R.id.btn_expulsar22)

    fun render(user: UsuarioModel) {
        name.text = user.username
    }



}