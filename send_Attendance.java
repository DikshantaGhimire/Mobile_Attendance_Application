package com.example.dikshanta.eyeattend;

/**
 * Created by namepc on 22/02/2017.
 */

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


public class send_Attendance extends AppCompatActivity{
    Context context;




    private static final String url = "jdbc:mysql://192.168.184.163:3306/login_page";
    private static final String user = "connect";
    private static final String pass = "connect";
    private String selected = "";
    private ListView lv;
    private ArrayList<String> your_array_list = new ArrayList<String>();
    private ArrayAdapter arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.virtual_listview);

        Log.i("Android", " Ran onCreate");
        new MyTask().execute();

    }




    private class MyTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void...arg0){

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Log.i("Android", " JDBC connecting");

                Connection con = DriverManager.getConnection(url, user, pass);
                Log.i("Android", " URL connection OK");

                Statement st = con.createStatement();

                Bundle b = getIntent().getExtras();
                String[] resultArr = b.getStringArray("arrayItems");




               Log.i("Android" , resultArr[0]);

            //    String date = "30/03/2017";
                String date = getIntent().getStringExtra("send_Attendance");
                String SQL_table = getIntent().getStringExtra("send_SQL");

                Log.i("Android", "Date is on send_Attendance class: "+date + "/ " + SQL_table);

                String m= "Maths";
                String ict = "ICT";
                String geography = "Geography";
                String  history = "History";

                if (SQL_table.equals(m) ){

                    //  String sql = "ALTER TABLE attendance ADD " + "'" + date + "'" + " INT (30)";
                    String sql = " ALTER TABLE `attendance` ADD `" + date + "` INT(10) NOT NULL";

                    st.executeUpdate(sql);
               }
                else if (SQL_table.equals(ict) ){
                    //  String sql = "ALTER TABLE attendance ADD " + "'" + date + "'" + " INT (30)";
                    String sql = " ALTER TABLE `ict_attendance` ADD `" + date + "` INT(10) NOT NULL";

                    st.executeUpdate(sql);

                }




                for (int i = 0; i < resultArr.length; ++i) {

                    String mystring = resultArr[i];
                    String arr[] = mystring.split(" ", 2);

                    if (SQL_table.equals(m) ) {
                        String sql2 = "UPDATE attendance SET `" + date + "` ='1' WHERE Firstname = " + "'" + arr[0] + "'";

                        st.executeUpdate(sql2);
                    }
                    else if (SQL_table.equals(ict)) {

                        String sql2 = "UPDATE ict_attendance SET `" + date + "` ='1' WHERE Firstname = " + "'" + arr[0] + "'";
                        st.executeUpdate(sql2);
                    }

                    }


                String sql_Php = " ALTER TABLE `attendance` ADD `" + date + "` INT(10) NOT NULL";


            }
            catch(Exception e){
                e.printStackTrace();
                Log.i("Android", "Connection Fail. ");
            }
            return null;
        }





        @Override
        protected void onPostExecute(Void result){


    }




} }

