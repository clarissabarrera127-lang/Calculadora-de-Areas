package com.example.calculadoradereas

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.PI
import kotlin.math.pow

class CalculoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculo)

        // Enlazamos componentes del XML
        val tvTitulo = findViewById<TextView>(R.id.tvTituloFigura)
        val etCampo1 = findViewById<EditText>(R.id.etCampo1)
        val etCampo2 = findViewById<EditText>(R.id.etCampo2)
        val btnCalcular = findViewById<Button>(R.id.btnCalcular)
        val tvResultado = findViewById<TextView>(R.id.tvResultado)

        // Recibimos el texto que nos mandó la pantalla principal
        val figura = intent.getStringExtra("FIGURA_SELECCIONADA") ?: ""

        // Configuramos la pantalla según la figura elegida
        when (figura) {
            "cuadrado" -> {
                tvTitulo.text = "Área del Cuadrado"
                etCampo1.hint = "Lado (cm)"
                etCampo2.visibility = View.GONE // Ocultamos el campo 2
            }
            "triangulo" -> {
                tvTitulo.text = "Área del Triángulo"
                etCampo1.hint = "Base (cm)"
                etCampo2.hint = "Altura (cm)"
                etCampo2.visibility = View.VISIBLE // Mostramos el campo 2
            }
            "circulo" -> {
                tvTitulo.text = "Área del Círculo"
                etCampo1.hint = "Radio (cm)"
                etCampo2.visibility = View.GONE // Ocultamos el campo 2
            }
        }

        // Acción al presionar el botón Calcular
        btnCalcular.setOnClickListener {
            val txt1 = etCampo1.text.toString()
            val txt2 = etCampo2.text.toString()

            // Validación: Que los campos necesarios no estén vacíos
            if (txt1.isEmpty() || (figura == "triangulo" && txt2.isEmpty())) {
                Toast.makeText(this, "Por favor introduce los datos requeridos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val v1 = txt1.toDouble()
            var area = 0.0

            // Hacemos la operación matemática según corresponda
            when (figura) {
                "cuadrado" -> area = v1.pow(2)               // Lado * Lado
                "circulo" -> area = PI * v1.pow(2)           // pi * r^2
                "triangulo" -> {
                    val v2 = txt2.toDouble()
                    area = (v1 * v2) / 2                     // (Base * Altura) / 2
                }
            }

            // Mostramos el resultado con dos decimales
            tvResultado.text = String.format("Resultado: %.2f cm²", area)
        }
    }
}