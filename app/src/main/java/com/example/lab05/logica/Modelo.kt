package com.example.lab05.logica

object Modelo {
    private var usuarios:ArrayList<Usuario> = ArrayList<Usuario>()

    init{
        usuarios.add(Usuario("@admin1","admin"))
        usuarios.add(Usuario("@admin2","admin"))
    }

    fun agregarUsuario(usuario:Usuario){
        usuarios.add(usuario)
    }

    fun obtenerUsuario(usuario:String):Usuario?{
        usuarios.forEach { it ->
            if(it.usuario.equals(usuario))
                return it
        }
        return null
    }

    fun modificarUsuario(usuario:Usuario):Boolean{
        usuarios.forEach { it ->
            if(it.usuario.equals(usuario.usuario)){
                it.cambiarContraseña(usuario.contrasenia)
                return true
            }
        }
        return false
    }
}