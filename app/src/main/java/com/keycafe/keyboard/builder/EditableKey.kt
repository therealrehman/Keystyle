package com.keycafe.keyboard.builder

import androidx.compose.runtime.Immutable
import com.keycafe.keyboard.engine.KeyAction
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class EditableKey(
    val id: String = kotlin.random.Random.nextLong().toString(),
    val label: String,
    val action: KeyAction,
    val widthWeight: Float = 1f,
    val isSpecial: Boolean = false
) {
    companion object {
        fun fromStandard(label: String, action: KeyAction, weight: Float = 1f, special: Boolean = false) =
            EditableKey(label = label, action = action, widthWeight = weight, isSpecial = special)
    }
}
