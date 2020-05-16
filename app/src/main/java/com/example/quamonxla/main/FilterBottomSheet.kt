package com.example.quamonxla.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.core.view.forEach
import androidx.fragment.app.DialogFragment
import com.example.quamonxla.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.sheet_frag_filter.*
import kotlinx.android.synthetic.main.sheet_frag_filter.view.*

class FilterBottomSheet(val heSoFilter: Array<Int>, val filterHelper: FilterHelper) :
    BottomSheetDialogFragment() {
    lateinit var filterTable: TableLayout
    lateinit var qValue: EditText
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.sheet_frag_filter, container, false)
        qValue = view.qValue
        val filters = view.filterGroup
        filterTable = view.filterTable
        val mainActivity = activity as MainActivity
        filters.check(mainActivity.filterTypeId)

        when (mainActivity.filterTypeId) {
            R.id.customFilter -> view.filterTable.visibility = View.VISIBLE
            R.id.qValue ->  view.qValue.visibility = View.VISIBLE
            else -> {}
        }

        filters.setOnCheckedChangeListener { group, checkedId ->
            mainActivity.filterTypeId = checkedId
            qValue.visibility = View.GONE
            filterTable.visibility = View.GONE
            when (checkedId) {
                R.id.customFilter,R.id.trongSo -> {
                    filterTable.visibility = View.VISIBLE
                    Toast.makeText(context, "Nhập các hệ số của bộ lọc", Toast.LENGTH_SHORT).show()
                }
                R.id.dhtp -> {
                    qValue.visibility = View.VISIBLE
                }
                else -> {}

            }

        }
        return view
    }

    override fun onDestroy() {
        if (filterTable.visibility == View.VISIBLE) getHeSoFilter()
        if (qValue.visibility == View.VISIBLE) {
            val string = qValue.text.toString()
            if (string != "") {
                filterHelper.qValue = string.toInt()
            }

        }
        super.onDestroy()

    }


    private fun getHeSoFilter() {
        var index = 0
        for (i in 0..filterTable.childCount - 1) {
            val row = filterTable.getChildAt(i) as TableRow
            for (j in 0..row.childCount - 1) {
                val editText = row.getChildAt(j) as EditText
                heSoFilter[index++] = editText.text.toString().toInt()

            }
        }

    }


}