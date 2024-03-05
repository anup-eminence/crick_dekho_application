package com.example.cricdekho.ui.home

import android.content.Context
import androidx.fragment.app.Fragment
import com.example.cricdekho.util.ProgressbarListener

open class BaseFragment : Fragment() {
    lateinit var progressBarListener: ProgressbarListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ProgressbarListener) {
            progressBarListener = context
        } else {
            throw RuntimeException("$context must implement CommonProgressBarListener")
        }
    }
}