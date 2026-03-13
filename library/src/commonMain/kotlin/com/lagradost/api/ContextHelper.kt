package com.lagradost.api

/**
 * A multiplatform weak reference wrapper.
 * On JVM/Android, delegates to java.lang.ref.WeakReference.
 */
expect class WeakRef<T : Any>(referent: T) {
    fun get(): T?
}

/**
 * Set context for android specific code such as webview.
 * Does nothing on JVM.
 */
expect fun setContext(context: WeakRef<Any>)
/**
 * Helper function for Android specific context.
 * Do not use this unless absolutely necessary.
 * setContext() must be called before this is called.
 * @return Context if on android, null if not.
 */
expect fun getContext(): Any?
