package com.excise.excisemanagement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Quick_scan_fagment extends Fragment {

    TextView textviewscanname,scandate, scanrcvby, scanrcvfrm, scanprdctname, scanprcttype,scanlot, scanweight,scanqty
              ,scanloc;

    String url = "http://10.0.0.154/inventory/fetchscandetails.php";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_quick_scan_fagment, container, false);

        textviewscanname=view.findViewById(R.id.scantoteid);
        scandate=view.findViewById(R.id.scandate);
        scanrcvby=view.findViewById(R.id.scanrcvby);
        scanrcvfrm=view.findViewById(R.id.scanrcvfrm);
        scanprdctname=view.findViewById(R.id.scanprdctname);
        scanprcttype=view.findViewById(R.id.scanprcttype);
        scanlot=view.findViewById(R.id.scanlot);
        scanweight=view.findViewById(R.id.scanweight);
        scanqty=view.findViewById(R.id.scanqty);
        scanloc=view.findViewById(R.id.scanloc);

        if (getActivity() != null) {
            getActivity().setTitle("Product Details");
        }

        Bundle bundle=this.getArguments();
        String msg=bundle.getString("key");
        textviewscanname.setText(msg);



     fetchscandetails();




        return view;

    }

    private void fetchscandetails() {
        final String textname = textviewscanname.getText().toString();

        // Create a StringRequest to make a POST request to the URL
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
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

                                    String date = dataObject.getString("date");
                                    String receiveBy = dataObject.getString("recive_by");
                                    String receiveFrom = dataObject.getString("receive_from");

                                    String productName = dataObject.getString("productname");
                                    String productType = dataObject.getString("product_type");
                                    String lot = dataObject.getString("lot");
                                    String weight = dataObject.getString("weight");
                                    String quantity = dataObject.getString("quantity");
                                    String location = dataObject.getString("location");

                                    // Set values to TextViews
                                    scandate.setText(date);
                                    scanrcvby.setText(receiveBy);
                                    scanrcvfrm.setText(receiveFrom);
                                    scanprdctname.setText(productName);
                                    scanprcttype.setText(productType);
                                    scanlot.setText(lot);
                                    scanweight.setText(weight);
                                    scanqty.setText(quantity);
                                    scanloc.setText(location);

                                }

                            } else {
                                // Handle unsuccessful response
                                Toast.makeText(getActivity(), "Failed to fetch data", Toast.LENGTH_SHORT).show();
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
                params.put("tote_id", textname);
                return params;
            }
        };

        // Add the request to the RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

}