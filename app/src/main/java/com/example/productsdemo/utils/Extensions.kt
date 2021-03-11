package com.example.productsdemo.utils

import java.text.NumberFormat
import java.util.Locale

fun Int.formatPrice(): String {
    val format = NumberFormat.getCurrencyInstance(Locale.US)
    format.maximumFractionDigits = 0

    return format.format(this)
}