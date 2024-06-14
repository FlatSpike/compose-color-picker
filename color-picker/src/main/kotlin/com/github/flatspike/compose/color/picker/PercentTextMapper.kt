package com.github.flatspike.compose.color.picker

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

internal object PercentTextMapper {

    private val Regex = Regex("^((([0-9]|[1-9][0-9])(\\.[0-9]?)?)|(100(\\.0?)?))?$")
    private val Format = DecimalFormat(
        "0.#",
        DecimalFormatSymbols().apply { decimalSeparator = '.' }
    )

    fun isValid(text: String): Boolean = text.matches(Regex)

    fun toValue(text: String): Float =
        if (text.isBlank()) 0f else Format.parse(text)?.toFloat() ?: 0f

    fun toText(value: Float): String = Format.format(value)
}