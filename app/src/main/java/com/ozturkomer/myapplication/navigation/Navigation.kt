package com.ozturkomer.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ozturkomer.myapplication.screens.MainScreen
import com.ozturkomer.myapplication.screens.ResultScreen

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController, startDestination = "main") {
        composable("main") { MainScreen(navController) }  // navController parametresini geçiyoruz
        composable(
            "result/{correctCount}/{wrongCount}/{imageSize}",
            arguments = listOf(
                navArgument("correctCount") { type = NavType.IntType },
                navArgument("wrongCount") { type = NavType.IntType },
                navArgument("imageSize") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val correctCount = backStackEntry.arguments?.getInt("correctCount") ?: 0
            val wrongCount = backStackEntry.arguments?.getInt("wrongCount") ?: 0
            val imageSize = backStackEntry.arguments?.getInt("imageSize") ?: 0
            ResultScreen(navController, correctCount, wrongCount, imageSize)
        }
    }
}
