package com.lagradost.cloudstream3.utils

import org.mozilla.javascript.Context
import org.mozilla.javascript.EvaluatorException

actual object JsEngine {
    actual fun evaluate(script: String): String {
        val rhino = Context.enter()
        return try {
            rhino.setInterpretedMode(true)
            val scope = rhino.initStandardObjects()
            rhino.evaluateString(scope, script, "JavaScript", 1, null).toString()
        } catch (e: EvaluatorException) {
            ""
        } finally {
            Context.exit()
        }
    }

    actual fun evaluateAndGetVariable(script: String, variableName: String): String {
        val rhino = Context.enter()
        return try {
            rhino.setInterpretedMode(true)
            val scope = rhino.initStandardObjects()
            rhino.evaluateString(scope, script, "JavaScript", 1, null)
            scope.get(variableName, scope).toString()
        } catch (e: Exception) {
            ""
        } finally {
            Context.exit()
        }
    }
}
