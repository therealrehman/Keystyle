package com.keycafe.keyboard.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeStudioScreen(onBack: () -> Unit) {
    var cornerRadius by remember { mutableFloatStateOf(6f) }
    var keySpacing by remember { mutableFloatStateOf(4f) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Theme Studio") },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null) } }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            Text("Corner Radius: ${cornerRadius.toInt()}dp", style = MaterialTheme.typography.titleMedium)
            Slider(value = cornerRadius, onValueChange = { cornerRadius = it }, valueRange = 0f..24f)
            
            Spacer(Modifier.height(16.dp))
            
            Text("Key Spacing: ${keySpacing.toInt()}dp", style = MaterialTheme.typography.titleMedium)
            Slider(value = keySpacing, onValueChange = { keySpacing = it }, valueRange = 0f..12f)
            
            Spacer(Modifier.height(24.dp))
            
            Text("Color Presets", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf(Color.Black, Color(0xFF1C1B1F), Color(0xFF2D2C31), Color.White).forEach { color ->
                    Box(
                        Modifier.size(48.dp).padding(2.dp),
                        contentAlignment = androidx.compose.ui.Alignment.Center
                    ) {
                        androidx.compose.foundation.BorderStroke(2.dp, Color.Gray)
                        androidx.compose.foundation.layout.Box(
                            modifier = Modifier.fillMaxSize()
                                .then(androidx.compose.foundation.border(androidx.compose.foundation.BorderStroke(2.dp, Color.Gray), androidx.compose.foundation.shape.CircleShape))
                                .background(color, androidx.compose.foundation.shape.CircleShape)
                        )
                    }
                }
            }
            
            Spacer(Modifier.weight(1f))
            
            Button(onClick = { /* Save Theme Logic */ }, modifier = Modifier.fillMaxWidth()) {
                Text("Save & Apply Theme")
            }
        }
    }
}
