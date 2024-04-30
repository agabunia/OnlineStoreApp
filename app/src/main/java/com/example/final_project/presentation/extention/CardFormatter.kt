package com.example.final_project.presentation.extention

import android.widget.EditText
import androidx.core.widget.doAfterTextChanged

fun EditText.formatCardInfo(chunkSize: Int, separator: String) {
    this.doAfterTextChanged { text ->
        val formattedText =
            text.toString().replace(separator, "").chunked(chunkSize).joinToString(separator)

        if (formattedText != text.toString()) {
            this.setText(formattedText)
            this.setSelection(this.length())
        }
    }
}
