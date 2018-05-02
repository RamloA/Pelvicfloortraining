package com.example.pelvicfloortraining;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

/**
 * Created by Ellen on 07-03-2018.
 */

public class dagbog extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout mdrawLayout;
    private ActionBarDrawerToggle mtoggle;
    LineGraphSeries<DataPoint> series;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagbog);
        mdrawLayout = findViewById(R.id.draw_layout); //
        mtoggle = new ActionBarDrawerToggle(this, mdrawLayout, R.string.Open, R.string.Close); //create an action bar
        mdrawLayout.addDrawerListener(mtoggle); //listen if we open or close it
        mtoggle.syncState(); //
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //
        NavigationView navigationView = findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this); // will check all the items

       /* if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new Workoutfragment()).commit();
            navigationView.setCheckedItem(R.id.graph_workout);
        }*/

    }

    @Override
    public void onBackPressed() {
        if(mdrawLayout.isDrawerOpen(GravityCompat.START)){
            mdrawLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mtoggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.Workout){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new Workoutfragment()).commit();
            double y, x;
            x=0.0;
            GraphView graph = findViewById(R.id.graph_workout);
            series = new LineGraphSeries<>();
            for(int i=0; i<100; i++) {
                x = x + 0.1;
                y = Math.sin(1/x);
                series.appendData(new DataPoint(x, y), true, 100);
            }
            graph.addSeries(series);
        }
        else if (item.getItemId()==R.id.Waterlevel){
              Toast.makeText(this, "This is Water level", Toast.LENGTH_LONG);
                double y, x;
                x=0.0;
                GraphView graph = findViewById(R.id.graph_water_level);
                series = new LineGraphSeries<>();
                for(int i=0; i<100; i++) {
                    x = x + 0.1;
                    y = Math.sin(x);
                    series.appendData(new DataPoint(x, y), true, 100);
                }
                graph.addSeries(series);
        }
       // mdrawLayout.closeDrawer(GravityCompat.START);
        return true;
    }


}
