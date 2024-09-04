package com.excise.excisemanagement;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class excise_adjustvalue extends AppCompatActivity {


    Spinner spinner;
    
    EditText date,adjustvalue,adjustreason;
    
    Button adjustsubmit;
    Calendar calendar;

    SharedPreferences sharedPreferences;

    String url = "http://10.0.0.154/excise/adjustvalueinsert.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excise_adjustvalue);


        Toolbar toolbar = findViewById(R.id.adjustvalue_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Excise Inventory Adjustment");
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Set the parent activity for proper navigation
        // Replace ".DrawerActivity" with the name of your parent activity
        // This will handle the up navigation when the back arrow is clicked
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        String[] options = {"Select Province", "AB", "BC", "MB", "NB", "NL", "NT", "NS", "NU", "ON", "PE", "QC", "SK", "YT"};

        // Find the spinner view
        spinner = findViewById(R.id.adjustspinner);
        date=findViewById(R.id.adjustdate);
        adjustvalue=findViewById(R.id.adjustvalue);
        adjustreason=findViewById(R.id.adjustreason);
        adjustsubmit=findViewById(R.id.adjustbutton);
        

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
               R.layout.spinnertext, options);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        calendar = Calendar.getInstance();
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DatePicker", "Date EditText clicked");
                showDatePickerDialog();
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Handle the selection here
                String selectedItem = options[position];
                // Update the image based on the selection
                String selectedProvince = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
        
        adjustsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validateInput()) {
                    // If input is valid, insert data
                    adjustvalueinsert();
                }






               // adjustvalueinsert();
            }
        });



    }

    private boolean validateInput() {

        if (spinner.getSelectedItem().toString().equals("Select Province")) {
            // Show error message

            ((TextView) spinner.getSelectedView()).setError("Please select a province");
            //  Toast.makeText(getApplicationContext(), "Please select a province", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (date.getText().toString().isEmpty()){


            date.setError("Please select date");
            return  false;
        }


        if (adjustvalue.getText().toString().isEmpty()){


            adjustvalue.setError("Please enter number of adjust stamps");
            return  false;
        }
        return  true;
    }





    private void adjustvalueinsert() {

        RequestQueue queue = Volley.newRequestQueue(this);

        String adjustdate=date.getText().toString();
        String adjustvalue1=adjustvalue.getText().toString();
        String adjreason=adjustreason.getText().toString();

        sharedPreferences = getApplicationContext().getSharedPreferences("logindetails", MODE_PRIVATE);

        String recvby = sharedPreferences.getString("username", "");


        String selectedProvince = spinner.getSelectedItem().toString();
        Log.d("SelectedProvince", selectedProvince);




//        sharedPreferences = getApplicationContext().getSharedPreferences("logindetails", MODE_PRIVATE);
//
//        String recvby = sharedPreferences.getString("username", "");

//        if (imageBitmap == null) {
//            Toast.makeText(this, "Image is null. Capture an image first.", Toast.LENGTH_SHORT).show();
//            return;
//        }



        // Create a String request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle response from the server

                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                        Log.d("response",response);
//                        Intent intent = new Intent(getApplicationContext(), Excise_Section.class);
//                        startActivity(intent);
//                        Intent intent = new Intent(getApplicationContext(), Excise_management.class);
//                        startActivity(intent);
                        adjustreason.setText("");
                        adjustvalue.setText("");
                        date.setText("");


                        // Optionally, you can reset the Spinner to the default selection
                        spinner.setSelection(0);



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error occurred", Toast.LENGTH_SHORT).show();
            }


        }) {
            @Override
            protected Map<String, String> getParams() {
                // Parameters to be sent to the PHP script
                Map<String, String> params = new HashMap<>();

                params.put("provincename",selectedProvince);
                params.put("datetime",adjustdate );

                params.put("valueadjusted",adjustvalue1);
                params.put("adjustreason",adjreason);
                params.put("doneby",recvby);



                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void showDatePickerDialog() {

        // Get current year, month, and day
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year1, monthOfYear, dayOfMonth1) -> {
                    // Set the selected date in the EditText field
                    String formattedDate = String.format("%04d-%02d-%02d", year1, monthOfYear + 1, dayOfMonth1);
                    // Set the formatted date in the EditText field
                    date.setText(formattedDate);
                    //  fromdate.setText(year1+"-"+(monthOfYear + 1)+"-"+ dayOfMonth1 );
                },
                year, month, dayOfMonth);
        datePickerDialog.show();
    }

}