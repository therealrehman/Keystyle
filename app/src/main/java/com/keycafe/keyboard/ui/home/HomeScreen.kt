package com.keycafe.keyboard.ui.home

import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(
    onNavigateToSettings: () -> Unit,
    micPermissionGranted: Boolean = true,
    onRequestMicPermission: () -> Unit = {}
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("KeyStyle Keyboard", fontSize = 32.sp, style = MaterialTheme.typography.headlineLarge)
        Spacer(Modifier.height(8.dp))
        Text("Customize your typing experience", style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center)

        Spacer(Modifier.height(48.dp))

        Button(
            onClick = { context.startActivity(Intent(Settings.ACTION_INPUT_METHOD_SETTINGS)) },
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) {
            Text("Enable Keyboard in Settings", fontSize = 18.sp)
        }

        Spacer(Modifier.height(16.dp))

        // FIX: this was previously in MainActivity directly. Voice input
        // (the keyboard's mic button) silently fails without this
        // permission being granted once from the app -- an IME service
        // cannot request runtime permissions itself.
        OutlinedButton(
            onClick = onRequestMicPermission,
            enabled = !micPermissionGranted,
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) {
            Text(
                if (micPermissionGranted) "Microphone access granted" else "Allow microphone for voice typing",
                fontSize = 16.sp
            )
        }

        Spacer(Modifier.height(16.dp))

        OutlinedButton(
            onClick = onNavigateToSettings,
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) {
            Text("Open App Settings & Studios", fontSize = 18.sp)
        }
    }
}
