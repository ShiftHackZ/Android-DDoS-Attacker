package com.shifthackz.android.attacker.presentation.widget

import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.shifthackz.android.attacker.R

class BandwidthGraphAdapter {

    private val pool = mutableListOf(
        0, 0, 0, 0, 0,
        0, 0, 0, 0, 0,
        0, 0, 0, 0, 0,
    )

    private var lineChart: LineChart? = null

    fun setLineChart(chart: LineChart) {
        this.lineChart = chart
        decorateChart()
        invalidate()
    }

    fun onNewValue(value: Int) {
        pool.removeAt(0)
        pool.add(value)
        lineChart?.isVisible = true
        invalidate()
    }

    private fun invalidate() {
        val values = arrayListOf<Entry>()
        pool.forEachIndexed { index, value ->
            values.add(Entry(index * 1f, value * 1f))
        }
        val dataSet = LineDataSet(values, "").apply {
            setDrawCircles(false)
            setDrawValues(false)
            setDrawFilled(true)
            lineChart?.context?.let { ctx ->
                fillDrawable = ContextCompat.getDrawable(ctx, R.drawable.bg_chart_area)
                color = ContextCompat.getColor(ctx, R.color.colorChartLine)
            }
            mode = LineDataSet.Mode.CUBIC_BEZIER
            lineWidth = 2f
        }
        val data = LineData(listOf(dataSet))
        lineChart?.data = data
        lineChart?.invalidate()
        lineChart?.requestLayout()
    }

    private fun decorateChart() = lineChart?.apply {
        legend.isEnabled = false
        setViewPortOffsets(0f, 0f, 0f, 0f)
        setTouchEnabled(false)
        description = Description().apply {
            text = ""
        }
        xAxis.apply {
            isEnabled = false
            setDrawGridLines(false)
        }
        axisLeft.apply {
            isEnabled = false
            setDrawGridLines(false)
        }
        axisRight.apply {
            isEnabled = false
            setDrawGridLines(false)
        }
    }
}