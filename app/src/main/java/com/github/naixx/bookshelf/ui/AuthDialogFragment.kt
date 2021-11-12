package com.github.naixx.bookshelf.ui

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.github.naixx.bookshelf.R
import com.github.naixx.bookshelf.databinding.AuthDialogBinding
import com.github.naixx.bookshelf.network.AuthBody
import com.github.naixx.bookshelf.withProgress
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import common.arguments
import common.boolean
import common.handleError

class AuthDialogFragment : DialogFragment(R.layout.auth_dialog) {
    companion object {
        fun newInstance(isLogin: Boolean): AuthDialogFragment = AuthDialogFragment().arguments {
            it::isLogin += isLogin
        }
    }

    private val isLogin by boolean()
    private val viewModel: BookshelfViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val model = AuthBody("", "")
        val b = AuthDialogBinding.inflate(layoutInflater)
        b.title.text = getString(if (isLogin) R.string.login else R.string.register)
        b.model = model

        return MaterialAlertDialogBuilder(requireContext())
            .setView(b.root)
            .setPositiveButton(if (isLogin) R.string.login else R.string.register) { _, _ ->
                auth(model)
                dismiss()
            }.create()
    }

    @SuppressLint("CheckResult")
    private fun auth(model: AuthBody) {
        val source = if (!isLogin)
            viewModel.register(model)
        else
            viewModel.login(model)
        source.withProgress(requireContext()).subscribe({
            startActivity(Intent(requireContext(), MainActivity::class.java))
            activity?.finish()
        }, handleError())
    }
}
