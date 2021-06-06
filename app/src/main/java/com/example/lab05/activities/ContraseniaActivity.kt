package com.example.lab05.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.lab05.R
import com.example.lab05.logica.Modelo
import com.example.lab05.logica.Usuario
import kotlinx.android.synthetic.main.activity_contrasenia.*

class ContraseniaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contrasenia)

        handleCambioContrasenia()

    }

    private fun handleCambioContrasenia() {
        btn_editUser.setOnClickListener {
            var usuario = et_usuario.text.toString()
            var actual = et_contrasenia_actual.text.toString()
            var nueva = et_nueva_contrasenia.text.toString()
            var confirmacion = et_confirmacion_contrasenia.text.toString()

            if(!usuario.isEmpty() && !actual.isEmpty() && !nueva.isEmpty() && !confirmacion.isEmpty()){
                if(nueva.equals(confirmacion)){
                    var usuario = Modelo.obtenerUsuario(usuario)
                    if(usuario != null){
                        usuario.cambiarContraseña(nueva)
                        Modelo.modificarUsuario(usuario)
                        var intent: Intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }else{
                        Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(this, "Confirmación de la contraseña nueva no coincide", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "Se deben llenar todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}