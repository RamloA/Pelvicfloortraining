package com.example.pelvicfloortraining;

import android.arch.persistence.room.Room;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class Info_fluid extends AppCompatActivity {
    int position;
    AppDatabase db;
    TextView fluid, urination, leakage;
    private static final String TAG = "Info_fluid";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_fluid);
        fluid = findViewById(R.id.fluidintake_show);
        urination = findViewById(R.id.urination_show);
        leakage = findViewById(R.id.Leakage_show);
        db = Room.databaseBuilder(this, AppDatabase.class, "Fluidintake")
                .allowMainThreadQueries()
                .build();
        getIncomingIntent();
    }
    private void getIncomingIntent() {
        Log.d(TAG, "getIncomingIntent: Checking incoming intent");
        if (getIntent().hasExtra("Pos")) {
            Log.d(TAG, "getIncomingIntent: found Intent");
            String pos= getIntent().getStringExtra("Pos");
            position = new Integer(pos.toString());
            set(position);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void set(int position){
                fluid.setText(""+db.fluidintakeDao().getAllFluidintake().get(position).getFluidintake());
                urination.setText(""+db.fluidintakeDao().getAllFluidintake().get(position).getUrination_level());
                leakage.setText(db.fluidintakeDao().getAllFluidintake().get(position).getLeakage());
    }
}
