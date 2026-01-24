package com.app.fitcorepro.view

import com.app.fitcorepro.model.Alimento
import com.app.fitcorepro.model.Refeicao

interface RefeicaoView {

    fun showLoading()
    fun hideLoading()
    fun showRefeicoes(refeicoes: List<Refeicao>)
    fun showError(message: String)
//    fun onRemoveRefeicao(refeicao: Refeicao)
//    fun onAddAlimento(alimento: Alimento)
//    fun onEditAlimento(alimento: Alimento)
//    fun onRemoveAlimento(alimento: Alimento)
}

