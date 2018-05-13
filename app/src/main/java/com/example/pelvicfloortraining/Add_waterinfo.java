package com.example.pelvicfloortraining;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Add_waterinfo extends AppCompatActivity {
    private static final String TAG = "Add_waterinfo";
    DatePicker datePicker;
    TimePicker timePicker;
    EditText fluid, urination, leakage;
    Button next;
    int i;
    AppDatabase db;
    Date date1;
    SimpleDateFormat sdf;
    String dateString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_waterinfo);
        datePicker = findViewById(R.id.datePicker);
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        datePicker.updateDate(mYear,mMonth, mDay);
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
                date1= new Date (datePicker.getDayOfMonth(), datePicker.getMonth(), datePicker.getYear());
                sdf = new SimpleDateFormat("dd-MM-yyyy");
                dateString = sdf.format(date1);
                Log.d(TAG, "onNext:"+ dateString);datePicker.setVisibility(View.GONE);
                timePicker.setVisibility(View.VISIBLE);
                i++;
                break;
            case 1:
                timePicker.setVisibility(View.GONE);
                fluid.setVisibility(View.VISIBLE);
                urination.setVisibility(View.VISIBLE);
                leakage.setVisibility(View.VISIBLE);
                next.setText("Save");
                i++;
                break;
            case 2:
                db.fluidintakeDao().insertAll(new Fluidintake(dateString, "14.00", 800,200 , "YES"));
                Toast.makeText(this,"Information is saved",Toast.LENGTH_LONG).show();
                startActivity(new Intent(Add_waterinfo.this, dagbog.class));
                break;
        }


    }
}
