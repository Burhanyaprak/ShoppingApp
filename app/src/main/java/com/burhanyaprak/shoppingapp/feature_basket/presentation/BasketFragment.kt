package com.burhanyaprak.shoppingapp.feature_basket.presentation

import android.app.AlertDialog
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.burhanyaprak.shoppingapp.R
import com.burhanyaprak.shoppingapp.databinding.FragmentBasketBinding
import com.burhanyaprak.shoppingapp.feature_product.domain.model.ProductLocal
import com.burhanyaprak.shoppingapp.feature_product.presentation.details.ProductLocalViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BasketFragment : Fragment() {
    private var _binding: FragmentBasketBinding? = null
    private val binding get() = _binding!!
    private val productLocalAdapter by lazy {
        BasketAdapter().apply {
            increaseItemClickListener = { product ->
                product.quantity = product.quantity.plus(1)
                updateQuantity(product, product.quantity)
            }
            decreaseItemClickListener = { product ->
                if(product.quantity > 1 ){
                    product.quantity = product.quantity.minus(1)
                    updateQuantity(product, product.quantity)
                }
            }
        }
    }

    private fun updateQuantity(product: ProductLocal, quantity: Int) {
        val productLocal = product.copy(quantity =  quantity)
        lifecycleScope.launch {
            viewModel.addProduct(productLocal)
        }
        listAllProducts()
    }

    private val viewModel by viewModels<ProductLocalViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBasketBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        listAllProducts()

        binding.buttonPurchase.setOnClickListener {
            askPurchaseBasket()
        }
    }

    private fun navigateProductPage() {
        findNavController().navigate(R.id.action_basketFragment_to_productFragment)
    }

    private fun initRecyclerView() {
        binding.recyclerViewBasket.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = productLocalAdapter
        }
    }

    private fun listAllProducts() {
        lifecycleScope.launch {
            viewModel.getProducts()
            viewModel.productsState.collectLatest { it ->
                if (it.error != null) {
                    Toast.makeText(
                        requireContext(), it.error, Toast.LENGTH_SHORT
                    ).show()
                } else if (it.isLoading) {
                    //TODO(Loading animation)
                } else {
                    productLocalAdapter.submitList(it.products)
                    setTotalPrice(it.products)
                }
            }
        }
    }

    private fun setTotalPrice(products: List<ProductLocal>) {
        var basketPrice = 0.0
        products.forEach {
            val totalPriceProduct = it.quantity * it.price
            basketPrice += totalPriceProduct
        }
        binding.textViewBasketPrice.text = basketPrice.toFloat().toString()
    }

    private fun askPurchaseBasket() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton(requireContext().getString(R.string.alert_dialog_positive_button)) { _, _ ->
            Toast.makeText(
                requireContext(),
                getString(R.string.purchase_successful),
                Toast.LENGTH_LONG
            ).show()
            clearBasket()
            navigateProductPage()
        }
        builder.setNegativeButton(requireContext().getString(R.string.alert_dialog_negative_button)) { _, _ -> }
        builder.setMessage(requireContext().getString(R.string.alert_dialog_message))
        builder.create().show()
    }

    private fun clearBasket() {
        lifecycleScope.launch {
            viewModel.clearBasket()
        }
        val buttonBasket = requireActivity().findViewById<View>(R.id.buttonBasket) as Button
        buttonBasket.text = "0.0"
    }

    override fun onResume() {
        super.onResume()
        val toolbar = requireActivity().findViewById<View>(R.id.my_toolbar) as Toolbar
        toolbar.visibility = View.INVISIBLE
    }
}
