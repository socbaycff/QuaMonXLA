package com.example.quamonxla

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar
import com.example.quamonxla.main.MainActivity
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : AppCompatActivity() {

    companion object {
        var COL_NUM = "rowNum"
        var ROW_NUM = "colNum"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        val supportActionBar = supportActionBar
        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.action_bar)

//        TapTargetSequence(this).targets(
//            TapTarget.forView(rowNumEditText,"1. Nhập số dòng của ma trận").cancelable(false).transparentTarget(true).drawShadow(true),
//            TapTarget.forView(colNumEditText,"2. Nhập số cột của ma trận").cancelable(false).transparentTarget(true).drawShadow(true),
//            TapTarget.forView(nextBtn,"3. Nhấn để tiếp tục nhập ma trận").cancelable(false).transparentTarget(true).drawShadow(true)
//        ).start()
    }


    fun next(v: View) {
        val row = rowNumEditText.text.toString()
        val col = colNumEditText.text.toString()
        if ( row != "" && col != "")
        startActivity(Intent(this, MainActivity::class.java).apply {
            putExtra(ROW_NUM,row.toInt())
            putExtra(COL_NUM,col.toInt())
        })
    }
}
