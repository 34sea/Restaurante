package com.example.restaurante

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.restaurante.databinding.ActivityFrutasBinding
import com.example.restaurante.databinding.ActivityHomeBinding
import com.google.firebase.firestore.FirebaseFirestore

class Frutas : AppCompatActivity() {

    private lateinit var binding: ActivityFrutasBinding

    private val fr = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFrutasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val idT = intent.getStringExtra("id")

        binding.btnLogout.setOnClickListener {
            sandesF()
        }

        binding.btnReservas.setOnClickListener {
            fr.collection("Clientes").document(idT.toString())
                .get().addOnSuccessListener {dc->
                    val status = dc.getString("status").toString()
                    if(status == "s"){
                        reser()
                    }else{
                        reservas()
                    }
                }
        }

        binding.sandes.setOnClickListener {
            sandesF()
        }

        binding.salgado.setOnClickListener {
            salgadosF()
        }

        binding.mariscos.setOnClickListener {
            mariscosF()
        }

        binding.frutas.setOnClickListener {
            frutasF()
        }
    }

    private fun login(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun reservas(){
        val id = intent.getStringExtra("id")
        val intent = Intent(this, Reservas::class.java)
        intent.putExtra("id", id.toString())
        startActivity(intent)
    }

    private fun reser(){
        val idtT = intent.getStringExtra("id")
        val intent = Intent(this, Reservado::class.java)
        intent.putExtra("id", idtT.toString())
        startActivity(intent)
    }

    private fun sandesF(){
        val idtT = intent.getStringExtra("id")
        val intent = Intent(this, Home::class.java)
        intent.putExtra("id", idtT.toString())
        startActivity(intent)
    }

    private fun salgadosF(){
        val idtT = intent.getStringExtra("id")
        val intent = Intent(this, Salgados::class.java)
        intent.putExtra("id", idtT.toString())
        startActivity(intent)
    }
    private fun mariscosF(){
        val idtT = intent.getStringExtra("id")
        val intent = Intent(this, Mariscos::class.java)
        intent.putExtra("id", idtT.toString())
        startActivity(intent)
    }

    private fun frutasF(){
        val idtT = intent.getStringExtra("id")
        val intent = Intent(this, Frutas::class.java)
        intent.putExtra("id", idtT.toString())
        startActivity(intent)
    }
}