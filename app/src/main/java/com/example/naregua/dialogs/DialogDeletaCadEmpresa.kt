package com.example.naregua.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.naregua.MainActivity
import com.example.naregua.databinding.DialogEmpresadeletadaBinding
import com.example.naregua.databinding.DialogUsuariodeletadoBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.lang.IllegalStateException

class DialogDeletaCadEmpresa: DialogFragment() {
    lateinit var binding: DialogEmpresadeletadaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val titleText = arguments?.getString(TITLE_TEXT)
            ?: throw IllegalArgumentException("Ops, passe o titulo")
        return activity?.let{
            binding =
                DialogEmpresadeletadaBinding.inflate(requireActivity().layoutInflater).apply {
                    tvCaddeletado.text = titleText
                }


            binding.btnVoltar.setOnClickListener {
                val auth = FirebaseAuth.getInstance()

                val userId = auth.currentUser?.uid
                auth.currentUser?.delete()
                    ?.addOnSuccessListener {

                        if (userId != null) {
                            val database = FirebaseDatabase.getInstance()
                            val usersRef = database.getReference("empresas")

                            usersRef.child(userId).removeValue()
                                .addOnSuccessListener {

                                }
                                .addOnFailureListener { exception ->

                                }
                        }
                    }

                dismiss()
                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
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
            tag: String = DialogCadDeletado::class.simpleName.toString()
        ) {
            DialogCadDeletado().apply {
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