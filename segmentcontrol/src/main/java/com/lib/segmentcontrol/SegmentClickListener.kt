package com.lib.segmentcontrol

/**
 * Single click listener for a segment
 */
fun interface SegmentClickListener {
    fun onSegmentSelected(selectedIndex: Int)
}