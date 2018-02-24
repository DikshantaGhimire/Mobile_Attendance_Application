package com.example.dikshanta.eyeattend;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by namepc on 21/02/2017.
 */

public class Table_check extends AsyncTask<String, Void, String> {

    Context context;
    AlertDialog dialog;
    Intent intent = new Intent();
    String year_select = "";
    String subject_select = "";
    String section_select = "";
    String date_select = "";

//    String type = "login";

    //asign context to local context

    Table_check (Context ctx) {
        context = ctx;

    }

    @Override
    //string onn AsyncTask because of return type below as well as post execute
    protected String doInBackground(String... paramater) {

        String catogery = paramater[0];
        String url = "http://192.168.184.163/checkTable.php";

        if (catogery.equals("access")) {

            try {
                String check_Table = paramater[1];
                String classYear = paramater[2];
                String classSection = paramater [3];
                String dateSend = paramater [4];


                Log.i("Android", "Year varaiable is passed across: " + classYear);
                Log.i("Android", "  ");
                Log.i("Android", "  ");
                Log.i("Android", "  ");
                Log.i("Android", "  ");
                intent.putExtra("Year_Variable", classYear);
                intent.putExtra("Subject_Variable",check_Table);
                intent.putExtra("Section_Variable",classSection);
                intent.putExtra("Date_Variable", dateSend);





                URL address = new URL (url);
                HttpURLConnection httpconn = (HttpURLConnection)address.openConnection();
                httpconn.setRequestMethod("POST");
                httpconn.setDoOutput(true);
                httpconn.setDoInput(true);
                OutputStream sendText = httpconn.getOutputStream();
                BufferedWriter writePath = new BufferedWriter(new OutputStreamWriter(sendText, "UTF-8"));
                //extract value of username and password as params from Login.java class
                String send_value = URLEncoder.encode("check_Table","UTF-8")+"="+URLEncoder.encode(check_Table,"UTF-8")
                        ;
                //post data url
                //add post data to bufferwriter
                writePath.write(send_value);
                writePath.flush();
                writePath.close();
                sendText.close();
                InputStream recieveText = httpconn.getInputStream();
                BufferedReader readPath = new BufferedReader(new InputStreamReader(recieveText, "iso-8859-1"));
                String r = "";
                String string;

                while((string = readPath.readLine()) != null) {
                    r +=string;

                }

                readPath.close();
                recieveText.close();
                //close from hhtp connection
                httpconn.disconnect();

                //returning the result from above

                return r;


            } catch (MalformedURLException e) {

                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }




    @Override
    protected void onPreExecute() {
        dialog = new AlertDialog.Builder(context).create();
        dialog.setTitle("Table Check");


    }


    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }



    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        year_select= intent.getStringExtra("Year_Variable");
        subject_select= intent.getStringExtra("Subject_Variable");
        section_select= intent.getStringExtra("Section_Variable");
        date_select = intent.getStringExtra("Date_Variable");





        if (result.contains("Table Exists ")) {
            Toast.makeText(context, result, Toast.LENGTH_LONG).show();

            Bundle bundle = new Bundle();
            bundle.putString("subject", subject_select);
            bundle.putString("year", year_select);
            bundle.putString("section", section_select);
            bundle.putString("date", date_select);


           Intent intent = new Intent(context, JSONParser.class);


            String year = year_select;
            String subject = subject_select;
            String section = section_select;
            String date = date_select;

            Log.i("Android", "Test check : " + year + "/" + subject + "/" + section);
            Log.i("Android", "Test check : " + date);


            intent.putExtras(bundle);


            context.startActivity(intent);

        } else {
            dialog.setMessage(result);
            dialog.show();

        }

    }

}
