package com.github.flatspike.compose.color.picker

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.github.flatspike.compose.color.picker.util.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters

@RunWith(Parameterized::class)
class HsvTest(
    private val hsv: Int,
    private val argb: Int
) {

    @Test
    fun `hsv to color conversion`() {
        assertEquals(Color(argb), Hsv(hsv).toColor())
    }

    @Test
    fun `color to hsv conversion`() {
        assertEquals(Hsv(hsv), Color(argb).hsv)
    }

    companion object {

        private fun MutableList<Array<Any>>.add(hsv: Hsv, color: Color) {
            add(arrayOf(hsv.toInt(), color.toArgb()))
        }

        @JvmStatic
        @Parameters
        fun data() = buildList {
            add(Hsv(0f, 0f, 0f), Color(0, 0, 0))
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
