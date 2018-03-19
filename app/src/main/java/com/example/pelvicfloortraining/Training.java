package com.example.pelvicfloortraining;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


public class Training extends AppCompatActivity {


    private static final String TAG = "Training";
    BluetoothAdapter mBluetoothAdapter;


    private final BroadcastReceiver mBroadcastRecevier1 = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(mBluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, mBluetoothAdapter.ERROR);

                switch (state) {
                    case BluetoothAdapter.STATE_OFF:
                        Log.d(TAG, "anReceive: STATE OFF");
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        Log.d(TAG, "mBroadcastReceiver1: STATE TURINING OOF");
                        break;
                    case BluetoothAdapter.STATE_ON:
                        Log.d(TAG, "mBroadcastReceiver1: STATE ON");
                        break;
                    case BluetoothAdapter.STATE_TURNING_ON:
                        Log.d(TAG, "mBroadcastReceiver1: TURNING ON");
                        break;
                }
            }
        }

    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastRecevier1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);
        Button btnONOFF = (Button) findViewById(R.id.btnONOFF);



        btnONOFF.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                enableDisablBT();
            };
        });


    }

    public void enableDisablBT() {

        if (mBluetoothAdapter == null) {
            Log.d(TAG, "enabledisableBT: Does not have BT caoabilities");
        }
        else if(!mBluetoothAdapter.isEnabled()){
            Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enableBTIntent);
            IntentFilter BTIntent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
            registerReceiver(mBroadcastRecevier1, BTIntent);
        }

        else if(mBluetoothAdapter.isEnabled()){
            mBluetoothAdapter.disable();

            IntentFilter BTIntent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
            registerReceiver(mBroadcastRecevier1, BTIntent);
        }
    }


}
