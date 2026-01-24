package com.app.fitcorepro.view

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.fitcorepro.R
import com.app.fitcorepro.model.Alimento
import com.app.fitcorepro.model.Refeicao
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item

class RefeicaoItem(
    private val refeicao: Refeicao,
    val onRemoveRefeicao: (refeicao: Refeicao) -> Unit,
    val onAddAlimento: () -> Unit,
    val onEditAlimento: (alimento: Alimento) -> Unit,
    val onRemoveAlimento: (alimento: Alimento) -> Unit
) : Item<RefeicaoItem.RefeicaoViewHolder>() {

    private var isExpanded = true
    private lateinit var deleteRefeicao: ImageButton
    private lateinit var addAlimento: ImageButton

    class RefeicaoViewHolder(itemView: View) : GroupieViewHolder(itemView)

    override fun createViewHolder(itemView: View) = RefeicaoViewHolder(itemView)

    @SuppressLint("NotifyDataSetChanged")
    override fun bind(viewHolder: RefeicaoViewHolder, position: Int) {
        refeicao.let {
            viewHolder.itemView.findViewById<TextView>(R.id.title_card).text = it.titulo
            val recyclerView = viewHolder.itemView.findViewById<RecyclerView>(R.id.rc_refeicao)
            val adapter = GroupieAdapter()
            recyclerView.layoutManager = LinearLayoutManager(viewHolder.itemView.context)
            recyclerView.adapter = adapter
            val alimentoItems =
                it.alimentos.map { alimento ->
                    AlimentoItem(
                        alimento,
                        { onEditAlimento(alimento) },
                        { onRemoveAlimento(alimento) }
                    )
                }
            adapter.addAll(alimentoItems)
            adapter.notifyDataSetChanged()
            deleteRefeicao = viewHolder.itemView.findViewById(R.id.btn_remove_refeicao)
            addAlimento = viewHolder.itemView.findViewById(R.id.fab_add_alimento)
            deleteRefeicao.setOnClickListener {
                onRemoveRefeicao(refeicao)
            }
            addAlimento.setOnClickListener { onAddAlimento() }
            updateVisibility(viewHolder.itemView)

            viewHolder.itemView.findViewById<ImageButton>(R.id.btn_toggle_visibility_refeicao)
                .setOnClickListener {
                    toggleVisibility(viewHolder.itemView)
                }

            viewHolder.itemView.findViewById<TextView>(R.id.title_card).setOnClickListener {
                toggleVisibility(viewHolder.itemView)
            }
        }
    }

    override fun getLayout() = R.layout.card_refeicao

    private fun toggleVisibility(itemView: View) {
        isExpanded = !isExpanded
        updateVisibility(itemView)
    }

    private fun updateVisibility(itemView: View) {
        itemView.findViewById<RecyclerView>(R.id.rc_refeicao).isVisible = isExpanded
        itemView.findViewById<FloatingActionButton>(R.id.fab_add_alimento).isVisible = isExpanded
        itemView.findViewById<ImageButton>(R.id.btn_remove_refeicao).isVisible = isExpanded

        val toggleButton = itemView.findViewById<ImageButton>(R.id.btn_toggle_visibility_refeicao)
        val iconRes = if (isExpanded) R.drawable.outline_expand_circle_down_24
        else R.drawable.outline_expand_circle_up_24
        toggleButton.setImageResource(iconRes)
    }

}