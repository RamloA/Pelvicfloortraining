package com.example.pelvicfloortraining;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

public class Workoutfragment extends Fragment {
    LineGraphSeries<DataPoint> series;
    public  View view;
    AppDatabase db;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.frament_workout, container, false);

        try {
            graph();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return view;
    }

    private void graph() throws ParseException {
        db = Room.databaseBuilder(getContext(), AppDatabase.class, "Training")
                .allowMainThreadQueries()
                .build();
        if(db.trainingDao().getLog().size()!=0)
        {
            String x;
            int y;
            GraphView graph;
            graph = view.findViewById(R.id.graph_workout);
            series = new LineGraphSeries<>();
            for (int i=0; i<db.trainingDao().getLog().size(); i++){
                x=db.trainingDao().getLog().get(i).getDato();

                Date date = new SimpleDateFormat("dd.MM.yy").parse(x);
               // DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                //Date date = format.parse(x);
                //Date date1 = new SimpleDateFormat("dd.MM.yyyy").format(date);
                Toast.makeText(getContext(), date.toString(), Toast.LENGTH_SHORT).show();
                y=db.trainingDao().getLog().get(i).getMax();
               series.appendData(new DataPoint(date, y), true, db.trainingDao().getLog().size());
            }
           graph.addSeries(series);

        }
    }
}
