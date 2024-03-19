package com.example.cricdekho.util

import android.graphics.Color

interface ProgressbarListener {
    fun showProgressBar(progressColor : Int?=null)
    fun hideProgressBar()
}