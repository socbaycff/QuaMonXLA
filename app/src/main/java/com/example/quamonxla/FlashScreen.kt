package com.example.quamonxla


import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.quamonxla.main.MainActivity
import gr.net.maroulis.library.EasySplashScreen



class FlashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val config = EasySplashScreen(this)
            .withFullScreen()
            .withTargetActivity(StartActivity::class.java)
            .withSplashTimeOut(1000)
            .withBackgroundColor(Color.parseColor("#1a1b29"))
            .withHeaderText("Version 1.2")
            .withFooterText("")
            .withBeforeLogoText("")
            .withAfterLogoText("Phao XLA")
            .withLogo(R.mipmap.ic_launcher_round)
        config.headerTextView.setTextColor(Color.WHITE)
        config.footerTextView.setTextColor(Color.WHITE)
        config.beforeLogoTextView.setTextColor(Color.WHITE)
        config.afterLogoTextView.setTextColor(Color.WHITE)
        val easySplashScreen: View = config.create()
        setContentView(easySplashScreen)
    }
}
