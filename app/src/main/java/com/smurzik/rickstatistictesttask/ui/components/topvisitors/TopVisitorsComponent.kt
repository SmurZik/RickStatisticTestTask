package com.smurzik.rickstatistictesttask.ui.components.topvisitors

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.smurzik.rickstatistictesttask.R
import com.smurzik.rickstatistictesttask.domain.models.TopVisitorsModel
import com.smurzik.rickstatistictesttask.ui.theme.RickStatisticTestTaskTheme

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun TopVisitorsComponent(
    modifier: Modifier = Modifier,
    topVisitors: List<TopVisitorsModel>
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Чаще всех посещают Ваш профиль",
            style = MaterialTheme.typography.titleMedium
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(14.dp)
                )
        ) {
            topVisitors.forEach { visitor ->
                Row(
                    modifier = Modifier.padding(start = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .padding(vertical = 12.dp)
                            .size(38.dp)
                    ) {
                        GlideImage(
                            model = visitor.imageUri,
                            contentDescription = null,
                            modifier = Modifier.clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                        if (visitor.isOnline)
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .background(color = Color.Green, shape = CircleShape)
                                    .align(Alignment.BottomEnd)
                                    .border(
                                        width = 1.dp,
                                        color = MaterialTheme.colorScheme.primary,
                                        shape = CircleShape
                                    )
                            )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            modifier = Modifier.padding(start = 12.dp),
                            text = "${visitor.username}, ${visitor.age}",
                            style = MaterialTheme.typography.labelMedium
                        )
                        Image(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_next),
                            contentDescription = null,
                            modifier = Modifier.padding(end = 24.dp)
                        )
                    }
                }
                HorizontalDivider(
                    thickness = 0.5.dp,
                    color = MaterialTheme.colorScheme.onTertiary,
                    modifier = Modifier.padding(start = 66.dp)
                )
            }
        }
    }
}

@Composable
@Preview
fun TopVisitorsComponentPreview() {
    RickStatisticTestTaskTheme {
        TopVisitorsComponent(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            topVisitors = listOf(
                TopVisitorsModel(
                    username = "ann.aeom",
                    isOnline = true,
                    age = 25
                ),
                TopVisitorsModel(
                    username = "akimovahuiw",
                    isOnline = false,
                    age = 23
                ),
                TopVisitorsModel(
                    username = "gulia.filova",
                    isOnline = true,
                    age = 32
                )
            )
        )
    }
}