package com.github.flatspike.compose.color.picker

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

interface ColorTextMapper {

    fun isValid(text: String): Boolean

    fun toColor(text: String, currentColor: Color): Color

    fun toText(color: Color, currentText: String): String
}

object ColorTextMappers {

    object Hex : ColorTextMapper {

        private val Regex = Regex("^[a-fA-F0-9]{0,8}$")

        override fun isValid(text: String): Boolean = text.matches(Regex)

        override fun toColor(text: String, currentColor: Color): Color =
            Color((if (text.isBlank()) 0L else text.toLong(16)))

        @OptIn(ExperimentalStdlibApi::class)
        override fun toText(color: Color, currentText: String): String =
            color.toArgb().toHexString()
    }

    object Alpha : ColorTextMapper {
        override fun isValid(text: String): Boolean = PercentTextMapper.isValid(text)

        override fun toColor(text: String, currentColor: Color): Color =
            currentColor.copy(alpha = PercentTextMapper.toValue(text) / 100f)

        override fun toText(color: Color, currentText: String): String =
            PercentTextMapper.toText(color.alpha * 100f)
    }


    object Red : ColorTextMapper {

        override fun isValid(text: String): Boolean = PercentTextMapper.isValid(text)

        override fun toColor(text: String, currentColor: Color): Color =
            currentColor.copy(red = PercentTextMapper.toValue(text) / 100f)

        override fun toText(color: Color, currentText: String): String =
            PercentTextMapper.toText(color.red * 100f)
    }

    object Green : ColorTextMapper {

        override fun isValid(text: String): Boolean =
            PercentTextMapper.isValid(text)

        override fun toColor(text: String, currentColor: Color): Color =
            currentColor.copy(green = PercentTextMapper.toValue(text) / 100f)

        override fun toText(color: Color, currentText: String): String =
            PercentTextMapper.toText(color.green * 100f)
    }

    object Blue : ColorTextMapper {

        override fun isValid(text: String): Boolean =
            PercentTextMapper.isValid(text)

        override fun toColor(text: String, currentColor: Color): Color =
            currentColor.copy(blue = PercentTextMapper.toValue(text) / 100f)

        override fun toText(color: Color, currentText: String): String =
            PercentTextMapper.toText(color.blue * 100f)
    }
}
