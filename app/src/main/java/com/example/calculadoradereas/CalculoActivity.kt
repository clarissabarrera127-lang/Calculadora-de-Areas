package com.example.calculadoradereas

import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.PI
import kotlin.math.pow

class CalculoActivity : AppCompatActivity() {

    // Variable para guardar qué sonido le toca a la figura actual
    private var sonidoFiguraId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculo)

        // Enlazamos componentes del XML (Incluyendo el nuevo ImageView)
        val ivIcono = findViewById<ImageView>(R.id.ivIconoFigura)
        val tvTitulo = findViewById<TextView>(R.id.tvTituloFigura)
        val etCampo1 = findViewById<EditText>(R.id.etCampo1)
        val etCampo2 = findViewById<EditText>(R.id.etCampo2)
        val btnCalcular = findViewById<Button>(R.id.btnCalcular)
        val tvResultado = findViewById<TextView>(R.id.tvResultado)

        // Recibimos el texto que nos mandó la pantalla principal
        val figura = intent.getStringExtra("FIGURA_SELECCIONADA") ?: ""

        // Configuramos la pantalla (Texto, Imagen y Sonido asignado) según la figura
        when (figura) {
            "cuadrado" -> {
                tvTitulo.text = "Área del Cuadrado"
                etCampo1.hint = "Lado (cm)"
                etCampo2.visibility = View.GONE
                ivIcono.setImageResource(R.drawable.cuadrado) // Asigna icono
                sonidoFiguraId = R.raw.cuadrado               // Guarda el ID del sonido
            }
            "triangulo" -> {
                tvTitulo.text = "Área del Triángulo"
                etCampo1.hint = "Base (cm)"
                etCampo2.hint = "Altura (cm)"
                etCampo2.visibility = View.VISIBLE
                ivIcono.setImageResource(R.drawable.triangulo) // Asigna icono
                sonidoFiguraId = R.raw.triangulo               // Guarda el ID del sonido
            }
            "circulo" -> {
                tvTitulo.text = "Área del Círculo"
                etCampo1.hint = "Radio (cm)"
                etCampo2.visibility = View.GONE
                ivIcono.setImageResource(R.drawable.circulo) // Asigna icono
                sonidoFiguraId = R.raw.circulo               // Guarda el ID del sonido
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

            // ¡REPRODUCIR SONIDO! Al pasar la validación, suena la figura
            reproducirSonido(sonidoFiguraId)

            val v1 = txt1.toDouble()
            var area = 0.0

            // Hacemos la operación matemática según corresponda
            when (figura) {
                "cuadrado" -> area = v1.pow(2)
                "circulo" -> area = PI * v1.pow(2)
                "triangulo" -> {
                    val v2 = txt2.toDouble()
                    area = (v1 * v2) / 2
                }
            }

            // Mostramos el resultado con dos decimales
            tvResultado.text = String.format("Resultado: %.2f cm²", area)
        }
    }

    // Función auxiliar para reproducir el audio de la figura
    private fun reproducirSonido(idSonido: Int) {
        if (idSonido != 0) {
            val mp = MediaPlayer.create(this, idSonido)
            mp.setOnCompletionListener { it.release() }
            mp.start()
        }
    }
}