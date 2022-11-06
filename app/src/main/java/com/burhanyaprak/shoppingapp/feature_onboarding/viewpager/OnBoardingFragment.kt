package com.burhanyaprak.shoppingapp.feature_onboarding.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.burhanyaprak.shoppingapp.R
import com.burhanyaprak.shoppingapp.databinding.FragmentOnBoardingBinding
import com.burhanyaprak.shoppingapp.feature_onboarding.OnBoardingState
import com.burhanyaprak.shoppingapp.feature_onboarding.model.OnBoarding
import com.google.firebase.auth.FirebaseAuth

class OnBoardingFragment : Fragment() {
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var onBoardingViewPager: ViewPager2
    private var _binding: FragmentOnBoardingBinding? = null
    private val binding get() = _binding!!
    private lateinit var onBoardingState: OnBoardingState
    private var auth: FirebaseAuth? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnBoardingBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        val currentUser = auth?.currentUser
        onBoardingViewPager = binding.onBoardingViewPager
        onBoardingState = OnBoardingState(requireContext())
        setOnBoardingItems()

        binding.nextOnBoarding.setOnClickListener {
            if (onBoardingViewPager.currentItem + 1 < viewPagerAdapter.itemCount) {
                onBoardingViewPager.currentItem += 1
            } else {
                onBoardingState.onBoardingFinished()
                if (currentUser != null) {
                    navigateToProductPage()
                } else {
                    navigateToLoginPage()
                }
            }
        }
        binding.prevBoarding.setOnClickListener {
            onBoardingViewPager.currentItem -= 1
        }
        binding.skipOnBoarding.setOnClickListener {
            onBoardingState.onBoardingFinished()
            if (currentUser != null) {
                navigateToProductPage()
            } else {
                navigateToLoginPage()
            }
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
                if (position + 1 == viewPagerAdapter.itemCount) {
                    binding.nextOnBoarding.text = getString(R.string.finish)
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
        findNavController().navigate(R.id.action_onBoardingFragment_to_productFragment)
    }

    private fun navigateToLoginPage() {
        findNavController().navigate(R.id.action_onBoardingFragment_to_viewPagerFragment)
    }

    override fun onResume() {
        super.onResume()
        val buttonBasket = requireActivity().findViewById<View>(R.id.buttonBasket) as Button
        buttonBasket.visibility = View.INVISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}