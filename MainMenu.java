package com.example.dikshanta.eyeattend;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Dikshanta on 08/02/2017.
 */

public class MainMenu extends Activity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainlist);


    }


    public void student_profile(View v)
    {
        Intent intent = new Intent(MainMenu.this, MainActivity.class);
        startActivity(intent);
    }

    public void attendance (View v)
    {
        Intent intent = new Intent(MainMenu.this, calander.class);
        startActivity(intent);
    }

    public void student_listview (View v)
    {
       Intent intent = new Intent(MainMenu.this, Maths_Attendance.class);
       startActivity(intent);
    }

    public void calander (View v)
    {
        Intent intent = new Intent(MainMenu.this, calander.class);
        startActivity(intent);
    }


}
