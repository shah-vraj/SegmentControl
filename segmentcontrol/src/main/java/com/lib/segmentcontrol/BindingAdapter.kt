package com.lib.segmentcontrol

import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.databinding.BindingAdapter

object BindingAdapter {

    @BindingAdapter("isVisibleInvisible")
    @JvmStatic
    fun View.setIsVisibleInvisible(isVisible: Boolean) {
        visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
    }

    @BindingAdapter("selectedCornerRadius", "selectedBackgroundColor", requireAll = true)
    @JvmStatic
    fun View.setCornerRadiusAndBackgroundColor(cornerRadius: Float, backgroundColor: Int) {
        background = GradientDrawable().apply {
            setColor(backgroundColor)
            this.cornerRadius = cornerRadius
        }
    }
}