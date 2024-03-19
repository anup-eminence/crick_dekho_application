package com.example.cricdekho.util

import android.content.Context
import android.view.View
import android.widget.Toast

fun Context.showToast(msg : String) {
    Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}