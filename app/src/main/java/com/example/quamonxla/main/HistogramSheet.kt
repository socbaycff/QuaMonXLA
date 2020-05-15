package com.example.quamonxla.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quamonxla.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.sheet_frag_histo.view.*

class HistogramSheet(val histogram: MutableMap<Int,Int>) : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.sheet_frag_histo, container, false)
        val recycler = view.histoRecycler
        recycler.adapter = HistogramRecyclerAdapter(histogram)
        recycler.layoutManager = LinearLayoutManager(context)
        return view
    }

}