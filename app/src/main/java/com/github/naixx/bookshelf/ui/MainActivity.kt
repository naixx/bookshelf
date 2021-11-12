package com.github.naixx.bookshelf.ui

import android.content.Context
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.naixx.bookshelf.RxObserveDsl
import com.github.naixx.bookshelf.databinding.MainActivityBinding
import com.github.naixx.bookshelf.withProgress
import com.jakewharton.rxbinding3.appcompat.queryTextChanges
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), RxObserveDsl {

    private val viewModel: BookshelfViewModel by viewModels()

    private val adapter by lazy { BooksAdapter() }
    val binding by lazy { MainActivityBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setActionBar(binding.toolbar)

        binding.recyclerView.adapter = adapter

        viewModel.books().withProgress(binding.progressBar).observe {
            adapter.submitList(it)
        }

        val position = savedInstanceState?.getInt(SELECTION) ?: 0
        binding.recyclerView.scrollToPosition(position)

        binding.search.queryTextChanges().debounce(500, TimeUnit.MILLISECONDS).switchMap {
            viewModel.books(it.toString()).withProgress(binding.progressBar)
        }.observe {
            binding.empty.isVisible = it.isEmpty()
            adapter.submitList(it)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SELECTION, (binding.recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition())
    }

    companion object {
        const val SELECTION = "selection"
    }

    override fun getContext(): Context = this
}

