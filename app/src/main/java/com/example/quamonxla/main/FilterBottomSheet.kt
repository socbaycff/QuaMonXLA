package com.example.quamonxla.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.quamonxla.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.sheet_frag_filter.view.*

class FilterBottomSheet: BottomSheetDialogFragment() {



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.sheet_frag_filter, container, false)
        val filters =  view.filterGroup
        filters.check(R.id.avg)
        val mainActivity = activity as MainActivity

        filters.setOnCheckedChangeListener { group, checkedId ->
            mainActivity.filterTypeId = checkedId
        }
        return view
    }

}