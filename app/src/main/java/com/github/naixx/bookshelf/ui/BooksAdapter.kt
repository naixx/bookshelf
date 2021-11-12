package com.github.naixx.bookshelf.ui

import androidx.recyclerview.widget.DiffUtil
import com.github.naixx.bookshelf.databinding.BooksListitemBinding
import com.github.naixx.bookshelf.network.Book
import common.adapter.SimpleListAdapter
import common.toast

class BooksAdapter() :
    SimpleListAdapter<Book, BooksListitemBinding>(
        { layoutInflater, viewGroup, b -> BooksListitemBinding.inflate(layoutInflater, viewGroup, b) },
        object : DiffUtil.ItemCallback<Book?>() {
            override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean =
                oldItem == newItem
        }) {
    override fun onBind(item: Book, binding: BooksListitemBinding, position: Int) {
        binding.item = item
        binding.root.setOnClickListener { binding.root.context.toast("Not implemented") }
    }
}

