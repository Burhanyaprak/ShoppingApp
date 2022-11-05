package com.burhanyaprak.shoppingapp.feature_product.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.burhanyaprak.shoppingapp.databinding.LayoutProductItemViewBinding
import com.burhanyaprak.shoppingapp.feature_product.domain.model.Product

class ProductAdapter(var itemClickListener: ((Product) -> Unit)? = null) :
    ListAdapter<Product, ProductAdapter.ProductViewHolder>(ProductDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding =
            LayoutProductItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ProductViewHolder(binding, itemClickListener)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ProductViewHolder(
        binding: LayoutProductItemViewBinding,
        private val clickedItem: ((Product) -> Unit)?
    ) :
        RecyclerView.ViewHolder(binding.root) {
        private val textViewProductName: TextView = binding.textViewProductName
        private val textViewProductPrice: TextView = binding.textViewProductPrice
        private val imageViewProductImage: ImageView = binding.imageViewProductImage
        fun bind(product: Product) {
            textViewProductName.text = product.title
            textViewProductPrice.text = product.price.toString()
            Glide.with(itemView.context)
                .load(product.image)
                .into(imageViewProductImage)

            itemView.setOnClickListener {
                clickedItem?.invoke(product)
            }
        }
    }

    class ProductDiffUtil : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

    }

}