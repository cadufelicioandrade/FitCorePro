package com.app.fitcorepro.data

import com.app.fitcorepro.model.PlanoSemanal
import com.app.fitcorepro.presentation.PlanoSemanalCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlanoSemanalDataSource {

    fun cadastrarPlanoSemanal(planoSemanal: PlanoSemanal, callback: PlanoSemanalCallback) {
        HTTPClient.retrofit()
            .create(PlanoSemanalAPI::class.java)
            .cadastrarPlanoSemanal(planoSemanal)
            .enqueue(object : Callback<PlanoSemanal> {
                override fun onResponse(
                    call: Call<PlanoSemanal>,
                    response: Response<PlanoSemanal>
                ) {
                    if (response.isSuccessful) {
                        // Requisição bem-sucedida (código 2xx)
                        response.body()?.let { planoSalvo ->
                            // Se o corpo da resposta não for nulo, retorna sucesso
                            callback.onSuccess(planoSalvo)
                        } ?: run {
                            // Se o corpo da resposta for nulo (cenário raro em sucesso)
                            callback.onError("Resposta do servidor veio vazia.")
                        }
                    } else {
                        // Requisição falhou no servidor (código 4xx, 5xx)
                        val errorMsg = response.errorBody()?.string() ?: "Erro desconhecido do servidor"
                        callback.onError("Falha ao cadastrar: ${response.code()} - $errorMsg")
                    }
                }

                override fun onFailure(call: Call<PlanoSemanal>, t: Throwable) {
                    // Falha de comunicação (sem internet, timeout, etc.)
                    callback.onError("Erro de comunicação: ${t.message}")
                }
            })
    }

    fun findPlanoSemanalByUsuarioId(usuarioId: String, callback: PlanoSemanalCallback) {
        //TODO Implementar o HTTPClient.retrofit()....
        // buscar pelo plano ativo para o respectivo usuário
    }
}
