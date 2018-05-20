package com.example.pelvicfloortraining;


import android.content.Intent;
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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class dagbog extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout mdrawLayout;
    private ActionBarDrawerToggle mtoggle;
    TextView Insert;
    Button fab;

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
        Insert = findViewById(R.id.Insert);
        fab=findViewById(R.id.myFAB);
        if(savedInstanceState==null){
            Fragment fragment=new Waterfragment();
            FragmentManager  fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragment_container,fragment);
            transaction.commit();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        if(mdrawLayout.isDrawerOpen(GravityCompat.START)){
            mdrawLayout.closeDrawer(GravityCompat.START);
        }
        else {
            startActivity(new Intent(dagbog.this, HomeActivity.class));
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
        if(item.getItemId()==R.id.Workout){
            fragment = new Workoutfragment();

        }
        else if (item.getItemId()==R.id.Waterlevel){
            fragment=new Waterfragment();
        }

        if (fragment!=null){
            FragmentManager  fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragment_container,fragment);
            transaction.commit();
        }
       mdrawLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    public void add_water (View View){
        Intent intent = new Intent(dagbog.this, Add_waterinfo.class);
        startActivity(intent);
    }

}


