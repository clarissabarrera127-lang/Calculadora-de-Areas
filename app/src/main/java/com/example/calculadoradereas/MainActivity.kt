package com.example.calculadoradereas
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Enlazamos los botones del XML con el código
        val btnCuadrado = findViewById<Button>(R.id.btnCuadrado)
        val btnTriangulo = findViewById<Button>(R.id.btnTriangulo)
        val btnCirculo = findViewById<Button>(R.id.btnCirculo)

        // Escuchamos los clics
        btnCuadrado.setOnClickListener { abrirCalculo("cuadrado") }
        btnTriangulo.setOnClickListener { abrirCalculo("triangulo") }
        btnCirculo.setOnClickListener { abrirCalculo("circulo") }
    }

    // Función para abrir la segunda pantalla enviando un dato ("extra")
    private fun abrirCalculo(figura: String) {
        val intentActual = Intent(this, CalculoActivity::class.java)
        intentActual.putExtra("FIGURA_SELECCIONADA", figura)
        startActivity(intentActual)
    }
}