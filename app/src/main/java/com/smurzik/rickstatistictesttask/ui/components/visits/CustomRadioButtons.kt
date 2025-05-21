package com.smurzik.rickstatistictesttask.ui.components.visits

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smurzik.rickstatistictesttask.domain.models.SortType
import com.smurzik.rickstatistictesttask.ui.theme.RickStatisticTestTaskTheme

@Composable
fun CustomRadioButtons(
    modifier: Modifier = Modifier,
    options: List<SortType>,
    selectedOption: SortType,
    onOptionSelected: (SortType) -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        options.forEach { option ->
            val isSelected = option == selectedOption
            Button(
                onClick = {
                    onOptionSelected(option)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSelected) MaterialTheme.colorScheme.surface
                    else MaterialTheme.colorScheme.background,
                    contentColor = if (isSelected) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.onBackground
                ),
                shape = RoundedCornerShape(49.dp),
                border = if (isSelected) null else BorderStroke(
                    1.dp,
                    MaterialTheme.colorScheme.outline
                )
            ) {
                Text(text = option.label, style = MaterialTheme.typography.labelMedium)
            }
        }
    }
}

@Composable
@Preview
fun CustomRadioButtonsPreview() {
    RickStatisticTestTaskTheme {
        CustomRadioButtons(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.background),
            options = SortType.entries,
            selectedOption = SortType.DAYS,
            onOptionSelected = {}
        )
    }
}