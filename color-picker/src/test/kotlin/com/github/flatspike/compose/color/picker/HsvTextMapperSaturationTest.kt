package com.github.flatspike.compose.color.picker

import com.github.flatspike.compose.color.picker.HsvTextMappers.Saturation
import com.github.flatspike.compose.color.picker.util.assertEquals
import org.junit.Assert
import org.junit.Test

class HsvTextMapperSaturationTest {

    @Test
    fun `isValid return true`() {
        assert(Saturation.isValid(""))
        assert(Saturation.isValid("0"))
        assert(Saturation.isValid("0."))
        assert(Saturation.isValid("0.0"))
        assert(Saturation.isValid("9"))
        assert(Saturation.isValid("9."))
        assert(Saturation.isValid("9.0"))
        assert(Saturation.isValid("99.9"))
        assert(Saturation.isValid("100.0"))
    }

    @Test
    fun `isValid return false`() {
        Assert.assertFalse(Saturation.isValid(" "))
        Assert.assertFalse(Saturation.isValid("."))
        Assert.assertFalse(Saturation.isValid("-1"))
        Assert.assertFalse(Saturation.isValid("1.00"))
        Assert.assertFalse(Saturation.isValid("100.1"))
        Assert.assertFalse(Saturation.isValid("text"))
    }

    @Test
    fun `toHsv return valid hsv`() {
        assertEquals(Hsv(0f, 0f, 0f), Saturation.toHsv("", Hsv.Black))
        assertEquals(Hsv(0f, 0f, 0f), Saturation.toHsv("0", Hsv.Black))
        assertEquals(Hsv(0f, 0f, 0f), Saturation.toHsv("0.", Hsv.Black))
        assertEquals(Hsv(0f, 0f, 0f), Saturation.toHsv("0.0", Hsv.Black))
        assertEquals(Hsv(0f, 0.09f, 0f), Saturation.toHsv("9", Hsv.Black))
        assertEquals(Hsv(0f, 0.09f, 0f), Saturation.toHsv("9.", Hsv.Black))
        assertEquals(Hsv(0f, 0.09f, 0f), Saturation.toHsv("9.0", Hsv.Black))
        assertEquals(Hsv(0f, 0.999f, 0f), Saturation.toHsv("99.9", Hsv.Black))
        assertEquals(Hsv(0f, 1f, 0f), Saturation.toHsv("100.0", Hsv.Black))
    }

    @Test
    fun `toText return valid text`() {
        Assert.assertEquals("0", Saturation.toText(Hsv(0f, 0f, 0f), ""))
        Assert.assertEquals("0.4", Saturation.toText(Hsv(0f, 0.002f, 0f), ""))
        Assert.assertEquals("0.8", Saturation.toText(Hsv(0f, 0.006f, 0f), ""))
        Assert.assertEquals("1.2", Saturation.toText(Hsv(0f, 0.010f, 0f), ""))
        Assert.assertEquals("1.6", Saturation.toText(Hsv(0f, 0.014f, 0f), ""))
        Assert.assertEquals("100", Saturation.toText(Hsv(0f, 0.999f, 0f), ""))
        Assert.assertEquals("100", Saturation.toText(Hsv(0f, 1f, 0f), ""))
    }
}