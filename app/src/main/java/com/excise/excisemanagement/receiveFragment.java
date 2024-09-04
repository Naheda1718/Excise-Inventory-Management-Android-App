package com.excise.excisemanagement;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class receiveFragment extends Fragment {


    private ArrayList<Receiveitems> rcvitems;

    ArrayList<Receiveitems>filteredList;
    Receiveadapter receiveadapter;
     ListView lstrcvitems;

     SearchView searchedittext;



    String url = "http://10.0.0.154/excise/fetchrcvexcisedetails.php";
    String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

    String FILE_NAME = "Receive_Inventorydata"+currentDate +".xls";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_receive, container, false);

        lstrcvitems = view.findViewById(R.id.lstrcvitems);

        rcvitems = new ArrayList<>();

        receiveadapter = new Receiveadapter(getActivity(), rcvitems);
        lstrcvitems.setAdapter(receiveadapter);
        searchedittext=view.findViewById(R.id.searchEditText);

        searchedittext.setQueryHint("Search By Province");
        searchedittext.setIconifiedByDefault(false);

        EditText searchEditText = searchedittext.findViewById(androidx.appcompat.R.id.search_src_text);

// Set hint color and text color programmatically
        searchEditText.setHintTextColor(getResources().getColor(R.color.lightwhite));
        searchEditText.setTextColor(getResources().getColor(R.color.white));

        // Set the hint text color
        FloatingActionButton fab = view.findViewById(R.id.fab);

        loadrcvdetails();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exportrcvdetails(getActivity());
            }
        });

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


  return view;
    }


    private void filter(String newText) {
        filteredList = new ArrayList<>();
        for (Receiveitems receiveitems : rcvitems) {
            if (receiveitems.getProvince().toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(receiveitems);
            }
        }
        receiveadapter.filterlist(filteredList);




    }
    private void exportrcvdetails(FragmentActivity activity) {
        if (searchedittext.getQuery().toString().isEmpty()) {
            exportList(activity, rcvitems);
        } else {
            exportList(activity, filteredList);
        }
    }


    private void exportList(FragmentActivity activity, ArrayList<Receiveitems> list) {


        HSSFWorkbook workbook = new HSSFWorkbook();

        // Create a sheet
        Sheet sheet = workbook.createSheet("Receive Inventory Data");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Date");
        headerRow.createCell(1).setCellValue("Province");
        headerRow.createCell(2).setCellValue("Excise Matches Province");
    //    headerRow.createCell(3).setCellValue("Comment ");
        headerRow.createCell(3).setCellValue("Stamps Received ");
        headerRow.createCell(4).setCellValue("Location");
       // headerRow.createCell(6).setCellValue("Document");
        headerRow.createCell(5).setCellValue("Additional Comment");
        headerRow.createCell(6).setCellValue("Received By");



        try {
            // Add ListView data
            for (int i = 0; i < list.size(); i++) {

                Receiveitems currentItem = list.get(i);
                Row row = sheet.createRow(i + 1); // Start from row 1 (after header)
                row.createCell(0).setCellValue(currentItem.getDatetime());
                row.createCell(1).setCellValue(currentItem.getProvince());
                row.createCell(2).setCellValue(currentItem.getExcise());
               // row.createCell(3).setCellValue(currentItem.getCmnt());
                row.createCell(3).setCellValue(currentItem.getStamp());
                row.createCell(4).setCellValue(currentItem.getLocation());
           //     row.createCell(6).setCellValue(currentItem.getDocument());
                row.createCell(5).setCellValue(currentItem.getAddcommnt());
                row.createCell(6).setCellValue(currentItem.getDoneby());
            }

            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), FILE_NAME);
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                workbook.write(fileOutputStream);
                fileOutputStream.close();
                workbook.close();

                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri uri = FileProvider.getUriForFile(getContext(), getActivity().getPackageName() + ".provider", file);
                intent.setDataAndType(uri, "application/vnd.ms-excel");
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(intent);
                Toast.makeText(getActivity(), "Excel file exported to " + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Failed to export Excel file", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void loadrcvdetails() {
       // rcvitems.clear();

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
                                String datetime = itemObject.getString("datetime");
                                String provincename = itemObject.getString("provincename");
                                String excise = itemObject.getString("excisematches");
                             //   String comment = itemObject.getString("comment");
                                String stamp = itemObject.getString("stamprcv");
                                String location = itemObject.getString("location");
                               // String document = itemObject.getString("document");
                                String addcomt = itemObject.getString("addcomment");
                                String doneby = itemObject.getString("rcvby");



                                // Create DataModel object and add it to dataList
                                rcvitems.add(new Receiveitems(datetime,provincename,excise,stamp,location,addcomt,doneby));
                            }

                            receiveadapter.notifyDataSetChanged(); // Refresh GridView
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