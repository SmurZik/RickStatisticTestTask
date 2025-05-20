package com.smurzik.rickstatistictesttask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.smurzik.rickstatistictesttask.ui.components.CustomTopBar
import com.smurzik.rickstatistictesttask.ui.components.visits.VisitorsComponent
import com.smurzik.rickstatistictesttask.ui.theme.RickStatisticTestTaskTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RickStatisticTestTaskTheme {
                Scaffold { innerPadding ->
                    Column(
                        modifier = Modifier.padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = innerPadding.calculateTopPadding(),
                            bottom = innerPadding.calculateBottomPadding()
                        )
                    ) {
                        CustomTopBar()
                        VisitorsComponent()
                    }
                }
            }
        }
    }
}