package com.app.fitcorepro.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.fitcorepro.R
import com.app.fitcorepro.enuns.DiaSemana
import com.app.fitcorepro.model.Alimento
import com.app.fitcorepro.model.PlanoSemanal
import com.app.fitcorepro.model.PlanoSemanalDia
import com.app.fitcorepro.model.Refeicao
import com.xwray.groupie.GroupieAdapter
import java.util.Date

class CadastroPlanoSemanalFragment : Fragment() {

    private var alimentosAdapter = GroupieAdapter()
    private val adapterRefeicao = GroupieAdapter()
    private lateinit var txtNomePlanoSemanal: EditText
    private lateinit var lstDiasSemana: AutoCompleteTextView
    private lateinit var rc_refeicao: RecyclerView
    private lateinit var rc_alimento: RecyclerView
    private lateinit var editNomeRefeicao: EditText
    private lateinit var editNomeAlimento: EditText
    private lateinit var editGramaAlimento: EditText
    private lateinit var btnAddAlimentoNaRefeicao: Button
    private lateinit var btnAddRefeicaoAoDia: Button

    private var alimentosParaNovaRefeicao = mutableListOf<Alimento>()
    private var refeicoesParaUmDia = mutableListOf<Refeicao>()

    private var refeicoesSemana = mutableMapOf<String, MutableList<Refeicao>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //TODO implementar o presenter
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cadastro_novo_plano_semanal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        txtNomePlanoSemanal = view.findViewById(R.id.edit_nome_plano)

        lstDiasSemana = view.findViewById(R.id.autocomplete_dia_semana)
        editNomeRefeicao = view.findViewById(R.id.edit_nova_refeicao_nome)
        editNomeAlimento = view.findViewById(R.id.edit_novo_alimento_nome)
        editGramaAlimento = view.findViewById(R.id.edit_novo_grama_alimento)
        btnAddRefeicaoAoDia = view.findViewById(R.id.btn_add_refeicao_ao_dia)
        btnAddAlimentoNaRefeicao = view.findViewById(R.id.btn_add_alimento_a_refeicao)

        // *** Buscando os TextInputLayouts para usar o .error ***
        val layoutNomePlano =
            view.findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.layout_nome_plano_semanal)
        val layoutDiaSemana =
            view.findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.layout_dia_semana)
        val layoutNomeRefeicao =
            view.findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.layout_nova_refeicao_nome)
        val layoutNomeAlimento =
            view.findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.layout_novo_alimento_nome)
        val layoutGramas =
            view.findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.layout_novo_grama_alimento)
        val btnSalvarPlano = view.findViewById<Button>(R.id.btn_salvar_plano)


        val diasSemanaFormatados = DiaSemana.entries.map {
            it.name.lowercase().replaceFirstChar { char -> char.uppercase() }
        }

        val adapterDiasSemana = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            diasSemanaFormatados
        )

        lstDiasSemana.setAdapter(adapterDiasSemana)

        rc_alimento = view.findViewById(R.id.rv_alimentos_para_adicionar)
        rc_alimento.layoutManager = LinearLayoutManager(requireContext())
        rc_alimento.adapter = alimentosAdapter

        rc_refeicao = view.findViewById(R.id.rv_refeicoes_do_dia)
        rc_refeicao.layoutManager = LinearLayoutManager(requireContext())
        rc_refeicao.adapter = adapterRefeicao

        lstDiasSemana.setOnItemClickListener { parent, _, position, _ ->
            val diaSelecionado = parent.getItemAtPosition(position).toString()
            carregarRefeicoesDoDia(diaSelecionado)
        }

        btnAddAlimentoNaRefeicao.setOnClickListener {
            val nomeAlimento = editNomeAlimento.text.toString()
            val gramas = editGramaAlimento.text.toString()

            if (nomeAlimento.isBlank() || gramas.isBlank()) {
                layoutNomeAlimento.error = if (nomeAlimento.isBlank()) "Obrigatório" else null
                layoutGramas.error = if (gramas.isBlank()) "Obrigatório" else null
                return@setOnClickListener
            }
            // Limpa erros anteriores se a validação passar
            layoutNomeAlimento.error = null
            layoutGramas.error = null

            alimentosAdapter.add(
                AlimentoCadastroItem(
                    editNomeAlimento.text.toString(),
                    editGramaAlimento.text.toString()
                )
            )

            alimentosParaNovaRefeicao.add(
                Alimento(
                    "",
                    editNomeAlimento.text.toString(),
                    editGramaAlimento.text.toString().toInt(),
                    Date()
                )
            )
            editNomeAlimento.text.clear()
            editGramaAlimento.text.clear()
            editNomeAlimento.requestFocus()
        }

        btnAddRefeicaoAoDia.setOnClickListener {
            val nomeRefeicao = editNomeRefeicao.text.toString()
            val diaSelecionado = lstDiasSemana.text.toString()

            val isNomeRefeicaoEmpty = nomeRefeicao.isBlank()
            val isAlimentosListEmpty = alimentosParaNovaRefeicao.isEmpty()
            val isDiaSemanaEmpty = diaSelecionado.isBlank()

            if (isNomeRefeicaoEmpty || isAlimentosListEmpty || isDiaSemanaEmpty) {
                layoutNomeRefeicao.error = if (isNomeRefeicaoEmpty) "Obrigatório" else null
                layoutDiaSemana.error = if (isDiaSemanaEmpty) "Selecione um dia" else null

                if (isAlimentosListEmpty) {
                    Toast.makeText(
                        requireContext(),
                        "Adicione pelo menos um alimento à refeição",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                return@setOnClickListener
            }
            // Limpa erros anteriores
            layoutNomeRefeicao.error = null
            layoutDiaSemana.error = null


            val novoRefeicao = Refeicao(
                "",
                editNomeRefeicao.text.toString(),
                1,
                diaSelecionado,
                alimentosParaNovaRefeicao.toMutableList(),
                Date()
            )

            refeicoesSemana.getOrPut(diaSelecionado) { mutableListOf() }.add(novoRefeicao)

            carregarRefeicoesDoDia(diaSelecionado)

            adapterRefeicao.add(
                CadastroPlanoSemanalItem(
                    editNomeRefeicao.text.toString(),
                    alimentosParaNovaRefeicao.take(3).joinToString(", ") { it.nome }
                )
            )
            refeicoesParaUmDia.add(
                Refeicao(
                    "",
                    editNomeRefeicao.text.toString(),
                    1,
                    diaSelecionado,
                    alimentosParaNovaRefeicao.toMutableList(),
                    Date()
                )
            )
            alimentosParaNovaRefeicao.clear()
            alimentosAdapter.clear()
            editNomeRefeicao.text.clear()
            editNomeAlimento.text.clear()
            editGramaAlimento.text.clear()
            editNomeRefeicao.requestFocus()
        }

        btnSalvarPlano.setOnClickListener {
            val nomeDoPlano = txtNomePlanoSemanal.text.toString()

            if (nomeDoPlano.isBlank()) {
                layoutNomePlano.error = "Obrigatório"
                return@setOnClickListener
            }

            layoutNomePlano.error = null

            if (refeicoesSemana.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Adicione pelo menos um dia com uma refeição",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            var planoSemanalDiaList = mutableListOf<PlanoSemanalDia>()


            refeicoesSemana.map { (dia, refeicoes) ->
                val planoSemanalDia = PlanoSemanalDia(
                    "",
                    "",
                    DiaSemana.valueOf(dia.uppercase()),
                    Date(),
                    refeicoes
                )
                planoSemanalDiaList.add(planoSemanalDia)

            }

            val planoSemanal = PlanoSemanal(
                "",
                nomeDoPlano,
                true,
                "",
                Date(),
                planoSemanalDiaList
            )

            // TODO: Chamar o seu Presenter/ViewModel para enviar 'planoSemanal' e 'nomeDoPlano' para o backend
        }
    }

    private fun carregarRefeicoesDoDia(diaSelecionado: String) {
        adapterRefeicao.clear()
        val refeicoesDoDia = refeicoesSemana[diaSelecionado]

        if (refeicoesDoDia != null) {
            val itemsParaAdapter = refeicoesDoDia.map { refeicao ->
                val descricao = refeicao.alimentos.take(3).joinToString(", ") { it.nome }
                CadastroPlanoSemanalItem(refeicao.tipo, descricao)
            }
        }
    }
}