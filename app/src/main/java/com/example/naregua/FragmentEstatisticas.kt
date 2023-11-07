package com.example.naregua

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.naregua.databinding.FragmentEstatisticasBinding
import com.example.naregua.databinding.FragmentHomeEmpresaBinding
import org.eazegraph.lib.charts.ValueLineChart
import org.eazegraph.lib.models.ValueLinePoint
import org.eazegraph.lib.models.ValueLineSeries

class FragmentEstatisticas: Fragment() {
    private lateinit var binding: FragmentEstatisticasBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentEstatisticasBinding.inflate(inflater, container, false)

        val graphView: ValueLineChart = binding.cubiclinechart
        val series: ValueLineSeries = ValueLineSeries()
        val series2: ValueLineSeries = ValueLineSeries()

        series.color = Color.BLUE

        series.addPoint(ValueLinePoint("jan", 2.4f))
        series.addPoint(ValueLinePoint("Feb", 3.4f))
        series.addPoint(ValueLinePoint("Mar", .4f))
        series.addPoint(ValueLinePoint("Apr", 1.2f))
        series.addPoint(ValueLinePoint("Mai", 2.6f))
        series.addPoint(ValueLinePoint("Jun", 1.0f))
        series.addPoint(ValueLinePoint("Jul", 3.5f))
        series.addPoint(ValueLinePoint("Aug", 2.4f))
        series.addPoint(ValueLinePoint("Sep", 2.4f))
        series.addPoint(ValueLinePoint("Oct", 3.4f))
        series.addPoint(ValueLinePoint("Nov", .4f))
        series.addPoint(ValueLinePoint("Dec", 1.3f))

        graphView.addSeries(series)
        graphView.startAnimation()
        return binding.root
    }
}