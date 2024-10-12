package com.example.restaurante

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.restaurante.databinding.ActivityFrutasBinding
import com.example.restaurante.databinding.ActivityHomeBinding
import com.example.restaurante.databinding.ActivityReservadoBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore

class Reservado : AppCompatActivity() {

    private lateinit var binding: ActivityReservadoBinding

    private val fr = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReservadoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val idT = intent.getStringExtra("id").toString()

        fr.collection("Reservas")
            .whereEqualTo("idUsers", idT)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    for (document in documents) {
                        binding.dataRerva.text =  document.getString("data").toString()
                    }
                    //telaPrincipal(documents.id)
                } else {
                }
            }
            .addOnFailureListener { exception ->
            }

        binding.btnLogout.setOnClickListener {
            sandesF()
        }
    }

    private fun sandesF(){
        val idtT = intent.getStringExtra("id")
        val intent = Intent(this, Home::class.java)
        intent.putExtra("id", idtT.toString())
        startActivity(intent)
    }
}