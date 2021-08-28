package com.vlad1m1r.bltaxi.taxi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.baseui.observe
import com.vlad1m1r.bltaxi.taxi.adapter.AdapterTaxiRecycler
import com.vlad1m1r.bltaxi.taxi.adapter.ItemMoveHelperCallback
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.vlad1m1r.bltaxi.taxi.adapter.ItemTaxiViewModel
import com.vlad1m1r.bltaxi.taxi.databinding.FragmentTaxiBinding
import dagger.hilt.android.AndroidEntryPoint
import org.jetbrains.annotations.TestOnly
import javax.inject.Inject

@AndroidEntryPoint
class TaxiFragment: Fragment(R.layout.fragment_taxi), AdapterTaxiRecycler.PositionChanged {

    private val viewModel: TaxiViewModel by viewModels()

    @Inject
    lateinit var navigator: TaxiNavigator

    private lateinit var binding: FragmentTaxiBinding
    private lateinit var itemTouchHelper: ItemTouchHelper
    private lateinit var taxiAdapter: AdapterTaxiRecycler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTaxiBinding.bind(view)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        setupRecycleView()
        binding.viewModel!!.loadTaxis()
        observe(binding.viewModel!!.taxis) {
            getAdapter().setList(if(it.isNullOrEmpty()) emptyList() else it)
        }
    }

    override fun onResume() {
        super.onResume()
        if(activity is AppCompatActivity) {
            (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(false)
            (activity as AppCompatActivity).supportActionBar!!.setTitle(R.string.app_name)
        }
    }

    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
        itemTouchHelper.startDrag(viewHolder)
    }

    override fun saveChanges(taxis: List<ItemTaxiViewModel>) {
        binding.viewModel!!.setTaxiOrder(taxis)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_activity_taxi, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                requireActivity().onBackPressed()
                return true
            }
            R.id.menu_item_settings -> {
                navigator.openSettingsScreen()
                return true
            }
            R.id.menu_item_about -> {
                navigator.openAboutScreen()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupRecycleView() {
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = GridLayoutManager(context, resources.getInteger(R.integer.taxi_span))

        val callback = ItemMoveHelperCallback(getAdapter())
        itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }

    private fun getAdapter(): AdapterTaxiRecycler {
        if (binding.recyclerView.adapter != null) {
        } else if (::taxiAdapter.isInitialized) {
            binding.recyclerView.adapter = taxiAdapter
        } else {
            binding.recyclerView.adapter = AdapterTaxiRecycler(this)
        }
        taxiAdapter = binding.recyclerView.adapter as AdapterTaxiRecycler
        return taxiAdapter
    }

    @TestOnly
    fun setViewModel(viewModel: TaxiViewModel) {
        binding.viewModel = viewModel
    }
}
