package com.example.pelvicfloortraining;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

public class Add_waterinfo extends AppCompatActivity {

    DatePicker datePicker;
    TimePicker timePicker;
    EditText fluid, urination, leakage;
    int i;
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
    }

    public void onNext(View view) {
        datePicker.setVisibility(View.GONE);
        timePicker.setVisibility(View.VISIBLE);
        if (timePicker.getVisibility()==View.VISIBLE){
            i++;
            if (i==2){
                timePicker.setVisibility(View.GONE);
                fluid.setVisibility(View.VISIBLE);
                urination.setVisibility(View.VISIBLE);
                leakage.setVisibility(View.VISIBLE);
            }

        }

    }
}
