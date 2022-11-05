package com.burhanyaprak.shoppingapp.feature_product.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.burhanyaprak.shoppingapp.R
import com.burhanyaprak.shoppingapp.feature_product.domain.model.Product
import com.burhanyaprak.shoppingapp.feature_product.domain.model.ProductLocal
import com.burhanyaprak.shoppingapp.feature_product.presentation.details.ProductLocalViewModel
import com.burhanyaprak.shoppingapp.databinding.FragmentProductBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ProductFragment : Fragment() {
    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!
    private val viewModelProduct by viewModels<ProductViewModel>()
    private val viewModelProductLocal by viewModels<ProductLocalViewModel>()
    private val spanCount = 2
    private val productAdapter by lazy {
        ProductAdapter().apply {
            itemClickListener = { product ->
                navigateToProductDetailFragment(product)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        listAllProducts()
        getBasketProducts()
    }

    private fun setBasketTotalPrice(productList: List<ProductLocal>) {
        var basketTotalPrice = 0.0
        productList.forEach {
            val productPrice = it.price * it.quantity
            basketTotalPrice += productPrice
        }
        val buttonBasket = requireActivity().findViewById<View>(R.id.buttonBasket) as Button
        buttonBasket.text = basketTotalPrice.toFloat().toString()
    }

    private fun getBasketProducts() {
        lifecycleScope.launch {
            viewModelProductLocal.getProducts()
            viewModelProductLocal.productsState.collectLatest { it ->
                if (it.error != null) {
                    Toast.makeText(
                        requireContext(),
                        it.error,
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (it.isLoading) {
                    //TODO(Loading animation)
                } else {
                    setBasketTotalPrice(it.products)
                }
            }
        }
    }

    private fun listAllProducts() {
        lifecycleScope.launch {
            viewModelProduct.getProducts()
            viewModelProduct.productsState.collectLatest { it ->
                if (it.error != null) {
                    Toast.makeText(
                        requireContext(),
                        it.error,
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (it.isLoading) {
                    //TODO(Loading animation)
                } else {
                    productAdapter.submitList(it.products)

                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val toolbar = requireActivity().findViewById<View>(R.id.my_toolbar) as Toolbar
        toolbar.visibility = View.VISIBLE
        toolbar.title = "Product"

        val buttonBasket = requireActivity().findViewById<View>(R.id.buttonBasket) as Button
        buttonBasket.visibility = View.VISIBLE
    }

    private fun initRecyclerView() {
        binding.recyclerViewProducts.apply {
            layoutManager = GridLayoutManager(requireContext(), spanCount)
            adapter = productAdapter
        }
    }

    private fun navigateToProductDetailFragment(product: Product) {
        findNavController().navigate(
            ProductFragmentDirections.actionProductFragmentToDetailFragment(
                product
            )
        )
    }
}