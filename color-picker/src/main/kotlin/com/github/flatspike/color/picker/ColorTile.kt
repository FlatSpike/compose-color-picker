package com.github.flatspike.color.picker

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

private val DefaultSize = 64.dp

@Composable
fun ColorTile(
    color: Color,
    modifier: Modifier = Modifier
) {
    Spacer(
        modifier = modifier
            .sizeIn(DefaultSize, DefaultSize)
            .drawBehind {
                drawCheckerboard()
                drawRect(color)
            }
    )
}

@Preview(showBackground = true)
@Composable
private fun ColorTilePreview() {
    ColorTile(color = Color.Red.copy(alpha = 0.5f))
}
