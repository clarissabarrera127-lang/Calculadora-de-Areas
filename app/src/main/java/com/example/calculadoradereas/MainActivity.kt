package com.example.calculadoradereas
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Enlazamos las tarjetas contenedoras (CardViews)
        val cardCuadrado = findViewById<CardView>(R.id.cardCuadrado)
        val cardTriangulo = findViewById<CardView>(R.id.cardTriangulo)
        val cardCirculo = findViewById<CardView>(R.id.cardCirculo)

        // Eventos de clic con sonido y navegación
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
    }

    // Función auxiliar para reproducir cualquier archivo de audio de la carpeta raw
    private fun reproducirSonido(idSonido: Int) {
        val mp = MediaPlayer.create(this, idSonido)
        mp.setOnCompletionListener { it.release() } // Libera la memoria RAM cuando el sonido termina
        mp.start()
    }

    private fun abrirCalculo(figura: String) {
        val intentActual = Intent(this, CalculoActivity::class.java)
        intentActual.putExtra("FIGURA_SELECCIONADA", figura)
        startActivity(intentActual)
    }
}