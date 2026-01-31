package com.app.fitcorepro.view

import android.view.View
import android.widget.TextView
import com.app.fitcorepro.R
import com.app.fitcorepro.model.Alimento
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item

class AlimentoCadastroItem(private val alimento: String, private val gramas: String) : Item<AlimentoCadastroItem.AlimentoCadastroViewHolder>() {

    class AlimentoCadastroViewHolder(itemView: View) : GroupieViewHolder(itemView)

    override fun createViewHolder(itemView: View) = AlimentoCadastroViewHolder(itemView)

    override fun bind(viewHolder: AlimentoCadastroViewHolder, position: Int) {
        viewHolder.itemView.findViewById<TextView>(R.id.item_alimento_nome).text = alimento
        viewHolder.itemView.findViewById<TextView>(R.id.item_alimento_grama).text = gramas
    }

    override fun getLayout() = R.layout.item_alimento_cadastro
}