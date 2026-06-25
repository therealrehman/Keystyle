package com.keycafe.keyboard.ui.builder

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.keycafe.keyboard.builder.CustomLayout

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KeyboardBuilderScreen(onBack: () -> Unit) {
    val layout = remember { CustomLayout() } // Load from repo in real app
    var selectedKeyId by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Keyboard Builder") },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null) } },
                actions = {
                    TextButton(onClick = { /* Save Layout */ }) { Text("Save") }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            Text("Tap a key to select, then adjust width.", style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(16.dp))
            
            // Visual representation of rows
            layout.rows.forEach { row ->
                Row(modifier = Modifier.fillMaxWidth().height(50.dp)) {
                    row.forEach { key ->
                        Box(
                            modifier = Modifier
                                .weight(key.widthWeight)
                                .padding(2.dp)
                                .background(
                                    if (key.id == selectedKeyId) MaterialTheme.colorScheme.primary 
                                    else MaterialTheme.colorScheme.surfaceVariant,
                                    MaterialTheme.shapes.small
                                ),
                            contentAlignment = androidx.compose.ui.Alignment.Center
                        ) {
                            Text(key.label, color = MaterialTheme.colorScheme.onSurface)
                        }
                    }
                }
            }
            
            Spacer(Modifier.height(24.dp))
            
            if (selectedKeyId != null) {
                Text("Selected Key Width", style = MaterialTheme.typography.titleMedium)
                Slider(value = 1f, onValueChange = { /* Update key width */ }, valueRange = 0.5f..4f)
            } else {
                Text("No key selected", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.outline)
            }
        }
    }
}
