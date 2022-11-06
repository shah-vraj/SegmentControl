/*
 * File created by Vraj Shah
 */

package com.lib.segmentcontrol

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.view.setPadding
import androidx.recyclerview.widget.RecyclerView
import com.lib.segmentcontrol.databinding.LayoutSegmentControlBinding

/**
 * Segment control view to show multiple segments in a linear manner
 */
class SegmentControlView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private var mSelectedIndex = 0
    private val mDefaultPadding = resources.getDimensionPixelSize(R.dimen.default_text_size)
    private var mBinding: LayoutSegmentControlBinding
    private lateinit var mOnSegmentClickListener: SegmentClickListener

    //region attributes
    var defaultBackgroundColor = Color.BLACK
        set(value) {
            field = value
            notifyAdapterOnAttributeChanged()
        }
    var selectedBackgroundColor = Color.WHITE
        set(value) {
            field = value
            notifyAdapterOnAttributeChanged()
        }
    var textColor = Color.WHITE
        set(value) {
            field = value
            notifyAdapterOnAttributeChanged()
        }
    var selectedTextColor = Color.BLACK
        set(value) {
            field = value
            notifyAdapterOnAttributeChanged()
        }
    var textSize = resources.getDimension(R.dimen.default_text_size)
        set(value) {
            field = value
            notifyAdapterOnAttributeChanged()
        }
    var textTypeface: Typeface? = null
        set(value) {
            field = value
            notifyAdapterOnAttributeChanged()
        }
    var strokeWidth = 0
        set(value) {
            field = value
            notifyAdapterOnAttributeChanged()
        }
    var strokeColor = Color.WHITE
        set(value) {
            field = value
            notifyAdapterOnAttributeChanged()
        }
    var cornerRadius = resources.getDimension(R.dimen.default_corner_radius)
        set(value) {
            field = value
            notifyAdapterOnAttributeChanged()
        }
    //end region

    private val mSegmentControlAdapter by lazy {
        SegmentControlAdapter(mutableListOf(), this::setOnSegmentSelected)
    }
    private val mBackground by lazy {
        GradientDrawable().apply {
            setColor(defaultBackgroundColor)
            setStroke(strokeWidth, strokeColor)
            cornerRadius = this@SegmentControlView.cornerRadius
        }
    }

    private val mSegmentControlAttributes: SegmentControlAttributes
        get() = SegmentControlAttributes(
            defaultBackgroundColor,
            selectedBackgroundColor,
            textColor,
            selectedTextColor,
            textSize,
            textTypeface,
            strokeWidth,
            strokeColor,
            cornerRadius
        )

    init {
        setAttributes(context, attrs)
        notifyAdapterOnAttributeChanged()
        mBinding = LayoutSegmentControlBinding.inflate(LayoutInflater.from(context), this, true)
        mBinding.recyclerView.apply {
            adapter = mSegmentControlAdapter
            itemAnimator = null
            background = mBackground
            updatePadding(mDefaultPadding + strokeWidth)
        }
    }

    fun setSegments(segmentList: Array<String>) {
        mSegmentControlAdapter.addSegments(segmentList)
    }

    fun setSelectedIndex(selectedIndex: Int) {
        mSegmentControlAdapter.setSelectedIndex(selectedIndex)
    }

    fun setOnSegmentSelectedListener(onSegmentClickListener: SegmentClickListener) {
        mOnSegmentClickListener = onSegmentClickListener
        onSegmentClickListener.onSegmentSelected(mSelectedIndex)
    }

    private fun setOnSegmentSelected(selectedIndex: Int) {
        mSelectedIndex = selectedIndex
        if (this::mOnSegmentClickListener.isInitialized) {
            mOnSegmentClickListener.onSegmentSelected(selectedIndex)
        }
    }

    private fun setAttributes(context: Context, attrs: AttributeSet?) {
        context.theme?.obtainStyledAttributes(attrs, R.styleable.SegmentControlAttributes, 0, 0)
            ?.apply {
                try {
                    defaultBackgroundColor =
                        getColor(R.styleable.SegmentControlAttributes_ss_defaultBackgroundColor, defaultBackgroundColor)
                    selectedBackgroundColor =
                        getColor(R.styleable.SegmentControlAttributes_ss_selectedBackgroundColor, selectedBackgroundColor)
                    textColor =
                        getColor(R.styleable.SegmentControlAttributes_ss_textColor, textColor)
                    selectedTextColor =
                        getColor(R.styleable.SegmentControlAttributes_ss_selectedTextColor, selectedTextColor)
                    textSize =
                        getDimension(R.styleable.SegmentControlAttributes_ss_textSize, textSize)
                    strokeWidth =
                        getInt(R.styleable.SegmentControlAttributes_ss_strokeWidth, strokeWidth)
                    strokeColor =
                        getColor(R.styleable.SegmentControlAttributes_ss_strokeColor, strokeColor)
                    cornerRadius =
                        getDimension(R.styleable.SegmentControlAttributes_ss_cornerRadius, cornerRadius)
                    val typeface = getString(R.styleable.SegmentControlAttributes_ss_textTypeface)
                    if (typeface != null && typeface.isNotEmpty()) {
                        try {
                            textTypeface = Typeface.createFromAsset(context.assets, typeface)
                        } catch (exception: Exception) {
                            Log.d(TAG, "Cannot find font: $typeface")
                        }
                    }
                } finally {
                    recycle()
                }
            }
    }

    private fun notifyAdapterOnAttributeChanged() {
        mSegmentControlAdapter.setSegmentControlAttributes(mSegmentControlAttributes)
    }

    private fun RecyclerView.updatePadding(padding: Int) {
        (layoutParams as? RecyclerView.LayoutParams)?.apply {
            setPadding(padding)
            this@updatePadding.layoutParams = this
        }
    }

    companion object {
        private val TAG = SegmentControlView::class.java.canonicalName
    }
}