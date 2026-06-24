package com.keycafe.keyboard.builder

import kotlinx.serialization.Serializable

@Serializable
data class CustomLayout(
    val name: String = "Default",
    val keys: List<List<EditableKey>> = emptyList()
)
