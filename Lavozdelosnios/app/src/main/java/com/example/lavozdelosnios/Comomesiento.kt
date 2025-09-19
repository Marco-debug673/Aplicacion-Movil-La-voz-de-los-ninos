package com.example.lavozdelosnios

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.lavozdelosnios.databinding.ActivityComomesientoBinding

class Comomesiento : AppCompatActivity() {

    private lateinit var binding: ActivityComomesientoBinding
    private var mediaPlayer: MediaPlayer? = null
    private var isPlaying: Boolean = false
    private var currentQuestionIndex = 0
    private val questions = listOf(
        "¿Cómo te sentirías si tus amigos no te dejan jugar con ellos?",
        "¿Cómo te sentirías si ganaras un premio en la escuela?",
        "¿Cómo te sentirías si escucharas un ruido fuerte en la noche?",
        "¿Cómo te sentirías si se rompe tu juguete favorito?",
        "¿Cómo te sentirías si ves una película divertida con tu familia?",
        "¿Cómo te sentirías si te encuentras solo en un lugar desconocido?",
        "¿Cómo te sentirías si tu mascota se enferma?",
        "¿Cómo te sentirías si haces un dibujo muy bonito y todos te felicitan?",
        "¿Cómo te sentirías si pierdes en un juego que querías ganar?",
        "¿Cómo te sentirías si alguien te da un abrazo cuando estás triste?"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityComomesientoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mediaPlayer = MediaPlayer.create(this, R.raw.fondo)
        mediaPlayer?.start()
        isPlaying = true

        setupAudioControls()
        setupImageView2Popup()

        // Activar el movimiento para cada carita en la actividad
        enableDrag(binding.imageView3)
        enableDrag(binding.imageView4)
        enableDrag(binding.imageView5)
        enableDrag(binding.imageView6)
        enableDrag(binding.imageView7)
    }

    private fun setupAudioControls() {
        binding.imageButton3.visibility = View.GONE

        binding.imageButton.setOnClickListener {
            if (!isPlaying) {
                if (mediaPlayer == null) {
                    mediaPlayer = MediaPlayer.create(this, R.raw.fondo)
                }
                mediaPlayer?.start()
                isPlaying = true
                binding.imageButton3.visibility = View.VISIBLE
            } else {
                mediaPlayer?.pause()
                isPlaying = false
                binding.imageButton3.visibility = View.GONE
            }
        }

        binding.imageButton3.setOnClickListener {
            mediaPlayer?.pause()
            isPlaying = false
            binding.imageButton3.visibility = View.GONE
        }
    }

    private fun setupImageView2Popup() {
        binding.imageView2.setOnClickListener {
            showQuestionPopup(it)
        }
    }

    @SuppressLint("InflateParams")
    private fun showQuestionPopup(anchorView: View) {
        val popupView = layoutInflater.inflate(R.layout.popup_question_with_emotions, null)
        val questionTextView = popupView.findViewById<TextView>(R.id.questionTextView)
        questionTextView.text = questions[currentQuestionIndex]

        val popupWindow = PopupWindow(
            popupView,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            true
        )

        popupWindow.showAsDropDown(anchorView, 0, 0)

        // Configurar clic en el PopupWindow para avanzar a la siguiente pregunta
        popupView.setOnClickListener {
            popupWindow.dismiss()
            currentQuestionIndex++
            if (currentQuestionIndex < questions.size) {
                showQuestionPopup(binding.imageView2)
            } else {
                showCompletionDialog()
                currentQuestionIndex = 0
            }
        }
    }

    // Permitir movimiento libre de las caritas en la actividad
    @SuppressLint("ClickableViewAccessibility")
    private fun enableDrag(view: ImageView) {
        view.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    val params = v.layoutParams as ViewGroup.MarginLayoutParams
                    params.leftMargin = event.rawX.toInt() - v.width / 2
                    params.topMargin = event.rawY.toInt() - v.height / 2
                    v.layoutParams = params
                }
                MotionEvent.ACTION_MOVE -> {
                    val params = v.layoutParams as ViewGroup.MarginLayoutParams
                    params.leftMargin = event.rawX.toInt() - v.width / 2
                    params.topMargin = event.rawY.toInt() - v.height / 2
                    v.layoutParams = params
                }
            }
            true
        }
    }

    private fun showCompletionDialog() {
        AlertDialog.Builder(this)
            .setTitle("¡Felicidades!")
            .setMessage("Has contestado todas las preguntas correctamente.")
            .setPositiveButton("Ok") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}