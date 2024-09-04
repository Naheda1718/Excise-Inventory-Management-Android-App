package com.excise.excisemanagement;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Add_inventory extends Fragment {

    EditText recieve_from, prodtname, producttype, lot, weight, quantity, location;
    Button addbutton;

    EditText textviewscan,tote;
    String url = "http://10.0.0.154/inventory/addtote.php";


    SharedPreferences sharedPreferences;

    Date currentDate = new Date();

    // Define a date format
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // Format the current date and time as a string
    String formattedDateTime = dateFormat.format(currentDate);

    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

    // Format the current time as a string
    String formattedTime = timeFormat.format(currentDate);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View view=inflater.inflate(R.layout.fragment_add_inventory, container, false);
        recieve_from = view.findViewById(R.id.recieve_from);
        prodtname = view.findViewById(R.id.productname);
        producttype = view.findViewById(R.id.producttype);
        lot = view.findViewById(R.id.lot);
        weight = view.findViewById(R.id.weight);
        quantity = view.findViewById(R.id.quantity);
        tote = view.findViewById(R.id.tote);
        location = view.findViewById(R.id.location);
        addbutton = view.findViewById(R.id.addbutton);
        textviewscan=view.findViewById(R.id.textviewscan);

        textviewscan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scantote();

            }
        });


        if (getActivity() != null) {
            getActivity().setTitle("Add Inventory");
        }
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });

return view;
    }

    private void scantote() {
        IntentIntegrator integrator = IntentIntegrator.forSupportFragment(this);
        integrator.setPrompt("Scan a barcode or QR code");
        integrator.setOrientationLocked(false);
        integrator.initiateScan();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_SHORT).show();
            } else {
                // Handle the scanned result (e.g., display it in a TextView)
                String scannedData = result.getContents();
                tote.setText(scannedData);
                Toast.makeText(getActivity(), "Scanned: " + scannedData, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void addItem() {

        RequestQueue queue = Volley.newRequestQueue(getActivity());

        // Get values from EditText fields
        sharedPreferences = getActivity().getSharedPreferences("logindetails", MODE_PRIVATE);

        String recive = recieve_from.getText().toString();
        String productn = prodtname.getText().toString();
        String productty = producttype.getText().toString();
        String lotno = lot.getText().toString();
        String pweight = weight.getText().toString();
        String qty = quantity.getText().toString();
        String totno = tote.getText().toString();
        String loct = location.getText().toString();
        String recvby = sharedPreferences.getString("username", "");


        // Create a String request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // Handle response from the server

                        Toast.makeText(getActivity(), "added successfully", Toast.LENGTH_SHORT).show();
                        recieve_from.setText("");
                        prodtname.setText("");
                        producttype.setText("");
                        lot.setText("");
                        weight.setText("");
                        quantity.setText("");
                        tote.setText("");
                        location.setText("");

                        Intent intent = new Intent(getActivity(), drawer.class);
                        startActivity(intent);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error occurred", Toast.LENGTH_SHORT).show();
            }


        }) {
            @Override
            protected Map<String, String> getParams() {
                // Parameters to be sent to the PHP script
                Map<String, String> params = new HashMap<>();
                params.put("date", formattedDateTime);

                params.put("recive_by", recvby);
                params.put("receive_from", recive);
                params.put("tote_id", totno);
                params.put("productname", productn);
                params.put("product_type", productty);
                params.put("weight", pweight);
                params.put("lot", lotno);
                params.put("quantity", qty);
                params.put("location", loct);


                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
