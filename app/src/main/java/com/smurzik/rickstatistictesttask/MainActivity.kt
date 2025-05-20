package com.smurzik.rickstatistictesttask

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.smurzik.rickstatistictesttask.data.remote.StatisticService
import com.smurzik.rickstatistictesttask.presentation.VisitorsViewModel
import com.smurzik.rickstatistictesttask.ui.theme.RickStatisticTestTaskTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RickStatisticTestTaskTheme {

                val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
                val visitorsViewModel: VisitorsViewModel = viewModel()
                val visitorsState by visitorsViewModel.uiState.collectAsState()

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
                        LaunchedEffect(visitorsState) {
                            visitorsViewModel.getVisitors()
                            Log.d("smurzLog", visitorsState.toString())
                        }
                    }
                }
            }
        }
    }
}