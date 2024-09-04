package com.excise.excisemanagement;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class ItemDetailActivity extends AppCompatActivity {


    EditText recieve_from, prodtname, producttype, lot, weight, quantity, tote, location;
    Button addbutton;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        recieve_from = findViewById(R.id.recieve_from);
        prodtname = findViewById(R.id.productname);
        producttype = findViewById(R.id.producttype);
        lot = findViewById(R.id.lot);
        weight = findViewById(R.id.weight);
        quantity = findViewById(R.id.quantity);
        tote = findViewById(R.id.tote);
        location = findViewById(R.id.location);
        addbutton = findViewById(R.id.addbutton);


        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });


    }

    private void addItem() {

        RequestQueue queue = Volley.newRequestQueue(this);

        // Get values from EditText fields
      sharedPreferences = getSharedPreferences("logindetails", MODE_PRIVATE);

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

                        Toast.makeText(ItemDetailActivity.this, "added successfully", Toast.LENGTH_SHORT).show();
                        recieve_from.setText("");
                        prodtname.setText("");
                        producttype.setText("");
                        lot.setText("");
                        weight.setText("");
                        quantity.setText("");
                        tote.setText("");
                        location.setText("");


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


