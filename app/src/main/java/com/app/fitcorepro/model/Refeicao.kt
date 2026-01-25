package com.app.fitcorepro.model

import java.util.Date

data class Refeicao(
    val id: Long,
    val titulo:String,
    val alimentos:List<Alimento>,
    val createdDate: Date = Date()
)
