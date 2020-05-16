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
            sharedPreferences.edit().putBoolean(MAIN_FLAG,false).apply()
         return isShowMainGUide

        }

        fun showStart(): Boolean {
            sharedPreferences.edit().putBoolean(START_FLAG,false).apply()
          return isShowStartGuide
        }

        fun showResult(): Boolean {
            sharedPreferences.edit().putBoolean(RESULT_FLAG,false).apply()
           return isShowResultGuide
        }

    }
}