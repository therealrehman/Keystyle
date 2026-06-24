package com.keycafe.keyboard.voice

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VoiceInputHelper @Inject constructor() {
    
    sealed interface VoiceState {
        object Idle : VoiceState
        object Listening : VoiceState
        data class Result(val text: String) : VoiceState
        data class Error(val message: String) : VoiceState
    }

    private val _state = MutableStateFlow<VoiceState>(VoiceState.Idle)
    val state = _state.asStateFlow()
    private var recognizer: SpeechRecognizer? = null

    fun startListening(context: Context) {
        if (!SpeechRecognizer.isRecognitionAvailable(context)) {
            _state.value = VoiceState.Error("Not available"); return
        }
        recognizer = SpeechRecognizer.createSpeechRecognizer(context).apply {
            setRecognitionListener(object : RecognitionListener {
                override fun onReadyForSpeech(params: Bundle?) { _state.value = VoiceState.Listening }
                override fun onResults(results: Bundle?) {
                    val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                    _state.value = VoiceState.Result(matches?.firstOrNull() ?: "")
                }
                override fun onError(error: Int) { _state.value = VoiceState.Error("Error: $error") }
                override fun onBeginningOfSpeech() {}
                override fun onRmsChanged(rmsdB: Float) {}
                override fun onBufferReceived(buffer: ByteArray?) {}
                override fun onEndOfSpeech() {}
                override fun onEvent(eventType: Int, params: Bundle?) {}
                override fun onPartialResults(partialResults: Bundle?) {}
            })
        }
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        }
        recognizer?.startListening(intent)
    }

    fun stopListening() { recognizer?.stopListening() }
    fun destroy() { recognizer?.destroy(); recognizer = null }
}
