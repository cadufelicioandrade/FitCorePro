package com.app.fitcorepro.view

import android.view.View
import android.widget.TextView
import com.app.fitcorepro.R
import com.app.fitcorepro.model.Alimento
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item


class AlimentoItem(private val alimento: Alimento?) : Item<AlimentoItem.AlimentoViewHolder>() {

    constructor() : this(null)

    class AlimentoViewHolder(itemView: View) : GroupieViewHolder(itemView)

    override fun createViewHolder(itemView: View) = AlimentoViewHolder(itemView)

    override fun bind(viewHolder: AlimentoViewHolder, position: Int) {
        alimento?.let{
            viewHolder.itemView.findViewById<TextView>(R.id.nome_alimento).text = it.nome
            viewHolder.itemView.findViewById<TextView>(R.id.gramas_alimento).text = "${it.gramas}g"
        }
    }

    override fun getLayout() = R.layout.item_refeicao


}