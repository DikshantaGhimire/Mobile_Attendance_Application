package com.example.dikshanta.eyeattend;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

/**
 * Created by namepc on 23/02/2017.
 */

public class calander  extends AppCompatActivity{

    CalendarView calendarView;
    Intent intent = new Intent();

    @Override
    protected void onCreate(Bundle saveInstanceState) {
       super.onCreate(saveInstanceState);
       setContentView(R.layout.calander);


       Toast.makeText(calander.this, "Pick Date to Mark Attendance", Toast.LENGTH_SHORT).show();
       calendarView = (CalendarView) findViewById(R.id.calander);
       calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){



           @Override
           public void onSelectedDayChange (CalendarView view, int year, int  month, int dayofMonth) {

               //dates are stored in a variable here.
               Toast.makeText(getApplicationContext(),dayofMonth + "/" + month + "/" + year, Toast.LENGTH_SHORT).show();

               String date = ""+ dayofMonth + "/" + month + "/" + year +"";
               Log.i("Android", date);
               intent.putExtra("Dates", date);


               //above attendnace sends data straight away
//               Intent intent = new Intent(getApplicationContext(), send_Attendance.class);
//               intent.putExtra("EXTRA_SESSION_ID", date);
//               startActivity(intent);


           }
       });
   }

    public void calanderButton(View v)
    {
        intent.setClass(this, Attendance_menu.class);

        String value1= intent.getStringExtra("Dates");
        Log.i("Android", "Data is on the Button" + value1);

        intent.putExtra("CalanderDate", value1);

        startActivity(intent);



    }



}
