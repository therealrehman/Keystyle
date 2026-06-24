package com.keycafe.keyboard.service

import android.inputmethodservice.InputMethodService
import android.view.View
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.lifecycle.setViewTreeViewModelStoreOwner
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.keycafe.keyboard.engine.KeyAction
import com.keycafe.keyboard.engine.KeyboardViewModel
import com.keycafe.keyboard.ui.KeyboardScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CoreImeService : InputMethodService(), 
    LifecycleOwner, ViewModelStoreOwner, SavedStateRegistryOwner {

    @Inject lateinit var viewModel: KeyboardViewModel
    
    private val lifecycleRegistry = LifecycleRegistry(this)
    private val store = ViewModelStore()
    private val savedStateRegistryController = SavedStateRegistryOwner::class.java.getDeclaredConstructor().newInstance() as SavedStateRegistryOwner

    override val lifecycle: Lifecycle get() = lifecycleRegistry
    override val viewModelStore: ViewModelStore get() = store
    override val savedStateRegistry: SavedStateRegistry get() = savedStateRegistryController.savedStateRegistry

    override fun onCreate() {
        super.onCreate()
        lifecycleRegistry.currentState = Lifecycle.State.CREATED
    }

    override fun onCreateInputView(): View {
        return ComposeView(this).apply {
            setViewTreeLifecycleOwner(this@CoreImeService)
            setViewTreeViewModelStoreOwner(this@CoreImeService)
            setViewTreeSavedStateRegistryOwner(this@CoreImeService)
            
            setContent {
                val state by viewModel.uiState.collectAsState()
                KeyboardScreen(
                    state = state,
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
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycleRegistry.currentState = Lifecycle.State.DESTROYED
    }
}
