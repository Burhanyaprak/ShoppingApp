package com.burhanyaprak.shoppingapp.feature_search.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.burhanyaprak.shoppingapp.R
import com.burhanyaprak.shoppingapp.databinding.FragmentSearchBinding
import com.burhanyaprak.shoppingapp.feature_product.domain.model.Product
import com.burhanyaprak.shoppingapp.feature_product.presentation.home.ProductAdapter
import com.burhanyaprak.shoppingapp.feature_product.presentation.home.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModelProduct by viewModels<ProductViewModel>()
    private val viewModelCategory by viewModels<CategoryViewModel>()
    private val spanCount = 2
    private val productAdapter by lazy {
        ProductAdapter().apply {
            itemClickListener = { product ->
                navigateToProductDetailFragment(product)
            }
        }
    }
    private val categoryAdapter by lazy {
        CategoryAdapter().apply {
            itemClickListener = { category ->
                getProductOfCategory(category)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSearchBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerViews()
        getCategories()
        listSearchedProducts()
        binding.searchViewProduct.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query?.length!! > 2) {
                    listSearchedProducts(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText?.length!! > 2) {
                    listSearchedProducts(newText)
                } else {
                    listSearchedProducts()
                }
                return false
            }
        })
    }

    private fun getCategories() {
        lifecycleScope.launch {
            viewModelCategory.getCategories()
            viewModelCategory.productsState.collectLatest { it ->
                if (it.error != null) {
                    Toast.makeText(
                        requireContext(),
                        it.error,
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (it.isLoading) {
                    //TODO(Loading animation)
                } else {
                    categoryAdapter.submitList(it.products)
                }
            }
        }
    }

    private fun listSearchedProducts(title: String = "") {
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
                    val list = arrayListOf<Product>()
                    it.products.forEach {
                        if (it.title.lowercase().contains(title)) {
                            list.add(it)
                        }
                    }
                    productAdapter.submitList(list)
                }
            }
        }
    }

    private fun getProductOfCategory(category: String) {
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
                    val list = arrayListOf<Product>()
                    it.products.forEach {
                        if (it.category.lowercase().contains(category)) {
                            list.add(it)
                        }
                    }
                    productAdapter.submitList(list)
                }
            }
        }
    }

    private fun initRecyclerViews() {
        binding.recyclerViewProducts.apply {
            layoutManager = GridLayoutManager(requireContext(), spanCount)
            adapter = productAdapter
        }
        binding.recyclerViewCategories.apply {
            adapter = categoryAdapter
        }
    }

    private fun navigateToProductDetailFragment(product: Product) {
        findNavController().navigate(
            SearchFragmentDirections.actionSearchFragmentToDetailFragment(
                product
            )
        )
    }

    override fun onResume() {
        super.onResume()
        val toolbar = requireActivity().findViewById<View>(R.id.my_toolbar) as Toolbar
        toolbar.visibility = View.VISIBLE
        toolbar.title = "Search"

        val buttonBasket = requireActivity().findViewById<View>(R.id.buttonBasket) as Button
        buttonBasket.visibility = View.VISIBLE
    }
}