package com.example.naregua.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.example.naregua.databinding.DialogInserirServBinding
import java.lang.IllegalStateException

class DialogInserirServicos: DialogFragment() {
    lateinit var binding: DialogInserirServBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val titleText = arguments?.getString(TITLE_TEXT)
            ?: throw IllegalArgumentException("Ops, passe o titulo")
        return activity?.let{
            binding =
                DialogInserirServBinding.inflate(requireActivity().layoutInflater).apply {
                    tvDesejacadastrar.text = titleText
                }

            binding.btnCadastrar.setOnClickListener {
                parentFragmentManager.setFragmentResult(
                    FRAGMENT_RESULT,
                    bundleOf(
                        NOME_SERVICO to binding.etNome.text.toString(),
                        VALOR_SERVICO to binding.etValor.text.toString()
                    )
                )

                dismiss()
            }

            binding.ivVoltar.setOnClickListener{
                dismiss()
            }

            AlertDialog.Builder(it)
                .setView(binding.root)
                .create()
        }?:throw IllegalStateException("Activity cannot be Null")
    }

    companion object {
        const val TITLE_TEXT = "TITLE_TEXT"
        const val FRAGMENT_RESULT = "FRAGMENT_RESULT"
        const val NOME_SERVICO = "NOME_SERVICO"
        const val VALOR_SERVICO = "VALOR_SERVICO"
        fun show(
            title: String,
            fragmentManager: FragmentManager,
            tag: String = DialogInserirServicos::class.simpleName.toString()
        ) {
            DialogInserirServicos().apply {
                arguments = bundleOf(
                    TITLE_TEXT to title,
                )
            }.show(
                fragmentManager,
                tag
            )
        }
    }
}