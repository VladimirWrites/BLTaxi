package com.vlad1m1r.bltaxi.taxi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.baseui.BaseFragment
import com.vlad1m1r.baseui.observe
import com.vlad1m1r.bltaxi.domain.model.ItemTaxi
import com.vlad1m1r.bltaxi.taxi.adapter.AdapterTaxiRecycler
import com.vlad1m1r.bltaxi.taxi.adapter.ItemMoveHelperCallback
import org.koin.android.viewmodel.ext.android.viewModel
import android.view.*
import com.vlad1m1r.bltaxi.taxi.databinding.FragmentTaxiBinding
import org.koin.android.ext.android.inject


class TaxiFragment: BaseFragment(), AdapterTaxiRecycler.PositionChanged {
    private lateinit var binding: FragmentTaxiBinding

    private val viewModel: TaxiViewModel by viewModel()
    private val navigator: TaxiNavigator by inject()

    private lateinit var itemTouchHelper: ItemTouchHelper
    private lateinit var taxiAdapter: AdapterTaxiRecycler

    interface FragmentHolder {
        fun addShortcuts(itemTaxiArrayList: List<ItemTaxi>)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_taxi, container, false)
        binding = FragmentTaxiBinding.bind(view)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        setupRecycleView()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadTaxis()
        observe(viewModel.taxis) {
            getAdapter().setList(if(it.isNullOrEmpty()) emptyList() else it)
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        (activity as AppCompatActivity).supportActionBar!!.setTitle(R.string.app_name)
    }

    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
        itemTouchHelper.startDrag(viewHolder)
    }

    override fun saveChanges(taxis: List<ItemTaxi>) {
        viewModel.setTaxiOrder(taxis)
        (activity as FragmentHolder).addShortcuts(taxis)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_activity_taxi, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                activity!!.onBackPressed()
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
            binding.recyclerView.adapter = AdapterTaxiRecycler(this, viewModel)
        }
        taxiAdapter = binding.recyclerView.adapter as AdapterTaxiRecycler
        return taxiAdapter
    }
}
