package com.keycafe.keyboard.ui.animation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimationStudioScreen(onBack: () -> Unit) {
    var maxRadius by remember { mutableFloatStateOf(120f) }
    var duration by remember { mutableFloatStateOf(350f) }
    var isEnabled by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Animation Studio") },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null) } }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Enable Animations", style = MaterialTheme.typography.titleMedium)
                Switch(checked = isEnabled, onCheckedChange = { isEnabled = it })
            }
            
            if (isEnabled) {
                Spacer(Modifier.height(24.dp))
                
                Text("Burst Radius: ${maxRadius.toInt()}dp", style = MaterialTheme.typography.titleMedium)
                Slider(value = maxRadius, onValueChange = { maxRadius = it }, valueRange = 50f..300f)
                
                Spacer(Modifier.height(16.dp))
                
                Text("Animation Speed: ${duration.toInt()}ms", style = MaterialTheme.typography.titleMedium)
                Slider(value = duration, onValueChange = { duration = it }, valueRange = 100f..1000f)
                
                Spacer(Modifier.height(24.dp))
                Text("Burst Colors (4-Color Radial)", style = MaterialTheme.typography.titleMedium)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf(0xFFD0BCFF, 0xFFEFB8C8, 0xFFA8EAD6, 0xFFFFF59D).forEach { hex ->
                        Box(
                            Modifier.size(48.dp).background(androidx.compose.ui.graphics.Color(hex), androidx.compose.foundation.shape.CircleShape)
                        )
                    }
                }
            }
            
            Spacer(Modifier.weight(1f))
            
            Button(onClick = { /* Save Animation Logic */ }, modifier = Modifier.fillMaxWidth()) {
                Text("Save Animation Settings")
            }
        }
    }
}
