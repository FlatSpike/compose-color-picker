package com.github.flatspike.color.picker

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.flatspike.color.picker.util.drawCheckerboard

@Composable
fun ColorTile(
    color: Color,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.size(64.dp)) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCheckerboard()
            drawRect(color)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ColorTilePreview() {
    ColorTile(color = Color.Red.copy(alpha = 0.5f))
}
