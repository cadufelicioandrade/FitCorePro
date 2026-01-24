package com.app.fitcorepro.view

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import com.app.fitcorepro.R
import com.app.fitcorepro.model.Alimento
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item


class AlimentoItem(
    private val alimento: Alimento,
    val onEditClicked: (alimento: Alimento) -> Unit,
    val onRemoveClicked: (alimento: Alimento) -> Unit
) : Item<AlimentoItem.AlimentoViewHolder>() {

    private lateinit var editAlimento: ImageButton
    private lateinit var removeAlimento: ImageButton

    class AlimentoViewHolder(itemView: View) : GroupieViewHolder(itemView)

    override fun createViewHolder(itemView: View) = AlimentoViewHolder(itemView)

    override fun bind(viewHolder: AlimentoViewHolder, position: Int) {
        alimento?.let {
            viewHolder.itemView.findViewById<TextView>(R.id.nome_alimento).text = it.nome
            viewHolder.itemView.findViewById<TextView>(R.id.gramas_alimento).text = "${it.gramas}g"

            editAlimento = viewHolder.itemView.findViewById(R.id.btn_edit_item_refeicao)
            removeAlimento = viewHolder.itemView.findViewById(R.id.btn_remove_item_refeicao)


            editAlimento.setOnClickListener {
                onEditClicked(alimento)
            }

            removeAlimento.setOnClickListener {
                onRemoveClicked(alimento)
            }
        }
    }

    override fun getLayout() = R.layout.item_refeicao


}