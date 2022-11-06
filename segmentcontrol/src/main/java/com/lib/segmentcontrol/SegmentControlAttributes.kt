/*
 * File created by Vraj Shah
 */

package com.lib.segmentcontrol

import android.graphics.Color
import android.graphics.Typeface

/**
 * Data class representing the attributes provided to [SegmentControlView]
 */
data class SegmentControlAttributes(
    val defaultBackgroundColor: Int = Color.BLACK,
    val selectedBackgroundColor: Int = Color.WHITE,
    val textColor: Int = Color.WHITE,
    val selectedTextColor: Int = Color.BLACK,
    val textSize: Float = 0F,
    val textTypeface: Typeface? = null,
    val strokeWidth: Int = 0,
    val strokeColor: Int = Color.WHITE,
    val cornerRadius: Float = 0F
)
