package com.app.fitcorepro.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.fitcorepro.R
import com.app.fitcorepro.model.Alimento
import com.app.fitcorepro.model.PlanoSemanal
import com.app.fitcorepro.model.Refeicao
import com.app.fitcorepro.presentation.PlanoSemanalPresenter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.xwray.groupie.GroupieAdapter

class PlanoSemanalFragment : Fragment(), PlanoSemanalView {

    private val adapter = GroupieAdapter()
    private lateinit var progress: ProgressBar
    private lateinit var presenter: PlanoSemanalPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = PlanoSemanalPresenter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dieta_semana, container, false)
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
            Alimento("1", "Ovo cozido", 2),
            Alimento("2", "Pão integral", 1),
            Alimento("3", "Mamão", 100)
        )
        val alimentosAlmoco = listOf(
            Alimento("4", "Filé de Frango", 150),
            Alimento("5", "Arroz integral", 200),
            Alimento("6", "Salada de alface", 50)
        )
        val alimentosLancheTarde = listOf(
            Alimento("1", "Ovo cozido", 2),
            Alimento("2", "Pão integral", 1),
            Alimento("3", "Mamão", 100)
        )
        val alimentosJantar = listOf(
            Alimento("4", "Filé de Frango", 150),
            Alimento("5", "Arroz integral", 200),
            Alimento("6", "Salada de alface", 50)
        )

        val refeicaoCafe = Refeicao("1", "Café da Manhã", 0,"1",alimentosCafe)
        val refeicaoAlmoco = Refeicao("2", "Almoço", 0,"1",alimentosAlmoco)
        val refeicaoLancheTarde = Refeicao("3", "Lanche da Tarde",  0,"1",alimentosLancheTarde)
        val refeicaoJantar = Refeicao("4", "Jantar",  0,"1",alimentosJantar)

        val listaDeRefeicoes = listOf(refeicaoCafe, refeicaoAlmoco, refeicaoLancheTarde, refeicaoJantar)
        val addRefeicao = view.findViewById<FloatingActionButton>(R.id.fab_add_refeicao)

        addRefeicao.setOnClickListener {
            Toast.makeText(requireContext(), "Add refeição", Toast.LENGTH_SHORT).show()
        }

        // Converte a lista de dados (List<Refeicao>) para uma lista de Itens do Groupie (List<RefeicaoItem>)
        val itemsParaAdapter = listaDeRefeicoes.map { refeicao ->
            RefeicaoItem(
                refeicao,
                { refeicaoARemover ->
                    Toast.makeText(
                        requireContext(),
                        "Refeição removida: ${refeicaoARemover.id} - ${refeicaoARemover.tipo}",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                {
                    Toast.makeText(requireContext(), "Add Alimento", Toast.LENGTH_SHORT).show()
                }, { alimentoEdit ->
                    Toast.makeText(
                        requireContext(),
                        "Edit Alimento: ${alimentoEdit.id} - ${alimentoEdit.nome}",
                        Toast.LENGTH_SHORT
                    ).show()

                }, { alimentoARemover ->
                    Toast.makeText(
                        requireContext(),
                        "Remove Alimento: ${alimentoARemover.id} - ${alimentoARemover.nome}",
                        Toast.LENGTH_SHORT
                    ).show()
                })
        }

        // Adiciona tudo ao adapter PRINCIPAL
        adapter.addAll(itemsParaAdapter)
    }

    override fun showLoading() {
        //TODO mostrar a barra de progresso
    }

    override fun hideLoading() {
        //TODO ocultar a barra de progresso
    }

    override fun showPlanoSemanal(planoSemanal: PlanoSemanal) {
        // TODO passar o plano, refeições e seus respectivos alimentos para o adapter
        // ainda implementar as acões dos botões da semana (seg, ter, qua...)
    }

    override fun onError(message: String) {
        //TODO apresentar mensagem de erro pro usuário
    }

}