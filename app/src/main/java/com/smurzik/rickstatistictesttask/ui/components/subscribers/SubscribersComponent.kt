package com.smurzik.rickstatistictesttask.ui.components.subscribers

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smurzik.rickstatistictesttask.R
import com.smurzik.rickstatistictesttask.ui.theme.RickStatisticTestTaskTheme

@Composable
fun SubscribersComponent(
    modifier: Modifier = Modifier
) {
    Subscribers(

    )
}

@Composable
fun Subscribers(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(bottom = 32.dp)
    ) {
        Text(
            text = "Наблюдатели",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        Column(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(16.dp),
                ),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)
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
                            text = "200",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Image(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_up),
                            contentDescription = null
                        )
                    }
                    Text(
                        text = "Новые наблюдатели в этом месяце",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                    )
                }
            }

            HorizontalDivider(
                thickness = 0.5.dp,
                color = MaterialTheme.colorScheme.onTertiary
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                Image(
                    modifier = Modifier.padding(end = 20.dp),
                    imageVector = ImageVector.vectorResource(R.drawable.ic_negative_chart),
                    contentDescription = null
                )
                Column {
                    Row(
                        modifier = Modifier.padding(bottom = 7.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.padding(end = 6.dp),
                            text = "10",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Image(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_down),
                            contentDescription = null
                        )
                    }
                    Text(
                        text = "Пользователей перестали за Вами наблюдать",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun SubscribersComponent() {
    RickStatisticTestTaskTheme {
        Subscribers(modifier = Modifier.background(color = MaterialTheme.colorScheme.background))
    }
}