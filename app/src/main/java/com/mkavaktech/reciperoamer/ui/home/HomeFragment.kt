package com.mkavaktech.reciperoamer.ui.home
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

import com.bumptech.glide.Glide

import com.mkavaktech.reciperoamer.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.getRandomFood()
        observerRandomFood()
    }

    private fun observerRandomFood() {
        homeViewModel.randomFoodLiveData.observe(viewLifecycleOwner
        ) { value ->
            Glide.with(this@HomeFragment).load(value.strMealThumb).into(binding.randomFoodImage)
        }
    }

}