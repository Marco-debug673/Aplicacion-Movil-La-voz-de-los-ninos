package com.example.lavozdelosnios

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Rect
import android.media.MediaPlayer
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.PopupWindow
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.lavozdelosnios.databinding.ActivityYoCuidoMiCuerpoBinding

class Yo_cuido_mi_cuerpo : AppCompatActivity() {

    private lateinit var binding: ActivityYoCuidoMiCuerpoBinding
    private var mediaPlayer: MediaPlayer? = null
    private var isPlaying: Boolean = false

    private val greenParts = listOf(
        Rect(20, 100, 120, 200), // Manitas
        Rect(165, 100, 265, 200), // Piecitos
        Rect(50, 250, 150, 350), // Cabecita
        Rect(137, 250, 237, 350), // Hombritos
        Rect(93, 30, 193, 130) // Parte superior
    )

    private val yellowParts = listOf(
        Rect(50, 200, 150, 300), // Piernitas
        Rect(137, 200, 237, 300), // Cuellito
        Rect(93, 70, 193, 170), // Carita
        Rect(93, 50, 193, 150), // Parte superior
        Rect(93, 180, 193, 280) // Pancita
    )

    private val redParts = listOf(
        Rect(93, 90, 180, 150), // Boquita
        Rect(93, 120, 180, 190), // Pechito
        Rect(93, 230, 180, 300) // Partes íntimas
    )

    @SuppressLint("InflateParams", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityYoCuidoMiCuerpoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageButton4 = binding.imageButton4
        val imageView7 = binding.imageView7
        binding.ballContainer

        mediaPlayer = MediaPlayer.create(this, R.raw.fondo)
        mediaPlayer?.start()
        isPlaying = true

        setupAudioControls()

        imageView7.setOnClickListener {
            val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val popupView = inflater.inflate(R.layout.dialog_popup, null)

            val popupWindow = PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
            )

            val displayMetrics = resources.displayMetrics
            val screenHeight = displayMetrics.heightPixels

            val adjustedYPosition = (screenHeight / 2 - imageView7.height / 2) + 180

            popupWindow.showAtLocation(
                imageView7,
                Gravity.TOP or Gravity.CENTER_HORIZONTAL,
                0,
                adjustedYPosition
            )

            val dialogText = popupView.findViewById<TextView>(R.id.dialog_text)
            dialogText.text =
                "Hoy hablaremos de las partes del cuerpo, porque yo si cuido mi cuerpo"

            var clickCount = 0

            popupView.setOnClickListener {
                clickCount++

                when (clickCount) {
                    1 -> dialogText.text = "Hoy hablaremos de las partes del cuerpo, porque yo si cuido mi cuerpo"

                    2 -> dialogText.text = "Comenzamos con las partes verdes, mis manitas y mis piecitos. Mi cabecita y mis hombritos."

                    3 -> dialogText.text = "Ahora vamos con las amarillas, mis piernitas y mi cuellito, mi carita y mi pancita."

                    4 -> dialogText.text = "Terminamos con las partes rojas, mi boquita y mi pechito y sobre todo, mis partes intimas..."

                    else -> {
                        popupWindow.dismiss()
                    }
                }
            }
        }

        imageButton4.setOnClickListener {
            val colors = arrayOf("Verde", "Amarillo", "Rojo")
            val colorValues = arrayOf(Color.GREEN, Color.YELLOW, Color.RED)

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Selecciona el color de la pelotita a crear:")
                .setItems(colors) { _, which ->
                    val seletedColor = colorValues[which]

                    val seekBarDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_seekbar, null)
                    val seekBar = seekBarDialogView.findViewById<SeekBar>(R.id.seekBar)
                    val seekBarValue = seekBarDialogView.findViewById<TextView>(R.id.seekBarValue)

                    seekBar.max = 109
                    seekBar.progress = 0
                    seekBarValue.text = "0"

                    seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                        override fun onProgressChanged(
                            seekBar: SeekBar?,
                            progress: Int,
                            fromUser: Boolean
                        ) {
                            seekBarValue.text = (progress + 1).toString()
                        }

                        override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                        override fun onStopTrackingTouch(seekBar: SeekBar?) {}
                    })

                    val seekBarDialog = AlertDialog.Builder(this)
                        .setTitle("Selecciona el número de pelotitas")
                        .setView(seekBarDialogView)
                        .setPositiveButton("Crear") { dialog, _ ->
                            val numberOfBalls = seekBar.progress + 1
                            for(i in 1..numberOfBalls) {
                                crearPelotitas(seletedColor)
                            }
                            dialog.dismiss()
                        }
                        .setNegativeButton("Cancelar") { dialog, _ -> dialog.dismiss()}
                        .create()
                    seekBarDialog.show()
                }
                .setNegativeButton("Cancelar") { dialog, _ -> dialog.dismiss() }
            builder.show()
        }
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

    @SuppressLint("ClickableViewAccessibility")
    private fun crearPelotitas(selectedColor: Int) {
        val ballContainer = findViewById<FrameLayout>(R.id.ballContainer)

        val ballView = View(this)
        val size = 40

        val drawable = android.graphics.drawable.GradientDrawable()
        drawable.shape = android.graphics.drawable.GradientDrawable.OVAL
        drawable.setColor(selectedColor)

        ballView.background = drawable
        val params = FrameLayout.LayoutParams(size, size)
        ballView.layoutParams = params

        val randomX = (0 until (ballContainer.width - size)).random()
        val randomY = (0 until (ballContainer.height - size)).random()

        ballView.x = randomX.toFloat()
        ballView.y = randomY.toFloat()

        binding.imageView3.post{
        val imageView3 = binding.imageView3
        val bodyPositions = mapOf(
            "manitas" to Pair(imageView3.width * 0.2f, imageView3.height * 0.3f),
            "piecitos" to Pair(imageView3.width * 0.7f, imageView3.height * 0.8f),
            "cabecita" to Pair(imageView3.width * 0.5f, imageView3.height * 0.1f),
            "hombritos" to Pair(imageView3.width * 0.5f, imageView3.height * 0.4f),
            "pancita" to Pair(imageView3.width * 0.5f, imageView3.height * 0.6f)
        )

        ballView.setOnTouchListener { view, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> true
                MotionEvent.ACTION_MOVE -> {
                    val parent = view.parent as View
                    val x = event.rawX - parent.left - view.width / 2
                    val y = event.rawY - parent.top - view.height / 2

                    val maxX = parent.width - view.width
                    val maxY = parent.height - view.height

                    view.x = x.coerceIn(0f, maxX.toFloat())
                    view.y = y.coerceIn(0f, maxY.toFloat())
                    true
                }

                MotionEvent.ACTION_UP -> {
                    val ballRect = Rect(
                        view.x.toInt(),
                        view.y.toInt(),
                        (view.x + size).toInt(),
                        (view.y + size).toInt()
                    )

                    val imageView3Rect = Rect(
                        imageView3.left,
                        imageView3.top,
                        imageView3.right,
                        imageView3.bottom
                    )

                    if (Rect.intersects(ballRect, imageView3Rect)) {
                        val correctPosition = bodyPositions.entries.find { (_, position) ->
                            val targetX = imageView3.left + position.first - (size / 2)
                            val targetY = imageView3.top + position.second - (size / 2)
                            val targetRect = Rect(
                                targetX.toInt(),
                                targetY.toInt(),
                                (targetX + size).toInt(),
                                (targetY + size).toInt()
                            )
                            Rect.intersects(ballRect, targetRect)
                        }

                        if (correctPosition != null) {
                            val (targetX, targetY) = correctPosition.value
                            view.x = imageView3.left + targetX - (size / 2)
                            view.y = imageView3.top + targetY - (size / 2)
                        } else {
                            AlertDialog.Builder(this@Yo_cuido_mi_cuerpo)
                                .setTitle("Intenta de nuevo")
                                .setMessage("Coloca la pelotita en la parte correcta del cuerpo.")
                                .setPositiveButton("Ok") { dialog, _ -> dialog.dismiss() }
                                .show()
                        }
                        view.x = (imageView3.x + imageView3.width / 2 - view.width / 2)
                        view.y = (imageView3.y + imageView3.height / 2 - view.height / 2)

                        imageView3.animate().scaleX(1.2f).scaleY(1.2f).setDuration(300).withEndAction {
                            imageView3.animate().scaleX(1f).scaleY(1f).duration = 300
                        }.start()

                        view.setOnTouchListener(null)
                    } else {

                        var correctPosition = false
                        for (part in greenParts + yellowParts + redParts) {
                            if (Rect.intersects(ballRect, part)) {
                                correctPosition = true
                                break
                            }
                        }

                        if (!correctPosition) {
                            AlertDialog.Builder(this@Yo_cuido_mi_cuerpo)
                                .setTitle("Intenta de nuevo")
                                .setMessage("Coloca la pelotita en la parte correcta del cuerpo.")
                                .setPositiveButton("Entendido") { dialog, _ -> dialog.dismiss() }
                                .show()
                        } else {
                            AlertDialog.Builder(this@Yo_cuido_mi_cuerpo)
                                .setTitle("Éxito")
                                .setMessage("¡Pelotita colocada correctamente!")
                                .setPositiveButton("Entendido") { dialog, _ -> dialog.dismiss() }
                                .show()
                            view.visibility = View.VISIBLE
                        }
                    }
                    true
                }
                    else -> false
                }
            }
        }

        ballContainer.addView(ballView)
    }

    override fun onDestroy() {
        super.onDestroy()

        mediaPlayer?.release()
        mediaPlayer = null
    }
}