package com.smurzik.rickstatistictesttask.ui.components.visits

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.smurzik.rickstatistictesttask.R
import com.smurzik.rickstatistictesttask.domain.models.SortType
import com.smurzik.rickstatistictesttask.domain.models.SortTypeVisitors
import com.smurzik.rickstatistictesttask.presentation.visitors.VisitorsViewModel
import com.smurzik.rickstatistictesttask.ui.components.CustomRadioButtons
import com.smurzik.rickstatistictesttask.ui.theme.RickStatisticTestTaskTheme

@Composable
fun VisitorsComponent(
    modifier: Modifier = Modifier,
    viewModel: VisitorsViewModel = viewModel()
) {
    val statisticState by viewModel.uiState.collectAsState()

    Visitors(
        modifier = modifier,
        monthlyVisitors = statisticState.monthlyVisitors,
        chartVisitors = statisticState.chartVisitors,
        selectedSortType = statisticState.selectedSortType,
        onSortTypeSelect = { viewModel.getVisitorsChart(it) },
        isLoading = statisticState.loadingChart
    )
}

@Composable
fun Visitors(
    modifier: Modifier,
    monthlyVisitors: Int,
    chartVisitors: List<DataPoint>,
    selectedSortType: SortType,
    onSortTypeSelect: (sortType: SortType) -> Unit,
    isLoading: Boolean
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Посетители",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        Row(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(16.dp),
                )
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.padding(end = 20.dp),
                imageVector = ImageVector.vectorResource(R.drawable.ic_positive_chart),
                contentDescription = null
            )
            Column {
                Row(
                    modifier = Modifier.padding(bottom = 7.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.padding(end = 6.dp),
                        text = monthlyVisitors.toString(),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Image(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_up),
                        contentDescription = null
                    )
                }
                Text(
                    text = "Количество посетителей в этом месяце выросло",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                )
            }
        }

        CustomRadioButtons(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 28.dp, bottom = 12.dp),
            options = SortTypeVisitors.entries,
            selectedOption = selectedSortType,
            onOptionSelected = {
                onSortTypeSelect(it)
            }
        )

        CustomLineChart(
            data = chartVisitors,
            isLoading = isLoading
        )
    }
}


@Composable
@Preview
fun VisitorsPreview() {
    RickStatisticTestTaskTheme {
        Visitors(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background),
            monthlyVisitors = 16,
            chartVisitors = listOf(
                DataPoint(xLabel = "06.03", yValue = 21f),
                DataPoint(xLabel = "07.03", yValue = 5f),
                DataPoint(xLabel = "08.03", yValue = 15f),
                DataPoint(xLabel = "09.03", yValue = 19f),
                DataPoint(xLabel = "10.03", yValue = 13f),
                DataPoint(xLabel = "11.03", yValue = 9f),
                DataPoint(xLabel = "12.03", yValue = 14f),
            ),
            selectedSortType = SortTypeVisitors.WEEKS,
            onSortTypeSelect = { },
            isLoading = true
        )
    }
}