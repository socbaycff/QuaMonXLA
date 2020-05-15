package com.example.quamonxla.result

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quamonxla.R
import kotlinx.android.synthetic.main.item_detail.view.*

class DetailAdapter( list: Array<String>) : RecyclerView.Adapter<DetailAdapter.DetailHolder>() {
    val detailList = list.filter {
        it != ""
    }

    class DetailHolder(v: View) : RecyclerView.ViewHolder(v) {
        companion object {
            fun from(parent: ViewGroup): DetailHolder {
                val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.item_detail, parent, false)
                return DetailHolder(view)
            }
        }


        fun bind(str: String) {
            itemView.detail_text.setText(str)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailHolder {
        val holder = DetailHolder.from(parent)
        return holder
    }

    override fun getItemCount(): Int {
        return detailList.size
    }

    override fun onBindViewHolder(holder: DetailHolder, position: Int) {
      holder.bind(detailList[position])
    }
}