package com.github.flatspike.compose.color.picker

import com.github.flatspike.compose.color.picker.HsvTextMappers.Value
import com.github.flatspike.compose.color.picker.util.assertEquals
import org.junit.Assert.*
import org.junit.Test

class HsvTextMapperValueTest {

    @Test
    fun `isValid return true`() {
        assert(Value.isValid(""))
        assert(Value.isValid("0"))
        assert(Value.isValid("0."))
        assert(Value.isValid("0.0"))
        assert(Value.isValid("9"))
        assert(Value.isValid("9."))
        assert(Value.isValid("9.0"))
        assert(Value.isValid("99.9"))
        assert(Value.isValid("100.0"))
    }

    @Test
    fun `isValid return false`() {
        assertFalse(Value.isValid(" "))
        assertFalse(Value.isValid("."))
        assertFalse(Value.isValid("-1"))
        assertFalse(Value.isValid("1.00"))
        assertFalse(Value.isValid("100.1"))
        assertFalse(Value.isValid("text"))
    }

    @Test
    fun `toHsv return valid hsv`() {
        assertEquals(Hsv(0f, 0f, 0f), Value.toHsv("", Hsv.Black))
        assertEquals(Hsv(0f, 0f, 0f), Value.toHsv("0", Hsv.Black))
        assertEquals(Hsv(0f, 0f, 0f), Value.toHsv("0.", Hsv.Black))
        assertEquals(Hsv(0f, 0f, 0f), Value.toHsv("0.0", Hsv.Black))
        assertEquals(Hsv(0f, 0f, 0.09f), Value.toHsv("9", Hsv.Black))
        assertEquals(Hsv(0f, 0f, 0.09f), Value.toHsv("9.", Hsv.Black))
        assertEquals(Hsv(0f, 0f, 0.09f), Value.toHsv("9.0", Hsv.Black))
        assertEquals(Hsv(0f, 0f, 0.999f), Value.toHsv("99.9", Hsv.Black))
        assertEquals(Hsv(0f, 0f, 1f), Value.toHsv("100.0", Hsv.Black))
    }

    @Test
    fun `toText return valid text`() {
        assertEquals("0", Value.toText(Hsv(0f, 0f, 0f), ""))
        assertEquals("0.4", Value.toText(Hsv(0f, 0f, 0.002f), ""))
        assertEquals("0.8", Value.toText(Hsv(0f, 0f, 0.006f), ""))
        assertEquals("1.2", Value.toText(Hsv(0f, 0f, 0.010f), ""))
        assertEquals("1.6", Value.toText(Hsv(0f, 0f, 0.014f), ""))
        assertEquals("100", Value.toText(Hsv(0f, 0f, 0.999f), ""))
        assertEquals("100", Value.toText(Hsv(0f, 0f, 1f), ""))
    }
}