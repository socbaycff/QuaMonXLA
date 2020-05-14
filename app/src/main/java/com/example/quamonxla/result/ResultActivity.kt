package com.example.quamonxla.result

import android.os.Bundle
import android.text.InputType
import android.view.Gravity
import android.view.MenuItem
import android.widget.EditText
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import com.example.quamonxla.R
import com.example.quamonxla.StartActivity
import com.example.quamonxla.main.MainActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {

    lateinit var resultMatrix: Array<IntArray>
    lateinit var calculateArr: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        val supportActionBar = supportActionBar
        supportActionBar?.title = "Kết quả lọc"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        viewpager.adapter = ResultPagerAdapter(supportFragmentManager)

        tabLayout.setupWithViewPager(viewpager)


        init()
    }

    private fun init() {
        val extra = intent.extras
        resultMatrix = extra?.get(MainActivity.EXTRA_RESULT_MATRIX) as Array<IntArray>
        calculateArr = extra?.get(MainActivity.EXTRA_CALCULATE_ARR) as Array<String>




    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else ->super.onOptionsItemSelected(item)
        }
    }
}
