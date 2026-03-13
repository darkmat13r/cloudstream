package com.lagradost.cloudstream3.designsystem.tokens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.VolumeOff
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Centralized icon references for the design system.
 *
 * Components should use [CSIcons] instead of referencing [Icons] directly,
 * so icon choices can be swapped in one place.
 */
object CSIcons {
    // Navigation
    val Home: ImageVector get() = Icons.Default.Home
    val Search: ImageVector get() = Icons.Default.Search
    val Profile: ImageVector get() = Icons.Default.Person
    val Settings: ImageVector get() = Icons.Default.Settings
    val Back: ImageVector get() = Icons.AutoMirrored.Filled.ArrowBack
    val Close: ImageVector get() = Icons.Default.Close
    val ExpandMore: ImageVector get() = Icons.Default.KeyboardArrowDown

    // Media controls
    val Play: ImageVector get() = Icons.Default.PlayArrow
    val Pause: ImageVector get() = Icons.Default.Pause
    val SkipNext: ImageVector get() = Icons.Default.SkipNext
    val SkipPrevious: ImageVector get() = Icons.Default.SkipPrevious
    val VolumeOff: ImageVector get() = Icons.AutoMirrored.Filled.VolumeOff
    val VolumeUp: ImageVector get() = Icons.AutoMirrored.Filled.VolumeUp
    val Fullscreen: ImageVector get() = Icons.Default.Fullscreen

    // Actions
    val Add: ImageVector get() = Icons.Default.Add
    val Download: ImageVector get() = Icons.Default.Download
    val Share: ImageVector get() = Icons.Default.Share
    val ThumbUp: ImageVector get() = Icons.Default.ThumbUp
    val Favorite: ImageVector get() = Icons.Default.Favorite
    val FavoriteBorder: ImageVector get() = Icons.Default.FavoriteBorder
    val Notifications: ImageVector get() = Icons.Default.Notifications
    val MoreVert: ImageVector get() = Icons.Default.MoreVert
    val Check: ImageVector get() = Icons.Default.Check
    val Info: ImageVector get() = Icons.Default.Info
    val Star: ImageVector get() = Icons.Default.Star
}
