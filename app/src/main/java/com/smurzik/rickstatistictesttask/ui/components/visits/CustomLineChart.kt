package com.smurzik.rickstatistictesttask.ui.components.visits

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat
import com.smurzik.rickstatistictesttask.R
import com.smurzik.rickstatistictesttask.ui.theme.RickStatisticTestTaskTheme

@Composable
fun CustomLineChart(
    modifier: Modifier = Modifier,
    data: List<DataPoint>,
    isLoading: Boolean
) {
    val spacingHorizontal = 32.dp
    val spacingVertical = 160
    val scale = 80

    val selectedPoint = remember { mutableStateOf<DataPoint?>(null) }

    LaunchedEffect(isLoading) {
        selectedPoint.value = null
    }

    val maxY = data.maxOf { it.yValue * 1.2f }
    val minY = 0

    val surfaceColor = MaterialTheme.colorScheme.surface
    val primaryColor = MaterialTheme.colorScheme.primary
    val outlineColor = MaterialTheme.colorScheme.outline
    val onSecondaryColor = MaterialTheme.colorScheme.onSecondary
    val onBackgroundColor = MaterialTheme.colorScheme.onBackground
    val visitorsTypeFace = ResourcesCompat.getFont(LocalContext.current, R.font.gilroy_semibold)
    val dateTypeFace = ResourcesCompat.getFont(LocalContext.current, R.font.gilroy_medium)

    Box(
        modifier = modifier
            .height(208.dp)
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(
                horizontal = 32.dp,
                vertical = 10.dp
            ),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.surface,
                strokeWidth = 3.dp
            )
        } else
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTapGestures { tapOffset ->
                            val xSpacing = (size.width - spacingHorizontal.toPx()) / (data.size - 1)
                            val index =
                                ((tapOffset.x - spacingHorizontal.toPx() + xSpacing / 2) / xSpacing).toInt()
                                    .coerceIn(0, data.lastIndex)
                            if (selectedPoint.value == data[index]) selectedPoint.value = null
                            else {
                                selectedPoint.value = data[index]
                            }
                        }
                    }
            ) {
                val canvasWidth = size.width
                val canvasHeight = size.height

                val xSpacing = (canvasWidth - spacingHorizontal.toPx()) / (data.size - 1)
                val chartHeight = canvasHeight - spacingVertical

                // Points
                val points = data.mapIndexed { index, point ->
                    val ratio = (point.yValue - minY) / (maxY - minY)
                    val x = spacingHorizontal.toPx() / 2 + index * xSpacing
                    val y = chartHeight - (chartHeight * ratio) + scale
                    Offset(x, y)
                }

                // Grid Y
                val steps = 2
                repeat(steps + 1) { i ->
                    val y = chartHeight / steps * i
                    drawLine(
                        color = onBackgroundColor.copy(alpha = 0.2f),
                        start = Offset(0F, y + scale),
                        end = Offset(canvasWidth, y + scale),
                        strokeWidth = 1.dp.toPx(),
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(15f, 15f))
                    )
                }

                // Chart line
                for (i in 0 until points.size - 1) {
                    drawLine(
                        color = surfaceColor,
                        start = points[i],
                        end = points[i + 1],
                        strokeWidth = 3.dp.toPx()
                    )
                }

                // X-Axis labels
                data.forEachIndexed { index, point ->
                    val text = point.xLabel
                    val x = points[index].x
                    val y = size.height - 4.dp.toPx()

                    drawContext.canvas.nativeCanvas.drawText(
                        text,
                        x,
                        y,
                        android.graphics.Paint().apply {
                            textSize = 11.sp.toPx()
                            color = onSecondaryColor.toArgb()
                            textAlign = android.graphics.Paint.Align.CENTER
                            isAntiAlias = true
                            typeface = dateTypeFace
                        }
                    )
                }

                selectedPoint.value?.let { active ->
                    val index = data.indexOf(active)
                    val offset = points[index]

                    drawLine(
                        color = surfaceColor,
                        start = Offset(offset.x, scale.toFloat()),
                        end = Offset(offset.x, canvasHeight - scale),
                        strokeWidth = 1.dp.toPx(),
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(15f, 15f))
                    )
                }

                // Dots
                points.forEach { point ->
                    drawCircle(
                        color = surfaceColor,
                        radius = 6.dp.toPx(),
                        center = point
                    )
                    drawCircle(
                        color = Color.White,
                        radius = 3.dp.toPx(),
                        center = point
                    )
                }

                // Active Dot
                selectedPoint.value?.let { active ->
                    val index = data.indexOf(active)
                    val offset = points[index]

                    // Tooltip
                    val visitorsPaint = android.graphics.Paint().apply {
                        textSize = 15.sp.toPx()
                        color = surfaceColor.toArgb()
                        isAntiAlias = true
                        typeface = visitorsTypeFace
                    }

                    val datePaint = android.graphics.Paint().apply {
                        textSize = 13.sp.toPx()
                        color = onBackgroundColor.copy(alpha = 0.5f).toArgb()
                        isAntiAlias = true
                        typeface = dateTypeFace
                    }

                    val padding = 16.dp.toPx()
                    val lineHeight = visitorsPaint.fontSpacing
                    val specialNumbers = setOf(2, 3, 4)
                    val visitors =
                        if (active.yValue.toInt() % 10 == 1) "посетитель"
                        else if (active.yValue.toInt() % 10 in specialNumbers) "посетителя"
                        else "посетителей"
                    val textLines = listOf("${active.yValue.toInt()} $visitors", active.xLabel)
                    val textWidth = textLines.maxOf { visitorsPaint.measureText(it) }
                    val tooltipWidth = textWidth + padding * 2
                    val tooltipHeight = lineHeight * textLines.size + padding * 2

                    val tooltipX =
                        (offset.x - tooltipWidth / 2).coerceIn(0f, size.width - tooltipWidth)
                    val tooltipY = 10f

                    drawRoundRect(
                        color = primaryColor,
                        topLeft = Offset(tooltipX, tooltipY),
                        size = Size(tooltipWidth, tooltipHeight),
                        cornerRadius = CornerRadius(12.dp.toPx())
                    )

                    drawRoundRect(
                        color = outlineColor,
                        topLeft = Offset(tooltipX, tooltipY),
                        size = Size(tooltipWidth, tooltipHeight),
                        style = Stroke(width = 1.dp.toPx()),
                        cornerRadius = CornerRadius(12.dp.toPx())
                    )

                    textLines.forEachIndexed { i, line ->
                        drawContext.canvas.nativeCanvas.drawText(
                            line,
                            tooltipX + padding,
                            if (i == 0) tooltipY + padding + lineHeight - 10
                            else tooltipY + padding + (i + 1) * lineHeight,
                            if (i == 0) visitorsPaint else datePaint
                        )
                    }
                }
            }
    }
}

data class DataPoint(
    val xLabel: String,
    val yValue: Float,
)

@Preview
@Composable
fun CustomLineChartPreview() {
    RickStatisticTestTaskTheme {
        CustomLineChart(
            modifier = Modifier
                .height(208.dp)
                .background(color = MaterialTheme.colorScheme.primary)
                .padding(
                    horizontal = 32.dp,
                    vertical = 32.dp
                ),
            data = listOf(
                DataPoint(xLabel = "06.03", yValue = 21f),
                DataPoint(xLabel = "07.03", yValue = 5f),
                DataPoint(xLabel = "08.03", yValue = 15f),
                DataPoint(xLabel = "09.03", yValue = 19f),
                DataPoint(xLabel = "10.03", yValue = 13f),
                DataPoint(xLabel = "11.03", yValue = 9f),
                DataPoint(xLabel = "12.03", yValue = 14f),
            ),
            isLoading = true
        )
    }
}