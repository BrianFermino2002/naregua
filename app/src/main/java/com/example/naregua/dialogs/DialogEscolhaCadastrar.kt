package com.example.naregua.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.setFragmentResult
import com.example.naregua.CadastroActivity
import com.example.naregua.CadastroEmpresaActivity
import com.example.naregua.databinding.DialogEscolhacadBinding
import com.google.android.material.datepicker.MaterialDatePicker
import java.lang.IllegalStateException
import java.text.SimpleDateFormat
import java.util.Locale

class DialogEscolhaCadastrar: DialogFragment(){
    lateinit var binding:DialogEscolhacadBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val titleText = arguments?.getString(TITLE_TEXT)
            ?: throw IllegalArgumentException("Ops, passe o titulo")
        return activity?.let{
            binding =
                DialogEscolhacadBinding.inflate(requireActivity().layoutInflater).apply {
                    tvDesejacadastrar.text = titleText
                }

                binding.btnCadempresa.setOnClickListener {
                    val intent = Intent(requireContext(), CadastroEmpresaActivity::class.java)
                    startActivity(intent)
                    setFragmentResult(
                        FRAGMENT_RESULT, bundleOf(

                        )
                    )
                    dismiss()
                }

            binding.btnCaduser.setOnClickListener {
                val intent = Intent(requireContext(), CadastroActivity::class.java)
                startActivity(intent)
                setFragmentResult(
                    FRAGMENT_RESULT, bundleOf(

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

        fun show(
            title: String,
            fragmentManager: FragmentManager,
            tag: String = DialogEscolhaCadastrar::class.simpleName.toString()
        ) {
            DialogEscolhaCadastrar().apply {
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