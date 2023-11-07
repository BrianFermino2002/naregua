package com.example.naregua.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.naregua.databinding.DialogAjudaBinding
import com.example.naregua.databinding.DialogInserirServBinding
import java.lang.IllegalStateException

class DialogAjuda: DialogFragment() {
    lateinit var binding: DialogAjudaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val titleText = arguments?.getString(TITLE_TEXT)
            ?: throw IllegalArgumentException("Ops, passe o titulo")
        return activity?.let{
            binding =
                DialogAjudaBinding.inflate(requireActivity().layoutInflater).apply {
                    tvInformacoes.text = titleText
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
        fun show(
            title: String,
            fragmentManager: FragmentManager,
            tag: String = DialogAjuda::class.simpleName.toString()
        ) {
            DialogAjuda().apply {
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