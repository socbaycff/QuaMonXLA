package com.example.quamonxla.result

import android.os.Bundle
import android.text.InputType
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TableRow
import android.widget.TextView
import com.example.quamonxla.R
import com.example.quamonxla.main.FilterHelper
import com.example.quamonxla.main.HistogramSheet
import com.example.quamonxla.main.MainActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_result.view.*

class ResultFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_result, container, false)
        init(v)
        return v
    }


    private fun init(v: View) {
        val resultActivity = activity as ResultActivity

        val resultMatrix = resultActivity.resultMatrix

        val rowNum = resultMatrix.size
        val colNum = resultMatrix[0].size

        // init table layout
        val table = v.resultTable
//        for (i in 0..colNum-1) {
//            val row = TableRow(context)
//            for (j in 0..rowNum-1) {
//                row.addView(TextView(context).apply {
//                    setText(resultMatrix[j][i].toString())
//                    setBackgroundResource(R.drawable.textview_border)
//                    width = 200
//                    height = 200
//                    gravity = Gravity.CENTER
//                })
//            }
//
//            table.addView(row)
//        }
        for (i in 0..rowNum-1) {
            val row = TableRow(context)
            for (j in 0..colNum-1) {
                row.addView(TextView(context).apply {
                    setText(resultMatrix[i][j].toString())
                    setBackgroundResource(R.drawable.textview_border)
                    width = 200
                    height = 200
                    gravity = Gravity.CENTER
                })
            }

            table.addView(row)
        }


        v.showHist.setOnClickListener {
            val width = resultMatrix.get(0).size
            val histogram = FilterHelper(width).tinhHistogram(resultMatrix)
            HistogramSheet(histogram).show((activity as ResultActivity).supportFragmentManager,"histogram")
        }

        MainActivity.catView.dismiss()
    }


}
