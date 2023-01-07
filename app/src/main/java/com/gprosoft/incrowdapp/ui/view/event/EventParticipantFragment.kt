package com.gprosoft.incrowdapp.ui.view.event

import android.content.Context
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Im
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gprosoft.incrowdapp.R
import com.gprosoft.incrowdapp.data.model.*
import com.gprosoft.incrowdapp.data.network.MyApiEndpointInterface
import com.gprosoft.incrowdapp.ui.components.DialogFragmentLoading
import com.gprosoft.incrowdapp.ui.view.MainActivity2
import com.gprosoft.incrowdapp.ui.view.profile.myevents.ModifyEventFragment
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EventParticipantFragment : Fragment(){
    private var nombreEvento = ""
    lateinit var mCtx: Context
    lateinit var list: List<Mensaje>
    lateinit private var mensajeEditText: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_event_participant, container, false)
        mCtx = requireContext()

        val bundle: Bundle? = this.getArguments()
        val evento: Evento = bundle?.getSerializable("evento") as Evento
        setup(view, evento)

        mensajeEditText = view.findViewById<EditText>(R.id.escribir_mensaje)

        val listaMensajes = view.findViewById<RecyclerView>(R.id.recyclerMessages)
        listaMensajes.layoutManager= LinearLayoutManager(context)
        getList(listaMensajes)

        val btn_info = view.findViewById<ImageView>(R.id.textView2)
        btn_info.setOnClickListener {
            val builder = android.app.AlertDialog.Builder(activity)
            val vista = layoutInflater.inflate(R.layout.dialoginfo, null)
            builder.setView(vista)
            val dialog = builder.create()
            dialog.show()

            val nom_event2 = vista.findViewById<TextView>(R.id.nombre_evento)
            val nom_creador2 = vista.findViewById<TextView>(R.id.nombre_creador)
            val fecha2 = vista.findViewById<TextView>(R.id.fecha_evento)
            val hora2 = vista.findViewById<TextView>(R.id.hora_evento)
            val descripcion2 = vista.findViewById<TextView>(R.id.descripcion_evento)
            val categoria2 = vista.findViewById<TextView>(R.id.categoria_evento)

            nom_event2.hint = evento.nombre
            nom_creador2.hint = evento.organizador
            fecha2.hint = evento.fecha
            hora2.hint = evento.hora
            descripcion2.hint = evento.descripcion
            categoria2.hint = evento.categoria


        }


        val btn_salir_evento = view.findViewById<ImageView>(R.id.btn_leave_event)
        btn_salir_evento.setOnClickListener {
            var dialog = DialogFragmentLoading()
            if(evento.organizador == UsuarioProvider.usuarioModel.username){
                Toast.makeText(requireContext(), "You cannot leave your own event", Toast.LENGTH_SHORT).show()
            }else{
                dialog.show(requireActivity().supportFragmentManager,"customDialog")
                val retrofit = Retrofit.Builder()
                    .baseUrl(MyApiEndpointInterface.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                val service = retrofit.create(MyApiEndpointInterface::class.java)

                service.deleteParticipante(evento.nombre, UsuarioProvider.usuarioModel.username).enqueue(object :
                    Callback<RespuestaModel> {
                    override fun onResponse(call: Call<RespuestaModel>, response: Response<RespuestaModel>) {
                        val res: RespuestaModel = response.body()!!
                        if (res.success == true) {
                            //val bundle = Bundle()
                            //bundle.putSerializable("evento",evento)
                            dialog.dismiss()
                            Toast.makeText(requireContext(),
                                "Event successfully abandoned", Toast.LENGTH_LONG).show()
                            startActivity(requireActivity().intent)
                            requireActivity().finish()
                            requireActivity().overridePendingTransition(0,0)

                            /*val activity = view.context as AppCompatActivity
                            val event = ProfileFragment()

                            event.arguments = bundle
                                activity.supportFragmentManager.beginTransaction()
                                .replace(R.id.container,event)
                                .addToBackStack(null)
                                .commit()*/
                        } else {
                            dialog.dismiss()
                            showAlertLeaveEvent()
                        }
                    }
                    override fun onFailure(call: Call<RespuestaModel>, t: Throwable) {
                        println("ERROR AL SALIR AL EVENTO")
                    }
                })
            }
        }


        val btn_enviar = view.findViewById<Button>(R.id.send_message)
        btn_enviar.setOnClickListener {
            val texto = mensajeEditText.text.toString()
            if(texto.isNotEmpty()) {
                val MEDIA_TYPE_JSON: MediaType? = MediaType.parse("application/json; charset=utf-8")
                val J = JSONObject()
                J.put("autor", UsuarioProvider.usuarioModel.username)
                J.put("evento", evento.nombre)
                J.put("texto", texto)
                val body: RequestBody = RequestBody.create(MEDIA_TYPE_JSON, J.toString())

                val retrofit = Retrofit.Builder()
                    .baseUrl(MyApiEndpointInterface.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                var m: Mensaje
                val service = retrofit.create(MyApiEndpointInterface::class.java)

                service.createMensaje(body).enqueue(object : Callback<RespuestaModel> {
                    override fun onResponse(
                        call: Call<RespuestaModel>,
                        response: Response<RespuestaModel>
                    ) {
                        val res:RespuestaModel = response.body()!!
                        if (res.success == true) {
                            mensajeEditText.setText("")
                            getList(listaMensajes)
                        } else {
                            showAlertMensajes()
                        }
                    }

                    override fun onFailure(call: Call<RespuestaModel>, t: Throwable) {
                        println("ERROR AL MANDAR MENSAJE")
                    }
                })
            }
        }

        val btn_salir = view.findViewById<ImageView>(R.id.salir_de_evento)
        btn_salir.setOnClickListener{
            startActivity(requireActivity().intent)
            requireActivity().finish()
            requireActivity().overridePendingTransition(0,0)
        }

        val btn_participantes = view.findViewById<ImageView>(R.id.buttonParticipants)
        btn_participantes.setOnClickListener {
            if(UsuarioProvider.usuarioModel.username == evento.organizador){
                val bundle = Bundle()
                bundle.putSerializable("evento",evento)
                println("vamos a la lista de participantes")
                //val activity = v.context as AppCompatActivity
                val lista_admin = ListaParticipantesFragment()
                lista_admin.arguments = bundle
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.container,lista_admin)
                    .addToBackStack(null)
                    .commit()
            }else{
                val builder = android.app.AlertDialog.Builder(activity)
                val vista = layoutInflater.inflate(R.layout.dialogparticipants, null)
                builder.setView(vista)
                val dialog = builder.create()
                dialog.show()
                val recyclerParticipants : RecyclerView = vista.findViewById(R.id.recyclerParticipantes)
                recyclerParticipants.layoutManager = LinearLayoutManager(context)
                val retrofit = Retrofit.Builder()
                    .baseUrl(MyApiEndpointInterface.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                val service = retrofit.create(MyApiEndpointInterface::class.java)
                service.participantes(evento.nombre).enqueue(object : Callback<List<UsuarioModel>> {
                    override fun onResponse(call: Call<List<UsuarioModel>>, response: Response<List<UsuarioModel>>) {
                        if (!response.body().isNullOrEmpty()) {
                            val listaParticipantes = response.body()!!
                            recyclerParticipants.adapter = ParticipantesAdapter(listaParticipantes, evento.nombre, false)
                        }
                    }
                    override fun onFailure(call: Call<List<UsuarioModel>>, t: Throwable) {
                        println("ERROR AL RECIBIR LA LISTA DE PARTICIPANTES DEL EVENTO")
                    }
                })
            }
        }
        return view
    }

    private fun showAlertLeaveEvent() {
        val builder = AlertDialog.Builder(mCtx)
        builder.setTitle("Error")
        builder.setMessage("Error leaving the event")
        builder.setPositiveButton("Ok", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showAlertMensajes() {
        val builder = AlertDialog.Builder(mCtx)
        builder.setTitle("Error")
        builder.setMessage("Error sending a message")
        builder.setPositiveButton("Ok", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun setup(view: View, evento: Evento) {
        val nom_event = view.findViewById<TextView>(R.id.textView9)
        val fecha = view.findViewById<TextView>(R.id.textView10)
        val hora = view.findViewById<TextView>(R.id.textView11)
        nombreEvento = evento.nombre
        nom_event.hint = nombreEvento
        fecha.hint = evento.fecha
        hora.hint = evento.hora
    }

    private fun getList(recyclerView: RecyclerView?) {
        val retrofit = Retrofit.Builder()
            .baseUrl(MyApiEndpointInterface.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(MyApiEndpointInterface::class.java)
        service.mensajeEvento(nombreEvento).enqueue(object : Callback<List<Mensaje>> {
            override fun onResponse(call: Call<List<Mensaje>>, response: Response<List<Mensaje>>) {
                if (response.body() != null) {
                    list = response.body()!!
                    recyclerView?.adapter = MensajeAdapter(list)
                }
            }
            override fun onFailure(call: Call<List<Mensaje>>, t: Throwable) {
                println("ERROR AL CARGAR LOS MENSAJES")
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