package com.example.calculadoradereas

import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.PI
import kotlin.math.pow
import kotlin.math.tan

class CalculoActivity : AppCompatActivity() {

    private var sonidoFiguraId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculo)

        val ivIcono    = findViewById<ImageView>(R.id.ivIconoFigura)
        val tvTitulo   = findViewById<TextView>(R.id.tvTituloFigura)
        val etCampo1   = findViewById<EditText>(R.id.etCampo1)
        val etCampo2   = findViewById<EditText>(R.id.etCampo2)
        val etCampo3   = findViewById<EditText>(R.id.etCampo3)
        val btnCalcular = findViewById<Button>(R.id.btnCalcular)
        val tvResultado = findViewById<TextView>(R.id.tvResultado)

        // Botones inferiores
        val btnLimpiar = findViewById<ImageButton>(R.id.btnLimpiar)
        val btnInicio = findViewById<ImageButton>(R.id.btnInicio)
        val btnSalir = findViewById<ImageButton>(R.id.btnSalir)

        val figura = intent.getStringExtra("FIGURA_SELECCIONADA") ?: ""

        // ── Configuración de pantalla según figura ──────────────────────────
        when (figura) {
            "cuadrado" -> {
                tvTitulo.text = "Área del Cuadrado"
                etCampo1.hint = "Lado (cm)"
                etCampo2.visibility = View.GONE
                etCampo3.visibility = View.GONE
                ivIcono.setImageResource(R.drawable.cuadrado)
                sonidoFiguraId = R.raw.cuadrado
            }
            "triangulo" -> {
                tvTitulo.text = "Área del Triángulo"
                etCampo1.hint = "Base (cm)"
                etCampo2.hint = "Altura (cm)"
                etCampo2.visibility = View.VISIBLE
                etCampo3.visibility = View.GONE
                ivIcono.setImageResource(R.drawable.triangulo)
                sonidoFiguraId = R.raw.triangulo
            }
            "circulo" -> {
                tvTitulo.text = "Área del Círculo"
                etCampo1.hint = "Radio (cm)"
                etCampo2.visibility = View.GONE
                etCampo3.visibility = View.GONE
                ivIcono.setImageResource(R.drawable.circulo)
                sonidoFiguraId = R.raw.circulo
            }
            "rectangulo" -> {
                tvTitulo.text = "Área del Rectángulo"
                etCampo1.hint = "Base (cm)"
                etCampo2.hint = "Altura (cm)"
                etCampo2.visibility = View.VISIBLE
                etCampo3.visibility = View.GONE
                ivIcono.setImageResource(R.drawable.rectangulo)
                sonidoFiguraId = R.raw.rectangulo
            }
            "trapecio" -> {
                tvTitulo.text = "Área del Trapecio"
                etCampo1.hint = "Base mayor (cm)"
                etCampo2.hint = "Base menor (cm)"
                etCampo3.hint = "Altura (cm)"
                etCampo2.visibility = View.VISIBLE
                etCampo3.visibility = View.VISIBLE
                ivIcono.setImageResource(R.drawable.trapecio)
                sonidoFiguraId = R.raw.trapecio
            }
            "poligono" -> {
                tvTitulo.text = "Área del Polígono Regular"
                etCampo1.hint = "Longitud del lado (cm)"
                etCampo2.hint = "Número de lados (≥ 5)"
                etCampo2.visibility = View.VISIBLE
                etCampo3.visibility = View.GONE
                ivIcono.setImageResource(R.drawable.poligono)
                sonidoFiguraId = R.raw.poligono
            }
            "rombo" -> {
                tvTitulo.text = "Área del Rombo"
                etCampo1.hint = "Diagonal mayor (cm)"
                etCampo2.hint = "Diagonal menor (cm)"
                etCampo2.visibility = View.VISIBLE
                etCampo3.visibility = View.GONE
                ivIcono.setImageResource(R.drawable.rombo)
                sonidoFiguraId = R.raw.rombo
            }
            "ovalo" -> {
                tvTitulo.text = "Área del Óvalo"
                etCampo1.hint = "Radio mayor (cm)"
                etCampo2.hint = "Radio menor (cm)"
                etCampo2.visibility = View.VISIBLE
                etCampo3.visibility = View.GONE
                ivIcono.setImageResource(R.drawable.ovalo)
                sonidoFiguraId = R.raw.ovalo
            }
            "paralelogramo" -> {
                tvTitulo.text = "Área del Paralelogramo"
                etCampo1.hint = "Base (cm)"
                etCampo2.hint = "Altura (cm)"
                etCampo2.visibility = View.VISIBLE
                etCampo3.visibility = View.GONE
                ivIcono.setImageResource(R.drawable.paralelogramo)
                sonidoFiguraId = R.raw.paralelogramo
            }
        }

        // ── Calcular ────────────────────────────────────────────────────────
        btnCalcular.setOnClickListener {
            val txt1 = etCampo1.text.toString()
            val txt2 = etCampo2.text.toString()
            val txt3 = etCampo3.text.toString()

            val necesitaCampo2 = figura in listOf(
                "triangulo",
                "rectangulo",
                "trapecio",
                "poligono",
                "rombo",
                "ovalo",
                "paralelogramo"
            )
            val necesitaCampo3 = figura == "trapecio"

            if (txt1.isEmpty()
                || (necesitaCampo2 && txt2.isEmpty())
                || (necesitaCampo3 && txt3.isEmpty())) {
                Toast.makeText(this, "Por favor introduce los datos requeridos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (figura == "poligono") {
                val n = txt2.toDoubleOrNull()
                if (n == null || n < 5 || n != n.toLong().toDouble()) {
                    Toast.makeText(this, "El número de lados debe ser un entero ≥ 5", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }

            reproducirSonido(sonidoFiguraId)

            val v1 = txt1.toDouble()
            var area = 0.0

            when (figura) {
                "cuadrado"   -> area = v1.pow(2)
                "circulo"    -> area = PI * v1.pow(2)
                "triangulo"  -> area = (v1 * txt2.toDouble()) / 2.0
                "rectangulo" -> area = v1 * txt2.toDouble()
                "trapecio"   -> {
                    val baseMayor = v1
                    val baseMenor = txt2.toDouble()
                    val altura    = txt3.toDouble()
                    area = ((baseMayor + baseMenor) / 2.0) * altura
                }
                "poligono"   -> {
                    val lado = v1
                    val n    = txt2.toDouble()
                    area = (n * lado.pow(2)) / (4.0 * tan(PI / n))
                }
                "rombo" -> {
                    val diagonalMayor = v1
                    val diagonalMenor = txt2.toDouble()
                    area = (diagonalMayor * diagonalMenor) / 2.0
                }

                "ovalo" -> {
                    val radioMayor = v1
                    val radioMenor = txt2.toDouble()
                    area = PI * radioMayor * radioMenor
                }

                "paralelogramo" -> {
                    val base = v1
                    val altura = txt2.toDouble()
                    area = base * altura
                }
            }

            tvResultado.text = String.format("Resultado: %.2f cm²", area)
        }

        // ── Lógica de los botones inferiores ────────────────────────────────

        // 1. Limpiar campos
        btnLimpiar.setOnClickListener {
            etCampo1.text.clear()
            etCampo2.text.clear()
            etCampo3.text.clear()
            tvResultado.text = "Resultado: "
            etCampo1.requestFocus()
        }

        // 2. Regresar al menú principal
        btnInicio.setOnClickListener {
            finish()
        }

        // 3. Salir completamente de la app
        btnSalir.setOnClickListener {
            finishAffinity()
        }
    }

    private fun reproducirSonido(idSonido: Int) {
        if (idSonido != 0) {
            val mp = MediaPlayer.create(this, idSonido)
            mp.setOnCompletionListener { it.release() }
            mp.start()
        }
    }
}