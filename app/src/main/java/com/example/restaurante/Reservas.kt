package com.example.restaurante

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.restaurante.databinding.ActivityReservadoBinding
import com.example.restaurante.databinding.ActivityReservasBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore

class Reservas : AppCompatActivity() {

    private lateinit var binding: ActivityReservasBinding

    private val fr = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReservasBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val idAnt = intent.getStringExtra("id")
        binding.editUsers.text = "Id: "+idAnt.toString()

        binding.backbtn.setOnClickListener {
            sandesF()
        }

        binding.btnSalvar.setOnClickListener {m->
            val year = binding.data.year
            val month = binding.data.month + 1 // Mês começa em 0, então adicione 1
            val day = binding.data.dayOfMonth

            // Faça o que for necessário com a data selecionada
            val selectedDate = "$year-$month-$day"
            binding.editData.text = selectedDate.toString()
            //sandesF()
            val idUsuario = fr.collection("Reservas").document().id
            if (selectedDate.isEmpty() || idAnt.toString().isEmpty ()) {
                val snackbar = Snackbar.make(m, "erro", Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.show()

            } else {
                val usuariosMap = hashMapOf(
                    "data" to selectedDate,
                    "idUsers" to idAnt.toString()
                )
                fr.collection("Reservas").document(idUsuario)
                    .set(usuariosMap).addOnCompleteListener {
                        val estado = hashMapOf<String, Any>(
                            "status" to "s"
                        )
                        fr.collection("Clientes").document(idAnt.toString())
                            .update(estado).addOnCompleteListener {
                                sandesF()
                            }
                    }.addOnFailureListener {
                        val snackbar = Snackbar.make(m, "Falha", Snackbar.LENGTH_SHORT)
                        snackbar.setBackgroundTint(Color.RED)
                        snackbar.show()
                    }
            }
        }
    }

    private fun sandesF(){
        val idtT = intent.getStringExtra("id")
        val intent = Intent(this, Home::class.java)
        intent.putExtra("id", idtT.toString())
        startActivity(intent)
    }
}