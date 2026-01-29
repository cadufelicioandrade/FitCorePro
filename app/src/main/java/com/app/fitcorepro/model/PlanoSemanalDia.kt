package com.app.fitcorepro.model

import com.app.fitcorepro.enuns.DiaSemana
import java.util.Date

data class PlanoSemanalDia(
    val id: String,
    val planoSemanaId: String,
    val diaSemana: DiaSemana,
    val createDate: Date = Date(),
    val refeicoes: List<Refeicao>
    )