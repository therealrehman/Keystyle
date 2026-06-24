package com.keycafe.keyboard.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class KeyboardTheme(
    val id: String = "default",
    val name: String = "Default Material",
    val backgroundColor: Color = Color(0xFF1C1B1F),
    val keyColor: Color = Color(0xFF2D2C31),
    val keyTextColor: Color = Color.White,
    val specialKeyColor: Color = Color(0xFF3A393E),
    val accentColor: Color = Color(0xFFD0BCFF),
    val cornerRadius: Float = 6f,
    val keySpacing: Float = 4f,
    val backgroundImageUri: String? = null,
    val backgroundBlur: Float = 0f
) {
    companion object {
        val DEFAULT = KeyboardTheme()
        
        val NEON_DARK = KeyboardTheme(
            id = "neon_dark",
            name = "Neon Dark",
            backgroundColor = Color.Black,
            keyColor = Color(0xFF1A1A1A),
            accentColor = Color.Cyan,
            cornerRadius = 12f
        )
    }
}
