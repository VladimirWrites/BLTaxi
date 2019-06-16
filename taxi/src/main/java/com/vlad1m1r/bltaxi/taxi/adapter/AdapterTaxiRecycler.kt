package com.vlad1m1r.bltaxi.taxi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.bltaxi.domain.model.ItemTaxi
import com.vlad1m1r.bltaxi.taxi.BR
import com.vlad1m1r.bltaxi.taxi.R
import com.vlad1m1r.bltaxi.taxi.TaxiViewModel
import java.lang.IllegalArgumentException

import java.util.Collections

private const val TYPE_TAXI = 0

class AdapterTaxiRecycler(private val listener: PositionChanged,
                          private val taxiViewModel: TaxiViewModel) :
    RecyclerView.Adapter<ViewHolderTaxi>(), MoveItem {

    private var list: List<ItemTaxi> = emptyList()

    interface PositionChanged {
        fun onStartDrag(viewHolder: RecyclerView.ViewHolder)
        fun saveChanges(taxis: List<ItemTaxi>)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderTaxi {

        return when (viewType) {
            TYPE_TAXI -> ViewHolderTaxi(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_taxi,
                    parent,
                    false
                )
            )
            else -> throw IllegalArgumentException("Adapter supports only ViewHolderTaxi")
        }
    }

    override fun onBindViewHolder(holder: ViewHolderTaxi, position: Int) {

        when (holder.itemViewType) {

            TYPE_TAXI -> {

                val itemTaxi = list[position]

                holder.binding.setVariable(BR.itemTaxi, itemTaxi)
                holder.binding.setVariable(BR.viewModel, taxiViewModel)
                holder.binding.executePendingBindings()

                holder.itemView.visibility = View.VISIBLE

                holder.itemView.setOnLongClickListener {
                    listener.onStartDrag(holder)
                    false
                }
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return list[position].id
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

    fun setList(list: List<ItemTaxi>) {
        this.list = list
        notifyDataSetChanged()
    }
}
