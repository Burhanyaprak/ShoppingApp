package com.burhanyaprak.shoppingapp.onboarding

import android.content.Context

class OnBoardingState(
    private val context: Context,
) {
    private val sharedPref by lazy {
        context.getSharedPreferences("settings", Context.MODE_PRIVATE)
    }

    fun onBoardingFinished() {
        val sharedPrefEditor = sharedPref.edit()
        sharedPrefEditor.putBoolean("onBoardingState", true)
        sharedPrefEditor.apply()
    }

    fun onBoardingOpenState(): Boolean {
        return sharedPref.getBoolean("onBoardingState", false)
    }
}