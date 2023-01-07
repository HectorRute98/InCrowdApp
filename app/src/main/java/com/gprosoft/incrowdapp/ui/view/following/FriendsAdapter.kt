package com.gprosoft.incrowdapp.ui.view.following

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.gprosoft.incrowdapp.R
import com.gprosoft.incrowdapp.data.model.UsuarioModel
import com.gprosoft.incrowdapp.data.model.UsuarioProvider
import com.gprosoft.incrowdapp.data.network.MyApiEndpointInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FriendsAdapter(private val friends : List<UsuarioModel>) : RecyclerView.Adapter<FriendsAdapter.FriendsViewHolder>() {

    class FriendsViewHolder(view : View) : RecyclerView.ViewHolder(view) {

        private val name = view.findViewById<TextView>(R.id.item_name)
        private val rate = view.findViewById<RatingBar>(R.id.item_rate)

        fun bind(friend : UsuarioModel) {
            name.text = friend.username
            rate.rating = friend.valoracion!!
            rate.setOnRatingBarChangeListener { ratingBar, rating, _ -> rate.rating = friend.valoracion }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.friends_row_item, parent, false)

        return FriendsViewHolder(view)
    }

    override fun onBindViewHolder(holder: FriendsViewHolder, position: Int) {
        val user = friends[position]
        holder.bind(user)
        holder.itemView.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v : View){
                val retrofit = Retrofit.Builder()
                    .baseUrl(MyApiEndpointInterface.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                val service = retrofit.create(MyApiEndpointInterface::class.java)
                service.amigosUsuario(UsuarioProvider.usuarioModel.username).enqueue(object :
                    Callback<List<UsuarioModel>> {
                    override fun onResponse(call: Call<List<UsuarioModel>>, response: Response<List<UsuarioModel>>) {
                        if (response.body() != null) {
                            val friendslist: List<UsuarioModel> = response.body()!!
                            val check = friendslist.find { it.username == user.username }
                            if (check == null) {
                                goUserPage(v, user)
                            } else {
                                goFriendPage(v, user)
                            }
                        }
                    }

                    override fun onFailure(call: Call<List<UsuarioModel>>, t: Throwable) {
                        println("ERROR AL RECIBIR LA LISTA DE AMIGOS")
                    }
                })
            }
        })
    }

    override fun getItemCount(): Int = friends.size

    private fun goUserPage(v : View, user : UsuarioModel) {
        val bundle = Bundle()
        bundle.putSerializable("usuario", user)
        val activity = v.context as AppCompatActivity
        val transit = UserPageFragment()
        transit.arguments = bundle
        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.container, transit)
            .addToBackStack(null)
            .commit()
    }

    private fun goFriendPage(v : View, user : UsuarioModel) {
        val bundle = Bundle()
        bundle.putSerializable("usuario", user)
        val activity = v.context as AppCompatActivity
        val transit = FriendPageFragment()
        transit.arguments = bundle
        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.container, transit)
            .addToBackStack(null)
            .commit()
    }
}