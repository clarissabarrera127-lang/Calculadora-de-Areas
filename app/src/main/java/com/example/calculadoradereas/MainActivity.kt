package com.example.calculadoradereas

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Figuras existentes
        val cardCuadrado  = findViewById<CardView>(R.id.cardCuadrado)
        val cardTriangulo = findViewById<CardView>(R.id.cardTriangulo)
        val cardCirculo   = findViewById<CardView>(R.id.cardCirculo)

        // Figuras nuevas
        val cardRectangulo = findViewById<CardView>(R.id.cardRectangulo)
        val cardTrapecio   = findViewById<CardView>(R.id.cardTrapecio)
        val cardPoligono   = findViewById<CardView>(R.id.cardPoligono)

        // Botón para salir
        val btnSalirApp = findViewById<Button>(R.id.btnSalirApp)

        // --- Figuras existentes ---
        cardCuadrado.setOnClickListener {
            reproducirSonido(R.raw.cuadrado)
            abrirCalculo("cuadrado")
        }
        cardTriangulo.setOnClickListener {
            reproducirSonido(R.raw.triangulo)
            abrirCalculo("triangulo")
        }
        cardCirculo.setOnClickListener {
            reproducirSonido(R.raw.circulo)
            abrirCalculo("circulo")
        }

        // --- Figuras nuevas ---
        cardRectangulo.setOnClickListener {
            reproducirSonido(R.raw.rectangulo)
            abrirCalculo("rectangulo")
        }
        cardTrapecio.setOnClickListener {
            reproducirSonido(R.raw.trapecio)
            abrirCalculo("trapecio")
        }
        cardPoligono.setOnClickListener {
            reproducirSonido(R.raw.poligono)
            abrirCalculo("poligono")
        }

        // --- Lógica para salir de la aplicación ---
        btnSalirApp.setOnClickListener {
            finishAffinity() // Cierra la app completamente
        }
    }

    private fun reproducirSonido(idSonido: Int) {
        val mp = MediaPlayer.create(this, idSonido)
        mp.setOnCompletionListener { it.release() }
        mp.start()
    }

    private fun abrirCalculo(figura: String) {
        val intent = Intent(this, CalculoActivity::class.java)
        intent.putExtra("FIGURA_SELECCIONADA", figura)
        startActivity(intent)
    }
}