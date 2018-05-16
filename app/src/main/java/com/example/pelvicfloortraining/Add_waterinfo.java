package com.example.pelvicfloortraining;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;


public class Add_waterinfo extends AppCompatActivity {
    private static final String TAG = "Add_waterinfo";
    DatePicker datePicker;
    TimePicker timePicker;
    EditText fluid, urination, leakage;
    Button next;
    int i;
    AppDatabase db;
    String dateString;
    String timeString;
    int month;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_waterinfo);
        datePicker = findViewById(R.id.datePicker);
        datePicker.setVisibility(View.VISIBLE);
        timePicker = findViewById(R.id.timePicker);
        fluid = findViewById(R.id.fluidintake);
        urination = findViewById(R.id.urination_amount);
        leakage = findViewById(R.id.ifleakge);
        i=0;
        next = findViewById(R.id.Next);
        db= Room.databaseBuilder(this, AppDatabase.class, "Fluidintake")
                .allowMainThreadQueries()
                .build();
    }

    public void onNext(View view) {
        switch (i){
            case 0:
                month=datePicker.getMonth()+1;
                dateString = ""+datePicker.getDayOfMonth()+"-"+month+"-"+datePicker.getYear();
                datePicker.setVisibility(View.GONE);
                timePicker.setVisibility(View.VISIBLE);
                i++;
                break;
            case 1:
                timePicker.setVisibility(View.GONE);
                timeString = String.valueOf((timePicker.getHour()))+"."+ String.valueOf(timePicker.getMinute());
                Log.d(TAG, "onNext:time"+timeString);
                fluid.setVisibility(View.VISIBLE);
                urination.setVisibility(View.VISIBLE);
                leakage.setVisibility(View.VISIBLE);
                next.setText("Save");
                i++;
                break;

            case 2:
                String leakage2 = String.valueOf(leakage.getText());
                db.fluidintakeDao().insertAll(new Fluidintake(dateString, timeString, fluid.getInputType(),urination.getInputType(), leakage2));
                Toast.makeText(this,"Information is saved",Toast.LENGTH_LONG).show();
                startActivity(new Intent(Add_waterinfo.this, dagbog.class));
                break;
        }
    }
}
