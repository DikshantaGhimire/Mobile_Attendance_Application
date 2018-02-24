package com.example.dikshanta.eyeattend;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by Dikshanta on 15/02/2017.

 */

public class Attendance_menu extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinner_subject;
    Spinner spinner_year;
    Spinner spinner_section;
    Context context;
    Intent intent = new Intent();
    //adding data into spinner with adapter
    ArrayAdapter<CharSequence> adapter;
    ArrayAdapter<CharSequence> adapter_year;
    ArrayAdapter<CharSequence> adapter_section;


    String[] subjects = {"Select Subject", "Maths", "ICT", "Physics", "Geography",
            "History", "Spanish" };

    String[] year = {"Select Year", "7", "8", "9", "10" };

    String[] section = {"Select Section","A", "B"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        ArrayAdapter<String> adapter_sub = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, subjects);
        ArrayAdapter<String> adapter_yer = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, year);
        ArrayAdapter<String> adapter_sect = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, section);

        //subjects
        spinner_subject = (Spinner) findViewById(R.id.spinner1);
        spinner_subject.setAdapter(adapter_sub);
        spinner_subject.setOnItemSelectedListener(this);

        //year
        spinner_year = (Spinner) findViewById(R.id.spinner2);
        spinner_year.setAdapter(adapter_yer);
        spinner_year.setOnItemSelectedListener(this);

        //subjects
        spinner_section = (Spinner) findViewById(R.id.spinner3);
        spinner_section.setAdapter(adapter_sect);
        spinner_section.setOnItemSelectedListener(this);



    }

    @Override
    public void onItemSelected(AdapterView<?> spinner, View view,
                               int position, long arg3) {

        int id = spinner.getId();  //You can also use int id= view.getId();

        switch (id) {
            case R.id.spinner1:
                // Do what you want

                String spinner_subject=spinner.getItemAtPosition(position).toString();


               intent.putExtra("SpinnerName1", spinner_subject);

                break;
            case R.id.spinner2:

                String spinner_year=spinner.getItemAtPosition(position).toString();
                intent.putExtra("SpinnerName2", spinner_year);
                break;
            case R.id.spinner3:

                String spinner_section=spinner.getItemAtPosition(position).toString();
                intent.putExtra("SpinnerName3", spinner_section);
                break;



        }



    }

    public void spinner_button(View arg0) {

        intent.setClass(this, Attendance_menu.class);

       String v1= intent.getStringExtra("SpinnerName1");
        String v2= intent.getStringExtra("SpinnerName2");
        String v3= intent.getStringExtra("SpinnerName3");
        String sendDate = getIntent().getStringExtra("CalanderDate");
        Log.i("Android", "Date button passed from calander: "+sendDate);


        String catogery = "access";
        Table_check runBackground = new Table_check(this);
        runBackground.execute(catogery, v1, v2, v3, sendDate);
        startActivity(intent);

    }

    @Override
    public void onNothingSelected(AdapterView<?> position){


    }


}



