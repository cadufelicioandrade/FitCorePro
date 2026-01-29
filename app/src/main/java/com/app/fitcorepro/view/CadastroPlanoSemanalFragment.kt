package com.app.fitcorepro.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.fitcorepro.R
import com.app.fitcorepro.enuns.DiaSemana
import com.xwray.groupie.GroupieAdapter

class CadastroPlanoSemanalFragment : Fragment() {

    private val adapter = GroupieAdapter()
    private lateinit var txtNomePlanoSemanal: EditText
    private lateinit var lstDiasSemana: AutoCompleteTextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var editNomeRefeicao: EditText
    private lateinit var editNomeAlimento: EditText
    private lateinit var btnAddRefeicaoAoDia: Button

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
        btnAddRefeicaoAoDia = view.findViewById(R.id.btn_add_refeicao_ao_dia)

        val adapterDiasSemana = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            DiaSemana.entries.toTypedArray()
        )

        lstDiasSemana.setAdapter(adapterDiasSemana)

        recyclerView = view.findViewById<RecyclerView>(R.id.rv_refeicoes_do_dia)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter


        btnAddRefeicaoAoDia.setOnClickListener {
            adapter.add(
                CadastroPlanoSemanalItem(
                    editNomeRefeicao.text.toString(),
                    editNomeAlimento.text.toString(),
                    "100"
                )
            )
            editNomeRefeicao.text.clear()
            editNomeAlimento.text.clear()
        }


    }
}