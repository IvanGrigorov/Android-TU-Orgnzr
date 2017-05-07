package com.androidprojects.tudevs.tu_orgnzr.Settings;


import android.graphics.Color;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.AbstractList;
import java.util.AbstractMap;

/**
 * Created by Ivan Grigorov on 13/08/2016.
 * Contact with me at ivangrigorov9 at gmail.com
 * <p/>
 * Graph Details and rendering engine provided by
 * Jonas Gehring - http://www.android-graphview.org//
 */

public class GraphDesigner {

    public static void designGraph(GraphView graphView, AbstractList<AbstractMap.SimpleEntry<String, DataPoint[]>> series, String[] horizontalLabel) {
        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graphView);
        staticLabelsFormatter.setHorizontalLabels(horizontalLabel);
        graphView.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
        LineGraphSeries<DataPoint> secondSeries = new LineGraphSeries<>(series.get(1).getValue());
        LineGraphSeries<DataPoint> firstSeries = new LineGraphSeries<>(series.get(0).getValue());
        graphView.addSeries(firstSeries);
        firstSeries.setThickness(3);
        secondSeries.setThickness(3);
        secondSeries.setColor(Color.RED);
        secondSeries.setAnimated(true);
        graphView.addSeries(secondSeries);
        graphView.animate();
        graphView.setTitle("Temperature");
        graphView.getGridLabelRenderer().setVerticalLabelsSecondScaleColor(Color.RED);
        graphView.getGridLabelRenderer().setTextSize(12f);
        graphView.getGridLabelRenderer().reloadStyles();

    }
}