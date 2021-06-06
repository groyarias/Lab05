package com.example.lab05.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.lab05.R
import com.example.lab05.logica.Modelo
import com.example.lab05.logica.Usuario
import kotlinx.android.synthetic.main.activity_registro.*

class RegistroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        handleRegistro()
    }

    private fun handleRegistro() {
        btnAgregarUsuario.setOnClickListener {
            var usuario = et_usuario.text.toString()
            var contrasenia = et_contrasenia.text.toString()

            if(!usuario.isEmpty() && !contrasenia.isEmpty()){

                var nuevoUsuario = Usuario(usuario, contrasenia)
                Modelo.agregarUsuario(nuevoUsuario)

                var intent:Intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                Toast.makeText(this, "Se deben llenar todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }


}