package com.keycafe.keyboard

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import com.keycafe.keyboard.navigation.AppNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val context = LocalContext.current

                    // FIX: an InputMethodService cannot request runtime
                    // permissions itself -- only an Activity can. Without
                    // this, tapping the mic button in the keyboard would
                    // silently fail to produce any speech results on
                    // Android 6.0+, since RECORD_AUDIO would never actually
                    // be granted. permissionLauncher is kept here at the
                    // Activity level and exposed down to HomeScreen.
                    var micPermissionGranted by remember {
                        mutableStateOf(
                            ContextCompat.checkSelfPermission(
                                context, Manifest.permission.RECORD_AUDIO
                            ) == PackageManager.PERMISSION_GRANTED
                        )
                    }
                    val permissionLauncher = rememberLauncherForActivityResult(
                        ActivityResultContracts.RequestPermission()
                    ) { granted -> micPermissionGranted = granted }

                    // FIX: this is the actual root cause of "Settings/Builder
                    // screens don't show in the app" -- MainActivity was
                    // rendering its own standalone UI directly and never
                    // used AppNavHost at all, so none of the new screens
                    // (Settings, Theme Studio, Animation Studio, Builder)
                    // were ever reachable, no matter how correctly they
                    // compiled.
                    val navController = rememberNavController()
                    AppNavHost(
                        navController = navController,
                        micPermissionGranted = micPermissionGranted,
                        onRequestMicPermission = {
                            permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                        }
                    )
                }
            }
        }
    }
}
