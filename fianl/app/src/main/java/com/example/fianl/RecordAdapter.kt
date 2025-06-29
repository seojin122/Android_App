package com.example.fianl

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecordAdapter(private val items: List<Record>) :
    RecyclerView.Adapter<RecordAdapter.RecordViewHolder>() {

    inner class RecordViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textDate: TextView = view.findViewById(R.id.textRecordDate)
        val textSummary: TextView = view.findViewById(R.id.textRecordSummary)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_record, parent, false)
        return RecordViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        val item = items[position]
        holder.textDate.text = item.date
        holder.textSummary.text = "체중: ${item.weight}, 수면: ${item.sleep}, 운동: ${if (item.exercised) "O" else "X"}"
    }

    override fun getItemCount(): Int = items.size
}
