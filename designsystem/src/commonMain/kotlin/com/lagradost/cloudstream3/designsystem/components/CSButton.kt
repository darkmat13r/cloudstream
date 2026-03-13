package com.lagradost.cloudstream3.designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.lagradost.cloudstream3.designsystem.foundation.csFocusable
import com.lagradost.cloudstream3.designsystem.theme.CSTheme
import com.lagradost.cloudstream3.designsystem.tokens.AlphaDefaults
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Primary CTA button — Netflix-style full-width white button with black text.
 * Used for "Play", "Watch Now", "Subscribe" actions.
 */
@Composable
fun CSPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    enabled: Boolean = true,
) {
    val interactionSource = remember { MutableInteractionSource() }

    androidx.compose.material3.Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(CSTheme.sizes.buttonHeight)
            .csFocusable(interactionSource = interactionSource, shape = CSTheme.shapes.md),
        enabled = enabled,
        shape = CSTheme.shapes.md,
        colors = ButtonDefaults.buttonColors(
            containerColor = CSTheme.colors.textPrimary,
            contentColor = CSTheme.colors.textOnAccent,
            disabledContainerColor = CSTheme.colors.surfaceTertiary,
            disabledContentColor = CSTheme.colors.textQuaternary,
        ),
        interactionSource = interactionSource,
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(CSTheme.sizes.iconMd),
            )
            Spacer(Modifier.width(CSTheme.spacing.sm))
        }
        Text(
            text = text,
            style = CSTheme.typography.titleMedium,
        )
    }
}

/**
 * Secondary button — outlined style for secondary actions.
 * Used for "Add to List", "Download", etc.
 */
@Composable
fun CSSecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    enabled: Boolean = true,
) {
    val interactionSource = remember { MutableInteractionSource() }

    androidx.compose.material3.OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .height(CSTheme.sizes.buttonHeight)
            .csFocusable(interactionSource = interactionSource, shape = CSTheme.shapes.md),
        enabled = enabled,
        shape = CSTheme.shapes.md,
        border = BorderStroke(1.dp, if (enabled) CSTheme.colors.borderSelected else CSTheme.colors.borderChip),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = CSTheme.colors.textPrimary,
            disabledContentColor = CSTheme.colors.textQuaternary,
        ),
        interactionSource = interactionSource,
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(CSTheme.sizes.iconMd),
            )
            Spacer(Modifier.width(CSTheme.spacing.sm))
        }
        Text(
            text = text,
            style = CSTheme.typography.titleMedium,
        )
    }
}

/**
 * Accent button — uses brand accent color.
 * Used for "Resume", "Continue" CTAs.
 */
@Composable
fun CSAccentButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    enabled: Boolean = true,
) {
    val interactionSource = remember { MutableInteractionSource() }

    androidx.compose.material3.Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(CSTheme.sizes.buttonHeight)
            .csFocusable(interactionSource = interactionSource, shape = CSTheme.shapes.md),
        enabled = enabled,
        shape = CSTheme.shapes.md,
        colors = ButtonDefaults.buttonColors(
            containerColor = CSTheme.colors.accentPrimary,
            contentColor = CSTheme.colors.textOnAccent,
            disabledContainerColor = CSTheme.colors.surfaceTertiary,
            disabledContentColor = CSTheme.colors.textQuaternary,
        ),
        interactionSource = interactionSource,
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(CSTheme.sizes.iconMd),
            )
            Spacer(Modifier.width(CSTheme.spacing.sm))
        }
        Text(
            text = text,
            style = CSTheme.typography.titleMedium,
        )
    }
}

/**
 * Icon action button — for vertical icon+label actions below content detail.
 * Netflix-style "Add to List", "Download", "Share", "Rate".
 */
@Composable
fun CSIconActionButton(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    tint: Color = CSTheme.colors.textPrimary,
    enabled: Boolean = true,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val alpha = if (enabled) AlphaDefaults.Full else AlphaDefaults.Disabled

    Column(
        modifier = modifier
            .csFocusable(interactionSource = interactionSource, shape = CSTheme.shapes.md)
            .padding(CSTheme.spacing.sm),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        androidx.compose.material3.IconButton(
            onClick = onClick,
            enabled = enabled,
            interactionSource = interactionSource,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = tint.copy(alpha = alpha),
                modifier = Modifier.size(CSTheme.sizes.iconLg),
            )
        }
        Text(
            text = label,
            style = CSTheme.typography.caption,
            color = CSTheme.colors.textSecondary.copy(alpha = alpha),
        )
    }
}

/**
 * Small pill button — for "New Episode" badges, trailer buttons.
 */
@Composable
fun CSPillButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = Color.Transparent,
    contentColor: Color = CSTheme.colors.textPrimary,
    borderColor: Color = CSTheme.colors.borderSelected,
) {
    androidx.compose.material3.OutlinedButton(
        onClick = onClick,
        modifier = modifier.height(CSTheme.sizes.buttonHeightSm),
        shape = CSTheme.shapes.pill,
        border = BorderStroke(1.dp, borderColor),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = containerColor,
            contentColor = contentColor,
        ),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 0.dp),
    ) {
        Text(text = text, style = CSTheme.typography.titleSmall)
    }
}

// --- Previews ---

@Preview
@Composable
private fun CSPrimaryButtonPreview() {
    CSTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            CSPrimaryButton(text = "Play", onClick = {})
            CSPrimaryButton(text = "Play", onClick = {}, enabled = false)
        }
    }
}

@Preview
@Composable
private fun CSSecondaryButtonPreview() {
    CSTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            CSSecondaryButton(text = "Add to List", onClick = {})
            CSAccentButton(text = "Resume", onClick = {})
            CSPillButton(text = "Trailers", onClick = {})
        }
    }
}
