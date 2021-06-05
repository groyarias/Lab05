package com.example.lab05.adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.lab05.R
import com.example.lab05.activities.CursoActivity
import com.example.lab05.activities.EstudianteActivity
import com.example.lab05.logica.Curso
import com.example.lab05.logica.Estudiante
import kotlinx.android.synthetic.main.item_curso_row.view.*
import kotlinx.android.synthetic.main.item_curso_row.view.ivEditar
import kotlinx.android.synthetic.main.item_curso_row.view.ivEliminar
import kotlinx.android.synthetic.main.item_estudiante_row.view.*

class EstudianteAdaptador(val context: Context, val estudiantes:ArrayList<Estudiante>):
    RecyclerView.Adapter<EstudianteAdaptador.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstudianteAdaptador.ViewHolder {
        return EstudianteAdaptador.ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_estudiante_row,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: EstudianteAdaptador.ViewHolder, position: Int) {
        val elemento = estudiantes.get(position)
        holder.tvIdEstudiante.text = elemento.id
        holder.tvNombre.text = elemento.nombre
        holder.tvApellidos.text = elemento.apellidos
        holder.tvEdad.text = elemento.edad.toString()

        if (position % 2 == 0) {
            holder.llListEstudiantes.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.lightgray
                )
            )
        } else {
            holder.llListEstudiantes.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
        }

        holder.ivEditar.setOnClickListener { view ->
            if (context is EstudianteActivity) {
                context.dialogActualizarEstudiante(elemento)
            }
        }

        holder.ivEliminar.setOnClickListener { view ->
            if (context is EstudianteActivity){
                context.dialogEliminarEstudiante(elemento)
            }
        }
    }

    override fun getItemCount(): Int {
        return  estudiantes.size
    }

    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val llListEstudiantes = view.llListEstudiantes
        var tvIdEstudiante = view.tvIdEstudiante
        var tvNombre = view.tvNombre
        var tvApellidos = view.tvApellidos
        var tvEdad = view.tvEdad
        val ivEditar = view.ivEditarEstudiante
        val ivEliminar = view.ivEliminarEstudiante
    }

}