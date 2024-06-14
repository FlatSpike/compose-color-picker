package com.github.flatspike.compose.color.picker

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

interface HsvTextMapper {

    fun isValid(text: String): Boolean

    fun toHsv(text: String, currentHsv: Hsv): Hsv

    fun toText(hsv: Hsv, currentText: String): String
}

object HsvTextMappers {

    object Hue : HsvTextMapper {

        private val Regex = Regex(
            "^((([0-9]|[1-9][0-9]|[1-2][0-9]{2}|3[0-5][0-9])(\\.[0-9]?)?)|(360(\\.0?)?))?\$"
        )

        private val Format = DecimalFormat(
            "0.#",
            DecimalFormatSymbols().apply { decimalSeparator = '.' }
        )

        override fun isValid(text: String): Boolean = text.matches(Regex)

        override fun toHsv(text: String, currentHsv: Hsv): Hsv =
            (if (text.isBlank()) 0f else text.toFloat()).let { currentHsv.copy(hue = it) }

        override fun toText(hsv: Hsv, currentText: String): String = Format.format(hsv.hue)
    }

    object Saturation : HsvTextMapper {

        override fun isValid(text: String): Boolean = PercentTextMapper.isValid(text)

        override fun toHsv(text: String, currentHsv: Hsv): Hsv =
            currentHsv.copy(saturation = PercentTextMapper.toValue(text) / 100f)

        override fun toText(hsv: Hsv, currentText: String): String =
            PercentTextMapper.toText(hsv.saturation * 100f)
    }

    object Value : HsvTextMapper {

        override fun isValid(text: String): Boolean = PercentTextMapper.isValid(text)

        override fun toHsv(text: String, currentHsv: Hsv): Hsv =
            currentHsv.copy(value = PercentTextMapper.toValue(text) / 100f)

        override fun toText(hsv: Hsv, currentText: String): String =
            PercentTextMapper.toText(hsv.value * 100f)
    }
}
