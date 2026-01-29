package com.app.fitcorepro.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.fitcorepro.R
import com.app.fitcorepro.enuns.DiaSemana
import com.app.fitcorepro.model.Alimento
import com.app.fitcorepro.model.PlanoSemanal
import com.app.fitcorepro.model.PlanoSemanalDia
import com.app.fitcorepro.model.Refeicao
import com.app.fitcorepro.presentation.PlanoSemanalPresenter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.xwray.groupie.GroupieAdapter

class PlanoSemanalFragment : Fragment(), PlanoSemanalView {

    private val adapter = GroupieAdapter()
    private lateinit var progress: ProgressBar
    private lateinit var presenter: PlanoSemanalPresenter
    private var planoSemanalCompleto: PlanoSemanal? = null
    private lateinit var btnNovoPlano: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = PlanoSemanalPresenter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_plano_semanal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnNovoPlano = view.findViewById(R.id.btn_new_plan)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_dieta)
        progress = view.findViewById(R.id.progress_bar_dieta)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        planoSemanalCompleto = createMockPlanoSemanal()

        if(planoSemanalCompleto != null){
            setupDayButtons(view)
            updateRecyclerViewForDay(DiaSemana.SEGUNDA)
        }

        val addRefeicaoButton = view.findViewById<FloatingActionButton>(R.id.fab_add_refeicao)
        addRefeicaoButton.setOnClickListener {
            adicionarRefeicao()
        }

        btnNovoPlano.setOnClickListener {
            findNavController().navigate(R.id.action_nav_diet_week_to_nav_new_plan_week)
        }

    }

    override fun showLoading() {
        progress.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progress.visibility = View.GONE
    }

    override fun showPlanoSemanal(planoSemanal: PlanoSemanal) {
        val plan = planoSemanal.planoSemanalDias;
        // TODO passar o plano, refeições e seus respectivos alimentos para o adapter
        // ainda implementar as acões dos botões da semana (seg, ter, qua...)
    }

    override fun onError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    fun removerRefeicao(refeicao: Refeicao) {
        Toast.makeText(
            requireContext(),
            "Refeição removida: ${refeicao.id} - ${refeicao.tipo}",
            Toast.LENGTH_SHORT
        ).show()
    }

    fun adicionarRefeicao() {
        Toast.makeText(requireContext(), "Add Refeição", Toast.LENGTH_SHORT).show()
    }

    fun adicionarAlimento() {
        Toast.makeText(requireContext(), "Add Alimento", Toast.LENGTH_SHORT).show()
    }

    fun editarAlimento(alimento: Alimento) {
        Toast.makeText(
            requireContext(),
            "Edit Alimento: ${alimento.id} - ${alimento.nome}",
            Toast.LENGTH_SHORT
        ).show()
    }

    fun removerAlimento(alimento: Alimento) {
        Toast.makeText(
            requireContext(),
            "Remove Alimento: ${alimento.id} - ${alimento.nome}",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun setupDayButtons(view: View){
        view.findViewById<Button>(R.id.btn_monday).setOnClickListener {updateRecyclerViewForDay(DiaSemana.SEGUNDA)}
        view.findViewById<Button>(R.id.btn_tuesday).setOnClickListener {updateRecyclerViewForDay(DiaSemana.TERCA)}
        view.findViewById<Button>(R.id.btn_wednesday).setOnClickListener {updateRecyclerViewForDay(DiaSemana.QUARTA)}
        view.findViewById<Button>(R.id.btn_thursday).setOnClickListener {updateRecyclerViewForDay(DiaSemana.QUINTA)}
        view.findViewById<Button>(R.id.btn_friday).setOnClickListener {updateRecyclerViewForDay(DiaSemana.SEXTA)}
        view.findViewById<Button>(R.id.btn_saturday).setOnClickListener {updateRecyclerViewForDay(DiaSemana.SABADO)}
        view.findViewById<Button>(R.id.btn_sunday).setOnClickListener {updateRecyclerViewForDay(DiaSemana.DOMINGO)}
    }

    private fun updateRecyclerViewForDay(day: DiaSemana) {
        val planoDoDia = planoSemanalCompleto?.planoSemanalDias?.find { it.diaSemana == day }

        adapter.clear()
        planoDoDia?.let { diaEncontrado ->
            val itemsParaAdapter = diaEncontrado.refeicoes.map{ refeicao ->
                RefeicaoItem(
                    refeicao,
                    {refeicaoRemover -> removerRefeicao(refeicaoRemover)},
                    {adicionarAlimento()},
                    {aliemntoEdit -> editarAlimento(aliemntoEdit)},
                    {alimentoRemover -> removerAlimento(alimentoRemover)}
                )
            }
            adapter.addAll(itemsParaAdapter)
        }
        Toast.makeText(requireContext(), "Exibindo dieta de: ${day.name}", Toast.LENGTH_SHORT).show()

    }

    // DADOS MOCKADOS PARA TESTE
    fun createMockPlanoSemanal(): PlanoSemanal {
        // --- Alimentos Genéricos para Reutilização ---
        val alimentoOvo = Alimento(id = "1", nome = "Ovo cozido", gramas = 50)
        val alimentoPao = Alimento(id = "2", nome = "Pão integral", gramas = 40)
        val alimentoFrango = Alimento(id = "3", nome = "Filé de Frango", gramas = 150)
        val alimentoArroz = Alimento(id = "4", nome = "Arroz integral", gramas = 100)
        val alimentoSalada = Alimento(id = "5", nome = "Salada de alface e tomate", gramas = 80)
        val alimentoBatataDoce = Alimento(id = "6", nome = "Batata doce", gramas = 120)
        val alimentoIogurte = Alimento(id = "7", nome = "Iogurte Natural", gramas = 150)
        val alimentoWhey = Alimento(id = "8", nome = "Whey Protein", gramas = 30)

        // --- Montando os dias da semana ---
        val planoDias = DiaSemana.values().map { dia ->
            // Cria refeições diferentes para cada dia para o teste ser mais visual
            val cafeDaManha = Refeicao(
                id = "ref-cafe-${dia.ordinal}",
                tipo = "Café da Manhã",
                planoSemanaDiaId = "dia-${dia.ordinal}",
                alimentos = when(dia) {
                    DiaSemana.DOMINGO, DiaSemana.SEGUNDA, DiaSemana.QUARTA -> listOf(alimentoIogurte, alimentoWhey)
                    else -> listOf(alimentoOvo, alimentoPao)
                }
            )

            val almoco = Refeicao(
                id = "ref-almoco-${dia.ordinal}",
                tipo = "Almoço",
                planoSemanaDiaId = "dia-${dia.ordinal}",
                alimentos = when(dia) {
                    DiaSemana.QUARTA, DiaSemana.SABADO -> listOf(alimentoFrango, alimentoBatataDoce, alimentoSalada)
                    else -> listOf(alimentoFrango, alimentoArroz, alimentoSalada)
                }
            )

            val jantar = Refeicao(
                id = "ref-jantar-${dia.ordinal}",
                tipo = "Jantar",
                planoSemanaDiaId = "dia-${dia.ordinal}",
                alimentos = listOf(alimentoFrango, alimentoSalada)
            )

            PlanoSemanalDia(
                id = "dia-${dia.ordinal}",
                planoSemanaId = "plano1",
                diaSemana = dia,
                refeicoes = listOf(cafeDaManha, almoco, jantar)
            )
        }

        // --- Objeto Principal ---
        return PlanoSemanal(
            id = "plano1",
            nome = "Plano de Hipertrofia Básico",
            ativo = true,
            idUsuario = "user123",
            planoSemanalDias = planoDias
        )
    }


}
