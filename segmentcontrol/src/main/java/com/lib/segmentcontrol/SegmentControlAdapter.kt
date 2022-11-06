/*
 * File created by Vraj Shah
 */

package com.lib.segmentcontrol

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lib.segmentcontrol.databinding.ItemSegmentControlBinding

/**
 * Adapter responsible for managing segments in [SegmentControlView]
 */
class SegmentControlAdapter(
    private val mSegmentList: MutableList<Segment>,
    private val mOnSegmentClickListener: SegmentClickListener
) : RecyclerView.Adapter<SegmentControlAdapter.SegmentControlHolder>() {

    /**
     * Data class representing a single segment item
     */
    data class Segment(
        val label: String,
        var isSelected: Boolean = false
    )

    private var mSegmentControlAttributes = SegmentControlAttributes()
    private var mSelectedIndex = DEFAULT_SELECTED_INDEX

    inner class SegmentControlHolder(
        private val mViewBinding: ItemSegmentControlBinding
    ) : RecyclerView.ViewHolder(mViewBinding.root) {

        fun bind(segment: Segment, position: Int) {
            mViewBinding.apply {
                this.segment = segment
                attributes = mSegmentControlAttributes
                executePendingBindings()
                itemView.apply {
//                    background = GradientDrawable().apply {
//                        setColor(getBackgroundColor(segment.isSelected))
//                        cornerRadius = this@with.cornerRadius
//                    }
                    setOnClickListener { setSelectedIndex(position) }
                }
//                text.apply {
//                    setTextColor(getTextColor(segment.isSelected))
//                    typeface = textTypeface
//                    textSize = this@with.textSize
//                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SegmentControlHolder {
        val binding =
            ItemSegmentControlBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SegmentControlHolder(binding)
    }

    override fun onBindViewHolder(holder: SegmentControlHolder, position: Int) {
        holder.bind(mSegmentList[position], position)
    }

    override fun getItemCount(): Int = mSegmentList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setSegmentControlAttributes(segmentControlAttributes: SegmentControlAttributes) {
        mSegmentControlAttributes = segmentControlAttributes
        notifyDataSetChanged()
    }

    fun addSegments(segmentList: Array<String>) {
        mSegmentList.clear()
        mSegmentList.addAll(segmentList.map { Segment((it)) })
        setSelectedIndex(mSelectedIndex)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setSelectedIndex(selectedIndex: Int) {
        mSelectedIndex = selectedIndex
        mSegmentList.forEachIndexed { index, segment ->
            segment.isSelected = index == selectedIndex
        }
        mOnSegmentClickListener.onSegmentSelected(selectedIndex)
        notifyDataSetChanged()
    }

    private fun SegmentControlAttributes.getBackgroundColor(isSelected: Boolean) =
        if (isSelected) selectedBackgroundColor else defaultBackgroundColor

    private fun SegmentControlAttributes.getTextColor(isSelected: Boolean) =
        if (isSelected) selectedTextColor else textColor

    companion object {
        private const val DEFAULT_SELECTED_INDEX = 0
    }
}