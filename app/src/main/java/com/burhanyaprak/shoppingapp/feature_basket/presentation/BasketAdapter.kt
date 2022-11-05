package com.burhanyaprak.shoppingapp.feature_basket.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.burhanyaprak.shoppingapp.databinding.LayoutBasketItemViewBinding
import com.burhanyaprak.shoppingapp.feature_product.domain.model.ProductLocal

class BasketAdapter :
    ListAdapter<ProductLocal, BasketAdapter.BasketViewHolder>(
        BasketDiffUtil()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketViewHolder {
        val binding =
            LayoutBasketItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return BasketViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class BasketViewHolder(
        binding: LayoutBasketItemViewBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        private val textViewProductName: TextView = binding.textViewProductName
        private val textViewProductPrice: TextView = binding.textViewProductPrice
        private val imageViewProductImage: ImageView = binding.imageViewProductImage
        private val textViewProductQuantity: TextView = binding.textViewProductQuantity
        fun bind(product: ProductLocal) {
            textViewProductName.text = product.title
            textViewProductPrice.text = product.price.toString()
            textViewProductQuantity.text = product.quantity.toString()
            Glide.with(itemView.context)
                .load(product.image)
                .into(imageViewProductImage)
        }
    }

    class BasketDiffUtil : DiffUtil.ItemCallback<ProductLocal>() {
        override fun areItemsTheSame(oldItem: ProductLocal, newItem: ProductLocal): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductLocal, newItem: ProductLocal): Boolean {
            return oldItem == newItem
        }

    }

}
