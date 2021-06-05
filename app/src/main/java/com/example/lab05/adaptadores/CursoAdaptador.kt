package com.example.lab05.adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.lab05.R
import com.example.lab05.activities.CursoActivity
import com.example.lab05.logica.Curso
import kotlinx.android.synthetic.main.item_curso_row.view.*

class CursoAdaptador(val context: Context , val cursos:ArrayList<Curso>):
    RecyclerView.Adapter<CursoAdaptador.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_curso_row,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val elemento = cursos.get(position)
        holder.tvIdCurso.text = elemento.id
        holder.tvDescripcion.text = elemento.descripcion
        holder.tvCreditos.text = elemento.creditos.toString()

        if (position % 2 == 0) {
            holder.llListadoCursos.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.lightgray
                )
            )
        } else {
            holder.llListadoCursos.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
        }

        holder.ivEditar.setOnClickListener { view ->
            if (context is CursoActivity) {
                context.dialogActualizarCurso(elemento)
            }
        }

        holder.ivEliminar.setOnClickListener { view ->
            if (context is CursoActivity){
                context.dialogEliminarCurso(elemento)
            }
        }
    }

    override fun getItemCount(): Int {
        return cursos.size
    }

    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val llListadoCursos = view.llListCursos
        var tvIdCurso = view.tvIdCurso
        var tvDescripcion = view.tvDescripcion
        var tvCreditos = view.tvCreditos
        val ivEditar = view.ivEditar
        val ivEliminar = view.ivEliminar
    }
}