package com.example.lab05.activities

import android.app.AlertDialog
import android.app.Dialog
import android.database.sqlite.SQLiteException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lab05.R
import com.example.lab05.adaptadores.MatriculaAdaptador
import com.example.lab05.database.DatabaseHandler
import com.example.lab05.logica.Curso
import com.example.lab05.logica.Estudiante
import kotlinx.android.synthetic.main.activity_curso.*
import kotlinx.android.synthetic.main.activity_matricula.*
import kotlinx.android.synthetic.main.dialog_consultar_curso.*
import kotlinx.android.synthetic.main.dialog_consultar_estudiante.*

class MatriculaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matricula)

        supportActionBar!!.setTitle("Matricula")
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        handleConsultaEstudiante()
        handleConsultaCurso()
        handleMatricula()
        handleListarCursosMatriculados()

        configurarListaCursosRecyclerView(ArrayList())

    }

    fun handleConsultaEstudiante(){
        btnConsEst.setOnClickListener{
            try {
                var idEstudiante = etIdEstMatriculado.text.toString()
                if(!idEstudiante.isEmpty()){
                    try {
                        val databaseHandler:DatabaseHandler = DatabaseHandler(this)
                        var estudiante: Estudiante? = databaseHandler.consultarEstudiante(idEstudiante)
                        if(estudiante != null){
                            dialogConsultaEstudiante(estudiante)
                        }else{
                            mostrarDialog("Consulta base de datos","No se ha encontrado un estudiante con este identificador")
                        }
                    }
                    catch (e:SQLiteException){
                        mostrarDialog("Consulta base de datos","Error en base de datos")
                    }
                }else{
                    Toast.makeText(this, "Ingrese la identificación del estudiante", Toast.LENGTH_SHORT).show()
                }
            }catch (e:Exception){
                mostrarDialog("Consulta estudiante","Problemas al realizar la consulta")
            }
        }
    }

    fun dialogConsultaEstudiante(estudiante: Estudiante){
        val dialogConsulta = Dialog(this, R.style.Theme_Dialog)
        dialogConsulta.setCancelable(true)
        dialogConsulta.setContentView(R.layout.dialog_consultar_estudiante)
        dialogConsulta.tvDialogIdEst.text = estudiante.id
        dialogConsulta.tvDialogNombre.text = estudiante.nombre
        dialogConsulta.tvDialogApellidos.text = estudiante.apellidos
        dialogConsulta.tvDialogEdad.text = estudiante.edad.toString()
        dialogConsulta.show()
    }

    fun handleConsultaCurso(){
        btnConsCur.setOnClickListener{
            try {
                var idCurso = etIdCurMatricula.text.toString()
                if(!idCurso.isEmpty()){
                    try {
                        val databaseHandler:DatabaseHandler = DatabaseHandler(this)
                        var curso: Curso? = databaseHandler.consultarCurso(idCurso)
                        if(curso != null){
                            dialogConsultaCurso(curso)
                        }
                    }
                    catch (e:SQLiteException){
                        mostrarDialog("Consulta base de datos","No se han encontrado registros")
                    }
                }else{
                    Toast.makeText(this, "Ingrese el identificador del curso", Toast.LENGTH_SHORT).show()
                }
            }catch (e:Exception){
                mostrarDialog("Consulta curso","No se ha encontrado el curso")
            }
        }
    }

    fun dialogConsultaCurso(curso: Curso){
        val dialogConsulta = Dialog(this, R.style.Theme_Dialog)
        dialogConsulta.setCancelable(true)
        dialogConsulta.setContentView(R.layout.dialog_consultar_curso)
        dialogConsulta.tvDialogIdCurso.text = curso.id
        dialogConsulta.tvDialogDescripcion.text = curso.descripcion
        dialogConsulta.tvDialogCreditos.text = curso.creditos.toString()
        dialogConsulta.show()
    }

    private fun configurarListaCursosRecyclerView(cursosMatriculados:ArrayList<Curso>){
        if (cursosMatriculados.size > 0){
            rvCursosMatriculados.visibility = View.VISIBLE
            tvSinCursosMatriculados.visibility = View.GONE

            rvCursosMatriculados.layoutManager = LinearLayoutManager(this)
            val matriculaAdaptador = MatriculaAdaptador(this, cursosMatriculados)
            rvCursosMatriculados.adapter = matriculaAdaptador
        }else{
            rvCursosMatriculados.visibility = View.GONE
            tvSinCursosMatriculados.visibility = View.VISIBLE
        }
    }

    private fun handleMatricula() {
        btnMatricular.setOnClickListener {
            val idEstudiante = etIdEstMatriculado.text.toString()
            val idCurso = etIdCurMatricula.text.toString()

            if(!idEstudiante.isEmpty() && !idCurso.isEmpty()){
                val handlerBaseDatos:DatabaseHandler = DatabaseHandler(this)
                val estado = handlerBaseDatos.agregarDetalleMatricula(idEstudiante,idCurso)
                if(estado > -1){
                    Toast.makeText(this, "Detalle matricula registrado", Toast.LENGTH_SHORT).show()
                    borrarCampos()
                }else{
                    Toast.makeText(this, "Problemas al realizar la matricula", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "Llene ambos campos", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun handleListarCursosMatriculados() {
        btnCursosMat.setOnClickListener {
            val idEstudiante = etIdEstMatriculado.text.toString()

            if(!idEstudiante.isEmpty()){
                val handlerBaseDatos:DatabaseHandler = DatabaseHandler(this)
                var cursosMatriculados = handlerBaseDatos.obtenerCursoMatriculadosPorEstudiante(idEstudiante)
                configurarListaCursosRecyclerView(cursosMatriculados)
            }else{
                Toast.makeText(this, "Ingrese la identificación del estudiante", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun borrarCampos() {
        etIdEstMatriculado.text.clear()
        etIdCurMatricula.text.clear()
    }


    fun mostrarDialog(title : String,Message : String){
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(true)
        builder.setTitle(title)
        builder.setMessage(Message)
        builder.show()
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