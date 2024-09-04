package com.excise.excisemanagement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Inprocess_end extends Fragment {

Spinner endspinner;
TextView enddate,endprovince,endstamps,endmatches,endproduct,endlot,endpackage,endlocation;

EditText endreturn,enddamage,endreason;

String spinnerurl="http://10.0.0.154/excise/Fetchprocessendid.php";

Button endbutton;

    private ArrayList<SpinnerItem> spinnerItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_inprocess_end, container, false);


        endspinner=view.findViewById(R.id.spinnerend_id);
        enddate=view.findViewById(R.id.end_Date);
        endprovince=view.findViewById(R.id.end_Province);
        endstamps=view.findViewById(R.id.end_stampstaken);
        endmatches=view.findViewById(R.id.end_matches);
        endproduct=view.findViewById(R.id.end_Productname);
        endlot=view.findViewById(R.id.end_lotno);
        endpackage=view.findViewById(R.id.end_packageddate);
        endlocation=view.findViewById(R.id.end_location);
        endreturn=view.findViewById(R.id.end_stampsreturntoinv);
        enddamage=view.findViewById(R.id.end_Damagestamps);
        endreason=view.findViewById(R.id.end_reasonfordamage);
        endbutton=view.findViewById(R.id.endbutton);

        spinnerItems = new ArrayList<>();
        spinnerItems.add(new SpinnerItem(-1, "Select Id"));

        fetchspinnerID();

endbutton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        if (validateInput()) {
            // If input is valid, insert data
            updatevalue();
        }
     //   updatevalue();
    }
});




        return  view;
    }

    private boolean validateInput() {

        if (endspinner.getSelectedItem().toString().equals("Select Id")) {
            // Show error message

            ((TextView) endspinner.getSelectedView()).setError("Please select a province");
            //  Toast.makeText(getApplicationContext(), "Please select a province", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (endreturn.getText().toString().isEmpty()){


            endreturn.setError("Please enter no of stamps return");
            return  false;
        }

        if (enddamage.getText().toString().isEmpty()){


            enddamage.setError("Please no of stamps of damage");
            return  false;
        }

        if (endreason.getText().toString().isEmpty()){


            endreason.setError("Please fill reason for damage");
            return  false;
        }


        return  true;
    }


    private void fetchspinnerID() {

        RequestQueue queue = Volley.newRequestQueue(getActivity());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, spinnerurl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {



                        try {


                            JSONArray dataArray = response.getJSONArray("data");

                            // Process each JSON object in the "data" array
                            for (int i = 0; i < dataArray.length(); i++) {
                                JSONObject itemObject = dataArray.getJSONObject(i);

                                int id = itemObject.getInt("id"); // Parse ID as integer


                                String label = ""; // Update this with the label value from your data
                                SpinnerItem spinnerItem = new SpinnerItem(id, String.valueOf(id)); // Use ID as both ID and label
                                spinnerItems.add(spinnerItem);





                                // Create DataModel object and add it to dataList
                               // inprocesslist.add(new Inprocessitems(inprocessid,datetime,province, stamptaken, stampmatches, product, lot, packagedate, location, stamptoinv, damagestamp, reasndamage, doneby));
                            }

                            ArrayAdapter<SpinnerItem> adapter = new ArrayAdapter<>(getContext(), R.layout.spinnertext, spinnerItems);
                            adapter.setDropDownViewResource(R.layout.spinner_item);
                            endspinner.setAdapter(adapter);




                            endspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    SpinnerItem selectedItem = (SpinnerItem) parent.getItemAtPosition(position);

                                    Addvalues(selectedItem);
                                    // Handle the selection here

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                    // Do nothing
                                }
                            });

                          //  inprocessadapter.notifyDataSetChanged(); // Refresh GridView
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle errors
                Toast.makeText(getActivity(), "Error occurred", Toast.LENGTH_SHORT).show();
            }
        });

// Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }

    private void Addvalues(SpinnerItem selectedItem) {

       String spinnervalue = endspinner.getSelectedItem().toString();

        //String selectedProvince = spinner.getSelectedItem().toString();

        String urlforset="http://10.0.0.154/excise/fetchforinprocessend.php";

        String requestUrl = urlforset + "?id=" + selectedItem ;


        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String jsonResponse) {
                        try {
                            // Parse JSON response
                            JSONObject jsonObject = new JSONObject(jsonResponse);
                            String status = jsonObject.getString("status");

                            // Check if response is successful
                            if (status.equals("success")) {
                                // Extract the "data" array
                                JSONArray dataArray = jsonObject.getJSONArray("data");

                                // If there is at least one object in the array
                                if (dataArray.length() > 0) {
                                    // Get the first object from the array
                                    JSONObject dataObject = dataArray.getJSONObject(0);

                                    // Extract values from the object

                                    String datetime = dataObject.getString("datetime");
                                    String province = dataObject.getString("provincename");
                                    String stamptaken = dataObject.getString("stampstaken");
                                    String stampmatches = dataObject.getString("confirmstamp");
                                    String product = dataObject.getString("sku");
                                    String lot = dataObject.getString("lotno");
                                    String packagedate = dataObject.getString("pacakgedate");
                                    String location = dataObject.getString("location");
                                    String stamptoinv = dataObject.getString("stamptoinv");
                                    String damagestamp = dataObject.getString("damagestamp");
                                    String reasndamage = dataObject.getString("reasondamage");


                                    String doneby = dataObject.getString("doneby");

                                    // Set values to TextViews
                                    enddate.setText(datetime);
                                    endprovince.setText(province);
                                    endstamps.setText(stamptaken);
                                    endmatches.setText(stampmatches);
                                    endproduct.setText(product);
                                    endlot.setText(lot);
                                    endpackage.setText(packagedate);
                                    endlocation.setText(location);
                                    endreturn.setText(stamptoinv);
                                    enddamage.setText(damagestamp);
                                    endreason.setText(reasndamage);



                                }

                            } else {
                                // Handle unsuccessful response
                            //    Toast.makeText(getActivity(), "Failed to fetch data", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Handle JSON parsing error
                            Toast.makeText(getActivity(), "Error parsing JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Error handling
                        Toast.makeText(getActivity(), "Data Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Parameters to be sent in the POST request
                Map<String, String> params = new HashMap<>();
                params.put("id", spinnervalue);
                return params;
            }
        };

        // Add the request to the RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void updatevalue() {

        RequestQueue queue = Volley.newRequestQueue(getActivity());

        // Get values from EditText fields
        String spinnervalue = endspinner.getSelectedItem().toString();

        //String selectedProvince = spinner.getSelectedItem().toString();

        String urlforset="http://10.0.0.154/excise/updateendprocess.php";

        String requestUrl = urlforset + "?id=" + spinnervalue ;

        // Create a String request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle response from the server

                       // Toast.makeText(getActivity(), "register successfully", Toast.LENGTH_SHORT).show();


//                        Intent intent = new Intent(getActivity(), Excise_management.class);
//                        startActivity(intent);

                        enddate.setText("");
                        endprovince.setText("");
                        endstamps.setText("");
                        endmatches.setText("");
                        endproduct.setText("");
                        endlot.setText("");
                        endpackage.setText("");
                        endlocation.setText("");
                        endreturn.setText("");
                        enddamage.setText("");
                        endreason.setText("");

                        // Optionally, reset the Spinner selection
                        endspinner.setSelection(0);
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
                params.put("stamptoinv",endreturn.getText().toString() );
                params.put("damagestamp", enddamage.getText().toString());
                params.put("reasondamage",endreason.getText().toString());
                params.put("id",spinnervalue);
                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }




}
