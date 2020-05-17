package com.example.quamonxla.util

import android.content.Context
import android.content.SharedPreferences

class SharePreFlag {
    companion object {
        lateinit var sharedPreferences: SharedPreferences
        private var isShowMainGUide = true
        private var isShowStartGuide = true
        private var isShowResultGuide = true
        const val MAIN_FLAG = "main"
        const val START_FLAG = "start"
        const val RESULT_FLAG = "result"
        const val SPNAME = "guideShowFlag"

        fun getContext(context: Context) {
            sharedPreferences = context.getSharedPreferences(SPNAME, Context.MODE_PRIVATE)
            isShowStartGuide = sharedPreferences.getBoolean(START_FLAG, true)
            isShowMainGUide = sharedPreferences.getBoolean(MAIN_FLAG, true)
            isShowResultGuide = sharedPreferences.getBoolean(RESULT_FLAG, true)
        }

        fun showMain(): Boolean {
            val shouldShow = isShowMainGUide
            if (shouldShow) {
                sharedPreferences.edit().putBoolean(MAIN_FLAG,false).apply()
                isShowMainGUide = false
            }
         return  shouldShow
       }

        fun showStart(): Boolean {
            val shouldShow = isShowStartGuide
            if (shouldShow) {
                sharedPreferences.edit().putBoolean(START_FLAG,false).apply()
                isShowStartGuide = false
            }
            return  shouldShow
        }

        fun showResult(): Boolean {
            val shouldShow = isShowResultGuide
            if (shouldShow) {
                sharedPreferences.edit().putBoolean(RESULT_FLAG,false).apply()
                isShowResultGuide = false
            }
            return  shouldShow
        }

    }
}