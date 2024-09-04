package com.excise.excisemanagement;

import android.app.Dialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
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


public class inprocessfragment extends Fragment {

    private ArrayList<Inprocessitems> inprocesslist;
    ArrayList<Inprocessitems>filteredList;
    Inprocessadapter inprocessadapter;
    ListView lstinprocess;
    SearchView searchedittext;

    String url = "http://10.0.0.154/excise/fetchinprocessinventorydetails.php";

    String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

    String FILE_NAME = "Inprocess_Inventorydata_"+currentDate +".xls";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_inprocessfragment, container, false);

        lstinprocess = view.findViewById(R.id.lstinprocessinv);

        inprocesslist = new ArrayList<>();

        inprocessadapter = new Inprocessadapter(getActivity(), inprocesslist);
        lstinprocess.setAdapter(inprocessadapter);

        searchedittext=view.findViewById(R.id.searchEditText);


        searchedittext.setQueryHint("Search By Province");
        searchedittext.setIconifiedByDefault(false);

        EditText searchEditText = searchedittext.findViewById(androidx.appcompat.R.id.search_src_text);

// Set hint color and text color programmatically
        searchEditText.setHintTextColor(getResources().getColor(R.color.lightwhite));
        searchEditText.setTextColor(getResources().getColor(R.color.white));

        FloatingActionButton fab = view.findViewById(R.id.fab);

        loadinprocessdetails();



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                exportinprocessdetails(getActivity());
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

    private void showBlankDialog() {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialoge_blank);

        // Optionally, you can set a title for the dialog
        dialog.setTitle("Blank Dialog");

        dialog.show(); // Show the dialog

    }

    private void filter(String newText) {
        filteredList = new ArrayList<>();
        for (Inprocessitems inprocessitems : inprocesslist) {
            if (inprocessitems.getProvince().toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(inprocessitems);
            }
        }
        inprocessadapter.filterlist(filteredList);




    }

    private void exportinprocessdetails(FragmentActivity activity) {
        if (searchedittext.getQuery().toString().isEmpty()) {
            InprocessexportList(activity, inprocesslist);
        } else {
            InprocessexportList(activity, filteredList);
        }
    }


    private void InprocessexportList(FragmentActivity activity, ArrayList<Inprocessitems> list) {


        HSSFWorkbook workbook = new HSSFWorkbook();

        // Create a sheet
        Sheet sheet = workbook.createSheet("Receive Inventory Data");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Date");
        headerRow.createCell(1).setCellValue("Province");
        headerRow.createCell(2).setCellValue("Stamps Taken");
        headerRow.createCell(3).setCellValue("Stamp Matches Province ");
        headerRow.createCell(4).setCellValue("Product Name ");
        headerRow.createCell(5).setCellValue("Lot No#");
        headerRow.createCell(6).setCellValue("Pack Date");
        headerRow.createCell(7).setCellValue("Location");
        headerRow.createCell(8).setCellValue("Stamps Return to Inventory");
        headerRow.createCell(9).setCellValue("Damage stamps");
        headerRow.createCell(10).setCellValue("Reason For Damage");
        headerRow.createCell(11).setCellValue("Done By");



        try {
            // Add ListView data
            for (int i = 0; i < list.size(); i++) {

                Inprocessitems currentItem = list.get(i);
                Row row = sheet.createRow(i + 1); // Start from row 1 (after header)
                row.createCell(0).setCellValue(currentItem.getDatetime());
                row.createCell(1).setCellValue(currentItem.getProvince());
                row.createCell(2).setCellValue(currentItem.getStamptaken());
                row.createCell(3).setCellValue(currentItem.getStampmatches());
                row.createCell(4).setCellValue(currentItem.getProduct());
                row.createCell(5).setCellValue(currentItem.getLot());
                row.createCell(6).setCellValue(currentItem.getPackagedate());
                row.createCell(7).setCellValue(currentItem.getLocation());
                row.createCell(8).setCellValue(currentItem.getStamptoinv());
                row.createCell(9).setCellValue(currentItem.getDamagestamp());
                row.createCell(10).setCellValue(currentItem.getReasndamage());
                row.createCell(11).setCellValue(currentItem.getDoneby());
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



    private void loadinprocessdetails() {
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

                                String inprocessid=itemObject.getString("id");
                                String datetime = itemObject.getString("datetime");
                                String province = itemObject.getString("provincename");
                                String stamptaken = itemObject.getString("stampstaken");
                                String stampmatches = itemObject.getString("confirmstamp");
                                String product = itemObject.getString("sku");
                                String lot = itemObject.getString("lotno");
                                String packagedate = itemObject.getString("pacakgedate");
                                String location = itemObject.getString("location");
                                String stamptoinv = itemObject.getString("stamptoinv");
                                String damagestamp = itemObject.getString("damagestamp");
                                String reasndamage = itemObject.getString("reasondamage");


                                String doneby = itemObject.getString("doneby");



                                // Create DataModel object and add it to dataList
                                inprocesslist.add(new Inprocessitems(inprocessid,datetime,province, stamptaken, stampmatches, product, lot, packagedate, location, stamptoinv, damagestamp, reasndamage, doneby));
                            }

                            inprocessadapter.notifyDataSetChanged(); // Refresh GridView
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