package com.example.pelvicfloortraining;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.punchthrough.bean.sdk.Bean;
import com.punchthrough.bean.sdk.BeanDiscoveryListener;
import com.punchthrough.bean.sdk.BeanListener;
import com.punchthrough.bean.sdk.BeanManager;
import com.punchthrough.bean.sdk.message.BeanError;
import com.punchthrough.bean.sdk.message.Callback;
import com.punchthrough.bean.sdk.message.DeviceInfo;
import com.punchthrough.bean.sdk.message.ScratchBank;

import java.util.ArrayList;
import java.util.List;


public class Training extends AppCompatActivity {
    final List<Bean> beans = new ArrayList<>();
    private static final String TAG = "Training";
    Bean myBean;
    Button Connnectbtn;
    private Button startButton;
    private Button pauseButton;
    private Button stopButton;
    private TextView timevalue;
    private long startTime = 0L;

    long timeInMilliseconds = 0L; //SystemClock.uptimeMillis() - startTime;


    long timeSwapBuff = 0L;

    long updatedTime = 0L;
    private Handler customHandler = new Handler();


    BeanDiscoveryListener listener = new BeanDiscoveryListener() {
        @Override
        public void onBeanDiscovered(Bean bean, int rssi) {
            beans.add(bean);
            Log.d("Bluetooth", String.valueOf(bean.getDevice().getName()));
            Log.d("Address", String.valueOf(bean.getDevice().getAddress()));
        }

        @Override
        public void onDiscoveryComplete() {
            // This is called when the scan times out, defined by the .setScanTimeout(int seconds) method

            for (Bean bean : beans) {
                Log.d("Bluetooth", String.valueOf(bean.getDevice().getName()));
                //System.out.println(bean.getDevice().getName());   // "Bean"              (example)
                Log.d("Address", String.valueOf(bean.getDevice().getAddress()));
                // System.out.println(bean.getDevice().getAddress());    // "B4:99:4C:1E:BC:75" (example);
            }
        }
    };
    BeanListener beanListener = new BeanListener() {

        @Override
        public void onConnected() {

            System.out.println("connected to Bean!");
            Context context = getApplicationContext();
            CharSequence text = "connected to Bean!";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            myBean.readDeviceInfo(new Callback<DeviceInfo>() {
                @Override
                public void onResult(DeviceInfo deviceInfo) {
                    System.out.println(deviceInfo.hardwareVersion());
                    System.out.println(deviceInfo.firmwareVersion());
                    System.out.println(deviceInfo.softwareVersion());
                }
            });
            Connnectbtn.setVisibility(View.GONE);
            startButton.setVisibility(View.VISIBLE);
            pauseButton.setVisibility(View.VISIBLE);
        }

        @Override
        public void onConnectionFailed() {
            if (!myBean.isConnected()) {
                Context context = getApplicationContext();
                CharSequence text = "Could not connect to Bean!";
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }

        }

        @Override
        public void onDisconnected() {
            Context context = getApplicationContext();
            CharSequence text = "Disconnected to Bean!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

        @Override
        public void onError(BeanError error) {
            Context context = getApplicationContext();
            CharSequence text = "Bean Error";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

        @Override
        public void onReadRemoteRssi(int rssi) {
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, "Signal Strength " + rssi, duration);
            toast.show();
        }

        @Override
        public void onScratchValueChanged(ScratchBank bank, byte[] value) {

        }

        @Override
        public void onSerialMessageReceived(byte[] data) {

        }

    };
    String Bean_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);
        getIncomingIntent();
        Connnectbtn = findViewById(R.id.Connectbtn);
        Connnectbtn.setVisibility(View.VISIBLE);
        startButton = findViewById(R.id.Startbtn);
        pauseButton = findViewById(R.id.Pausebtn);
        stopButton = findViewById(R.id.Stopbtn);
        timevalue = findViewById(R.id.timevalue);
    }

    private void getIncomingIntent() {
        Log.d(TAG, "getIncomingIntent: Checking incoming intent");
        if (getIntent().hasExtra("Bean_address")) {
            Log.d(TAG, "getIncomingIntent: found Intent");
            Bean_add = getIntent().getStringExtra("Bean_address");
        }
    }

    private void Connect_Bean(String Bean_Add) {
        Log.d(TAG, "Connect_Bean: Connecting to bean");
        for (int i = 0; i < beans.size(); i++) {
            if (beans.get(i).getDevice().getAddress().equals(Bean_Add)) {
                Log.d(TAG, "Connect_Bean: connecting");
                myBean = beans.get(i);
                Log.d(TAG, String.valueOf(beans.get(i)));
                myBean.connect(this, beanListener);
                BeanManager.getInstance().cancelDiscovery();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        BeanManager.getInstance().setScanTimeout(50);
        BeanManager.getInstance().startDiscovery(listener);
    }

    public void On_connect(View view) {
        Log.d(TAG, "connect: button pushed");
        Connect_Bean(Bean_add);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myBean.disconnect();
    }

    public void OnStart_Btn(View view) {
        startTime = SystemClock.uptimeMillis();
        customHandler.postDelayed(updateTimerThread, 0);
        startButton.setVisibility(View.GONE);
        stopButton.setVisibility(View.VISIBLE);
    }

    public void OnPause_Btn(View view) {
        timeSwapBuff += timeInMilliseconds;
        customHandler.removeCallbacks(updateTimerThread);
        stopButton.setVisibility(View.GONE);
        startButton.setVisibility(View.VISIBLE);
    }

    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            timeInMilliseconds = 0L;//SystemClock.uptimeMillis() - startTime;
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updatedTime = timeSwapBuff + timeInMilliseconds;

            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (updatedTime % 1000);
            timevalue.setText("" + mins + ":"
                    + String.format("%02d", secs) + ":"
                    + String.format("%03d", milliseconds));
            customHandler.postDelayed(this, 0);
        }
    };

    public void Onstop_btn (View view){
        timeSwapBuff = 0L;
        customHandler.removeCallbacks(updateTimerThread);
        timevalue.setText("" + 0 + ":"
                + String.format("%02d", 0) + ":"
                + String.format("%03d", 0));
        stopButton.setVisibility(View.GONE);
        startButton.setVisibility(View.VISIBLE);

    }
}







