package com.smurzik.rickstatistictesttask.ui.components.age

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.smurzik.rickstatistictesttask.domain.models.SortType
import com.smurzik.rickstatistictesttask.domain.models.SortTypeAge
import com.smurzik.rickstatistictesttask.presentation.age.AgeStatisticViewModel
import com.smurzik.rickstatistictesttask.presentation.models.AgeStatisticUiModel
import com.smurzik.rickstatistictesttask.presentation.models.SexStatisticUiModel
import com.smurzik.rickstatistictesttask.ui.components.CustomRadioButtons
import com.smurzik.rickstatistictesttask.ui.theme.RickStatisticTestTaskTheme
import kotlin.math.ceil

@Composable
fun AgeComponent(
    modifier: Modifier = Modifier,
    ageStatisticViewModel: AgeStatisticViewModel = viewModel()
) {

    val ageStatisticState by ageStatisticViewModel.uiState.collectAsState()

    AgeStatistic(
        modifier = modifier,
        sexStatistic = ageStatisticState.sexStatistic,
        ageStatistic = ageStatisticState.ageStatistic,
        selectedSortType = ageStatisticState.selectedSortType,
        onSortTypeSelect = { ageStatisticViewModel.getAgeStatistic(it) }
    )
}

@Composable
fun AgeStatistic(
    modifier: Modifier = Modifier,
    sexStatistic: SexStatisticUiModel,
    ageStatistic: List<AgeStatisticUiModel>,
    selectedSortType: SortType,
    onSortTypeSelect: (sortType: SortType) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Пол и возраст",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        CustomRadioButtons(
            options = listOf(
                SortTypeAge.TODAY,
                SortTypeAge.WEEK,
                SortTypeAge.MONTH,
                SortTypeAge.ALL_TIME
            ),
            selectedOption = selectedSortType,
            onOptionSelected = {
                onSortTypeSelect(it)
            }
        )
        AgeChart(
            modifier = Modifier.fillMaxWidth(),
            sexStatistic = sexStatistic,
            ageStatistic = ageStatistic
        )

    }
}

@Composable
fun AgeChart(
    modifier: Modifier = Modifier,
    sexStatistic: SexStatisticUiModel,
    ageStatistic: List<AgeStatisticUiModel>
) {
    Column(
        modifier = modifier
            .padding(top = 12.dp)
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(16.dp),
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomDonutChart(
            male = sexStatistic.maleCountPercent.toFloat(),
            female = sexStatistic.femaleCountPercent.toFloat(),
        )
        Row(
            modifier = Modifier
                .padding(vertical = 20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .background(color = MaterialTheme.colorScheme.surface, shape = CircleShape)
                )
                Text(
                    text = "Мужчины",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "${sexStatistic.maleCountPercent}%",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .background(
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            shape = CircleShape
                        )
                )
                Text(
                    text = "Женщины",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "${sexStatistic.femaleCountPercent}%",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
        HorizontalDivider(
            thickness = 0.5.dp,
            color = MaterialTheme.colorScheme.onTertiary,
            modifier = Modifier.padding(bottom = 20.dp)
        )
        ageStatistic.forEach { statistic ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, bottom = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (!statistic.isLast) "${statistic.ageStart}-${statistic.ageEnd}" else ">${statistic.ageStart}",
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.width(43.dp)
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 32.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.padding(bottom = 4.dp)
                    ) {
                        Row(modifier = Modifier) {
                            val width =
                                if (statistic.maleCount == 0) 0.017f else 0.017f + statistic.maleCount / statistic.totalCount.toFloat() * 0.75f
                            HorizontalDivider(
                                thickness = 5.dp,
                                color = MaterialTheme.colorScheme.surface,
                                modifier = Modifier
                                    .fillMaxWidth(width)
                                    .clip(RoundedCornerShape(10.dp))
                            )
                        }
                        Text(
                            text = "${statistic.maleCount * 100 / statistic.totalCount}%",
                            style = MaterialTheme.typography.labelSmall
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row {
                            val width =
                                if (statistic.femaleCount == 0) 0.017f else 0.017f + statistic.femaleCount / statistic.totalCount.toFloat() * 0.75f
                            HorizontalDivider(
                                thickness = 5.dp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier
                                    .fillMaxWidth(width)
                                    .clip(RoundedCornerShape(10.dp))
                            )
                        }
                        Text(
                            text = "${ceil(statistic.femaleCount * 100.0 / statistic.totalCount).toInt()}%",
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun AgeStatisticPreview() {
    RickStatisticTestTaskTheme {
        AgeStatistic(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.background),
            sexStatistic = SexStatisticUiModel(25, 0),
            ageStatistic = listOf(AgeStatisticUiModel(18, 21, 25, 75, false, 100),
                AgeStatisticUiModel(22, 25, 0, 12, true, 100)),
            selectedSortType = SortTypeAge.TODAY,
            onSortTypeSelect = {}
        )
    }
}