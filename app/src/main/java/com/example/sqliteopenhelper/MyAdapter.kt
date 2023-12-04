package com.example.sqliteopenhelper

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val countriesList: ArrayList<Place>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = countriesList[position]

        holder.tvCountryName.text = currentItem.countryName
        holder.tvTownName.text = currentItem.townName
        holder.tvRating.text = currentItem.ratingScore.toString()

        holder.mainItemLayout.setOnClickListener {
            val intent = Intent(holder.itemView.context, UpdateItemActivity::class.java).apply {
                putExtra("itemId", currentItem.idVisitedPlace)
                putExtra("countryName", currentItem.countryName)
                putExtra("townName", currentItem.townName)
                putExtra("ratingScore", currentItem.ratingScore)
            }

            try {
                holder.itemView.context.startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun getItemCount(): Int {
        return countriesList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCountryName: TextView = itemView.findViewById(R.id.tvCountryName)
        val tvTownName: TextView = itemView.findViewById(R.id.tvTown)
        val tvRating: TextView = itemView.findViewById(R.id.tvRating)
        val mainItemLayout: ConstraintLayout = itemView.findViewById(R.id.mainItemLayout)
    }
}
