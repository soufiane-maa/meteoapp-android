package com.babel.meteoapp.ui.theme

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass

/**
 * Responsive design utilities for different screen sizes
 * Adapts UI components based on WindowSizeClass
 * 
 * This utility class provides methods to determine screen characteristics
 * and return appropriate values for spacing, sizing, and layout configuration
 * based on the current window size class.
 * 
 * Screen size categories:
 * - Compact: Phones (< 600dp width)
 * - Medium: Tablets (600dp - 840dp width) 
 * - Expanded: Large tablets (> 840dp width)
 */
object ResponsiveDesign {
    
    /**
     * Determine if the current screen is a tablet
     */
    fun isTablet(windowSizeClass: WindowSizeClass): Boolean {
        return windowSizeClass.widthSizeClass == WindowWidthSizeClass.Expanded ||
               windowSizeClass.widthSizeClass == WindowWidthSizeClass.Medium
    }
    
    /**
     * Determine if the current screen is compact (phone)
     */
    fun isCompact(windowSizeClass: WindowSizeClass): Boolean {
        return windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact
    }
    
    /**
     * Determine if the current screen is in landscape mode
     */
    fun isLandscape(windowSizeClass: WindowSizeClass): Boolean {
        return windowSizeClass.heightSizeClass == WindowHeightSizeClass.Compact
    }
    
    /**
     * Get the number of columns for grid layouts based on screen size
     */
    fun getColumnCount(windowSizeClass: WindowSizeClass): Int {
        return when {
            isTablet(windowSizeClass) -> 3
            isLandscape(windowSizeClass) -> 2
            else -> 1
        }
    }
    
    /**
     * Get the maximum content width based on screen size
     */
    fun getMaxContentWidth(windowSizeClass: WindowSizeClass): Float {
        return when {
            isTablet(windowSizeClass) -> 600f
            isLandscape(windowSizeClass) -> 400f
            else -> Float.MAX_VALUE
        }
    }
    
    /**
     * Get the appropriate padding based on screen size
     */
    fun getContentPadding(windowSizeClass: WindowSizeClass): Float {
        return when {
            isTablet(windowSizeClass) -> 32f
            isLandscape(windowSizeClass) -> 12f
            else -> 16f
        }
    }
    
    /**
     * Get the appropriate spacing between items based on screen size
     */
    fun getItemSpacing(windowSizeClass: WindowSizeClass): Float {
        return when {
            isTablet(windowSizeClass) -> 24f
            isLandscape(windowSizeClass) -> 8f
            else -> 12f
        }
    }
    
    /**
     * Get the appropriate icon size based on screen size
     */
    fun getIconSize(windowSizeClass: WindowSizeClass): Float {
        return when {
            isTablet(windowSizeClass) -> 48f
            isLandscape(windowSizeClass) -> 32f
            else -> 40f
        }
    }
    
    /**
     * Get the appropriate text size multiplier based on screen size
     */
    fun getTextSizeMultiplier(windowSizeClass: WindowSizeClass): Float {
        return when {
            isTablet(windowSizeClass) -> 1.2f
            isLandscape(windowSizeClass) -> 0.9f
            else -> 1.0f
        }
    }
}
