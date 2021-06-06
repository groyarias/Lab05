package com.example.lab05.logica

import java.io.Serializable

data class Usuario(
    var usuario:String,
    var contrasenia:String
):Serializable{

    fun cambiarContraseña(nuevaContrasenia:String){
        contrasenia = nuevaContrasenia
    }
}
