package com.example.restaurante

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.restaurante.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Tasks
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.QuerySnapshot


class MainActivity : AppCompatActivity() {

    private val fr = FirebaseFirestore.getInstance()

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnEntrar.setOnClickListener {it->
            val name = binding.editUsuario.text.toString()
            val password = binding.editSenha.text.toString()
            fr.collection("Clientes")
                .whereEqualTo("nome", name)
                .whereEqualTo("senha", password)
                .get()
                .addOnSuccessListener { documents ->
                    if (!documents.isEmpty) {
                        for (document in documents) {
                            val documentId = document.id
                            telaPrincipal(documentId.toString())
                        }
                        //telaPrincipal(documents.id)
                    } else {
                        val snackbar = Snackbar.make(it,"Inexistente", Snackbar.LENGTH_SHORT)
                        snackbar.setBackgroundTint(Color.RED)
                        snackbar.show()
                    }
                }
                .addOnFailureListener { exception ->
                    val snackbar = Snackbar.make(it,"Erro", Snackbar.LENGTH_SHORT)
                    snackbar.setBackgroundTint(Color.RED)
                    snackbar.show()
                }

        }
        binding.semConta.setOnClickListener {
            cadastro()
        }
    }

    private fun telaPrincipal(id: String){
        val intent = Intent(this, Home::class.java)
        intent.putExtra("id", id.toString())
        startActivity(intent)
    }

    private fun cadastro(){
        val intent = Intent(this, Cadastro::class.java)
        startActivity(intent)
    }

}