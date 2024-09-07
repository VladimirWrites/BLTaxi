package com.vlad1m1r.bltaxi.about.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.vlad1m1r.bltaxi.about.ui.databinding.FragmentAboutBinding
import dagger.hilt.android.AndroidEntryPoint
import org.jetbrains.annotations.TestOnly

@AndroidEntryPoint
class AboutFragment : Fragment(R.layout.fragment_about) {
    private lateinit var binding: FragmentAboutBinding

    private val viewModel: AboutViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentAboutBinding.bind(view)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        if(activity is AppCompatActivity) {
            (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            (activity as AppCompatActivity).supportActionBar!!.setTitle(R.string.about__name)
        }
    }

    @TestOnly
    fun setViewModel(viewModel: AboutViewModel) {
        binding.viewModel = viewModel
    }
}