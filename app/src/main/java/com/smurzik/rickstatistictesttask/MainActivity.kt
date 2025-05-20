package com.smurzik.rickstatistictesttask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smurzik.rickstatistictesttask.ui.theme.RickStatisticTestTaskTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RickStatisticTestTaskTheme {

                val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

                Scaffold(
                    topBar = {
                        MediumTopAppBar(
                            title = {
                                Text(
                                    modifier = Modifier.padding(start = 16.dp),
                                    text = "Статистика",
                                    style = MaterialTheme.typography.titleLarge
                                )
                            },
                            scrollBehavior = scrollBehavior,
                            colors = TopAppBarDefaults.topAppBarColors()
                                .copy(
                                    containerColor = MaterialTheme.colorScheme.background,
                                    scrolledContainerColor = MaterialTheme.colorScheme.background
                                )
                        )
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .nestedScroll(scrollBehavior.nestedScrollConnection)
                ) { innerPadding ->
                    Column(
                        modifier = Modifier.padding(innerPadding)
                    ) {

                    }
                }
            }
        }
    }
}