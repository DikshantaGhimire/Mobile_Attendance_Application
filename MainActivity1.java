package com.example.dikshanta.eyeattend;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by namepc on 25/02/2017.
 */

public class MainActivity1 extends Activity{

    private static final String url = "jdbc:mysql://192.168.184.163:3306/login_page";
    private static final String user = "connect";
    private static final String pass = "connect";
    private TextView firstName, lastName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_event);
        firstName =(TextView)findViewById(R.id.textViewFirstName);
        lastName = (TextView)findViewById(R.id.textViewLastName);
        Button buttonLoad = (Button)findViewById(R.id.buttonLoad);

        buttonLoad.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                new MyTask().execute();

            }
        });

 }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }


    private class MyTask extends AsyncTask<Void, Void, Void>{

        private String fName = "", Lname = "";


        @Override
        protected Void doInBackground(Void... params) {

            try {


                Class.forName("com.mysql.jdbc.Driver").newInstance();

                Log.i("Android", " JDBC connecting");

                Connection con = DriverManager.getConnection(url,user,pass);

                Log.i("Android", " URL connection OK");

                Statement st = con.createStatement();
                String sql = "select * from user_data";

                final ResultSet rs = st.executeQuery(sql);

                rs.next();
                fName = rs.getString(2);
                fName = rs.getString(3);


            }
            catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            firstName.setText(fName);
            lastName.setText(Lname);
            super.onPostExecute(aVoid);
        }
    }




}

