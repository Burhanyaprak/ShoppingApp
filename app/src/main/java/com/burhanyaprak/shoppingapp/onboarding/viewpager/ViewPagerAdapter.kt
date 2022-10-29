package com.burhanyaprak.shoppingapp.onboarding.viewpager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.burhanyaprak.shoppingapp.databinding.LayoutOnboaringItemBinding
import com.burhanyaprak.shoppingapp.onboarding.model.OnBoarding

class ViewPagerAdapter(private val onBoardingItems: List<OnBoarding>) :
    RecyclerView.Adapter<ViewPagerAdapter.OnBoardingItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingItemViewHolder {
        return OnBoardingItemViewHolder(
            LayoutOnboaringItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: OnBoardingItemViewHolder, position: Int) {
        holder.bind(onBoardingItems[position])
    }

    class OnBoardingItemViewHolder(binding: LayoutOnboaringItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val imageOnBoardingItem = binding.imageViewOnBoarding
        private val textTitle = binding.shortDescription
        private val textDescription = binding.longDescription

        fun bind(onBoarding: OnBoarding) {
            imageOnBoardingItem.setImageResource(onBoarding.onBoardingImage)
            textTitle.text = onBoarding.shortDescription
            textDescription.text = onBoarding.longDescription
        }
    }

    override fun getItemCount(): Int {
        return onBoardingItems.size
    }
}