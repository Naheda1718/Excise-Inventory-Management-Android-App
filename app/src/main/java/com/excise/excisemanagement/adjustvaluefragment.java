package com.excise.excisemanagement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

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


public class adjustvaluefragment extends Fragment {

    private ArrayList<Adjustitems> adjustlist;

    ArrayList<Adjustitems>filteredList;
  
    Adjustadapter adjustadapter;
    ListView lstadjustment;
    SearchView searchedittext;

    SearchView searchView;
    String url = "http://10.0.0.154/excise/fetchadjustvalue.php";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_adjustvaluefragment, container, false);
        
        
        
        lstadjustment=view.findViewById(R.id.lstadjustment);
        adjustlist = new ArrayList<>();

        adjustadapter = new Adjustadapter(getActivity(), adjustlist);
        lstadjustment.setAdapter(adjustadapter);

        searchedittext=view.findViewById(R.id.searchEditText);
        searchedittext.setQueryHint("Search By Province");
        searchedittext.setIconifiedByDefault(false);


        EditText searchEditText = searchedittext.findViewById(androidx.appcompat.R.id.search_src_text);

// Set hint color and text color programmatically
        searchEditText.setHintTextColor(getResources().getColor(R.color.lightwhite));
        searchEditText.setTextColor(getResources().getColor(R.color.white));




        searchedittext.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

        loadadjustvaluedetails();
        
        return  view;
    }

    private void filter(String newText) {
        filteredList = new ArrayList<>();
        for (Adjustitems adjustitems : adjustlist) {
            if (adjustitems.getProvincename().toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(adjustitems);
            }
        }
        adjustadapter.filterlist(filteredList);




    }

    private void loadadjustvaluedetails() {

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


                                String date = itemObject.getString("datetime");
                                String province = itemObject.getString("provincename");
                                String adjustreason = itemObject.getString("adjustreason");
                                String adjustvalue = itemObject.getString("valueadjusted");


                                String doneby = itemObject.getString("doneby");



                                // Create DataModel object and add it to dataList
                                adjustlist.add(new Adjustitems(date,province,adjustvalue,adjustreason,doneby));
                            }

                            adjustadapter.notifyDataSetChanged(); // Refresh GridView
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

}

