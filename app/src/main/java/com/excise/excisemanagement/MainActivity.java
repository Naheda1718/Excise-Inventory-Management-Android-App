package com.excise.excisemanagement;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class MainActivity extends AppCompatActivity {
    GridView gridView;

    CustomAdapter customAdapter;
    private ArrayList<DataModel> dataList;


    String url = "http://10.0.0.154/inventory/fetchitems.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        gridView = findViewById(R.id.gridView);

        dataList = new ArrayList<>();

        //customAdapter = new CustomAdapter(this, dataList);
        gridView.setAdapter(customAdapter);
//        customAdapter = new CustomAdapter(this, dataList);
//        gridView.setAdapter(customAdapter);

    //    fetchitems();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }

    private void fetchitems() {
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
                        String id = itemObject.getString("id");
                        String name = itemObject.getString("name");
                        String image = itemObject.getString("image");

                        // Create DataModel object and add it to dataList
                      //  dataList.add(new DataModel(id,name, image));
                    }
                    customAdapter.notifyDataSetChanged(); // Refresh GridView
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle errors
                Toast.makeText(MainActivity.this, "Error occurred", Toast.LENGTH_SHORT).show();
            }
        });

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }
}







