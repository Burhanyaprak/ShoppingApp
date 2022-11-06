package com.burhanyaprak.shoppingapp.feature_basket.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.burhanyaprak.shoppingapp.databinding.LayoutBasketItemViewBinding
import com.burhanyaprak.shoppingapp.feature_product.domain.model.ProductLocal

class BasketAdapter(
    var increaseItemClickListener: ((ProductLocal) -> Unit)? = null,
    var decreaseItemClickListener: ((ProductLocal) -> Unit)? = null
) :
    ListAdapter<ProductLocal, BasketAdapter.BasketViewHolder>(
        BasketDiffUtil()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketViewHolder {
        val binding =
            LayoutBasketItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return BasketViewHolder(binding, increaseItemClickListener, decreaseItemClickListener)
    }

    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class BasketViewHolder(
        binding: LayoutBasketItemViewBinding,
        private val increaseClickedItem: ((ProductLocal) -> Unit)?,
        private val decreaseClickedItem: ((ProductLocal) -> Unit)?
    ) :
        RecyclerView.ViewHolder(binding.root) {
        private val textViewProductName: TextView = binding.textViewProductName
        private val textViewProductPrice: TextView = binding.textViewProductPrice
        private val imageViewProductImage: ImageView = binding.imageViewProductImage
        private val textViewProductQuantity: TextView = binding.textViewProductQuantity
        private val buttonIncrease: ImageButton = binding.buttonIncreaseQuantity
        private val buttonDecrease: ImageButton = binding.buttonDecreaseQuantity
        fun bind(product: ProductLocal) {
            textViewProductName.text = product.title
            textViewProductPrice.text = product.price.toString()
            textViewProductQuantity.text = product.quantity.toString()
            Glide.with(itemView.context)
                .load(product.image)
                .into(imageViewProductImage)

            buttonIncrease.setOnClickListener {
                increaseClickedItem?.invoke(product)
                textViewProductQuantity.text = product.quantity.toString()
            }
            buttonDecrease.setOnClickListener {
                decreaseClickedItem?.invoke(product)
                textViewProductQuantity.text = product.quantity.toString()
            }
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
