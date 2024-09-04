package com.excise.excisemanagement;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class showdetails extends AppCompatActivity {
    private RecyclerView recyclerView;

    ListView listView;

    private ArrayList<Item> itemList;
    DetailsAdapter detailsAdapter;


    SearchView searchEditText ;
    String url = "http://10.0.0.154/inventory/fetchdetails.php";
    private String previousQuery = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showdetails);

        listView = findViewById(R.id.lstStockItems);
        searchEditText=findViewById(R.id.searchEditText);

        itemList = new ArrayList<>();

        detailsAdapter = new DetailsAdapter(this, itemList);
        listView.setAdapter(detailsAdapter);
        Toolbar toolbar = findViewById(R.id.custom_toolbar);
        setSupportActionBar(toolbar);
       toolbar.setTitle("View Inventory");
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Set the parent activity for proper navigation
        // Replace ".DrawerActivity" with the name of your parent activity
        // This will handle the up navigation when the back arrow is clicked
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Set custom icon


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




    }

    private void loadSavedDetails() {

        itemList.clear();

    RequestQueue queue = Volley.newRequestQueue(this);

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


                            // Create DataModel object and add it to dataList
                            itemList.add(new Item(datetime,rcvby,rcvfrom,toteid,productname,product_type,lot,weight,quantity,location));
                        }
                        detailsAdapter.notifyDataSetChanged(); // Refresh GridView
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }


            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            // Handle errors
            Toast.makeText(getApplicationContext(), "Error occurred", Toast.LENGTH_SHORT).show();
        }
    });

// Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
                }
    private void filter(String newtext) {
        ArrayList<Item> filteredList = new ArrayList<>();
        for (Item item:itemList){
            if (item.getProductname().toLowerCase().contains(newtext.toLowerCase())) {
                filteredList.add(item);
            }

        }
        detailsAdapter.filterlist(filteredList);
    }

}

