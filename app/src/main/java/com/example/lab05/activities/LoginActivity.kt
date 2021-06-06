package com.example.lab05.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lab05.R
import com.example.lab05.logica.Modelo
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        handleLogin()
        handleRegistro()
        handleCambioContrasenia()

    }

    private fun handleRegistro() {
        tv_registrar.setOnClickListener {
            var intent:Intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)

        }
    }

    private fun handleCambioContrasenia(){
        tv_cambiar.setOnClickListener {
            var intent:Intent = Intent(this, ContraseniaActivity::class.java)
            startActivity(intent)

        }
    }

    private fun handleLogin(){
        btn_ingresar.setOnClickListener {
            var usuario = et_usuario.text.toString()
            var contrasenia = et_contrasenia.text.toString()

            if (!usuario.isEmpty() && !contrasenia.isEmpty()){
                var usuarioIntento = Modelo.obtenerUsuario(usuario)
                if(usuarioIntento!=null){
                    if(usuarioIntento.usuario.equals(usuario) && usuarioIntento.contrasenia.equals(contrasenia)){
                        var intent = Intent(this,NavDrawerActivity::class.java)
                        startActivity(intent)
                        finish()
                    }else{
                        Toast.makeText(this, "Usuario o contraseña inválidas", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(this, "No se ha encontrado al usuario", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "Se deben llenar todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}