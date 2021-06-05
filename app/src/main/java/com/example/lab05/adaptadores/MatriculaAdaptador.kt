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
import kotlinx.android.synthetic.main.item_matricula_row.view.*

class MatriculaAdaptador(val context: Context, val cursos:ArrayList<Curso>):
    RecyclerView.Adapter<MatriculaAdaptador.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_matricula_row,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val elemento = cursos.get(position)
        holder.tvIdCurMat.text = elemento.id
        holder.tvDesMat.text = elemento.descripcion

        if (position % 2 == 0) {
            holder.llCursosMatriculados.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.lightgray
                )
            )
        } else {
            holder.llCursosMatriculados.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
        }
    }

    override fun getItemCount(): Int {
        return cursos.size
    }

    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val llCursosMatriculados = view.llListCursosMatriculados
        var tvIdCurMat = view.tvIdCursoMat
        var tvDesMat = view.tvDescMat
        //val ivEliminar = view.ivElimMat
    }
}