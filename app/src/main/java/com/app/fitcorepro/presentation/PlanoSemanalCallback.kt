package com.app.fitcorepro.presentation

import com.app.fitcorepro.model.PlanoSemanal

interface PlanoSemanalCallback {
    fun onSuccess(planoSemanal: PlanoSemanal)
    fun onComplete()
    fun onError(message: String)
}

