package com.github.flatspike.compose.color.picker

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters
import java.math.RoundingMode

@RunWith(Parameterized::class)
class HsvToColorTest(
    private val hsv: Int,
    private val argb: Int
) {

    @Test
    fun toColor() {
        val argbColor = Color(argb).also { println(it) }
        val hsvColor = Hsv(hsv).also { println(it) }.toColor()
        assert(argbColor.red.round() == hsvColor.red.round())
        assert(argbColor.blue.round() == hsvColor.blue.round())
        assert(argbColor.green.round() == hsvColor.green.round())
    }

    @Test
    fun toHsv() {
        val argbHsv = Color(argb).hsv
        assert(argbHsv.hue.round() == argbHsv.hue.round())
        assert(argbHsv.saturation.round() == argbHsv.saturation.round())
        assert(argbHsv.value.round() == argbHsv.value.round())
    }

    companion object {


        private const val DEFAULT_SCALE = 2

        fun Float.round(scale: Int = DEFAULT_SCALE): Float =
            toBigDecimal().setScale(scale, RoundingMode.HALF_UP).toFloat()

        private fun MutableList<Array<Any>>.add(hsv: Hsv, color: Color) {
            add(arrayOf(hsv.toInt(), color.toArgb()))
        }

        @JvmStatic
        @Parameters
        fun data() = buildList {
            add(Hsv(0f, 0f, 0f), Color(0, 0, 0))
            add(Hsv(0f, 1f, 0f), Color(0, 0, 0))
            add(Hsv(0f, 0f, 0.5f), Color(127, 127, 127))
            add(Hsv(0f, 0f, 1f), Color(255, 255, 255))
            add(Hsv(0f, 1f, 1f), Color(255, 0, 0))
            add(Hsv(0f, 0.5f, 1f), Color(255, 127, 127))
            add(Hsv(0f, 0.5f, 0.5f), Color(127, 63, 63))
            add(Hsv(0f, 1f, 0.5f), Color(127, 0, 0))
            add(Hsv(60f, 1f, 1f), Color(255, 255, 0))
            add(Hsv(60f, 0.5f, 1f), Color(255, 255, 127))
            add(Hsv(60f, 0.5f, 0.5f), Color(127, 127, 63))
            add(Hsv(60f, 1f, 0.5f), Color(127, 127, 0))
            add(Hsv(120f, 1f, 1f), Color(0, 255, 0))
            add(Hsv(120f, 0.5f, 1f), Color(127, 255, 127))
            add(Hsv(120f, 0.5f, 0.5f), Color(63, 127, 63))
            add(Hsv(120f, 1f, 0.5f), Color(0, 127, 0))
            add(Hsv(180f, 1f, 1f), Color(0, 255, 255))
            add(Hsv(180f, 0.5f, 1f), Color(127, 255, 255))
            add(Hsv(180f, 0.5f, 0.5f), Color(63, 127, 127))
            add(Hsv(180f, 1f, 0.5f), Color(0, 127, 127))
            add(Hsv(240f, 1f, 1f), Color(0, 0, 255))
            add(Hsv(240f, 0.5f, 1f), Color(127, 127, 255))
            add(Hsv(240f, 0.5f, 0.5f), Color(63, 63, 127))
            add(Hsv(240f, 1f, 0.5f), Color(0, 0, 127))
            add(Hsv(300f, 1f, 1f), Color(255, 0, 255))
            add(Hsv(300f, 0.5f, 1f), Color(255, 127, 255))
            add(Hsv(300f, 0.5f, 0.5f), Color(127, 63, 127))
            add(Hsv(300f, 1f, 0.5f), Color(127, 0, 127))
        }
    }
}
