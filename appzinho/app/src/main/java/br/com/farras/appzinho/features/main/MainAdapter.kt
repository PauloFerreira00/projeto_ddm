package br.com.farras.appzinho.features.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import br.com.farras.appzinho.R
import br.com.farras.appzinho.databinding.ItemEventBinding
import br.com.farras.appzinho.models.Event

class MainAdapter(private val events: List<Event>) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemEventBinding = ItemEventBinding.inflate(inflater, parent, false)
        return ViewHolder(itemEventBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding(event = events[position])
    }

    override fun getItemCount(): Int {
        return events.size
    }

    inner class ViewHolder(private val itemEventBinding: ItemEventBinding) : RecyclerView.ViewHolder(itemEventBinding.root) {

        fun binding(event: Event) {
            itemEventBinding.event = event
        }
    }
}