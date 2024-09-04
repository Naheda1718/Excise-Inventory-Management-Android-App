package com.excise.excisemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class View_Inventory extends Fragment {

    private RecyclerView recyclerView;

    ListView listView;

    private ArrayList<Item> itemList;
    DetailsAdapter detailsAdapter;


    SearchView searchEditText;
    Button scan;
    Spinner spinner1,spinner2;

    String url = "http://10.0.0.154/inventory/fetchdetails.php";
    private String previousQuery = "";
    private BottomNavigationView bottomNavigationView;

    private ArrayList<String> dateList = new ArrayList<>();
    private ArrayAdapter<String> dateAdapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view__inventory, container, false);
        listView = view.findViewById(R.id.lstStockItems);
        searchEditText = view.findViewById(R.id.searchEditText);

        spinner1=view.findViewById(R.id.spinner1);
        spinner2=view.findViewById(R.id.spinner2);



        itemList = new ArrayList<>();


        dateAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, dateList);
        dateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dateAdapter);

        //  scan = view.findViewById(R.id.barcodescan);
        //  bottomNavigationView = view.findViewById(R.id.bottom_navigation);

        // bottomNavigationView.setOnNavigationItemSelectedListener(navListener);



//

//
        // Set up custom toolbar



//

        if (getActivity() != null) {
            getActivity().setTitle("View Inventory");
        }

        detailsAdapter = new DetailsAdapter(getActivity(), itemList);
        listView.setAdapter(detailsAdapter);


        loadSavedDetails();


        searchEditText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;

            }
        });

        return view;


    }

    private void scancode() {
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
                Toast.makeText(getActivity(), "Scanned: " + scannedData, Toast.LENGTH_SHORT).show();
            }
        }
    }




    private void loadSavedDetails() {

        itemList.clear();

        RequestQueue queue = Volley.newRequestQueue(getActivity());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        try {
                            JSONArray dataArray = response.getJSONArray("data");

                            // Process each JSON object in the "data" array
                            for (int i = 0; i < dataArray.length(); i++) {
                                JSONObject itemObject = dataArray.getJSONObject(i);
                                String datetime = itemObject.getString("date");
                                String rcvby = itemObject.getString("recive_by");
                                String rcvfrom = itemObject.getString("receive_from");
                                String toteid = itemObject.getString("tote_id");
                                String productname = itemObject.getString("productname");
                                String product_type = itemObject.getString("product_type");
                                String lot = itemObject.getString("lot");
                                String weight = itemObject.getString("weight");
                                String quantity = itemObject.getString("quantity");
                                String location = itemObject.getString("location");

                                dateList.add(datetime);
                                // Create DataModel object and add it to dataList
                                itemList.add(new Item(datetime, rcvby, rcvfrom, toteid, productname, product_type, lot, weight, quantity, location));
                            }
                            dateAdapter.notifyDataSetChanged();
                            detailsAdapter.notifyDataSetChanged(); // Refresh GridView
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

    private void filter(String newtext) {
        ArrayList<Item> filteredList = new ArrayList<>();
        for (Item item : itemList) {
            if (item.getProductname().toLowerCase().contains(newtext.toLowerCase())) {
                filteredList.add(item);
            } else {
                // If the entered text is alphanumeric, try to find matches in the lot number
                String itemLot = item.getLot().toLowerCase();
                // Check if the lot number contains the entered alphanumeric text
                if (itemLot.contains(newtext.toLowerCase())) {
                    filteredList.add(item);
                } else {
                    String productid = item.getToteid().toLowerCase();
                    // Check if the lot number contains the entered alphanumeric text
                    if (productid.contains(newtext.toLowerCase())) {
                        filteredList.add(item);

                    }

                }
                detailsAdapter.filterlist(filteredList);
            }


        }

    }
}