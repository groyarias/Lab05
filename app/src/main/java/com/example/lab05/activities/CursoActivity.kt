package com.example.lab05.activities

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lab05.R
import com.example.lab05.adaptadores.CursoAdaptador
import com.example.lab05.database.DatabaseHandler
import com.example.lab05.logica.Curso
import kotlinx.android.synthetic.main.activity_curso.*
import kotlinx.android.synthetic.main.dialog_actualizar_curso.*

class CursoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_curso)

        supportActionBar!!.setTitle("Gestión cursos")
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        btnAgregarCurso.setOnClickListener {
            agregarCurso()
        }

        configurarListaCursosRecyclerView()
    }

    private fun agregarCurso(){
        val idCurso = etIdCurso.text.toString()
        val descripcion = etDescripcion.text.toString()
        var creditos = 0

        try {
            creditos = Integer.parseInt(etCreditos.text.toString())
        }catch (e: NumberFormatException){
            Toast.makeText(this, "Ingrese un valor adecuado en los créditos",Toast.LENGTH_LONG).show()
        }

        if(!idCurso.isEmpty() && !descripcion.isEmpty() && creditos!= 0){
            val handlerBaseDatos:DatabaseHandler = DatabaseHandler(this)
            val estado = handlerBaseDatos.agregarCurso(Curso(idCurso, descripcion, creditos))
            if(estado > -1){
                Toast.makeText(this, "Curso agregado",Toast.LENGTH_SHORT).show()
                borrarCampos()
                configurarListaCursosRecyclerView()
            }
        }else{
            Toast.makeText(this, "Los campos no pueden estar vacios o datos inválidos",Toast.LENGTH_LONG).show()
        }
    }

    private fun obtenerListaCursos():ArrayList<Curso>{
        val handlerBaseDatos:DatabaseHandler = DatabaseHandler(this)
        val listaCursos:ArrayList<Curso> = handlerBaseDatos.obtenerListaCursos()


        return listaCursos
    }

    private fun configurarListaCursosRecyclerView(){
        if (obtenerListaCursos().size > 0){
            rvListadoCursos.visibility = View.VISIBLE
            tvSinCursosDisponibles.visibility = View.GONE

            rvListadoCursos.layoutManager = LinearLayoutManager(this)
            val cursoAdaptador = CursoAdaptador(this, obtenerListaCursos())
            rvListadoCursos.adapter = cursoAdaptador
        }else{
            rvListadoCursos.visibility = View.GONE
            tvSinCursosDisponibles.visibility = View.VISIBLE
        }
    }

    private fun borrarCampos(){
        etIdCurso.text.clear()
        etDescripcion.text.clear()
        etCreditos.text.clear()
    }

    fun dialogActualizarCurso(curso: Curso){
        val dialogActualizar = Dialog(this, R.style.Theme_Dialog)
        dialogActualizar.setCancelable(false)

        dialogActualizar.setContentView(R.layout.dialog_actualizar_curso)
        dialogActualizar.dialogIdCurso.setText(curso.id)
        dialogActualizar.dialogDescripcion.setText(curso.descripcion)
        dialogActualizar.dialogCreditos.setText(curso.creditos.toString())

        dialogActualizar.tvActualizarCurso.setOnClickListener(View.OnClickListener {
            val id = dialogActualizar.dialogIdCurso.text.toString()
            val descr = dialogActualizar.dialogDescripcion.text.toString()
            var cred = 0

            try {
                cred = Integer.parseInt(dialogActualizar.dialogCreditos.text.toString())
            }catch (e: NumberFormatException){
                Toast.makeText(this, "Ingrese un valor adecuado en los créditos",Toast.LENGTH_LONG).show()
            }

            val databaseHandler:DatabaseHandler = DatabaseHandler(this)

            if(!id.isEmpty() && !descr.isEmpty() && cred!= 0){
                val estado = databaseHandler.actualizarCurso(Curso(id,descr,cred))
                if(estado > -1){
                    Toast.makeText(applicationContext, "Curso actualizado",Toast.LENGTH_SHORT).show()
                    configurarListaCursosRecyclerView()
                    dialogActualizar.dismiss()
                }
            }else{
                Toast.makeText(applicationContext, "Los campos no pueden estar vacios o datos inválidos",Toast.LENGTH_LONG).show()
            }
        })
        dialogActualizar.tvCancelarActCurso.setOnClickListener(View.OnClickListener {
            dialogActualizar.dismiss()
        })
        dialogActualizar.show()
    }

    fun dialogEliminarCurso(curso: Curso) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Eliminar curso")
        builder.setMessage("Desea eliminar el curso ${curso.id}?")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        builder.setPositiveButton("Aceptar") { dialogInterface, which ->
            val databaseHandler: DatabaseHandler = DatabaseHandler(this)
            val estado = databaseHandler.eliminarCurso(curso)
            if (estado > -1) {
                Toast.makeText(applicationContext, "Curso borrado exitosamente!",Toast.LENGTH_SHORT).show()
                configurarListaCursosRecyclerView()
            }
            dialogInterface.dismiss()
        }
        builder.setNegativeButton("Cancelar"){dialogInterface, which ->
            dialogInterface.dismiss()
        }

        val alertDialog:AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home ->{
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}