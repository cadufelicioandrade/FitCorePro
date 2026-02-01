package com.app.fitcorepro.data

import com.app.fitcorepro.model.PlanoSemanal
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface PlanoSemanalAPI {


    @POST("plano-semanal")
    fun cadastrarPlanoSemanal(@Body planoSemanal: PlanoSemanal): Call<PlanoSemanal>

}