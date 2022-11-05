package com.burhanyaprak.shoppingapp.feature_product.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.burhanyaprak.shoppingapp.R
import com.burhanyaprak.shoppingapp.databinding.FragmentDetailBinding
import com.burhanyaprak.shoppingapp.feature_product.domain.model.ProductLocal
import com.burhanyaprak.shoppingapp.core.util.cycleTextViewExpansion
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<DetailFragmentArgs>()
    private val viewModel by viewModels<ProductLocalViewModel>()
    private var productQuantity = 1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textViewProductName.text = args.product.title
        binding.textViewProductPrice.text = args.product.price.toString()

        binding.textViewProductDescription.text = args.product.description
        binding.textViewProductDescription.setOnClickListener {
            binding.textViewProductDescription.cycleTextViewExpansion(binding.textViewProductDescription)
        }


        //TODO(Create a extention func for glide)
        Glide.with(requireContext())
            .load(args.product.image)
            .into(binding.imageViewProductImage)
        binding.textViewProductRating.text = args.product.rating.rate.toString()

        binding.buttonAddToBasket.setOnClickListener {
            val product =
                ProductLocal(0, args.product.title, args.product.image, args.product.price, productQuantity)
            lifecycleScope.launch {
                viewModel.addProduct(product)
                setBasketTotalPrice(product)
            }
        }

        binding.imageButtonIncreaseQuantity.setOnClickListener {
            productQuantity += 1
            "$productQuantity pics".also { binding.textViewProductQuantity.text = it }
        }
        binding.imageButtonDecreaseQuantity.setOnClickListener {
            if (productQuantity > 1) {
                productQuantity -= 1
                "$productQuantity pics".also { binding.textViewProductQuantity.text = it }
            }
        }
    }

    private fun setBasketTotalPrice(product: ProductLocal) {
        val buttonBasket = requireActivity().findViewById<View>(R.id.buttonBasket) as Button
        var basketTotalPrice: Double = buttonBasket.text.toString().toDouble()
        basketTotalPrice += product.price * productQuantity
        buttonBasket.text = basketTotalPrice.toFloat().toString()
    }

    override fun onResume() {
        super.onResume()
        val toolbar = requireActivity().findViewById<View>(R.id.my_toolbar) as Toolbar
        toolbar.visibility = View.INVISIBLE

        val buttonBasket = requireActivity().findViewById<View>(R.id.buttonBasket) as Button
        buttonBasket.visibility = View.INVISIBLE
    }
}