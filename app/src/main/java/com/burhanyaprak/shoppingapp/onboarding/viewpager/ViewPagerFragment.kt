package com.burhanyaprak.shoppingapp.onboarding.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.burhanyaprak.shoppingapp.R
import com.burhanyaprak.shoppingapp.databinding.FragmentViewPagerBinding
import com.burhanyaprak.shoppingapp.onboarding.model.OnBoarding

class ViewPagerFragment : Fragment() {
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var onBoardingViewPager: ViewPager2
    private var _binding: FragmentViewPagerBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewPagerBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBoardingViewPager = binding.onBoardingViewPager
        setOnBoardingItems()

        binding.nextOnBoarding.setOnClickListener {
            if (onBoardingViewPager.currentItem + 1 < viewPagerAdapter.itemCount) {
                onBoardingViewPager.currentItem += 1
            } else {
                navigateToProductPage()
            }
        }
        binding.prevBoarding.setOnClickListener {
            onBoardingViewPager.currentItem -= 1
        }
        binding.skipOnBoarding.setOnClickListener {
            navigateToProductPage()
        }
        onBoardingViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position > 0) {
                    binding.prevBoarding.visibility = View.VISIBLE
                } else {
                    binding.prevBoarding.visibility = View.INVISIBLE
                }
                if(position == viewPagerAdapter.itemCount){
                    binding.skipOnBoarding.text = getString(R.string.finish)
                }
            }
        })
    }

    private fun setOnBoardingItems() {
        viewPagerAdapter = ViewPagerAdapter(
            listOf(
                OnBoarding(
                    onBoardingImage = R.drawable.ic_products,
                    shortDescription = "Contrary to popular belief",
                    longDescription = "Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old."
                ),
                OnBoarding(
                    onBoardingImage = R.drawable.ic_basket,
                    shortDescription = "There are many variations of passages",
                    longDescription = "Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable"
                ),
                OnBoarding(
                    onBoardingImage = R.drawable.ic_payment,
                    shortDescription = "Lorem ipsum dolor sit amet",
                    longDescription = "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium"
                )
            )
        )
        onBoardingViewPager.adapter = viewPagerAdapter
    }

    private fun navigateToProductPage() {
        findNavController().navigate(R.id.productFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}