package com.burhanyaprak.shoppingapp.feature_search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.burhanyaprak.shoppingapp.R

class SearchFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
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