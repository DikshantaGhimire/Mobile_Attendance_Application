package com.example.dikshanta.eyeattend;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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
 * Created by Dikshanta on 23/01/2017.
 */

public class background extends AsyncTask<String, Void, String> {

    Context context;
    AlertDialog dialog;


    //asign context to local context

    background (Context ctx) {
        context = ctx;

    }

    @Override
    //string onn AsyncTask because of return type below as well as post execute
    protected String doInBackground(String... paramaters) {

        String catogery = paramaters[0];
        String url = "http://192.168.184.163/login.php";

        if (catogery.equals("access")) {

            try {
                String user_name = paramaters[1];
                String password = paramaters[2];

                URL address = new URL (url);
                HttpURLConnection httpconn = (HttpURLConnection)address.openConnection();
                httpconn.setRequestMethod("POST");
                httpconn.setDoOutput(true);
                httpconn.setDoInput(true);
                OutputStream sendText = httpconn.getOutputStream();
                BufferedWriter writePath = new BufferedWriter(new OutputStreamWriter(sendText, "UTF-8"));
                //extract value of username and password as params from Login.java class
                String send_value = URLEncoder.encode("user_name","UTF-8")+"="+URLEncoder.encode(user_name,"UTF-8")+ "&"
                        + URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
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
        dialog.setTitle("Login Status");

    }

    @Override
    protected void onPostExecute(String result) {
       super.onPostExecute(result);
        if (result.contains("Login Successful ")) {
            Toast.makeText(context, result, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(context, MainMenu.class);
            context.startActivity(intent);

        } else {
            dialog.setMessage(result);
            dialog.show();

        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}

