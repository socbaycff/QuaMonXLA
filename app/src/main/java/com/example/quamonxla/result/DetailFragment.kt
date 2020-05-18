package com.example.quamonxla.result


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quamonxla.R
import com.example.quamonxla.main.HistogramSheet
import kotlinx.android.synthetic.main.fragment_detail.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val v = inflater.inflate(R.layout.fragment_detail, container, false)
        val recycler = v.recycler
        val calculateArr = (activity as ResultActivity).calculateArr
        lifecycleScope.launch(Dispatchers.Default) {
            recycler.adapter = DetailAdapter(calculateArr)
            recycler.layoutManager = LinearLayoutManager(context)
            recycler.setHasFixedSize(true)
        }

        return v
    }


}
