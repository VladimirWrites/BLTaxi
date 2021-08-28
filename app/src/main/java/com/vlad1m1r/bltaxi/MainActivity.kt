package com.vlad1m1r.bltaxi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.navigation.findNavController
import com.vlad1m1r.bltaxi.taxi.ui.TaxiNavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var binding: ViewDataBinding? = null

    @Inject
    lateinit var navigator: TaxiNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    override fun onSupportNavigateUp() = (navigator as Navigator).navigateUp()?:false

    override fun onResume() {
        super.onResume()
        (navigator as Navigator).bind(findNavController(R.id.nav_host_fragment))
    }

    override fun onPause() {
        super.onPause()
        (navigator as Navigator).unbind()
    }
}
