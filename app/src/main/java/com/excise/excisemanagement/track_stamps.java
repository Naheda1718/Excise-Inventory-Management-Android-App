package com.excise.excisemanagement;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class track_stamps extends AppCompatActivity {

    Spinner spinner;
    EditText stamptaken,sku,packagedate,location,skulot,stamptoinv,damagestamp,damagereason,inprocessdate;
    CheckBox confirmstamp;

    private ImageView imageView;

    Button submittrack;
    SharedPreferences sharedPreferences;
    private static final String SPINNER_POSITION_KEY = "spinner_position";
    Date currentDate = new Date();
 Calendar calendar;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // Format the current date and time as a string
    String formattedDateTime = dateFormat.format(currentDate);

    private static final String CHECKBOX_KEY = "checkbox_state";

    String url="http://10.0.0.154/excise/addtrackstamp.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_stamps);
        Toolbar toolbar = findViewById(R.id.track_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("In-process Excise");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        String[] options = {"Select Province", "ON", "QC", "NS", "NB", "MB", "BC", "PE", "SK", "AB", "NL", "NT", "YT", "NU"};

        // Find the spinner view
        spinner = findViewById(R.id.statespinner);
        stamptaken= findViewById(R.id.stamptaken);
        sku=findViewById(R.id.sku);
        packagedate=findViewById(R.id.datepicker);
        location=findViewById(R.id.room);

        confirmstamp =  findViewById(R.id.stampcheckbox);
        imageView=findViewById(R.id.stateimage);
        submittrack=findViewById(R.id.tracksubmit);
        skulot=findViewById(R.id.skulot);
        stamptoinv=findViewById(R.id.returntoinv);
        damagestamp=findViewById(R.id.stampdamaged);
        damagereason= findViewById(R.id.damagereasn);

        damagereason.setVisibility(View.GONE);

        inprocessdate=findViewById(R.id.inprocessdate);



        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

// Retrieve values from SharedPreferences when page loads
       // String ed1Value = sharedPreferences.getString("province", "");
        String ed2Value = sharedPreferences.getString("noofstamp", "");
        String ed3Value = sharedPreferences.getString("productname", "");
        String ed4Value = sharedPreferences.getString("lotno", "");
        String ed5Value = sharedPreferences.getString("packagedate", "");
        String ed6Value = sharedPreferences.getString("location", "");
        String ed7value=sharedPreferences.getString("province","");



        boolean isChecked = sharedPreferences.getBoolean(CHECKBOX_KEY, false);
        confirmstamp.setChecked(isChecked);

        int savedPosition = sharedPreferences.getInt(SPINNER_POSITION_KEY, 0);

        stamptaken.setText(ed2Value);
        sku.setText(ed3Value);
        skulot.setText(ed4Value);
        packagedate.setText(ed5Value);
        location.setText(ed6Value);


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, options);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setSelection(adapter.getPosition(ed7value));


        calendar = Calendar.getInstance();

        inprocessdate.setOnClickListener(v -> showDatePickerDialog1());



        damagestamp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString().trim();
                if (!text.isEmpty()) {
                    damagereason.setVisibility(View.VISIBLE);

                } else {
                    damagereason.setVisibility(View.GONE);

                }
            }
        });
        submittrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (!damagestamp.getText().toString().trim().isEmpty()
//                        && damagereason.getText().toString().trim().isEmpty()) {
//                    damagereason.setError("Please enter the damage reason.");
//                    damagereason.requestFocus();
//                    return;
//                }

                submittrackform();

            }
        });


        confirmstamp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Display a toast message when "Yes" is selected
                if (isChecked) {
                    Toast.makeText(getApplicationContext(), "Yes is selected", Toast.LENGTH_SHORT).show();
                    // Uncheck the "No" CheckBox if "Yes" is selected
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(CHECKBOX_KEY, isChecked);
                    editor.apply();
                }
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Handle the selection here
                String selectedItem = options[position];
                // Update the image based on the selection
                updateImage(selectedItem);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(SPINNER_POSITION_KEY, position);
                editor.apply();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });


//        packagedate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showDatePickerDialog();
//            }
//        });
//        packagedate.setFilters(new InputFilter[]{new DateInputFilter()});
//        packagedate.setInputType(android.text.InputType.TYPE_CLASS_DATETIME);





    }




    private void showDatePickerDialog1() {
        int year = calendar.get(java.util.Calendar.YEAR);
        int month = calendar.get(java.util.Calendar.MONTH);
        int dayOfMonth = calendar.get(java.util.Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year1, monthOfYear, dayOfMonth1) -> {
                    // Set the selected date in the EditText field
                    String formattedDate = String.format("%04d-%02d-%02d", year1, monthOfYear + 1, dayOfMonth1);
                    // Set the formatted date in the EditText field
                    inprocessdate.setText(formattedDate);
                    //  fromdate.setText(year1+"-"+(monthOfYear + 1)+"-"+ dayOfMonth1 );
                },
                year, month, dayOfMonth);
        datePickerDialog.show();

    }

    @Override
    protected void onPause() {
        super.onPause();
        // Save values to SharedPreferences when leaving the page
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("province", spinner.getSelectedItem().toString());
        editor.putString("noofstamp", stamptaken.getText().toString());
        editor.putString("productname", sku.getText().toString());
        editor.putString("lotno", skulot.getText().toString());

        editor.putString("packagedate", packagedate.getText().toString());
        editor.putString("location", location.getText().toString());
        editor.apply();
    }
    private void submittrackform() {
        RequestQueue queue = Volley.newRequestQueue(this);






        String selectedProvince = spinner.getSelectedItem().toString();
        Log.d("SelectedProvince", selectedProvince);




        sharedPreferences = getApplicationContext().getSharedPreferences("logindetails", MODE_PRIVATE);

        String recvby = sharedPreferences.getString("username", "");

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
                params.put("datetime",inprocessdate.getText().toString() );
                params.put("provincename",selectedProvince);
                params.put("stampstaken",stamptaken.getText().toString() );
                if (confirmstamp.isChecked()) {
                    params.put("confirmstamp", "Yes");
                } else {
                    params.put("confirmstamp", "No");
                }

                params.put("sku", sku.getText().toString());
                params.put("lotno", skulot.getText().toString());

                params.put("pacakgedate",packagedate.getText().toString() );

                params.put("location",location.getText().toString() );

                params.put("stamptoinv",stamptoinv.getText().toString() );
                params.put("damagestamp",damagestamp.getText().toString() );
                params.put("reasondamage",damagereason.getText().toString() );
                params.put("doneby",recvby );




                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }



    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Set the selected date in the EditText field
                        String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        packagedate.setText(selectedDate);
                    }
                },
                year, month, dayOfMonth);

        datePickerDialog.show();
    }

    private void updateImage(String selectedItem) {
        int imageResource;
        switch (selectedItem) {

            case "ON":
                imageResource = R.drawable.onstamp;
                break;
            case "QC":
                imageResource = R.drawable.qc;
                break;
            case "NS":
                imageResource = R.drawable.ns;
                break;
            case "NB":
                imageResource = R.drawable.nb;
                break;
            case "MB":
                imageResource = R.drawable.mb;
                break;
            case "BC":
                imageResource = R.drawable.bc;
                break;
            case "PE":
                imageResource = R.drawable.pe;
                break;
            case "SK":
                imageResource = R.drawable.sk;
                break;
            case "AB":
                imageResource = R.drawable.ab;
                break;
            case "NL":
                imageResource = R.drawable.nl;
                break;
            case "NT":
                imageResource = R.drawable.nt;
                break;
            case "YT":
                imageResource = R.drawable.yt;
                break;
            case "NU":
                imageResource = R.drawable.nu;
                break;

            default:
                // If the selected option does not match, hide the ImageView
                imageView.setVisibility(View.GONE);
                // Also hide the CheckBoxes and TextView

                return;
        }

        // Show the ImageView and set the corresponding image resource
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageResource(imageResource);

        // Show the CheckBoxes and TextView when the image is visible



    }

    private class DateInputFilter implements InputFilter {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            // Enforce the format DD/MM/YYYY for date input
            String input = dest.toString().substring(0, dstart) + source.toString() + dest.toString().substring(dend);
            if (!input.matches("^\\d{0,2}/\\d{0,2}/\\d{0,4}$")) {
                return "";
            }
            return null;
        }


    }}