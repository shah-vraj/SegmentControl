package com.example.segmentcontrol

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.segmentcontrol.databinding.ActivityDemoBinding

class DemoActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityDemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityDemoBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.segmentControl.apply {
            setSegments(arrayOf("1", "Vraj", "Sunilkumar", "Shah"))
            setSelectedIndex(1)
            setOnSegmentSelectedListener {
                Log.d("TestLog", "Selected segment at index: $it")
            }
        }
    }
}