package com.lagradost.cloudstream3.utils

import platform.JavaScriptCore.JSContext
import platform.JavaScriptCore.JSValue

actual object JsEngine {
    actual fun evaluate(script: String): String {
        val context = JSContext()
        val result: JSValue? = context.evaluateScript(script)
        return result?.toString() ?: ""
    }

    actual fun evaluateAndGetVariable(script: String, variableName: String): String {
        val context = JSContext()
        context.evaluateScript(script)
        // Retrieve the variable by evaluating its name as a separate expression
        val result: JSValue? = context.evaluateScript(variableName)
        return result?.toString() ?: ""
    }
}
