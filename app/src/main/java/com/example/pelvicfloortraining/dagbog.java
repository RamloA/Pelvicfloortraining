package com.example.pelvicfloortraining;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;


public class dagbog extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout mdrawLayout;
    private ActionBarDrawerToggle mtoggle;
    LineGraphSeries<DataPoint> series;
    TextView Insert,Insert2;
    String date;
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
        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected); // will check all the items
       // AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "Training")
         //       .allowMainThreadQueries()
           //     .build();
        //Insert = findViewById(R.id.Insert);
         //date=db.trainingDao().getLog().getDato();
       // if (savedInstanceState==null){
           // Insert.setText(date);

        //}
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
        Fragment fragment=null;
        int i=0;
        if(item.getItemId()==R.id.Workout){
            fragment = new Workoutfragment();
             i=1;

        }
        else if (item.getItemId()==R.id.Waterlevel){
            fragment=new Waterfragment();
             i=2;
        }

        if (fragment!=null){
            FragmentManager  fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragment_container,fragment);
            transaction.commit();
            //graph(i);
        }


       mdrawLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    private void graph(int i){
        double y, x;
        GraphView graph=null;
        x=-4.0;
        if (i==1) {
            graph = findViewById(R.id.graph_workout);
        }
        else if (i==2){
            graph = findViewById(R.id.graph_water_level);
        }
        series = new LineGraphSeries<>();
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        /*for(int j=0; j<30; j++) {
            x = x + j;
            y = Math.sin(x);
            series.appendData(new DataPoint(x, y), true, 30);
        }*/
        if(graph!=null) {
            graph.addSeries(series);

        }
    }
}


