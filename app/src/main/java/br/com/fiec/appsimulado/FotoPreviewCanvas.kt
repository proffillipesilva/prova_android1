package br.com.fiec.appsimulado

import android.graphics.Bitmap
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.IntSize

@Composable
fun FotoPreviewCanvas(
    bitmap: Bitmap?,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        bitmap?.let {
            drawImage(
                image = it.asImageBitmap(),
                dstSize = IntSize(size.width.toInt(), size.height.toInt())
            )
        }
    }
}