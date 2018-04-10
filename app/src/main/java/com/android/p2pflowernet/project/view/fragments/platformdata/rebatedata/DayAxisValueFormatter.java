package com.android.p2pflowernet.project.view.fragments.platformdata.rebatedata;

import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.List;

/**
 * Created by philipp on 02/06/16.
 */
public class DayAxisValueFormatter implements IAxisValueFormatter {

    private BarLineChartBase<?> chart;
    private String flag;
    private List<String> names;

    public DayAxisValueFormatter(BarLineChartBase<?> chart, String flag, List<String> name) {
        this.chart = chart;
        this.flag = flag;
        this.names = name;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        int index = (int) value;
        return names.get(index);
    }
}
