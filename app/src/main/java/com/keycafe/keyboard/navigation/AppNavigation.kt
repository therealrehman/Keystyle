package com.keycafe.keyboard.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.keycafe.keyboard.settings.SettingsScreen
import com.keycafe.keyboard.ui.animation.AnimationStudioScreen
import com.keycafe.keyboard.ui.builder.KeyboardBuilderScreen
import com.keycafe.keyboard.ui.home.HomeScreen
import com.keycafe.keyboard.ui.theme.ThemeStudioScreen

object Routes {
    const val HOME = "home"
    const val THEME_STUDIO = "theme_studio"
    const val ANIMATION_STUDIO = "animation_studio"
    const val BUILDER = "builder"
    const val SETTINGS = "settings"
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    micPermissionGranted: Boolean,
    onRequestMicPermission: () -> Unit
) {
    NavHost(navController = navController, startDestination = Routes.HOME) {
        composable(Routes.HOME) {
            HomeScreen(
                onNavigateToSettings = { navController.navigate(Routes.SETTINGS) },
                micPermissionGranted = micPermissionGranted,
                onRequestMicPermission = onRequestMicPermission
            )
        }

        composable(Routes.SETTINGS) {
            SettingsScreen(
                onBack = { navController.popBackStack() },
                onOpenThemeStudio = { navController.navigate(Routes.THEME_STUDIO) },
                onOpenAnimationStudio = { navController.navigate(Routes.ANIMATION_STUDIO) },
                onOpenBuilder = { navController.navigate(Routes.BUILDER) }
            )
        }

        composable(Routes.THEME_STUDIO) {
            ThemeStudioScreen(onBack = { navController.popBackStack() })
        }

        composable(Routes.ANIMATION_STUDIO) {
            AnimationStudioScreen(onBack = { navController.popBackStack() })
        }

        composable(Routes.BUILDER) {
            KeyboardBuilderScreen(onBack = { navController.popBackStack() })
        }
    }
}
