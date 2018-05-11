package com.example.pelvicfloortraining;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class Workoutfragment extends Fragment {
    LineGraphSeries<DataPoint> series;
    public  View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.frament_workout, container, false);
        graph();
        return view;
    }

    private void graph(){
        double y, x;
        GraphView graph;
        x=-4.0;
        graph = view.findViewById(R.id.graph_workout);
        series = new LineGraphSeries<>();
        for(int j=0; j<30; j++) {
            x = x + 1;
            y = Math.sin(x);
            series.appendData(new DataPoint(x, y), true, 30);
        }
        if(graph!=null) {
            graph.addSeries(series);

        }
    }
}
