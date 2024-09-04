package com.excise.excisemanagement;

import static android.content.Context.MODE_PRIVATE;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class Inprocess_start extends Fragment {

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

    private SimpleDateFormat yearFormat;
    private SimpleDateFormat dateFormat1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_inprocess_start, container, false);

        String[] options = {"Select Province", "AB", "BC", "MB", "NB", "NL", "NT", "NS", "NU", "ON", "PE", "QC", "SK", "YT"};

        // Find the spinner view
        spinner = view.findViewById(R.id.statespinner);
        stamptaken= view.findViewById(R.id.stamptaken);
        sku=view.findViewById(R.id.sku);
        packagedate=view.findViewById(R.id.datepicker);
        location=view.findViewById(R.id.room);

        confirmstamp = view.findViewById(R.id.stampcheckbox);
        imageView=view.findViewById(R.id.stateimage);
        submittrack=view.findViewById(R.id.tracksubmit);
        skulot=view.findViewById(R.id.skulot);
        stamptoinv=view.findViewById(R.id.returntoinv);
        damagestamp=view.findViewById(R.id.stampdamaged);
        damagereason= view.findViewById(R.id.damagereasn);

        damagereason.setVisibility(View.GONE);

        inprocessdate=view.findViewById(R.id.inprocessdate);

      //  yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
        dateFormat1 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

       // sharedPreferences = view.getSharedPreferences("MyPrefs", MODE_PRIVATE);

// Retrieve values from SharedPreferences when page loads
        // String ed1Value = sharedPreferences.getString("province", "");
//        String ed2Value = sharedPreferences.getString("noofstamp", "");
//        String ed3Value = sharedPreferences.getString("productname", "");
//        String ed4Value = sharedPreferences.getString("lotno", "");
//        String ed5Value = sharedPreferences.getString("packagedate", "");
//        String ed6Value = sharedPreferences.getString("location", "");
//        String ed7value=sharedPreferences.getString("province","");
//


//        boolean isChecked = sharedPreferences.getBoolean(CHECKBOX_KEY, false);
//        confirmstamp.setChecked(isChecked);
//
//        int savedPosition = sharedPreferences.getInt(SPINNER_POSITION_KEY, 0);
//
//        stamptaken.setText(ed2Value);
//        sku.setText(ed3Value);
//        skulot.setText(ed4Value);
//        packagedate.setText(ed5Value);
//        location.setText(ed6Value);


        packagedate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No implementation needed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // No implementation needed
//                String input = s.toString().replaceAll("[^\\d]", "");
//                if (input.length() >= 8) {
//                    StringBuilder formattedDate = new StringBuilder();
//                    formattedDate.append(input.substring(0, 4)); // Year
//                    formattedDate.append("-");
//                    formattedDate.append(input.substring(4, 6)); // Month
//                    formattedDate.append("-");
//                    formattedDate.append(input.substring(6, 8)); // Day
//                    // Update EditText text with formatted date
//                    packagedate.removeTextChangedListener(this);
//                    packagedate.setText(formattedDate.toString());
//                    packagedate.setSelection(formattedDate.length());
//                    packagedate.addTextChangedListener(this);
//                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String input = s.toString().replaceAll("[^\\d]", "");
                if (input.length() >= 8) {
                    StringBuilder formattedDate = new StringBuilder();
                    formattedDate.append(input.substring(0, 4)); // Year
                    formattedDate.append("-");
                    formattedDate.append(input.substring(4, 6)); // Month
                    formattedDate.append("-");
                    formattedDate.append(input.substring(6, 8)); // Day
                    // Update EditText text with formatted date
                    packagedate.removeTextChangedListener(this);
                    packagedate.setText(formattedDate.toString());
                    packagedate.setSelection(formattedDate.length());
                    packagedate.addTextChangedListener(this);
                }
            }
        });


        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
              R.layout.spinnertext, options);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

    //    spinner.setSelection(adapter.getPosition(ed7value));


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
                if (validateInput()) {
                    // If input is valid, insert data
                    submittrackform();
                }
             //   submittrackform();

            }
        });


        confirmstamp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Display a toast message when "Yes" is selected
                if (isChecked) {
                    Toast.makeText(getContext(), "Yes is selected", Toast.LENGTH_SHORT).show();
                    // Uncheck the "No" CheckBox if "Yes" is selected
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putBoolean(CHECKBOX_KEY, isChecked);
//                    editor.apply();
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
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putInt(SPINNER_POSITION_KEY, position);
//                editor.apply();

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




        return  view;
    }

    private boolean validateInput() {


        if (inprocessdate.getText().toString().isEmpty()){


            inprocessdate.setError("Please select date");
            return  false;
        }

        if (spinner.getSelectedItem().toString().equals("Select Province")) {
            // Show error message

            ((TextView) spinner.getSelectedView()).setError("Please select a province");
            //  Toast.makeText(getApplicationContext(), "Please select a province", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (stamptaken.getText().toString().isEmpty()){


            stamptaken.setError("Please enter number of stamps");
            return  false;
        }

        if (!confirmstamp.isChecked()) {
            // Show error message if checkbox is not checked
            // Toast.makeText(getApplicationContext(), "Please check the checkbox", Toast.LENGTH_SHORT).show();
            confirmstamp.setError("Please check the checkbox ");
            return false;
        }

        if (sku.getText().toString().isEmpty()){


            sku.setError("Please enter product name");
            return  false;
        }

        if (skulot.getText().toString().isEmpty()){


            skulot.setError("Please enter product lot");
            return  false;
        }

        if (packagedate.getText().toString().isEmpty()){


            packagedate.setError("Please enter packaged date");
            return  false;
        }

        if (location.getText().toString().isEmpty()){


            location.setError("Please enter location");
            return  false;
        }







        return  true;
    }


    private void showDatePickerDialog1() {

        int year = calendar.get(java.util.Calendar.YEAR);
        int month = calendar.get(java.util.Calendar.MONTH);
        int dayOfMonth = calendar.get(java.util.Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
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
    private void updateImage(String selectedItem) {
        int imageResource;
        switch (selectedItem) {
            case "AB":
                imageResource = R.drawable.ab;
                break;
            case "BC":
                imageResource = R.drawable.bc;
                break;
            case "MB":
                imageResource = R.drawable.mb;
                break;
            case "NB":
                imageResource = R.drawable.nb;
                break;
            case "NL":
                imageResource = R.drawable.nl;
                break;
            case "NT":
                imageResource = R.drawable.nt;
                break;
            case "NS":
                imageResource = R.drawable.ns;
                break;
            case "NU":
                imageResource = R.drawable.nu;
                break;
            case "ON":
                imageResource = R.drawable.onstamp;
                break;
            case "PE":
                imageResource = R.drawable.pe;
                break;
            case "QC":
                imageResource = R.drawable.qc;
                break;
            case "SK":
                imageResource = R.drawable.sk;
                break;
            case "YT":
                imageResource = R.drawable.yt;
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

    private void submittrackform() {
        RequestQueue queue = Volley.newRequestQueue(getContext());






        String selectedProvince = spinner.getSelectedItem().toString();
        Log.d("SelectedProvince", selectedProvince);




        sharedPreferences = getActivity().getSharedPreferences("logindetails", MODE_PRIVATE);

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

                        Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                        Log.d("response",response);

//                     Intent intent = new Intent(getActivity(), Excise_management.class);
//                     startActivity(intent);

                        stamptaken.setText("");
                        sku.setText("");
                        packagedate.setText("");
                        location.setText("");
                        skulot.setText("");
                        confirmstamp.setChecked(false);

                        inprocessdate.setText("");

                        // Optionally, you can reset the Spinner to the default selection
                        spinner.setSelection(0);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error occurred", Toast.LENGTH_SHORT).show();
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

}

