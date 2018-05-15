package com.example.pelvicfloortraining;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import com.punchthrough.bean.sdk.Bean;
import com.punchthrough.bean.sdk.BeanDiscoveryListener;
import com.punchthrough.bean.sdk.BeanManager;


import java.util.ArrayList;
import java.util.List;

public class Bluetooth extends AppCompatActivity {
    final List<Bean> beans = new ArrayList<>();
    LocationManager locationManager;

    private  ArrayList<String> SS = new ArrayList<>();

    //Recycleview (slå sammen med ovenover)
    private ArrayList<String> mBeanAdd = new ArrayList<>();
    private ArrayList<String> mBeanSS = new ArrayList<>();

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("")
                        .setMessage("")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(Bluetooth.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        //Request location updates:
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }

            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        int REQUEST_ENABLE_BT = 1;
        startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        checkLocationPermission();
    }

    @Override
    protected void onStart() {
        super.onStart();
        BeanManager.getInstance().startDiscovery(listener);
        BeanManager.getInstance().setScanTimeout(15);

    }

    BeanDiscoveryListener listener = new BeanDiscoveryListener() {
        @Override
        public void onBeanDiscovered(Bean bean, int rssi) {
            beans.add(bean);
            Log.d("Bluetooth", String.valueOf(bean.getDevice().getName()));
            Log.d("Address", String.valueOf(bean.getDevice().getAddress()));
            SS.add(String.valueOf(rssi));
        }

        @Override
        public void onDiscoveryComplete() {
            // This is called when the scan times out, defined by the .setScanTimeout(int seconds) method
            // Printer det samme 2 gange
            for (Bean bean : beans) {
                Log.d("Bluetooth2", String.valueOf(bean.getDevice().getName()));
                //System.out.println(bean.getDevice().getName());   // "Bean"              (example)
                Log.d("Address2", String.valueOf(bean.getDevice().getAddress()));
                // System.out.println(bean.getDevice().getAddress());    // "B4:99:4C:1E:BC:75" (example);
            }
        }

    };

    private void initBeans(){
        //Error ses i logcat pga. vi ikke kalder den i onStart
        for (int i=0; i<beans.size(); i++){
            if(i==0) {
                mBeanAdd.add(String.valueOf(beans.get(i).getDevice().getAddress()));
                mBeanSS.add(SS.get(i));
            }
            if(i>0){
                if(beans.get(i)!=beans.get(i-1)){
                    mBeanAdd.add(String.valueOf(beans.get(i).getDevice().getAddress()));
                    mBeanSS.add(SS.get(i));
                }
            }
        }
        initAdapter();

    }

    private void  initAdapter(){
        RecyclerView recyclerView = findViewById(R.id.Recyclerview);
        RecyclerviewAdapter adapter = new RecyclerviewAdapter(this, mBeanAdd, mBeanSS);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if(mBeanAdd.size()!=0) {
            BeanManager.getInstance().cancelDiscovery();
        }

    }

    public void On_Scan(View view) {
        initBeans();
    }

    @Override
    protected void onResume() {
        super.onResume();
        BeanManager.getInstance().startDiscovery(listener);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
