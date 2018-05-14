package com.example.pelvicfloortraining;

import android.arch.persistence.room.Room;
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

import org.apache.commons.codec.binary.Hex;

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
    private TextView Currentpressure;
    private TextView Maxpressure;
    boolean pressure;

    long timeInMilliseconds = 0L; //SystemClock.uptimeMillis() - startTime;

   TrainingLog log= new TrainingLog();

    long timeSwapBuff = 0L;

    long updatedTime = 0L;
    private Handler customHandler = new Handler();


    BeanDiscoveryListener listener = new BeanDiscoveryListener() {
        @Override
        public void onBeanDiscovered(Bean bean, int rssi) {
            beans.add(bean);
            Log.d("Bluetooth3", String.valueOf(bean.getDevice().getName()));
            Log.d("Address3", String.valueOf(bean.getDevice().getAddress()));
        }

        @Override
        public void onDiscoveryComplete() {
            // This is called when the scan times out, defined by the .setScanTimeout(int seconds) method

            for (Bean bean : beans) {
                Log.d("Bluetooth4", String.valueOf(bean.getDevice().getName()));
                //System.out.println(bean.getDevice().getName());   // "Bean"              (example)
                Log.d("Address4", String.valueOf(bean.getDevice().getAddress()));
                // System.out.println(bean.getDevice().getAddress());    // "B4:99:4C:1E:BC:75" (example);
            }
        }
    };

    BeanListener beanListener = new BeanListener() {
        @Override
        public void onConnected() {

            System.out.println("connected to Bean!");
            Context context = getApplicationContext();

            BeanManager.getInstance().cancelDiscovery();

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
                Context context = getApplicationContext();
                CharSequence text = "Could not connect to Bean!";
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
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
            System.out.println(String.valueOf(error));
            Context context = getApplicationContext();
            //CharSequence text = error;
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, String.valueOf(error), duration);
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
            Log.d(TAG, "onSerialMessageReceived >> " + myBean.describe());
            char[] chars = Hex.encodeHex(data);
            Log.d(TAG, "data: " + String.valueOf(chars));
            int size = data.length;
            Log.d(TAG, "number of bytes: " + size);
            Log.d(TAG, "byte 1 (should be 82):" + Integer.toHexString(data[0] & 0xFF));
            byte digitalPins = data[1];
            String s1 = String.format("%8s", Integer.toBinaryString(digitalPins & 0xFF)).replace(' ', '0');
            Log.d(TAG, "byte 2  Pins[xx543210]:" + s1);
            byte a1L = data[2];
            byte a1H = data[3];
            byte a2L = data[4];
            byte a2H = data[5];
            int a1 = a1H * 256 + a1L;
            int a2 = a2H * 256 + a2L;
            Log.d(TAG, "A1 values:" + a1);
            double voltage = 0.004147+(-0.13188*a1);
            double transferfunction = 0.1+0.66667*voltage;
            Toast.makeText(getApplicationContext(), "Pressure:"+transferfunction, Toast.LENGTH_LONG).show();
            Currentpressure.setText(""+a2);
            Maxpressure.setText(""+transferfunction);
            Log.d(TAG, "A2 values:" + a2);
        }

    };
    String Bean_add;
    AppDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);
        BeanManager.getInstance().getBeans().addAll(beans);
        BeanManager.getInstance().startDiscovery(listener);
        BeanManager.getInstance().setScanTimeout(15);
        getIncomingIntent();
        Connnectbtn = findViewById(R.id.Connectbtn);
        Connnectbtn.setVisibility(View.VISIBLE);
        startButton = findViewById(R.id.Startbtn);
        pauseButton = findViewById(R.id.Pausebtn);
        stopButton = findViewById(R.id.Stopbtn);
        timevalue = findViewById(R.id.timevalue);
        Currentpressure = findViewById(R.id.Currentpressure);
        Maxpressure = findViewById(R.id.Maxpressure);
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "Training")
                .allowMainThreadQueries()
                .build();

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
        Log.d(TAG, "Number of beans:" + beans.size());
        for (int i = 0; i < beans.size(); i++) {
            if (beans.get(i).getDevice().getAddress().equals(Bean_Add)) {
                Log.d(TAG, "Connect_Bean: connecting");
                Toast.makeText(this, "Found"+Bean_Add, Toast.LENGTH_LONG).show();
                myBean = beans.get(i);
                Log.d(TAG, String.valueOf(beans.get(i)));
                myBean.connect(this, beanListener);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        BeanManager.getInstance().startDiscovery(listener);
        BeanManager.getInstance().setScanTimeout(15);
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
        Log.d("Onstart_Btn ","Button Start clicked");
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
            getMessage();
            customHandler.postDelayed(this, 0);

        }
    };

    public void Onstop_btn(View view) {
        //TrainingLog trainingLog= new TrainingLog(timevalue.getText().toString(),Currentpressure.getText().toString(), Maxpressure.getText().toString());
        //db.trainingDao().insert(trainingLog);
        timeSwapBuff = 0L;
        customHandler.removeCallbacks(updateTimerThread);
        timevalue.setText("" + 0 + ":"
                + String.format("%02d", 0) + ":"
                + String.format("%03d", 0));
        stopButton.setVisibility(View.GONE);
        startButton.setVisibility(View.VISIBLE);

    }


    private void getMessage(){
        Handler handler = new Handler();
        int delay = 1000; //milliseconds

        handler.postDelayed(new Runnable(){
            public void run(){
                //do something
                final String TAG="BEAN";
                final String BeanInfo=myBean.describe();
                byte requestCode=0x02;
                byte[] requestMsg= {requestCode};
                myBean.sendSerialMessage(requestMsg);
                handler.postDelayed(this, delay);
            }
        }, delay);

    }

}






