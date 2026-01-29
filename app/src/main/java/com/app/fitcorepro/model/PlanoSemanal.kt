package com.app.fitcorepro.model

import java.util.Date

data class PlanoSemanal(
    val id: String,
    val nome: String,
    val ativo: Boolean,
    val idUsuario: String,
    val createdDate: Date = Date(),
    val planoSemanalDias: List<PlanoSemanalDia>
)