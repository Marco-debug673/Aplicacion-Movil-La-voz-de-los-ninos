package com.example.lavozdelosnios

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.lavozdelosnios.databinding.ActivityMenuJuegosBinding

class MenuJuegos : AppCompatActivity() {

    private lateinit var binding: ActivityMenuJuegosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuJuegosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageView3.setOnClickListener {

            val dialogView = layoutInflater.inflate(R.layout.dialog_instrucciones, null)

            val dialogBuilder = AlertDialog.Builder(this)
                .setTitle("Instrucciones")
                .setView(dialogView)
                .setPositiveButton("Elige un personaje") { _, _ ->
                    showCharacterSelectionDialog()
                }
            val dialog = dialogBuilder.create()
            dialog.show()
        }

        binding.imageView4.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.dialog_instrucciones2, null)

            val dialogBuilder = AlertDialog.Builder(this)
                .setTitle("Instrucciones")
                .setView(dialogView)
                .setPositiveButton("Comenzar") { _, _ ->
                    val intent = Intent(this, Comomesiento::class.java)
                    startActivity(intent)
                }
            val dialog = dialogBuilder.create()
            dialog.show()

        }
    }

    private fun showCharacterSelectionDialog() {
        val dialogView = layoutInflater.inflate(R.layout.seleccion_personaje, null)

        // Configura el AlertDialog con las imágenes
        val alertDialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        // Encuentra las imágenes dentro del diseño del diálogo
        val image1: View = dialogView.findViewById(R.id.imageView1)
        val image2: View = dialogView.findViewById(R.id.imageView2)

        // Configura las acciones de las imágenes
        image1.setOnClickListener {
            // Ir a la actividad para el primer personaje
            val intent = Intent(this, Yo_cuido_mi_cuerpo_parte2::class.java)
            startActivity(intent)
            alertDialog.dismiss()
        }

        image2.setOnClickListener {
            // Ir a la actividad para el segundo personaje
            val intent = Intent(this, Yo_cuido_mi_cuerpo::class.java)
            startActivity(intent)
            alertDialog.dismiss()
        }

        alertDialog.show()
    }
}