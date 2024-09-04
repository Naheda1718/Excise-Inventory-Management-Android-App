package com.excise.excisemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Homefragment extends Fragment {


    GridView gridView;

    CustomAdapter customAdapter;

    String url = "http://10.0.0.154/inventory/fetchitems.php";


    int icons[] = {R.drawable.addinventory, R.drawable.viewinventory, R.drawable.barcodscan1, R.drawable.stamps,R.drawable.returnvendor};
    String name[] = {"Add Inventory", "View Inventory", "Quick Scan", "Excise Management","RTV Product Excise"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_homefragment, container, false);


        gridView = view.findViewById(R.id.gridView);


        customAdapter = new CustomAdapter(name, icons, getActivity());
        gridView.setAdapter(customAdapter);


//        customAdapter = new CustomAdapter(this, dataList);
//        gridView.setAdapter(customAdapter);

        //  fetchitems();
        if (getActivity() != null) {
            getActivity().setTitle("Home");
        }

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        openFragment(new Add_inventory());
                        break;
                    case 1:
                        openFragment1(new View_Inventory());
                        break;
                    case 2:
                        scanbarcode();
                        break;
                    case 3:
                       // openFragment2(new Excise_Section());
                        openexcisemanagement();
                        break;
                }
            }
        });
        return view;

    }

    private void openexcisemanagement() {

        Intent intent = new Intent(getActivity(), Excise_management.class);
        startActivity(intent);
    }

    private void scanbarcode() {
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

                Bundle bundle = new Bundle();
                bundle.putString("key", scannedData);
                Quick_scan_fagment quickScanFagment = new Quick_scan_fagment();
                quickScanFagment.setArguments(bundle);

                getFragmentManager().beginTransaction().replace(R.id.fragment_container, quickScanFagment).addToBackStack(null).commit();


                Toast.makeText(getActivity(), "Scanned: " + scannedData, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openFragment1(View_Inventory viewInventory) {
        if (getActivity() != null) {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, viewInventory)
                    .addToBackStack(null)
                    .commit();
        }
//        Intent intent = new Intent(getActivity(), showdetails.class);
//        startActivity(intent);
    }

    private void openFragment(Fragment fragment) {
        if (getActivity() != null) {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    private void openFragment2(Fragment excisefragment) {
        if (getActivity() != null) {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, excisefragment)
                    .addToBackStack(null)
                    .commit();
        }
    }


    private void fetchitems() {
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
                                String id = itemObject.getString("id");
                                String name = itemObject.getString("name");
                                String image = itemObject.getString("image");

                                // Create DataModel object and add it to dataList
                                //dataList.add(new DataModel(id,name, image));
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
                Toast.makeText(getContext(), "Error occurred", Toast.LENGTH_SHORT).show();
            }
        });

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }



}

