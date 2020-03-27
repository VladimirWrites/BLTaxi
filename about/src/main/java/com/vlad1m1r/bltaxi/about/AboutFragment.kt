package com.vlad1m1r.bltaxi.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.vlad1m1r.baseui.BaseFragment
import com.vlad1m1r.bltaxi.about.databinding.FragmentAboutBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AboutFragment : BaseFragment() {
    private lateinit var binding: FragmentAboutBinding

    private val viewModel: AboutViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_about, container, false)
        binding = FragmentAboutBinding.bind(view)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        if(activity is AppCompatActivity) {
            (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            (activity as AppCompatActivity).supportActionBar!!.setTitle(R.string.about__name)
        }

        return view
    }
}