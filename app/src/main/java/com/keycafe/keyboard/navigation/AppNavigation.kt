package com.keycafe.keyboard.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.keycafe.keyboard.MainActivity
import com.keycafe.keyboard.ui.theme.ThemeStudioScreen // Placeholder for future
// import com.keycafe.keyboard.ui.builder.KeyboardBuilderScreen // Placeholder for future

object Routes {
    const val HOME = "home"
    const val THEME_STUDIO = "theme_studio"
    const val BUILDER = "builder"
    const val SETTINGS = "settings"
}

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.HOME) {
        composable(Routes.HOME) {
            // Ye MainActivity ki screen hai jo humne pehle banayi thi
            // Future mein isay HomeScreen composable se replace karenge
        }
        
        // Future UI Screens yahan add hongi (Batch 8 mein)
        /*
        composable(Routes.THEME_STUDIO) {
            ThemeStudioScreen()
        }
        */
    }
}
