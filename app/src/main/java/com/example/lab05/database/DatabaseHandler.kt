package com.example.lab05.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.lab05.logica.Curso
import com.example.lab05.logica.Estudiante

class DatabaseHandler(context: Context):
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

        companion object{
            private const val DATABASE_VERSION = 1 //Versión de la base de datos
            private const val DATABASE_NAME = "CursosDatabase" //Nombre de la base de datos
            //Tablas de la base de datos
            private const val TABLE_ESTUDIANTE = "EstudianteTable"
            private const val TABLE_CURSO = "CursoTable"
            private const val TABLE_DETALLE_MATRICULA = "DetalleMatriculaTable"

            //Atributos Estudiante: Id, Nombre, Apellidos y Edad
            private const val ID_ESTUDIANTE = "idEstudiante"
            private const val NOMBRE = "nombre"
            private const val APELLIDOS = "apellidos"
            private const val EDAD = "edad"

            //Atributos Curso: Id, Descripción y Créditos
            private const val ID_CURSO = "idCurso"
            private const val DESCRIPCION = "descripcion"
            private const val CREDITOS = "creditos"

            //Atributos DetalleMatricula: fkEstudiante, fkCurso
            private const val FK_ESTUDIANTE = "fkEstudiante"
            private const val FK_CURSO = "fkCurso"
        }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREAR_TABLA_ESTUDIANTE = ("CREATE TABLE " + TABLE_ESTUDIANTE + "("
                + ID_ESTUDIANTE + " TEXT PRIMARY KEY,"
                + NOMBRE + " TEXT,"
                + APELLIDOS + " TEXT,"
                + EDAD + " INTEGER)")

        val CREAR_TABLA_CURSO = ("CREATE TABLE " + TABLE_CURSO + "("
                + ID_CURSO + " TEXT PRIMARY KEY,"
                + DESCRIPCION + " TEXT,"
                + CREDITOS + " INTEGER)")

        val CREAR_TABLA_DETALLE_MATRICULA = ("CREATE TABLE " + TABLE_DETALLE_MATRICULA + "("
                + FK_ESTUDIANTE + " TEXT,"
                + FK_CURSO + " TEXT,"
                +"FOREIGN KEY("+FK_ESTUDIANTE+") REFERENCES "+ TABLE_ESTUDIANTE +"("+ ID_ESTUDIANTE +"),"
                +"FOREIGN KEY("+FK_CURSO+") REFERENCES "+ TABLE_CURSO +"("+ ID_CURSO +"))")
        db?.execSQL(CREAR_TABLA_ESTUDIANTE)
        db?.execSQL(CREAR_TABLA_CURSO)
        db?.execSQL(CREAR_TABLA_DETALLE_MATRICULA)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_ESTUDIANTE")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_CURSO")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_DETALLE_MATRICULA")
        onCreate(db)
    }

    fun agregarEstudiante(estudiante: Estudiante):Long{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID_ESTUDIANTE,estudiante.id)
        contentValues.put(NOMBRE,estudiante.nombre)
        contentValues.put(APELLIDOS,estudiante.apellidos)
        contentValues.put(EDAD,estudiante.edad)

        //Insertando fila
        val result = db.insert(TABLE_ESTUDIANTE, null, contentValues)
        db.close()
        return result
    }

    fun obtenerListaEstudiantes():ArrayList<Estudiante>{
        val listaEstudiantes = ArrayList<Estudiante>()

        val consultaSelect = "SELECT * FROM $TABLE_ESTUDIANTE"
        val db = this.readableDatabase
        try {
            val cursor: Cursor = db.rawQuery(consultaSelect, null)
            if (cursor.moveToFirst()){
                do {
                    val estudiante = Estudiante(
                        cursor.getString(cursor.getColumnIndex(ID_ESTUDIANTE)),
                        cursor.getString(cursor.getColumnIndex(NOMBRE)),
                        cursor.getString(cursor.getColumnIndex(APELLIDOS)),
                        cursor.getInt(cursor.getColumnIndex(EDAD))
                    )
                    listaEstudiantes.add(estudiante)
                }while (cursor.moveToNext())
            }
            cursor.close()
        }catch (e:SQLiteException){
            db.execSQL(consultaSelect)
            return ArrayList()
        }
        return listaEstudiantes
    }

    fun actualizarEstudiante(estudiante: Estudiante):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID_ESTUDIANTE, estudiante.id)
        contentValues.put(NOMBRE, estudiante.nombre)
        contentValues.put(APELLIDOS, estudiante.apellidos)
        contentValues.put(EDAD, estudiante.edad)
        //Actualizar fila
        val esExitosa = db.update(TABLE_ESTUDIANTE, contentValues, ID_ESTUDIANTE + "= ?", arrayOf(estudiante.id))
        db.close()
        return esExitosa
    }

    fun eliminarEstudiante(estudiante: Estudiante):Int{
        val db = this.writableDatabase
        //Eliminar fila
        val esExitosa = db.delete(TABLE_ESTUDIANTE, ID_ESTUDIANTE + "= ?", arrayOf(estudiante.id))
        db.close()
        return esExitosa
    }

    fun consultarEstudiante(idEstudiante:String): Estudiante? {

        val consultaSelect = "SELECT * FROM $TABLE_ESTUDIANTE WHERE $ID_ESTUDIANTE = ?"
        val db = this.readableDatabase
        try {
            val cursor: Cursor = db.rawQuery(consultaSelect, arrayOf(idEstudiante))
            if(cursor.moveToFirst()){
                val estudiante = Estudiante(
                    cursor.getString(cursor.getColumnIndex(ID_ESTUDIANTE)),
                    cursor.getString(cursor.getColumnIndex(NOMBRE)),
                    cursor.getString(cursor.getColumnIndex(APELLIDOS)),
                    cursor.getInt(cursor.getColumnIndex(EDAD))
                )
                return estudiante
            }
            cursor.close()
            return null
        }catch (e:SQLiteException){
            db.execSQL(consultaSelect)
            return null
        }
    }

    fun agregarCurso(curso:Curso):Long{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID_CURSO,curso.id)
        contentValues.put(DESCRIPCION,curso.descripcion)
        contentValues.put(CREDITOS,curso.creditos)

        //Insertando fila
        val result = db.insert(TABLE_CURSO, null, contentValues)
        db.close()
        return result
    }

    fun obtenerListaCursos():ArrayList<Curso>{
        val listaCursos = ArrayList<Curso>()

        val consultaSelect = "SELECT * FROM $TABLE_CURSO"
        val db = this.readableDatabase
        try {
            val cursor: Cursor = db.rawQuery(consultaSelect, null)
            if (cursor.moveToFirst()){
                do {
                    val curso = Curso(
                        cursor.getString(cursor.getColumnIndex(ID_CURSO)),
                        cursor.getString(cursor.getColumnIndex(DESCRIPCION)),
                        cursor.getInt(cursor.getColumnIndex(CREDITOS))
                    )
                    listaCursos.add(curso)
                }while (cursor.moveToNext())
            }
            cursor.close()
        }catch (e:SQLiteException){
            db.execSQL(consultaSelect)
            return ArrayList()
        }
        return listaCursos
    }

    fun actualizarCurso(curso: Curso):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID_CURSO, curso.id)
        contentValues.put(DESCRIPCION, curso.descripcion)
        contentValues.put(CREDITOS, curso.creditos)
        //Actualizar fila
        val esExitosa = db.update(TABLE_CURSO, contentValues, ID_CURSO + "= ?", arrayOf(curso.id))
        db.close()
        return esExitosa
    }

    fun eliminarCurso(curso: Curso):Int{
        val db = this.writableDatabase
        //Eliminar fila
        val esExitosa = db.delete(TABLE_CURSO, ID_CURSO + "= ?", arrayOf(curso.id))
        db.close()
        return esExitosa
    }

    fun consultarCurso(idCurso:String): Curso? {

        val consultaSelect = "SELECT * FROM $TABLE_CURSO WHERE $ID_CURSO = ?"
        val db = this.readableDatabase
        try {
            val cursor: Cursor = db.rawQuery(consultaSelect, arrayOf(idCurso))
            cursor.moveToFirst()
            val curso = Curso(
                cursor.getString(cursor.getColumnIndex(ID_CURSO)),
                cursor.getString(cursor.getColumnIndex(DESCRIPCION)),
                cursor.getInt(cursor.getColumnIndex(CREDITOS))
            )
            cursor.close()
            return curso
        }catch (e:SQLiteException){
            db.execSQL(consultaSelect)
            return null
        }
    }

    fun agregarDetalleMatricula(idEstudiante: String, idCurso: String):Long{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(FK_ESTUDIANTE,idEstudiante)
        contentValues.put(FK_CURSO,idCurso)

        Log.d("Matricula","$idEstudiante $idCurso")

        //Insertando fila
        val result = db.insert(TABLE_DETALLE_MATRICULA, null, contentValues)
        db.close()
        return result
    }

    fun obtenerCursoMatriculadosPorEstudiante(idEstudiante:String):ArrayList<Curso>{
        var cursosMatriculados:ArrayList<Curso> = ArrayList()

        var consultaSelect = "SELECT $ID_CURSO,$DESCRIPCION,$CREDITOS FROM $TABLE_CURSO " +
                "LEFT JOIN $TABLE_DETALLE_MATRICULA ON $TABLE_DETALLE_MATRICULA.$FK_CURSO = $TABLE_CURSO.$ID_CURSO " +
                "LEFT JOIN $TABLE_ESTUDIANTE ON $TABLE_ESTUDIANTE.$ID_ESTUDIANTE = $TABLE_DETALLE_MATRICULA.$FK_ESTUDIANTE " +
                "WHERE $TABLE_ESTUDIANTE.$ID_ESTUDIANTE = ?"

        val db = this.readableDatabase
        try {
            val cursor: Cursor = db.rawQuery(consultaSelect, arrayOf(idEstudiante))
            if (cursor.moveToFirst()){
                do {
                    val curso = Curso(
                        cursor.getString(cursor.getColumnIndex(ID_CURSO)),
                        cursor.getString(cursor.getColumnIndex(DESCRIPCION)),
                        cursor.getInt(cursor.getColumnIndex(CREDITOS))
                    )
                    cursosMatriculados.add(curso)
                }while (cursor.moveToNext())
            }
            cursor.close()
        }catch (e:SQLiteException){
            db.execSQL(consultaSelect)
            return ArrayList()
        }
        return cursosMatriculados
    }
}