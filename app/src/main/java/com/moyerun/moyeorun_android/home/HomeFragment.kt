package com.moyerun.moyeorun_android.home

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.moyerun.moyeorun_android.common.extension.setOnDebounceClickListener
import com.moyerun.moyeorun_android.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.addButton.setOnDebounceClickListener {
            val action = HomeFragmentDirections.actionHomeToCreateRoom()
            it?.findNavController()?.navigate(action)
        }

        return binding.root
    }
}