package com.smurzik.rickstatistictesttask.ui.components.age

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smurzik.rickstatistictesttask.ui.theme.RickStatisticTestTaskTheme

@Composable
fun CustomDonutChart(
    modifier: Modifier = Modifier,
    male: Float,
    female: Float,
) {
    val total = male + female
    val firstSweep = (female / total) * 360f
    val secondSweep = (male / total) * 360f

    val femaleColor = MaterialTheme.colorScheme.onSurfaceVariant
    val maleColor = MaterialTheme.colorScheme.surface

    Box(
        modifier = modifier
            .padding(top = 32.dp)
            .size(143.dp)
    ) {
        Canvas(
            modifier = modifier
                .fillMaxSize()
        ) {
            val strokeWidth = 10.dp.toPx()

            // Female Arc
            if (female != 0f)
                drawArc(
                    color = femaleColor,
                    startAngle = 275f,
                    sweepAngle = if (male == 0f) firstSweep else firstSweep - 10f,
                    useCenter = false,
                    topLeft = Offset(0f, 0f),
                    size = size,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                )

            // Male Arc
            if (male != 0f)
                drawArc(
                    color = maleColor,
                    startAngle = firstSweep - 85f,
                    sweepAngle = if (female == 0f) secondSweep else secondSweep - 10f,
                    useCenter = false,
                    topLeft = Offset(0f, 0f),
                    size = size,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                )
        }
    }
}

@Composable
@Preview
fun CustomDonutChartPreview() {
    RickStatisticTestTaskTheme {
        CustomDonutChart(
            male = 40f,
            female = 60f
        )
    }
}