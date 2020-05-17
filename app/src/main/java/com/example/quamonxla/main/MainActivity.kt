package com.example.quamonxla.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
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
import com.example.quamonxla.util.SharePreFlag
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetSequence
import com.roger.catloadinglibrary.CatLoadingView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity(), View.OnFocusChangeListener, TextWatcher {

    companion object {
        const val EXTRA_RESULT_MATRIX = "extra_result_matrix"
        const val EXTRA_CALCULATE_ARR = "extra_calculate_arr"
        val catView by lazy {
            CatLoadingView()
        }
    }

    val heSoFilter by lazy {
        Array<Int>(9) { -1 }
    }

    private lateinit var filterHelper: FilterHelper
     var width: Int = 0
     var height: Int = 0
    lateinit var matrix: Array<IntArray>
    lateinit var resultMatrix: Array<IntArray>
    lateinit var calculateArr: Array<String>
    lateinit private var table: TableLayout
    var filterTypeId = R.id.avg
    private var currentRow = 0
    private var currentCol = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = toolb
        toolbar.inflateMenu(R.menu.menu_option)
       setSupportActionBar(toolbar)
        val supportActionBar = supportActionBar
        supportActionBar?.title = "Nhập ma trận"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (SharePreFlag.showMain()) {
            TapTargetSequence(this).targets(
                TapTarget.forView(tableLayout,"Nhập ma trận").cancelable(false).targetRadius(100).transparentTarget(true).drawShadow(true),
                TapTarget.forView(histogramShow,"Xem histogram").cancelable(false).transparentTarget(true).drawShadow(true),
                TapTarget.forView(chooseFilter,"Chọn bộ lọc").cancelable(false).transparentTarget(true).drawShadow(true),
                TapTarget.forToolbarMenuItem(toolb,R.id.check,"Xem kết quả").cancelable(false).transparentTarget(true).drawShadow(true)
            ).start()
        }

        init()
    }



    private fun init() {

        val intent = intent
        width = intent.getIntExtra(StartActivity.ROW_NUM, 0)
        height = intent.getIntExtra(StartActivity.COL_NUM, 0)
        filterHelper = FilterHelper(width)
        // init array
        matrix = Array(height) { IntArray(width) { -1 } } // default value la -1
        resultMatrix = Array(height) { IntArray(width) { -1 } }
        calculateArr = Array(height * width) { "" }


        // init table layout
        table = tableLayout
        for (i in 0..height - 1) {
            val row = TableRow(this)
            for (j in 0..width - 1) {
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
            FilterBottomSheet(heSoFilter,filterHelper).show(supportFragmentManager, "filter")
        }


        histogramShow.setOnClickListener {
            if (checkfillAll()) {
                val tinhHistogram = filterHelper.tinhHistogram(matrix)
                HistogramSheet(tinhHistogram).show(supportFragmentManager, "histogram")
            }
        }
    }


    fun startFilter() {

        // travel from (1,1) toi (row-1,col-1)
        if (filterTypeId == R.id.customFilter || filterTypeId == R.id.trongSo) {

            val phepLoc = when (filterTypeId) {
                R.id.customFilter -> FilterHelper::locCustom
                else -> FilterHelper::locTBTrongSo
            }
            var pixel = -1
            for (i in 1..height - 2) {
                for (j in 1..width - 2) {
                    pixel = phepLoc.invoke(filterHelper, i, j, matrix, calculateArr,heSoFilter)
                    resultMatrix[i][j] = pixel
                    print("")
                }
            }


        } else {
            val phepLoc = when (filterTypeId) {
                R.id.avg -> FilterHelper::locTB
                R.id.max -> FilterHelper::locMax
                R.id.median -> FilterHelper::locTrungVi
                R.id.midpoint -> FilterHelper::locMidpoint
                R.id.geo -> FilterHelper::locGeo
                R.id.dieuHoa -> FilterHelper::locDH
                R.id.dhtp -> FilterHelper::locDHTP
                R.id.alpha -> FilterHelper::alphaTrim
                else -> FilterHelper::locMin
            }
            var pixel = -1
            for (i in 1..height - 2) {
                for (j in 1..width - 2) {
                    pixel = phepLoc.invoke(filterHelper, i, j, matrix, calculateArr)
                    resultMatrix[i][j] = pixel
                }
            }

        }


    }


    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        // save index of selected cell
        if (hasFocus) {
            val editText = v as EditText
            if (editText.text.toString() == "X") {
                editText.setText("")
            }
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
        menuInflater.inflate(R.menu.menu_option, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.check -> {
                var flag = true
              if (filterTypeId == R.id.customFilter ||  filterTypeId == R.id.trongSo) {
                 if(!heSoFilter.all { it != -1 }) {
                     flag = false
                 }
              }
                if (!flag) {
                    Toast.makeText(applicationContext,"Chưa nhập đủ hệ số bộ lọc",Toast.LENGTH_SHORT).show()
                }

                if (checkfillAll() && flag ) {
                    val intent = Intent(this, ResultActivity::class.java)
                    intent.putExtra(EXTRA_RESULT_MATRIX, resultMatrix)
                    intent.putExtra(EXTRA_CALCULATE_ARR, calculateArr)
                    catView.setClickCancelAble(false)
                    catView.show(supportFragmentManager,"loading")
                    catView.setText("Đợi chút nhoa!!")
                    lifecycleScope.launch(Dispatchers.Default) {
                        fileEdge()
                        startFilter()
                        startActivity(intent)
                    }
                }
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun fileEdge() {
        // hang 0, hang cuoi
        for (i in 0..width-1) {
            resultMatrix[0][i] = matrix[0][i]
            resultMatrix[height-1][i] = matrix[height-1][i]
        }
        //cot 0 cot cuoi
        for (i in 0..height-1) {
            resultMatrix[i][0] = matrix[i][0]
            resultMatrix[i][width-1] = matrix[i][width-1]
        }


    }

    private fun checkfillAll(): Boolean {
        val isFill = matrix.all { row ->
            row.all {
                it != -1
            }
        }
        if (!isFill) Toast.makeText(this, "Chưa nhập đủ ma trận", Toast.LENGTH_SHORT).show()

        return isFill
    }
}
