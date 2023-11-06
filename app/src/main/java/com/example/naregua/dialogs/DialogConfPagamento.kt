package com.example.naregua.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.naregua.PrincipalUserActivity
import com.example.naregua.databinding.DialogVerificaPagamentoBinding
import java.lang.IllegalStateException

class DialogConfPagamento: DialogFragment(){
    lateinit var binding: DialogVerificaPagamentoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val titleText = arguments?.getString(TITLE_TEXT)
            ?: throw IllegalArgumentException("Ops, passe o titulo")
        return activity?.let{
            binding =
                DialogVerificaPagamentoBinding.inflate(requireActivity().layoutInflater).apply {
                tvNomeEmp.text = titleText
                }

           binding.btnVoltar.setOnClickListener {
                val intent = Intent(requireContext(), PrincipalUserActivity::class.java)
                startActivity(intent)
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

        fun show(
            title: String,
            fragmentManager: FragmentManager,
            tag: String = DialogConfPagamento::class.simpleName.toString()
        ) {
            DialogConfPagamento().apply {
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