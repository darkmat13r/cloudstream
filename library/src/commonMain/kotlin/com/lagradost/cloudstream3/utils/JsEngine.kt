package com.lagradost.cloudstream3.utils

/**
 * KMP-compatible JavaScript evaluation engine.
 * Uses Rhino on JVM/Android, JavaScriptCore on iOS.
 */
expect object JsEngine {
    /**
     * Evaluate a JavaScript expression and return the result as a string.
     * Returns empty string on evaluation failure.
     */
    fun evaluate(script: String): String

    /**
     * Evaluate a JavaScript script and return the value of a named variable.
     * Returns empty string if the variable is not found or evaluation fails.
     */
    fun evaluateAndGetVariable(script: String, variableName: String): String
}
