package com.app.fitcorepro.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.fitcorepro.R
import com.app.fitcorepro.model.Alimento
import com.app.fitcorepro.model.Refeicao
import com.xwray.groupie.GroupieAdapter

class DietaFragment : Fragment() {

    private val adapter = GroupieAdapter()
    private lateinit var progress: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO: implementar o presenter
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dieta, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_dieta)
        progress = view.findViewById(R.id.progress_bar_dieta)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        // CRIAÇÃO DOS DADOS DE EXEMPLO
        // (No futuro, isso virá de um banco de dados ou API)
        val alimentosCafe = listOf(
            Alimento( 1, "Ovo cozido", 2),
            Alimento(2, "Pão integral", 1),
            Alimento(3, "Mamão", 100)
        )

        val alimentosAlmoco = listOf(
            Alimento(4, "Filé de Frango", 150),
            Alimento(5, "Arroz integral", 200),
            Alimento(6, "Salada de alface", 50)
        )

        val refeicaoCafe = Refeicao(1,"Café da Manhã", alimentosCafe)
        val refeicaoAlmoco = Refeicao(2,"Almoço", alimentosAlmoco)

        val alimentosLancheTarde = listOf(
            Alimento( 1, "Ovo cozido", 2),
            Alimento(2, "Pão integral", 1),
            Alimento(3, "Mamão", 100)
        )

        val alimentosJantar = listOf(
            Alimento(4, "Filé de Frango", 150),
            Alimento(5, "Arroz integral", 200),
            Alimento(6, "Salada de alface", 50)
        )

        val refeicaoLancheTarde = Refeicao(3,"Lanche da Tarde", alimentosCafe)
        val refeicaoJantar = Refeicao(4,"Jantar", alimentosAlmoco)

        val listaDeRefeicoes = listOf(refeicaoCafe, refeicaoAlmoco,refeicaoLancheTarde,refeicaoJantar)

        // Converte a lista de dados (List<Refeicao>) para uma lista de Itens do Groupie (List<RefeicaoItem>)
        val itemsParaAdapter = listaDeRefeicoes.map { refeicao ->
            RefeicaoItem(refeicao)
        }

        // Adiciona tudo ao adapter PRINCIPAL
        adapter.addAll(itemsParaAdapter)
    }
}