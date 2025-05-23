package com.smurzik.rickstatistictesttask.ui.components.age

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smurzik.rickstatistictesttask.domain.models.SortType
import com.smurzik.rickstatistictesttask.ui.components.CustomRadioButtons
import com.smurzik.rickstatistictesttask.ui.theme.RickStatisticTestTaskTheme

@Composable
fun AgeComponent(
    modifier: Modifier = Modifier,
) {
    AgeStatistic(modifier = modifier)
}

@Composable
fun AgeStatistic(
    modifier: Modifier = Modifier
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
            options = listOf(SortType.DAYS, SortType.WEEKS, SortType.MONTHS),
            selectedOption = SortType.DAYS,
            onOptionSelected = {

            }
        )
        AgeChart(
            modifier = Modifier.fillMaxWidth()
        )

    }
}

@Composable
fun AgeChart(
    modifier: Modifier = Modifier
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
            male = 40f,
            female = 60f,
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
                    text = "40%",
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
                    text = "60%",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
        HorizontalDivider(
            thickness = 0.5.dp,
            color = MaterialTheme.colorScheme.onTertiary,
            modifier = Modifier.padding(bottom = 20.dp)
        )
        repeat(7) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, bottom = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "18-21",
                    style = MaterialTheme.typography.labelMedium
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
                        HorizontalDivider(
                            thickness = 5.dp,
                            color = MaterialTheme.colorScheme.surface,
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .clip(RoundedCornerShape(10.dp))
                        )
                        Text(
                            text = "10%",
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        HorizontalDivider(
                            thickness = 5.dp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .clip(RoundedCornerShape(10.dp))
                        )
                        Text(
                            text = "10%",
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
                .background(color = MaterialTheme.colorScheme.background)
        )
    }
}