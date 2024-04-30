package com.example.final_project.presentation.extention

fun String.formatCardNumber(): String {
    val visibleDigits = 4
    val maskedDigits = length - visibleDigits
    val maskedSegment = "*".repeat(maskedDigits)
    val visibleSegment = substring(length - visibleDigits)
    val formattedNumber = maskedSegment + visibleSegment
    return formattedNumber.replace(" ", "").chunked(4).joinToString(" ")
}