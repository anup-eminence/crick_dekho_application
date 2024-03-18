package com.example.cricdekho.ui.settings

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.DialogFragment
import com.example.cricdekho.R
import com.example.cricdekho.databinding.SettingDialogBinding
import com.example.cricdekho.util.showToast

class SettingUI : DialogFragment() {

    private lateinit var binding : SettingDialogBinding
    private lateinit var themeChangeListener: ThemeChangeListener
    var previousTheme: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = SettingDialogBinding.inflate(layoutInflater)
        if (dialog != null && dialog!!.window != null) {
            dialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
            dialog!!.window?.requestFeature(Window.FEATURE_NO_TITLE);
        }

        binding.okTv.setOnClickListener {
            if (dialog != null && dialog?.isShowing == true){
                dialog?.dismiss()
            }
        }

       binding.themeRadioGroup.setOnCheckedChangeListener { group, checkedId ->
           val radioId = group.findViewById<RadioButton>(checkedId)
           when(radioId.text.toString()){
               "Default" -> {}
               "Sporty" -> {}
               "Classic" -> {}
           }
       }

        binding.darkModePrefRadio.setOnCheckedChangeListener { group, checkedId ->
            val radioId = group.findViewById<RadioButton>(checkedId)
            val selectedTheme = radioId.text.toString()

            if (selectedTheme != previousTheme) { // Check if the selected theme is different from the previous one
                previousTheme = selectedTheme
                when (radioId.text.toString()) {
                    "System default" -> {}
                    "Light" -> {}
                    "Dark" -> {
                        if (::themeChangeListener.isInitialized) {
                            themeChangeListener.changeTheme("Dark")
                        }
                    }
                }
            }
        }

        binding.darkModePrefRadio.checkedRadioButtonId

        return binding.root
    }

    fun setLister(themeChangeListener: ThemeChangeListener){
        this.themeChangeListener = themeChangeListener
    }

    interface ThemeChangeListener {
        fun changeTheme(theme : String)
    }
}
