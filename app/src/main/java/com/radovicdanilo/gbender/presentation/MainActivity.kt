package com.radovicdanilo.gbender.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.radovicdanilo.gbender.data.model.Level
import com.radovicdanilo.gbender.data.model.Tuning
import com.radovicdanilo.gbender.di.AppCore
import com.radovicdanilo.gbender.domain.PracticeViewModel
import com.radovicdanilo.gbender.presentation.main.MainScreen
import com.radovicdanilo.gbender.presentation.practice.PracticeScreen
import com.radovicdanilo.gbender.ui.theme.GBenderTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GBenderTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.MainScreen.route
                ) {
                    composable(
                        route = Screen.MainScreen.route
                    ) {
                        MainScreen(navController)
                    }
                    composable(
                        route = Screen.PracticeScreen.route,
                    ) {
                        PracticeScreen(AppCore.instance.practiceViewModel)
                    }
                }
            }
        }
    }
}


