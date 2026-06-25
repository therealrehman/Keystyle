package com.keycafe.keyboard.service

import android.inputmethodservice.InputMethodService
import android.view.View
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.lifecycle.setViewTreeViewModelStoreOwner
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.keycafe.keyboard.engine.KeyAction
import com.keycafe.keyboard.engine.KeyboardViewModel
import com.keycafe.keyboard.theme.ThemeManager
import com.keycafe.keyboard.animation.RippleEngine
import com.keycafe.keyboard.ui.KeyboardScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CoreImeService : InputMethodService(),
    LifecycleOwner, ViewModelStoreOwner, SavedStateRegistryOwner {

    // FIX: @HiltViewModel classes must be obtained through a ViewModelProvider
    // (since ViewModel's lifecycle/retention is what the provider manages),
    // not via plain @Inject field injection. ThemeManager and RippleEngine
    // are plain @Singleton classes, so they ARE correctly field-injectable.
    @Inject lateinit var themeManager: ThemeManager
    @Inject lateinit var rippleEngine: RippleEngine
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject lateinit var voiceInputHelper: com.keycafe.keyboard.voice.VoiceInputHelper

    private lateinit var viewModel: KeyboardViewModel

    private val lifecycleRegistry = LifecycleRegistry(this)
    private val store = ViewModelStore()

    // FIX: SavedStateRegistryOwner is an INTERFACE. The previous code tried
    // to instantiate it via reflection (getDeclaredConstructor().newInstance()),
    // which throws InstantiationException at runtime on every single keyboard
    // load -- interfaces cannot be instantiated. SavedStateRegistryController
    // is the correct, standard way to obtain a SavedStateRegistry for a
    // custom LifecycleOwner like this IME service.
    private val savedStateRegistryController = SavedStateRegistryController.create(this)

    override val lifecycle: Lifecycle get() = lifecycleRegistry
    override val viewModelStore: ViewModelStore get() = store
    override val savedStateRegistry: SavedStateRegistry get() = savedStateRegistryController.savedStateRegistry

    override fun onCreate() {
        super.onCreate()
        savedStateRegistryController.performRestore(null)
        lifecycleRegistry.currentState = Lifecycle.State.CREATED
        viewModel = ViewModelProvider(this, viewModelFactory)[KeyboardViewModel::class.java]
    }

    override fun onCreateInputView(): View {
        lifecycleRegistry.currentState = Lifecycle.State.STARTED
        lifecycleRegistry.currentState = Lifecycle.State.RESUMED

        return ComposeView(this).apply {
            setViewTreeLifecycleOwner(this@CoreImeService)
            setViewTreeViewModelStoreOwner(this@CoreImeService)
            setViewTreeSavedStateRegistryOwner(this@CoreImeService)

            setContent {
                val state by viewModel.uiState.collectAsState()
                val theme by themeManager.currentTheme.collectAsState(
                    initial = com.keycafe.keyboard.theme.KeyboardTheme.DEFAULT
                )

                // Observes VoiceInputHelper's state and commits the
                // recognized text automatically once speech recognition
                // produces a result -- this is what makes the mic button in
                // the toolbar actually result in typed text, not just start
                // a recognizer that goes nowhere.
                val voiceState by voiceInputHelper.state.collectAsState()
                androidx.compose.runtime.LaunchedEffect(voiceState) {
                    val currentVoiceState = voiceState
                    if (currentVoiceState is com.keycafe.keyboard.voice.VoiceInputHelper.VoiceState.Result) {
                        if (currentVoiceState.text.isNotEmpty()) {
                            currentInputConnection?.commitText(currentVoiceState.text, 1)
                        }
                    }
                }

                KeyboardScreen(
                    state = state,
                    theme = theme,
                    rippleEngine = rippleEngine,
                    onKeyAction = { action -> handleKeyAction(action) }
                )
            }
        }
    }

    private fun handleKeyAction(action: KeyAction) {
        when (action) {
            is KeyAction.CommitText -> currentInputConnection.commitText(action.text, 1)
            KeyAction.Backspace -> currentInputConnection.deleteSurroundingText(1, 0)
            KeyAction.Enter -> currentInputConnection.performEditorAction(android.view.inputmethod.EditorInfo.IME_ACTION_UNSPECIFIED)
            KeyAction.Space -> currentInputConnection.commitText(" ", 1)
            KeyAction.ShiftToggle -> viewModel.toggleShift()
            KeyAction.SwitchToSymbols -> viewModel.switchLayout(com.keycafe.keyboard.engine.LayoutType.SYMBOLS)
            KeyAction.SwitchToNumbers -> viewModel.switchLayout(com.keycafe.keyboard.engine.LayoutType.NUMBERS)
            KeyAction.SwitchToQwerty -> viewModel.switchLayout(com.keycafe.keyboard.engine.LayoutType.QWERTY)
            KeyAction.StartVoiceInput -> voiceInputHelper.startListening(this)
        }
    }

    override fun onDestroy() {
        lifecycleRegistry.currentState = Lifecycle.State.DESTROYED
        super.onDestroy()
    }
}
