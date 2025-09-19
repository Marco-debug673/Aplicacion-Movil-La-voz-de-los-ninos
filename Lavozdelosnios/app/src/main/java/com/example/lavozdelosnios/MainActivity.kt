package com.example.lavozdelosnios

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import com.example.lavozdelosnios.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener{
            val intent = Intent(this, MenuJuegos::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("Juegos para niños", binding.textview.toString())
        outState.putInt("foregroundImage_visibility", binding.foregroundImage.visibility)
        outState.putInt("button_visibility", binding.button.visibility)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        val restoredText = savedInstanceState.getString("textview_Juegos para niños")
        restoredText?.let {
            binding.textview.text = it
        }

        binding.foregroundImage.visibility = savedInstanceState.getInt("foreground_visibility", View.VISIBLE)
        binding.button.visibility = savedInstanceState.getInt("button_visibility", View.VISIBLE)
    }
}