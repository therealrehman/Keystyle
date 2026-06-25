package com.keycafe.keyboard.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    onOpenThemeStudio: () -> Unit,
    onOpenAnimationStudio: () -> Unit,
    onOpenBuilder: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null) }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding).verticalScroll(rememberScrollState())
        ) {
            ListItem(
                headlineContent = { Text("Theme Studio") },
                supportingContent = { Text("Customize colors and shapes") },
                leadingContent = { Icon(Icons.Default.Palette, null) },
                modifier = Modifier.clickable { onOpenThemeStudio() }
            )
            HorizontalDivider()
            
            ListItem(
                headlineContent = { Text("Animation Studio") },
                supportingContent = { Text("Configure the 4-Color Radial Burst") },
                leadingContent = { Icon(Icons.Default.AutoAwesome, null) },
                modifier = Modifier.clickable { onOpenAnimationStudio() }
            )
            HorizontalDivider()
            
            ListItem(
                headlineContent = { Text("Keyboard Builder") },
                supportingContent = { Text("Resize and rearrange keys") },
                leadingContent = { Icon(Icons.Default.Build, null) },
                modifier = Modifier.clickable { onOpenBuilder() }
            )
            HorizontalDivider()
            
            ListItem(
                headlineContent = { Text("Privacy Policy") },
                supportingContent = { Text("Read our data safety practices") },
                leadingContent = { Icon(Icons.Default.Security, null) },
                modifier = Modifier.clickable { /* Navigate to privacy */ }
            )
        }
    }
}
