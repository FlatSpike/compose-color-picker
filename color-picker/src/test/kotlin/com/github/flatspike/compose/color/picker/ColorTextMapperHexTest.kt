package com.github.flatspike.compose.color.picker

import androidx.compose.ui.graphics.Color
import com.github.flatspike.compose.color.picker.ColorTextMappers.Hex
import com.github.flatspike.compose.color.picker.util.assertEquals
import org.junit.Assert.*
import org.junit.Test

class ColorTextMapperHexTest {

    @Test
    fun `isValid return true`() {
        assert(Hex.isValid(""))
        assert(Hex.isValid("01234567"))
        assert(Hex.isValid("89abcdef"))
        assert(Hex.isValid("ABCDEF01"))
    }

    @Test
    fun `isValid return false`() {
        assertFalse(Hex.isValid(" "))
        assertFalse(Hex.isValid(".-/~@"))
        assertFalse(Hex.isValid("sometext"))
        assertFalse(Hex.isValid("0123456789"))
    }

    @Test
    fun `toColor return valid color`() {
        assertEquals(Color(0x00, 0x00, 0x00, 0x00), Hex.toColor("", Color.Unspecified))
        assertEquals(Color(0x23, 0x45, 0x67, 0x01), Hex.toColor("01234567", Color.Unspecified))
        assertEquals(Color(0xab, 0xcd, 0xef, 0x89), Hex.toColor("89abcdef", Color.Unspecified))
        assertEquals(Color(0xcd, 0xef, 0x01, 0xab), Hex.toColor("ABCDEF01", Color.Unspecified))
    }

    @Test
    fun `toText return valid text`() {
        assertEquals("00000000", Hex.toText(Color(0x00, 0x00, 0x00, 0x00), ""))
        assertEquals("01234567", Hex.toText(Color(0x23, 0x45, 0x67, 0x01), ""))
        assertEquals("89abcdef", Hex.toText(Color(0xab, 0xcd, 0xef, 0x89), ""))
        assertEquals("abcdef01", Hex.toText(Color(0xcd, 0xef, 0x01, 0xab), ""))
    }
}