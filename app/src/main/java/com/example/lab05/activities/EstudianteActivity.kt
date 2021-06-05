package com.example.lab05.activities

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lab05.R
import com.example.lab05.adaptadores.EstudianteAdaptador
import com.example.lab05.database.DatabaseHandler
import com.example.lab05.logica.Estudiante
import kotlinx.android.synthetic.main.activity_estudiante.*
import kotlinx.android.synthetic.main.dialog_actualizar_estudiante.*

class EstudianteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estudiante)

        btnRegistrarEstudiante.setOnClickListener{
            agegarEstudiante()
        }

        configurarListaEstudiantesRecyclerView()

    }

    private fun configurarListaEstudiantesRecyclerView() {
        if (obtenerListaEstudiantes().size > 0){
            rvListadoEstudiantes.visibility = View.VISIBLE
            tvSinRegistroEstudiantes.visibility = View.GONE

            rvListadoEstudiantes.layoutManager = LinearLayoutManager(this)
            val estudianteAdaptador = EstudianteAdaptador(this, obtenerListaEstudiantes())
            rvListadoEstudiantes.adapter = estudianteAdaptador
        }else{
            rvListadoEstudiantes.visibility = View.GONE
            tvSinRegistroEstudiantes.visibility = View.VISIBLE
        }
    }

    private fun agegarEstudiante() {
        val idEstudiante = etIdEstudiante.text.toString()
        val nombre = etNombre.text.toString()
        val apellidos = etApellidos.text.toString()
        var edad = 0

        try {
            edad = Integer.parseInt(etEdad.text.toString())
        }catch (e: NumberFormatException){
            Toast.makeText(this, "Edad ingresada inválida", Toast.LENGTH_LONG).show()
        }

        if(!idEstudiante.isEmpty() && !nombre.isEmpty() && !apellidos.isEmpty() && edad > 0){
            val handlerBaseDatos:DatabaseHandler = DatabaseHandler(this)
            val estado = handlerBaseDatos.agregarEstudiante(Estudiante(idEstudiante,nombre, apellidos, edad))
            if(estado > -1){
                Toast.makeText(this, "Estudiante registrado", Toast.LENGTH_SHORT).show()
                borrarCampos()
                configurarListaEstudiantesRecyclerView()
            }
        }else{
            Toast.makeText(this, "Los campos no pueden estar vacíos o con datos inválidos", Toast.LENGTH_LONG).show()
        }
    }

    fun dialogActualizarEstudiante(estudiante: Estudiante) {
        val dialogActualizar = Dialog(this, R.style.Theme_Dialog)
        dialogActualizar.setCancelable(false)

        dialogActualizar.setContentView(R.layout.dialog_actualizar_estudiante)
        dialogActualizar.dialogIdEst.setText(estudiante.id)
        dialogActualizar.dialogNombre.setText(estudiante.nombre)
        dialogActualizar.dialogApellidos.setText(estudiante.apellidos)
        dialogActualizar.dialogEdad.setText(estudiante.edad.toString())

        dialogActualizar.tvActualizarEst.setOnClickListener(View.OnClickListener {
            val id = dialogActualizar.dialogIdEst.text.toString()
            val nombre = dialogActualizar.dialogNombre.text.toString()
            val apellidos = dialogActualizar.dialogApellidos.text.toString()
            var edad = 0

            try {
                edad = Integer.parseInt(dialogActualizar.dialogEdad.text.toString())
            }catch (e: NumberFormatException){
                Toast.makeText(this, "Ingrese una edad válida",Toast.LENGTH_LONG).show()
            }

            val databaseHandler:DatabaseHandler = DatabaseHandler(this)

            if(!id.isEmpty() && !nombre.isEmpty() && !apellidos.isEmpty() && edad > 0){
                val estado = databaseHandler.actualizarEstudiante(Estudiante(id, nombre, apellidos, edad))
                if(estado > -1){
                    Toast.makeText(applicationContext, "Estudiante actualizado",Toast.LENGTH_SHORT).show()
                    configurarListaEstudiantesRecyclerView()
                    dialogActualizar.dismiss()
                }
            }else{
                Toast.makeText(applicationContext, "Los campos no pueden estar vacíos o con datos inválidos",Toast.LENGTH_LONG).show()
            }
        })
        dialogActualizar.tvCancelarActEst.setOnClickListener(View.OnClickListener {
            dialogActualizar.dismiss()
        })
        dialogActualizar.show()
    }

    fun dialogEliminarEstudiante(estudiante: Estudiante) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Eliminar estudiante")
        builder.setMessage("Desea eliminar el estudiante ${estudiante.id}?")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        builder.setPositiveButton("Aceptar") { dialogInterface, which ->
            val databaseHandler: DatabaseHandler = DatabaseHandler(this)
            val estado = databaseHandler.eliminarEstudiante(estudiante)
            if (estado > -1) {
                Toast.makeText(applicationContext, "Estudiante borrado exitosamente!",Toast.LENGTH_SHORT).show()
                configurarListaEstudiantesRecyclerView()
            }
            dialogInterface.dismiss()
        }
        builder.setNegativeButton("Cancelar"){dialogInterface, which ->
            dialogInterface.dismiss()
        }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun borrarCampos() {
        etIdEstudiante.text.clear()
        etNombre.text.clear()
        etApellidos.text.clear()
        etEdad.text.clear()
    }

    private fun obtenerListaEstudiantes():ArrayList<Estudiante>{

        val handlerBaseDatos: DatabaseHandler = DatabaseHandler(this)
        val listaEstudiantes:ArrayList<Estudiante> = handlerBaseDatos.obtenerListaEstudiantes()
        return listaEstudiantes
    }


}