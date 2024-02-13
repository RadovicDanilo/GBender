package com.radovicdanilo.gbender.view

sealed class Screen(val route:String){
    object MainScreen: Screen("main_screen")
    object PracticeScreen: Screen("practice_screen")
}


