package com.app.fitcorepro.model

data class Treino(
    val id: Int,
    val diaSemana: String,
    val exercicios: List<Exercicio>
)
