package com.keycafe.keyboard.emoji

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

data class EmojiItem(val unicode: String, val name: String, val category: String)

@Singleton
class EmojiRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun getAllEmojis(): List<EmojiItem> = listOf(
        EmojiItem("😀", "grinning face", "SMILEYS"),
        EmojiItem("😂", "face with tears of joy", "SMILEYS"),
        EmojiItem("❤️", "red heart", "SMILEYS"),
        EmojiItem("👍", "thumbs up", "PEOPLE"),
        EmojiItem("", "cat face", "ANIMALS"),
        EmojiItem("🍕", "pizza", "FOOD"),
        EmojiItem("✈️", "airplane", "TRAVEL"),
        EmojiItem("💡", "light bulb", "OBJECTS"),
        EmojiItem("🏁", "chequered flag", "FLAGS")
    )
}
