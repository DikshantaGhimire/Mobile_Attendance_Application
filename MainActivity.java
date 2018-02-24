package com.example.dikshanta.eyeattend;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import junit.framework.Test;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.dikshanta.eyeattend.R.id.Mobile;
import static com.example.dikshanta.eyeattend.R.id.Nok;
import static com.example.dikshanta.eyeattend.R.id.Relationship;
import static com.example.dikshanta.eyeattend.R.id.forename;
import static com.example.dikshanta.eyeattend.R.id.sectionGroup;
import static com.example.dikshanta.eyeattend.R.id.surname;
import static com.example.dikshanta.eyeattend.R.id.yearGroup;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextId;
    private Button buttonGet;
    private TextView textView_firstName;
    private TextView textView_secondName;
    private TextView textView_year;
    private TextView textView_section;
    private TextView textView_NoK;
    private TextView textView_relationship;
    private TextView textView_mobile;




    private ProgressDialog loading;

    public static final String DATA_URL = "http://192.168.184.163/getData.php?firstname=";
    public static final String Fname = "firstname";
    public static final String Sname = "surname";
    public static final String Years = "year";
    public static final String Sections = "section";
    public static final String ARRAYS = "result";
    public static final String NoK = "Next_of_Kin";
    public static final String Relationships = "Relationship";
    public static final String Mobiles = "Mobile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextId = (EditText) findViewById(R.id.editTextId);
        buttonGet = (Button) findViewById(R.id.buttonGet);
        textView_firstName = (TextView) findViewById(forename);
        textView_secondName = (TextView) findViewById(surname);
        textView_year = (TextView) findViewById(yearGroup);
        textView_section = (TextView) findViewById(sectionGroup);
        textView_NoK = (TextView) findViewById(Nok);
        textView_relationship = (TextView) findViewById(Relationship);
        textView_mobile = (TextView) findViewById(Mobile);




        buttonGet.setOnClickListener(this);
        
    }

    private void value1() {
        String firstname = editTextId.getText().toString().trim();
        if (firstname.equals("")) {
            Toast.makeText(this, "Enter Student name", Toast.LENGTH_LONG).show();
            return;
        }
        loading = ProgressDialog.show(this,"Loading...","Please Wait...",false,false);

        String url = DATA_URL+editTextId.getText().toString().trim();

        StringRequest getString1 = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String connect) {
                loading.dismiss();
                showJSON(connect);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();

                    }
                });

        RequestQueue getString = Volley.newRequestQueue(this);
        getString.add(getString1);
    }

    private void showJSON(String response){

        String Firstname= "";
        String Surname = "";
        String Year = "";
        String Section = "";
        String Next_of_Kin = "";
        String Mobile= "";
        String Relationship ="";
        try {
            JSONObject JSON = new JSONObject(response);
            JSONArray r = JSON.getJSONArray(ARRAYS);
            JSONObject collegeData = r.getJSONObject(0);
            Firstname = collegeData.getString(Fname);
            Surname = collegeData.getString(Sname);
            Year = collegeData.getString(Years);
            Section = collegeData.getString(Sections);
            Next_of_Kin = collegeData.getString(NoK);
            Mobile = collegeData.getString(Mobiles);
            Relationship = collegeData.getString(Relationships);


        } catch (JSONException e) {
            e.printStackTrace();
        }
//        textViewResult.setText("Firstname: \t"+ Firstname + "\nSurname: \t" + Surname +
//                "\nYear: \t" + Year + "\nSection: " + Section);
        textView_firstName.setText(Firstname);
        textView_secondName.setText(Surname);
        textView_year.setText("Year: \t" +Year);
        textView_section.setText("Section: \t" + Section);
        textView_NoK.setText("N.o.K: \t" + Next_of_Kin);
        textView_mobile.setText("Mobile: \t" + Mobile);
        textView_relationship.setText("Relationship: \t" + Relationship);


  //      String abs = Firstname;


        //split  with different view rather than text.
    }

    @Override
    public void onClick(View v) {
        value1();
    }
}