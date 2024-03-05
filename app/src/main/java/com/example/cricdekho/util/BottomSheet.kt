package com.example.cricdekho.util

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.cricdekho.R
import com.example.cricdekho.databinding.SearchviewDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: SearchviewDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = SearchviewDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivClear.setOnClickListener {
            dismiss()
        }

        val customEditText = view.findViewById<EditText>(R.id.searchView)
        customEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                // Handle changes in the custom search query
            }
        })
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog as? BottomSheetDialog
        if (dialog != null) {
            val bottomSheet =
                dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.layoutParams?.height = ViewGroup.LayoutParams.MATCH_PARENT
        }
    }
}


////call bottom sheet in activity
//val bottomSheetFragment = BottomSheet()
//bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)