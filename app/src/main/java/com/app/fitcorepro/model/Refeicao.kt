package com.app.fitcorepro.model

data class Refeicao(
    val id: Long,
    val titulo:String,
    val alimentos:List<Alimento>
)
