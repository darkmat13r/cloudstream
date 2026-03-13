package com.lagradost.cloudstream3.designsystem.foundation

import android.app.UiModeManager
import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

actual val currentPlatform: PlatformType = PlatformType.Android

actual fun isTelevision(): Boolean {
    // Fallback check — callers should prefer CSTheme.isTV which uses Compose context
    return false
}

@Composable
fun isTelevisionDevice(): Boolean {
    val context = LocalContext.current
    val uiModeManager = context.getSystemService(android.content.Context.UI_MODE_SERVICE) as? UiModeManager
    return uiModeManager?.currentModeType == Configuration.UI_MODE_TYPE_TELEVISION
}
