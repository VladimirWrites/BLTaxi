package com.vlad1m1r.bltaxi.taxi.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.bltaxi.taxi.BR
import com.vlad1m1r.bltaxi.taxi.R
import java.lang.IllegalArgumentException

import java.util.Collections

private const val TYPE_TAXI = 0

class AdapterTaxiRecycler(private val listener: PositionChanged) :
    RecyclerView.Adapter<TaxiViewHolder>(), MoveItem {

    private var list: List<ItemTaxiViewModel> = emptyList()

    interface PositionChanged {
        fun onStartDrag(viewHolder: RecyclerView.ViewHolder)
        fun saveChanges(taxis: List<ItemTaxiViewModel>)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaxiViewHolder {

        return when (viewType) {
            TYPE_TAXI -> TaxiViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_taxi,
                    parent,
                    false
                )
            )
            else -> throw IllegalArgumentException("Adapter supports only TaxiViewHolder")
        }
    }

    override fun onBindViewHolder(taxiViewHolder: TaxiViewHolder, position: Int) {

        when (taxiViewHolder.itemViewType) {

            TYPE_TAXI -> {

                val itemTaxiViewModel = list[position]

                taxiViewHolder.binding.setVariable(BR.viewModel, itemTaxiViewModel)
                taxiViewHolder.binding.executePendingBindings()

                taxiViewHolder.itemView.setOnLongClickListener {
                    listener.onStartDrag(taxiViewHolder)
                    false
                }
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return list[position].itemTaxi.id
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        Collections.swap(list, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
        listener.saveChanges(list)
        return true
    }

    override fun getItemViewType(position: Int): Int {
        return TYPE_TAXI
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setList(list: List<ItemTaxiViewModel>) {
        this.list = list
        notifyDataSetChanged()
    }
}
