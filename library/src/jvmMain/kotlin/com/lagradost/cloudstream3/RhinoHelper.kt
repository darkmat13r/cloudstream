package com.lagradost.cloudstream3

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.mozilla.javascript.Context

/**
 * Get rhino context in a safe way as it needs to be initialized on the main thread.
 *
 * Make sure you get the scope using: val scope: Scriptable = rhino.initSafeStandardObjects()
 *
 * Use like the following: rhino.evaluateString(scope, js, "JavaScript", 1, null)
 **/
suspend fun getRhinoContext(): Context {
    return withContext(Dispatchers.Main) {
        val rhino = Context.enter()
        rhino.initSafeStandardObjects()
        rhino.setInterpretedMode(true)
        rhino
    }
}
