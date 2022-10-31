package com.burhanyaprak.shoppingapp.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.burhanyaprak.shoppingapp.R
import com.burhanyaprak.shoppingapp.databinding.FragmentSplashBinding
import com.burhanyaprak.shoppingapp.onboarding.OnBoardingState
import com.google.firebase.auth.FirebaseAuth

class SplashFragment : Fragment() {
    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!
    private lateinit var onBoardingState: OnBoardingState
    private var auth: FirebaseAuth? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSplashBinding.inflate(layoutInflater, container, false)
        auth = FirebaseAuth.getInstance()
        onBoardingState = OnBoardingState(requireContext())
        Handler(Looper.myLooper()!!).postDelayed({
            val currentUser = auth?.currentUser
            if (currentUser != null) {
                navigateToProductPage()
            } else {
                if (onBoardingState.onBoardingOpenState()) {
                    navigateToLoginPage()
                } else {
                    navigateToOnBoardingPage()
                }
            }
        }, 2000)

        return binding.root
    }

    private fun navigateToProductPage() {
        findNavController().navigate(R.id.action_splashFragment_to_productFragment)
    }

    private fun navigateToLoginPage() {
        findNavController().navigate(R.id.action_splashFragment_to_viewPagerFragment)
    }

    private fun navigateToOnBoardingPage() {
        findNavController().navigate(R.id.action_splashFragment_to_onBoardingFragment)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}