package com.example.cricdekho.ui.settings

import MySharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.example.cricdekho.R
import com.example.cricdekho.databinding.SettingDialogBinding
import com.example.cricdekho.util.Constants
import com.example.cricdekho.util.showToast

class SettingUI : DialogFragment() {

    private lateinit var binding: SettingDialogBinding
    private var themeChangeListener: ThemeChangeListener? = null

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

        val themeId = MySharedPreferences.readInt(Constants.PrefConstantas.THEME_ID, 0)

        when (themeId) {
            1 -> {
                binding.darkTheme.isChecked = true
                setDarkTheme()
            }
            2 -> {
                binding.systemDefault.isChecked = true
                setLightTheme()
            }
            else -> {
                binding.lightTheme.isChecked = true
                setLightTheme()
            }
        }

        binding.okTv.setOnClickListener {
            if (dialog != null && dialog?.isShowing == true) {
                dialog?.dismiss()
            }
        }

        binding.themeRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            val radioId = group.findViewById<RadioButton>(checkedId)
            when (radioId.text.toString()) {
                "Default" -> {
                    themeChangeListener?.changeTheme(ThemeType.DEFAULT)
                    setLightTheme()
                }
                "Sporty" -> {
                    themeChangeListener?.changeTheme(ThemeType.SPORTY)
                    setLightTheme()
                }
                "Classic" -> {
                    themeChangeListener?.changeTheme(ThemeType.CLASSIC)
                    setLightTheme()
                }
            }
        }

        binding.darkModePrefRadio.setOnCheckedChangeListener { group, checkedId ->
            val radioId = group.findViewById<RadioButton>(checkedId)
            val selectedTheme = radioId.text.toString()

            if (selectedTheme != previousTheme) { // Check if the selected theme is different from the previous one
                previousTheme = selectedTheme
                when (radioId.text.toString()) {
                    "System default" -> {
                        themeChangeListener?.changeTheme(ThemeType.SYSTEM_DEFAULT)
                        setLightTheme()
                    }

                    "Light" -> {
                        themeChangeListener?.changeTheme(ThemeType.LIGHT)
                        setLightTheme()
                    }

                    "Dark" -> {
                        themeChangeListener?.changeTheme(ThemeType.DARK)
                        setDarkTheme()
                    }
                }
                dialog?.dismiss()
            }
        }

        binding.darkModePrefRadio.checkedRadioButtonId

        return binding.root
    }

    private fun setLightTheme() {
        binding.apply {
            root.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.dialog_rounded_bg))
            settingTv.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            themeTv.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            defaultTheme.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            sportyTheme.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            classicTheme.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            darkModePreference.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            systemDefault.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            lightTheme.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            darkTheme.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            tvPrivacyPolicy.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            tvLicence.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            tvBrandGuidelines.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            tvFeedback.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            okTv.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        }
    }

    private fun setDarkTheme() {
        binding.apply {
            root.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.dialog_rounded_bg_night))
            settingTv.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            themeTv.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            defaultTheme.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            sportyTheme.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            classicTheme.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            darkModePreference.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            systemDefault.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            lightTheme.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            darkTheme.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            tvPrivacyPolicy.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            tvLicence.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            tvBrandGuidelines.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            tvFeedback.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            okTv.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        }
    }

    fun setLister(themeChangeListener: ThemeChangeListener) {
        this.themeChangeListener = themeChangeListener
    }

    interface ThemeChangeListener {
        fun changeTheme(theme: ThemeType)
    }
}

enum class ThemeType() {
    LIGHT,
    DARK,
    SYSTEM_DEFAULT,
    DEFAULT,
    SPORTY,
    CLASSIC
}
