package com.github.flatspike.compose.color.picker

import com.github.flatspike.compose.color.picker.HsvTextMappers.Hue
import com.github.flatspike.compose.color.picker.util.assertEquals
import org.junit.Assert
import org.junit.Assert.assertFalse
import org.junit.Test

class HsvTextMapperHueTest {

    @Test
    fun `isValid return true`() {
        assert(Hue.isValid(""))
        assert(Hue.isValid("0"))
        assert(Hue.isValid("0."))
        assert(Hue.isValid("0.0"))
        assert(Hue.isValid("9"))
        assert(Hue.isValid("9."))
        assert(Hue.isValid("9.0"))
        assert(Hue.isValid("359.9"))
        assert(Hue.isValid("360.0"))
    }

    @Test
    fun `isValid return false`() {
        assertFalse(Hue.isValid(" "))
        assertFalse(Hue.isValid("."))
        assertFalse(Hue.isValid("-1"))
        assertFalse(Hue.isValid("1.00"))
        assertFalse(Hue.isValid("360.1"))
        assertFalse(Hue.isValid("text"))
    }

    @Test
    fun `toHsv return valid hsv`() {
        assertEquals(Hsv(0f, 0f, 0f), Hue.toHsv("", Hsv.Black))
        assertEquals(Hsv(0f, 0f, 0f), Hue.toHsv("0", Hsv.Black))
        assertEquals(Hsv(0f, 0f, 0f), Hue.toHsv("0.", Hsv.Black))
        assertEquals(Hsv(0f, 0f, 0f), Hue.toHsv("0.0", Hsv.Black))
        assertEquals(Hsv(9f, 0f, 0f), Hue.toHsv("9", Hsv.Black))
        assertEquals(Hsv(9f, 0f, 0f), Hue.toHsv("9.", Hsv.Black))
        assertEquals(Hsv(9f, 0f, 0f), Hue.toHsv("9.0", Hsv.Black))
        assertEquals(Hsv(9.9f, 0f, 0f), Hue.toHsv("9.9", Hsv.Black))
        assertEquals(Hsv(359.9f, 0f, 0f), Hue.toHsv("359.9", Hsv.Black))
        assertEquals(Hsv(360f, 0f, 0f), Hue.toHsv("360.0", Hsv.Black))
    }

    @Test
    fun `toText return valid text`() {
        Assert.assertEquals("0", Hue.toText(Hsv(0f, 0f, 0f), ""))
        Assert.assertEquals("9", Hue.toText(Hsv(9f, 0f, 0f), ""))
        Assert.assertEquals("9.9", Hue.toText(Hsv(9.9f, 0f, 0f), ""))
        Assert.assertEquals("359.9", Hue.toText(Hsv(359.9f, 0f, 0f), ""))
        Assert.assertEquals("360", Hue.toText(Hsv(360f, 0f, 0f), ""))
    }
}