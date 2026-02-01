package com.app.fitcorepro.presentation

import com.app.fitcorepro.data.PlanoSemanalDataSource
import com.app.fitcorepro.model.PlanoSemanal
import com.app.fitcorepro.view.PlanoSemanalView

class PlanoSemanalPresenter(
    private val view: PlanoSemanalView,
    private val dataSource: PlanoSemanalDataSource = PlanoSemanalDataSource()
) : PlanoSemanalCallback {


    fun cadastrarPlanoSemanal(planoSemanal: PlanoSemanal) {
        view.showLoading()
        dataSource.cadastrarPlanoSemanal(planoSemanal, this)
    }


    fun findPlanoSemanalByUsuarioId(usuarioId: String) {
        view.showLoading()
        dataSource.findPlanoSemanalByUsuarioId(usuarioId, this)
    }

    override fun onSuccess(planoSemanal: PlanoSemanal) {
        view.showPlanoSemanal(planoSemanal)
        view.hideLoading()
    }

    override fun onComplete() {
        view.hideLoading()
    }

    override fun onError(message: String) {
        view.onError(message)
    }

}