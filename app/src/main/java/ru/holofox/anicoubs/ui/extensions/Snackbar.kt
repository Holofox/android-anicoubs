@file:JvmName("DesignSnackbarKt")
@file:Suppress("NOTHING_TO_INLINE")
package ru.holofox.anicoubs.ui.extensions

import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

/**
 * Display the Snackbar with the [Snackbar.LENGTH_LONG] duration.
 *
 * @param message the message text resource.
 */
@JvmName("longSnackbar")
inline fun View.longSnackbar(@StringRes message: Int) = Snackbar
    .make(this, message, Snackbar.LENGTH_LONG)
    .apply { show() }

/**
 * Display the Snackbar with the [Snackbar.LENGTH_LONG] duration.
 *
 * @param message the message text.
 */
@JvmName("longSnackbar2")
inline fun View.longSnackbar(message: CharSequence) = Snackbar
    .make(this, message, Snackbar.LENGTH_LONG)
    .apply { show() }

/**
 * Display the Snackbar with the [Snackbar.LENGTH_SHORT] duration.
 *
 * @param message the message text resource.
 */
@JvmName("snackbar")
inline fun View.snackbar(@StringRes message: Int) = Snackbar
    .make(this, message, Snackbar.LENGTH_SHORT)
    .apply { show() }

/**
 * Display the Snackbar with the [Snackbar.LENGTH_SHORT] duration.
 *
 * @param message the message text.
 */
@JvmName("snackbar2")
inline fun View.snackbar(message: CharSequence) = Snackbar
    .make(this, message, Snackbar.LENGTH_SHORT)
    .apply { show() }

/**
 * Display Snackbar with the [Snackbar.LENGTH_INDEFINITE] duration.
 *
 * @param message the message text resource.
 */
@JvmName("indefiniteSnackbar")
inline fun View.indefiniteSnackbar(
    @StringRes message: Int,
    @StringRes actionText: Int,
    noinline action: (View) -> Unit
) = Snackbar
    .make(this, message, Snackbar.LENGTH_INDEFINITE)
    .setAction(actionText, action)
    .apply { show() }

/**
 * Display Snackbar with the [Snackbar.LENGTH_INDEFINITE] duration.
 *
 * @param message the message text.
 */
@JvmName("indefiniteSnackbar2")
inline fun View.indefiniteSnackbar(
    message: CharSequence,
    actionText: CharSequence,
    noinline action: (View) -> Unit
) = Snackbar
    .make(this, message, Snackbar.LENGTH_INDEFINITE)
    .setAction(actionText, action)
    .apply { show() }