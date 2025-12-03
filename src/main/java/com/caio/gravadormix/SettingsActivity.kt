package com.caio.gravadormix

import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Conecta com o XML da tela de configurações
        setContentView(R.layout.activity_settings)

        // Exemplo: acessar um RadioButton
        val radioAlta: RadioButton = findViewById(R.id.radioAlta)
        radioAlta.setOnClickListener {
            Toast.makeText(this, "Qualidade Alta selecionada", Toast.LENGTH_SHORT).show()
        }
    }
}