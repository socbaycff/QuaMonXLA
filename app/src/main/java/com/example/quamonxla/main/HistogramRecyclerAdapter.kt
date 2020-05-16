package com.example.quamonxla.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quamonxla.R
import kotlinx.android.synthetic.main.item_pixel_value.view.*

class HistogramRecyclerAdapter( histogram: MutableMap<Int,Int>) : RecyclerView.Adapter<HistogramRecyclerAdapter.PixelValueHolder>() {
    val entries = histogram.entries.toList()


    class PixelValueHolder(v: View) : RecyclerView.ViewHolder(v) {
        companion object {
            fun from(viewGroup: ViewGroup): PixelValueHolder {
                val view = LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.item_pixel_value, viewGroup, false)

                return PixelValueHolder(view)
            }
        }


        fun bind(entry: MutableMap.MutableEntry<Int, Int>) {
            itemView.pixelValueTV.text = "Pixel ${entry.key}: ${entry.value}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PixelValueHolder {
        val holder = PixelValueHolder.from(parent)
        return holder
    }

    override fun getItemCount(): Int {
        return entries.size
    }

    override fun onBindViewHolder(holder: PixelValueHolder, position: Int) {
        val mutableEntry = entries.get(position)
        holder.bind(mutableEntry)
    }
}