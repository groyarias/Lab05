package com.example.lab05.logica

import java.io.Serializable

data class Estudiante(
    var id:String,
    var nombre:String,
    var apellidos:String,
    var edad:Int
):Serializable{
    var cursosMatriculados:ArrayList<Curso> = ArrayList<Curso>()
}
