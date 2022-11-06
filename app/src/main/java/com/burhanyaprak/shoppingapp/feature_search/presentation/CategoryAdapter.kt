package com.burhanyaprak.shoppingapp.feature_search.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.burhanyaprak.shoppingapp.databinding.LayoutSearchCategoriesBinding

class CategoryAdapter(var itemClickListener: ((String) -> Unit)? = null) :
    ListAdapter<String, CategoryAdapter.CategoryViewHolder>(CategoryDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding =
            LayoutSearchCategoriesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        return CategoryViewHolder(binding, itemClickListener)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CategoryViewHolder(
        binding: LayoutSearchCategoriesBinding,
        private val clickedItem: ((String) -> Unit)?
    ) :
        RecyclerView.ViewHolder(binding.root) {
        private val textViewCategoryName: TextView = binding.buttonCategory
        fun bind(category: String) {
            textViewCategoryName.text = category
            textViewCategoryName.setOnClickListener {
                clickedItem?.invoke(category)
            }
        }

    }

    class CategoryDiffUtil : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }

}