package com.keycafe.keyboard.data

import android.content.Context
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BackupRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val json = Json { ignoreUnknownKeys = true; prettyPrint = true }

    fun exportBackup(data: String, uri: Uri): Result<Unit> = runCatching {
        context.contentResolver.openOutputStream(uri)?.use { stream ->
            stream.write(data.toByteArray(Charsets.UTF_8))
        } ?: throw IllegalStateException("Cannot open output stream")
    }

    fun importBackup(uri: Uri): Result<String> = runCatching {
        context.contentResolver.openInputStream(uri)?.use { stream ->
            stream.bufferedReader().readText()
        } ?: throw IllegalStateException("Cannot open input stream")
    }
}
