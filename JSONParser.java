package com.example.dikshanta.eyeattend;

/**
 * Created by namepc on 22/02/2017.
 */

import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;


public class JSONParser extends AppCompatActivity{


    private static final String url = "jdbc:mysql://192.168.184.163:3306/login_page";
    private static final String user = "connect";
    private static final String pass = "connect";

    private ListView listviewLayout;
    private ArrayList<String> your_array_list = new ArrayList<String>();
    private ArrayAdapter arrayAdapter;
    Intent intent = new Intent();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.advert_screen);

        Log.i("Android", " Ran onCreate");

        listviewLayout = (ListView) findViewById(R.id.testing);
        listviewLayout.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);


        new runBackground().execute();


        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice,your_array_list) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                // Get the Item from ListView
                Log.i("Android", " Get Item from listview");
                View views = super.getView(position, convertView, parent);
                TextView tv = (TextView) views.findViewById(android.R.id.text1);
                tv.setTextColor(Color.rgb(124,36,98));
                return views;
            }
        };


    }


    private class runBackground extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void...arg0){

            try{
                Class.forName("com.mysql.jdbc.Driver");
                Log.i("Android", " JDBC connecting");

                Connection con = DriverManager.getConnection(url,user,pass);
                Log.i("Android", " URL connection OK");

                Statement st = con.createStatement();

                Bundle extras = getIntent().getExtras();
                String year, subject, section, dateCalander;
                subject = extras.getString("subject");
                year = extras.getString("year");
                section = extras.getString("section");
                dateCalander = extras.getString("date");

                intent.putExtra("Dates", dateCalander);
                intent.putExtra("AttendanceSQL", subject);


                Log.i("Android", " Value is in JSONpaser: " + subject + "/" + year + "/" + section);
                Log.i("Android", " Date is in JSONpaser: " + dateCalander);



                String sql = "SELECT * FROM " +subject+ " WHERE  year = " +year+ " AND section = '" + section+ "'";
                final ResultSet rs = st.executeQuery(sql);
                while(rs.isLast() == false) {
                    rs.next();

                    //create an array list with column 2 and 3 from the table.
                    your_array_list.add(rs.getString(2) + " " + rs.getString(3));

                }

            }
            catch(Exception e){
                e.printStackTrace();
                Log.i("Android", "Connection Fail. ");
            }
            return null;



        }





        @Override
        protected void onPostExecute(Void result){


            arrayAdapter.notifyDataSetChanged();

            listviewLayout.setAdapter(arrayAdapter);

            listviewLayout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,int position, long id){

            Toast.makeText(JSONParser.this, your_array_list.get(position), Toast.LENGTH_SHORT).show();

                }
            });


            super.onPostExecute(result);

        }

    }



    public void saveAttendance(View v) {

        String value1= intent.getStringExtra("Dates");
        String value2= intent.getStringExtra("AttendanceSQL");

        Log.i("Android", "Date button passed from calander: "+value1 + "/ " + value2);

        SparseBooleanArray loopsize = listviewLayout.getCheckedItemPositions();
        ArrayList<String> listview_Attendance = new ArrayList<String>();
        for (int i = 0; i < loopsize.size(); i++) {
            // Item position in adapter
            int position = loopsize.keyAt(i);
            if (loopsize.valueAt(i))
                listview_Attendance.add((String) arrayAdapter.getItem(position));
        }
        String[] stringArray = new String[listview_Attendance.size()];

        for (int i = 0; i < listview_Attendance.size(); i++) {
            stringArray[i] = listview_Attendance.get(i);
        }

        Intent intent = new Intent(getApplicationContext(),
                send_Attendance.class);

        //send date to send_Attendance
    ///    Log.i("Android", "Value 1 Date: " + value1);
        intent.putExtra("send_Attendance", value1);
        intent.putExtra("send_SQL", value2);

        // Create a bundle object
        Bundle b = new Bundle();
        b.putStringArray("arrayItems", stringArray);

        // Add the bundle to the intent.
        intent.putExtras(b);

        // start the ResultActivity
        startActivity(intent);

    }



}

