package com.example.restaurante

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.restaurante.databinding.ActivityCadastroBinding
import com.example.restaurante.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore

class Cadastro : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroBinding

    private val fr = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnEntrar.setOnClickListener {m->
            val idUsuario = fr.collection("Clientes").document().id
            val nome = binding.editUsuario.text.toString()
            val senha = binding.editSenha.text.toString()


            if (nome.isEmpty() || senha.isEmpty ()) {
                val snackbar = Snackbar.make(m, "campos vazio", Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.show()

            } else {
                val usuariosMap = hashMapOf(
                    "nome" to nome,
                    "senha" to senha
                )
                fr.collection("Clientes").document(idUsuario)
                    .set(usuariosMap).addOnCompleteListener {
                        login()
                    }.addOnFailureListener {
                        val snackbar = Snackbar.make(m, "Falha", Snackbar.LENGTH_SHORT)
                        snackbar.setBackgroundTint(Color.RED)
                        snackbar.show()
                    }
            }
        }

        binding.temConta.setOnClickListener {
            login()
        }


    }

    private fun login(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}