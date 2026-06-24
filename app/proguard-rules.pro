# Keep InputMethodService
-keep class * extends android.inputmethodservice.InputMethodService { *; }

# Keep Hilt DI
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }

# Keep Kotlin Serialization
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt
-keepclassmembers @kotlinx.serialization.Serializable class ** {
    *** Companion;
    *** INSTANCE;
    kotlinx.serialization.KSerializer serializer(...);
}

# Keep Compose SnapshotStateList
-keep class androidx.compose.runtime.snapshots.SnapshotStateList { *; }
