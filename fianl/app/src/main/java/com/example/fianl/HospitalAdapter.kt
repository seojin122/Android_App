package com.example.fianl

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class HospitalAdapter(
    private val hospitalList: List<Hospital>,
    private val onItemClick: (Hospital) -> Unit
) : RecyclerView.Adapter<HospitalAdapter.HospitalViewHolder>() {

    class HospitalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.textName)
        val address: TextView = itemView.findViewById(R.id.textAddress)
        val tel: TextView = itemView.findViewById(R.id.textPhone)
        val background: LinearLayout = itemView.findViewById(R.id.cardBackground)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HospitalViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_hospital_item, parent, false)
        return HospitalViewHolder(view)
    }

    override fun onBindViewHolder(holder: HospitalViewHolder, position: Int) {
        val hospital = hospitalList[position]
        holder.name.text = hospital.name
        holder.address.text = hospital.address
        holder.tel.text = hospital.tel

        val context = holder.itemView.context
        val colorId = when (hospital.source) {
            "산후조리원" -> R.color.yellow
            "산부인과" -> R.color.light_blue
            else -> android.R.color.white
        }
        holder.background.setBackgroundColor(ContextCompat.getColor(context, colorId))

        holder.itemView.setOnClickListener {
            onItemClick(hospital)
        }
    }

    override fun getItemCount(): Int = hospitalList.size
}