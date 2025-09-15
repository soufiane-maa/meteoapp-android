package com.babel.meteoapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.Typography
import androidx.compose.material3.Shapes
import androidx.compose.material3.ShapeDefaults
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.dimensionResource
import com.babel.meteoapp.R
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalContext
import android.app.Activity

private val DarkColorScheme = darkColorScheme(
    primary = BluePrimaryDark,
    onPrimary = BlueOnPrimaryDark,
    primaryContainer = BluePrimaryContainerDark,
    secondary = TealSecondaryDark,
    onSecondary = TealOnSecondaryDark,
    tertiary = OrangeTertiaryDark,
    onTertiary = OrangeOnTertiaryDark,
    surface = SurfaceDark,
    onSurface = OnSurfaceDark,
    background = BackgroundDark,
    onBackground = OnBackgroundDark,
    error = ErrorDark,
    onError = OnErrorDark
)

private val LightColorScheme = lightColorScheme(
    primary = BluePrimary,
    onPrimary = BlueOnPrimary,
    primaryContainer = BluePrimaryContainer,
    secondary = TealSecondary,
    onSecondary = TealOnSecondary,
    tertiary = OrangeTertiary,
    onTertiary = OrangeOnTertiary,
    surface = Surface,
    onSurface = OnSurface,
    background = Background,
    onBackground = OnBackground,
    error = Error,
    onError = OnError
)

@Composable
private fun AppTypography(): Typography {
    return Typography(
        displayLarge = TextStyle(
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Bold,
            fontSize = dimensionResource(R.dimen.text_size_headline).value.sp
        ),
        titleLarge = TextStyle(
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.SemiBold,
            fontSize = dimensionResource(R.dimen.text_size_xl).value.sp
        ),
        bodyLarge = TextStyle(
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Normal,
            fontSize = dimensionResource(R.dimen.text_size_lg).value.sp,
            lineHeight = dimensionResource(R.dimen.text_size_xl).value.sp
        ),
        labelMedium = TextStyle(
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Medium,
            fontSize = dimensionResource(R.dimen.text_size_sm).value.sp
        )
    )
}

private val AppShapes = Shapes(
    extraSmall = ShapeDefaults.ExtraSmall,
    small = ShapeDefaults.Small,
    medium = ShapeDefaults.Medium,
    large = ShapeDefaults.Large,
    extraLarge = ShapeDefaults.ExtraLarge
)

/**
 * Local provider for WindowSizeClass to enable responsive design throughout the app
 * This allows any composable to access the current window size class and adapt accordingly
 */
val LocalWindowSizeClass = compositionLocalOf<WindowSizeClass> { 
    error("WindowSizeClass not provided") 
}

/**
 * Main theme composable that provides responsive design support
 * Calculates window size class and makes it available throughout the app
 * 
 * Features:
 * - Automatic window size detection
 * - Fallback for non-Activity contexts
 * - Local provider for responsive utilities
 */
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun MeteoAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val activity = context as? Activity
    val windowSizeClass = if (activity != null) {
        calculateWindowSizeClass(activity)
    } else {
        // Fallback for non-Activity contexts using screen dimensions
        WindowSizeClass.calculateFromSize(
            androidx.compose.ui.unit.DpSize(
                width = LocalConfiguration.current.screenWidthDp.dp,
                height = LocalConfiguration.current.screenHeightDp.dp
            )
        )
    }
    
    // Provide window size class to all child composables
    CompositionLocalProvider(LocalWindowSizeClass provides windowSizeClass) {
        val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
        MaterialTheme(
            colorScheme = colorScheme,
            typography = AppTypography(),
            shapes = AppShapes,
            content = content
        )
    }
}