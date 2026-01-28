package com.app.fitcorepro.view

import com.app.fitcorepro.model.PlanoSemanal

interface PlanoSemanalView {

    fun showLoading()
    fun hideLoading()
    fun showPlanoSemanal(planoSemanal: PlanoSemanal)
    fun onError(message: String)

}

