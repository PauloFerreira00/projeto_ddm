package br.com.farras.appzinho.features.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import br.com.farras.appzinho.R

class MainAdapter(private val events: List<String>) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.ssid.text =  events[position]
    }

    override fun getItemCount(): Int {
        return events.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var ssid: AppCompatTextView = view.findViewById(R.id.tv_event_name) as AppCompatTextView
    }
}