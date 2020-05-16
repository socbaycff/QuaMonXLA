package com.example.quamonxla.result

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.quamonxla.R
import com.example.quamonxla.main.MainActivity
import com.example.quamonxla.util.SharePreFlag
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetSequence
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
        if (SharePreFlag.showResult()) {
            TapTargetSequence(this).targets(
                TapTarget.forView(tabLayout,"Chọn chế độ xem kết quả").cancelable(false).transparentTarget(true).drawShadow(true)
            ).start()
        }

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
