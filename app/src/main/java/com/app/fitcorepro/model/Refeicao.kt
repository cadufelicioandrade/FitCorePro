package com.app.fitcorepro.model

import java.util.Date

data class Refeicao(
    val id: String,
    val tipo:String,
    val orgem:Int = 0,
    val planoSemanaDiaId:String,
    val alimentos:List<Alimento>,
    val createdDate: Date = Date()
)
