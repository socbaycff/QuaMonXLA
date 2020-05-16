package com.example.quamonxla

import android.app.Application
import com.example.quamonxla.util.SharePreFlag

class XLAApplication :Application() {
    override fun onCreate() {
        super.onCreate()
        SharePreFlag.getContext(applicationContext)
    }
}