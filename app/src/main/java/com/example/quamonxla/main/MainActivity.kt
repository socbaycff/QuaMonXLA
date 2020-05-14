package com.example.quamonxla.main

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.quamonxla.R
import com.example.quamonxla.StartActivity
import com.example.quamonxla.result.ResultActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity(), View.OnFocusChangeListener, TextWatcher {

    companion object {
        const val EXTRA_RESULT_MATRIX = "extra_result_matrix"
        const val EXTRA_CALCULATE_ARR  = "extra_calculate_arr"
    }
    private lateinit var filterHelper: FilterHelper
    private var width: Int = 0
    private var height: Int = 0
    lateinit var matrix: Array<IntArray>
    lateinit var resultMatrix: Array<IntArray>
    lateinit var calculateArr: Array<String>
    lateinit private var table: TableLayout
    var filterTypeId = R.id.min
    private var currentRow = 0
    private var currentCol = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val supportActionBar = supportActionBar
        supportActionBar?.title = "Nhập ma trận"
        supportActionBar?.setDefaultDisplayHomeAsUpEnabled(true)

        init()
    }


    private fun init() {

        val intent = intent
        width = intent.getIntExtra(StartActivity.ROW_NUM,0)
        height = intent.getIntExtra(StartActivity.COL_NUM,0)
        filterHelper = FilterHelper(width)
        // init array
        matrix = Array(height) { IntArray(width) {-1} } // default value la -1
        resultMatrix = matrix.copyOf()
        calculateArr = Array(height*width) { "" }


        // init table layout
        table = tableLayout
        for (i in 0..height-1) {
            val row = TableRow(this)
            for (j in 0..width-1) {
                row.addView(EditText(this).apply {
                    setText("X")
                    inputType = InputType.TYPE_CLASS_NUMBER
                    addTextChangedListener(this@MainActivity)
                    setOnFocusChangeListener(this@MainActivity)
                    width = 200
                    height = 200
                    gravity = Gravity.CENTER

                })
            }

            table.addView(row)
        }

        chooseFilter.setOnClickListener {
            FilterBottomSheet().show(supportFragmentManager,"filter")
        }
    }




    fun startFilter() {
        // travel from (1,1) toi (row-1,col-1)
        val phepLoc = when (filterTypeId) {
            R.id.avg -> FilterHelper::locTB
            R.id.max -> FilterHelper::locMax
            R.id.median -> FilterHelper::locTrungVi
            else -> FilterHelper::locMin
        }
        var pixel = -1
        for (i in 1..height-2) {
            for (j in 1..width-2) {
                pixel = phepLoc.invoke(filterHelper,i,j,matrix,calculateArr)
                resultMatrix[i][j] = pixel
            }
        }
//        // test
//        resultMatrix.forEach {
//            it.forEach {
//                Log.i("",it.toString())
//            }
//        }


    }


    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        // save index of selected cell
        if (hasFocus) {
            val editText = v as EditText
            if (editText.text.toString() == "X") {editText.setText("")}
            val row = v.parent
            currentRow = table.indexOfChild(row as View)
            currentCol = (row as TableRow).indexOfChild(v)
        }
    }
    override fun afterTextChanged(s: Editable?) {
        val string = s.toString()
        if (string != "") {
            matrix[currentRow][currentCol] = string.toInt()
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_option,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       return when (item.itemId) {
            R.id.check -> {
                val isFillAll = matrix.all { row ->
                    row.all {
                        it != -1
                    }
                }
                if (isFillAll) {
                    val intent = Intent(this,ResultActivity::class.java)
                    intent.putExtra(EXTRA_RESULT_MATRIX, resultMatrix)
                    intent.putExtra(EXTRA_CALCULATE_ARR, calculateArr)
                    lifecycleScope.launch(Dispatchers.Default) {
                        startFilter()
                        startActivity(intent)
                    }

                } else {
                    Toast.makeText(this,"Chưa nhập đủ ma trận",Toast.LENGTH_SHORT).show()
                }

                return true
            }
            else -> super.onOptionsItemSelected(item)
        }



    }
}
