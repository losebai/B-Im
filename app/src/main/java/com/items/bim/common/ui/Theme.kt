package com.items.bim.common.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import com.items.bim.Context
import com.items.bim.R
import com.items.bim.ui.AppTypography
import com.items.bim.ui.backgroundDark
import com.items.bim.ui.backgroundDarkHighContrast
import com.items.bim.ui.backgroundDarkMediumContrast
import com.items.bim.ui.backgroundLight
import com.items.bim.ui.backgroundLightHighContrast
import com.items.bim.ui.backgroundLightMediumContrast
import com.items.bim.ui.errorContainerDark
import com.items.bim.ui.errorContainerDarkHighContrast
import com.items.bim.ui.errorContainerDarkMediumContrast
import com.items.bim.ui.errorContainerLight
import com.items.bim.ui.errorContainerLightHighContrast
import com.items.bim.ui.errorContainerLightMediumContrast
import com.items.bim.ui.errorDark
import com.items.bim.ui.errorDarkHighContrast
import com.items.bim.ui.errorDarkMediumContrast
import com.items.bim.ui.errorLight
import com.items.bim.ui.errorLightHighContrast
import com.items.bim.ui.errorLightMediumContrast
import com.items.bim.ui.inverseOnSurfaceDark
import com.items.bim.ui.inverseOnSurfaceDarkHighContrast
import com.items.bim.ui.inverseOnSurfaceDarkMediumContrast
import com.items.bim.ui.inverseOnSurfaceLight
import com.items.bim.ui.inverseOnSurfaceLightHighContrast
import com.items.bim.ui.inverseOnSurfaceLightMediumContrast
import com.items.bim.ui.inversePrimaryDark
import com.items.bim.ui.inversePrimaryDarkHighContrast
import com.items.bim.ui.inversePrimaryDarkMediumContrast
import com.items.bim.ui.inversePrimaryLight
import com.items.bim.ui.inversePrimaryLightHighContrast
import com.items.bim.ui.inversePrimaryLightMediumContrast
import com.items.bim.ui.inverseSurfaceDark
import com.items.bim.ui.inverseSurfaceDarkHighContrast
import com.items.bim.ui.inverseSurfaceDarkMediumContrast
import com.items.bim.ui.inverseSurfaceLight
import com.items.bim.ui.inverseSurfaceLightHighContrast
import com.items.bim.ui.inverseSurfaceLightMediumContrast
import com.items.bim.ui.onBackgroundDark
import com.items.bim.ui.onBackgroundDarkHighContrast
import com.items.bim.ui.onBackgroundDarkMediumContrast
import com.items.bim.ui.onBackgroundLight
import com.items.bim.ui.onBackgroundLightHighContrast
import com.items.bim.ui.onBackgroundLightMediumContrast
import com.items.bim.ui.onErrorContainerDark
import com.items.bim.ui.onErrorContainerDarkHighContrast
import com.items.bim.ui.onErrorContainerDarkMediumContrast
import com.items.bim.ui.onErrorContainerLight
import com.items.bim.ui.onErrorContainerLightHighContrast
import com.items.bim.ui.onErrorContainerLightMediumContrast
import com.items.bim.ui.onErrorDark
import com.items.bim.ui.onErrorDarkHighContrast
import com.items.bim.ui.onErrorDarkMediumContrast
import com.items.bim.ui.onErrorLight
import com.items.bim.ui.onErrorLightHighContrast
import com.items.bim.ui.onErrorLightMediumContrast
import com.items.bim.ui.onPrimaryContainerDark
import com.items.bim.ui.onPrimaryContainerDarkHighContrast
import com.items.bim.ui.onPrimaryContainerDarkMediumContrast
import com.items.bim.ui.onPrimaryContainerLight
import com.items.bim.ui.onPrimaryContainerLightHighContrast
import com.items.bim.ui.onPrimaryContainerLightMediumContrast
import com.items.bim.ui.onPrimaryDark
import com.items.bim.ui.onPrimaryDarkHighContrast
import com.items.bim.ui.onPrimaryDarkMediumContrast
import com.items.bim.ui.onPrimaryLight
import com.items.bim.ui.onPrimaryLightHighContrast
import com.items.bim.ui.onPrimaryLightMediumContrast
import com.items.bim.ui.onSecondaryContainerDark
import com.items.bim.ui.onSecondaryContainerDarkHighContrast
import com.items.bim.ui.onSecondaryContainerDarkMediumContrast
import com.items.bim.ui.onSecondaryContainerLight
import com.items.bim.ui.onSecondaryContainerLightHighContrast
import com.items.bim.ui.onSecondaryContainerLightMediumContrast
import com.items.bim.ui.onSecondaryDark
import com.items.bim.ui.onSecondaryDarkHighContrast
import com.items.bim.ui.onSecondaryDarkMediumContrast
import com.items.bim.ui.onSecondaryLight
import com.items.bim.ui.onSecondaryLightHighContrast
import com.items.bim.ui.onSecondaryLightMediumContrast
import com.items.bim.ui.onSurfaceDark
import com.items.bim.ui.onSurfaceDarkHighContrast
import com.items.bim.ui.onSurfaceDarkMediumContrast
import com.items.bim.ui.onSurfaceLight
import com.items.bim.ui.onSurfaceLightHighContrast
import com.items.bim.ui.onSurfaceLightMediumContrast
import com.items.bim.ui.onSurfaceVariantDark
import com.items.bim.ui.onSurfaceVariantDarkHighContrast
import com.items.bim.ui.onSurfaceVariantDarkMediumContrast
import com.items.bim.ui.onSurfaceVariantLight
import com.items.bim.ui.onSurfaceVariantLightHighContrast
import com.items.bim.ui.onSurfaceVariantLightMediumContrast
import com.items.bim.ui.onTertiaryContainerDark
import com.items.bim.ui.onTertiaryContainerDarkHighContrast
import com.items.bim.ui.onTertiaryContainerDarkMediumContrast
import com.items.bim.ui.onTertiaryContainerLight
import com.items.bim.ui.onTertiaryContainerLightHighContrast
import com.items.bim.ui.onTertiaryContainerLightMediumContrast
import com.items.bim.ui.onTertiaryDark
import com.items.bim.ui.onTertiaryDarkHighContrast
import com.items.bim.ui.onTertiaryDarkMediumContrast
import com.items.bim.ui.onTertiaryLight
import com.items.bim.ui.onTertiaryLightHighContrast
import com.items.bim.ui.onTertiaryLightMediumContrast
import com.items.bim.ui.outlineDark
import com.items.bim.ui.outlineDarkHighContrast
import com.items.bim.ui.outlineDarkMediumContrast
import com.items.bim.ui.outlineLight
import com.items.bim.ui.outlineLightHighContrast
import com.items.bim.ui.outlineLightMediumContrast
import com.items.bim.ui.outlineVariantDark
import com.items.bim.ui.outlineVariantDarkHighContrast
import com.items.bim.ui.outlineVariantDarkMediumContrast
import com.items.bim.ui.outlineVariantLight
import com.items.bim.ui.outlineVariantLightHighContrast
import com.items.bim.ui.outlineVariantLightMediumContrast
import com.items.bim.ui.primaryContainerDark
import com.items.bim.ui.primaryContainerDarkHighContrast
import com.items.bim.ui.primaryContainerDarkMediumContrast
import com.items.bim.ui.primaryContainerLight
import com.items.bim.ui.primaryContainerLightHighContrast
import com.items.bim.ui.primaryContainerLightMediumContrast
import com.items.bim.ui.primaryDark
import com.items.bim.ui.primaryDarkHighContrast
import com.items.bim.ui.primaryDarkMediumContrast
import com.items.bim.ui.primaryLight
import com.items.bim.ui.primaryLightHighContrast
import com.items.bim.ui.primaryLightMediumContrast
import com.items.bim.ui.scrimDark
import com.items.bim.ui.scrimDarkHighContrast
import com.items.bim.ui.scrimDarkMediumContrast
import com.items.bim.ui.scrimLight
import com.items.bim.ui.scrimLightHighContrast
import com.items.bim.ui.scrimLightMediumContrast
import com.items.bim.ui.secondaryContainerDark
import com.items.bim.ui.secondaryContainerDarkHighContrast
import com.items.bim.ui.secondaryContainerDarkMediumContrast
import com.items.bim.ui.secondaryContainerLight
import com.items.bim.ui.secondaryContainerLightHighContrast
import com.items.bim.ui.secondaryContainerLightMediumContrast
import com.items.bim.ui.secondaryDark
import com.items.bim.ui.secondaryDarkHighContrast
import com.items.bim.ui.secondaryDarkMediumContrast
import com.items.bim.ui.secondaryLight
import com.items.bim.ui.secondaryLightHighContrast
import com.items.bim.ui.secondaryLightMediumContrast
import com.items.bim.ui.surfaceDark
import com.items.bim.ui.surfaceDarkHighContrast
import com.items.bim.ui.surfaceDarkMediumContrast
import com.items.bim.ui.surfaceLight
import com.items.bim.ui.surfaceLightHighContrast
import com.items.bim.ui.surfaceLightMediumContrast
import com.items.bim.ui.surfaceVariantDark
import com.items.bim.ui.surfaceVariantDarkHighContrast
import com.items.bim.ui.surfaceVariantDarkMediumContrast
import com.items.bim.ui.surfaceVariantLight
import com.items.bim.ui.surfaceVariantLightHighContrast
import com.items.bim.ui.surfaceVariantLightMediumContrast
import com.items.bim.ui.tertiaryContainerDark
import com.items.bim.ui.tertiaryContainerDarkHighContrast
import com.items.bim.ui.tertiaryContainerDarkMediumContrast
import com.items.bim.ui.tertiaryContainerLight
import com.items.bim.ui.tertiaryContainerLightHighContrast
import com.items.bim.ui.tertiaryContainerLightMediumContrast
import com.items.bim.ui.tertiaryDark
import com.items.bim.ui.tertiaryDarkHighContrast
import com.items.bim.ui.tertiaryDarkMediumContrast
import com.items.bim.ui.tertiaryLight
import com.items.bim.ui.tertiaryLightHighContrast
import com.items.bim.ui.tertiaryLightMediumContrast
import io.github.oshai.kotlinlogging.KotlinLogging

private val lightScheme = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,
    error = errorLight,
    onError = onErrorLight,
    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,
    outline = outlineLight,
    outlineVariant = outlineVariantLight,
    scrim = scrimLight,
    inverseSurface = inverseSurfaceLight,
    inverseOnSurface = inverseOnSurfaceLight,
    inversePrimary = inversePrimaryLight,
)

private val darkScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    outline = outlineDark,
    outlineVariant = outlineVariantDark,
    scrim = scrimDark,
    inverseSurface = inverseSurfaceDark,
    inverseOnSurface = inverseOnSurfaceDark,
    inversePrimary = inversePrimaryDark,
)

private val mediumContrastLightColorScheme = lightColorScheme(
    primary = primaryLightMediumContrast,
    onPrimary = onPrimaryLightMediumContrast,
    primaryContainer = primaryContainerLightMediumContrast,
    onPrimaryContainer = onPrimaryContainerLightMediumContrast,
    secondary = secondaryLightMediumContrast,
    onSecondary = onSecondaryLightMediumContrast,
    secondaryContainer = secondaryContainerLightMediumContrast,
    onSecondaryContainer = onSecondaryContainerLightMediumContrast,
    tertiary = tertiaryLightMediumContrast,
    onTertiary = onTertiaryLightMediumContrast,
    tertiaryContainer = tertiaryContainerLightMediumContrast,
    onTertiaryContainer = onTertiaryContainerLightMediumContrast,
    error = errorLightMediumContrast,
    onError = onErrorLightMediumContrast,
    errorContainer = errorContainerLightMediumContrast,
    onErrorContainer = onErrorContainerLightMediumContrast,
    background = backgroundLightMediumContrast,
    onBackground = onBackgroundLightMediumContrast,
    surface = surfaceLightMediumContrast,
    onSurface = onSurfaceLightMediumContrast,
    surfaceVariant = surfaceVariantLightMediumContrast,
    onSurfaceVariant = onSurfaceVariantLightMediumContrast,
    outline = outlineLightMediumContrast,
    outlineVariant = outlineVariantLightMediumContrast,
    scrim = scrimLightMediumContrast,
    inverseSurface = inverseSurfaceLightMediumContrast,
    inverseOnSurface = inverseOnSurfaceLightMediumContrast,
    inversePrimary = inversePrimaryLightMediumContrast,


    )

private val highContrastLightColorScheme = lightColorScheme(
    primary = primaryLightHighContrast,
    onPrimary = onPrimaryLightHighContrast,
    primaryContainer = primaryContainerLightHighContrast,
    onPrimaryContainer = onPrimaryContainerLightHighContrast,
    secondary = secondaryLightHighContrast,
    onSecondary = onSecondaryLightHighContrast,
    secondaryContainer = secondaryContainerLightHighContrast,
    onSecondaryContainer = onSecondaryContainerLightHighContrast,
    tertiary = tertiaryLightHighContrast,
    onTertiary = onTertiaryLightHighContrast,
    tertiaryContainer = tertiaryContainerLightHighContrast,
    onTertiaryContainer = onTertiaryContainerLightHighContrast,
    error = errorLightHighContrast,
    onError = onErrorLightHighContrast,
    errorContainer = errorContainerLightHighContrast,
    onErrorContainer = onErrorContainerLightHighContrast,
    background = backgroundLightHighContrast,
    onBackground = onBackgroundLightHighContrast,
    surface = surfaceLightHighContrast,
    onSurface = onSurfaceLightHighContrast,
    surfaceVariant = surfaceVariantLightHighContrast,
    onSurfaceVariant = onSurfaceVariantLightHighContrast,
    outline = outlineLightHighContrast,
    outlineVariant = outlineVariantLightHighContrast,
    scrim = scrimLightHighContrast,
    inverseSurface = inverseSurfaceLightHighContrast,
    inverseOnSurface = inverseOnSurfaceLightHighContrast,
    inversePrimary = inversePrimaryLightHighContrast,


    )

private val mediumContrastDarkColorScheme = darkColorScheme(
    primary = primaryDarkMediumContrast,
    onPrimary = onPrimaryDarkMediumContrast,
    primaryContainer = primaryContainerDarkMediumContrast,
    onPrimaryContainer = onPrimaryContainerDarkMediumContrast,
    secondary = secondaryDarkMediumContrast,
    onSecondary = onSecondaryDarkMediumContrast,
    secondaryContainer = secondaryContainerDarkMediumContrast,
    onSecondaryContainer = onSecondaryContainerDarkMediumContrast,
    tertiary = tertiaryDarkMediumContrast,
    onTertiary = onTertiaryDarkMediumContrast,
    tertiaryContainer = tertiaryContainerDarkMediumContrast,
    onTertiaryContainer = onTertiaryContainerDarkMediumContrast,
    error = errorDarkMediumContrast,
    onError = onErrorDarkMediumContrast,
    errorContainer = errorContainerDarkMediumContrast,
    onErrorContainer = onErrorContainerDarkMediumContrast,
    background = backgroundDarkMediumContrast,
    onBackground = onBackgroundDarkMediumContrast,
    surface = surfaceDarkMediumContrast,
    onSurface = onSurfaceDarkMediumContrast,
    surfaceVariant = surfaceVariantDarkMediumContrast,
    onSurfaceVariant = onSurfaceVariantDarkMediumContrast,
    outline = outlineDarkMediumContrast,
    outlineVariant = outlineVariantDarkMediumContrast,
    scrim = scrimDarkMediumContrast,
    inverseSurface = inverseSurfaceDarkMediumContrast,
    inverseOnSurface = inverseOnSurfaceDarkMediumContrast,
    inversePrimary = inversePrimaryDarkMediumContrast,


    )

private val highContrastDarkColorScheme = darkColorScheme(
    primary = primaryDarkHighContrast,
    onPrimary = onPrimaryDarkHighContrast,
    primaryContainer = primaryContainerDarkHighContrast,
    onPrimaryContainer = onPrimaryContainerDarkHighContrast,
    secondary = secondaryDarkHighContrast,
    onSecondary = onSecondaryDarkHighContrast,
    secondaryContainer = secondaryContainerDarkHighContrast,
    onSecondaryContainer = onSecondaryContainerDarkHighContrast,
    tertiary = tertiaryDarkHighContrast,
    onTertiary = onTertiaryDarkHighContrast,
    tertiaryContainer = tertiaryContainerDarkHighContrast,
    onTertiaryContainer = onTertiaryContainerDarkHighContrast,
    error = errorDarkHighContrast,
    onError = onErrorDarkHighContrast,
    errorContainer = errorContainerDarkHighContrast,
    onErrorContainer = onErrorContainerDarkHighContrast,
    background = backgroundDarkHighContrast,
    onBackground = onBackgroundDarkHighContrast,
    surface = surfaceDarkHighContrast,
    onSurface = onSurfaceDarkHighContrast,
    surfaceVariant = surfaceVariantDarkHighContrast,
    onSurfaceVariant = onSurfaceVariantDarkHighContrast,
    outline = outlineDarkHighContrast,
    outlineVariant = outlineVariantDarkHighContrast,
    scrim = scrimDarkHighContrast,
    inverseSurface = inverseSurfaceDarkHighContrast,
    inverseOnSurface = inverseOnSurfaceDarkHighContrast,
    inversePrimary = inversePrimaryDarkHighContrast,


    )

@Immutable
data class ColorFamily(
    val color: Color,
    val onColor: Color,
    val colorContainer: Color,
    val onColorContainer: Color
)

val unspecified_scheme = ColorFamily(
    Color.Unspecified, Color.Unspecified, Color.Unspecified, Color.Unspecified
)

private val logger = KotlinLogging.logger {}

@RequiresApi(Build.VERSION_CODES.Q)
@SuppressLint("ResourceAsColor")
@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable() () -> Unit
) {
//  val colorScheme = when {
//      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//          logger.info { "sys" }
//          val context = LocalContext.current
//          if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//      }
//
//      darkTheme -> {
//          logger.info { "darkScheme" }
//          darkScheme
//      }
//      else -> {
//          logger.info { "lightScheme" }
//          lightScheme
//      }
//  }

    val colorScheme = lightScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            // window.statusBarColor = colorScheme.primary.toArgb()
            // 设置状态栏和导航栏透明
            window.statusBarColor = Color.Transparent.toArgb()
            window.navigationBarColor = Color.Transparent.toArgb()
            window.statusBarColor = Color.Transparent.toArgb()
            // 让内容可以显示到状态栏和导航栏下面区域
            WindowCompat.setDecorFitsSystemWindows(window, false)
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
            // 导航栏默认会有一层半透明遮罩， 这行代码，去掉默认半透明遮罩
            window.isNavigationBarContrastEnforced = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
    ) {
        CompositionLocalProvider(
            LocalIndication provides rememberRipple(color = Color(R.color.button_click)),
            content = content,
        )
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
@Preview(showBackground = true)
@Composable
fun ContrastAwareReplyThemeTest() {
    AppTheme() {
        Context(content = {
            Text(text = "test")
        })
    }
}


@Composable
fun ComposeTestTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = lightScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            (view.context as Activity).window.statusBarColor = Color.Transparent.toArgb() //透明
            ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}