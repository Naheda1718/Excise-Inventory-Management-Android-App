package com.excise.excisemanagement;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.net.ParseException;

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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class excise_report extends AppCompatActivity {

    TextView onstatevalue, abstatevalue, bcstatevalue, qcstatevalue, nsstatevalue,
            nbstatevalue, mbstatevalue, pestatevalue, skstatevalue, nlstatevalue, ntstatevalue, ytstatevalue, nustatevalue;


    TextView onusedvalue, abusedvalue, bcusedvalue, qcusedvalue, nsusedvalue,
            nbusedvalue, mbusedvalue, peusedvalue, skusedvalue, nlusedvalue, ntusedvalue, ytusedvalue, nuusedvalue;

    TextView ondamage, abdamage, bcdamage, qcdamage, nsdamage,
            nbdamage, mbdamage, pedamage, skdamage, nldamage, ntdamage, ytdamage, nudamage;

    TextView onclosing, abclosing, bcclosing, qcclosing, nsclosing,
            nbclosing, mbclosing, peclosing, skclosing, nlclosing, ntclosing, ytclosing, nuclosing;


    TextView onpening,bcopening,abopening,qcopening,nsopening,
              nbopening,mbopening,peopening,skopening,nlopening,ntopening,ytopening,nuopening;

    TextView abprovincename,bcprovince,mbprovince,nbprovince,nlprovince,ntprovince,nsprovince,
             nuprovince,onprovince,peprovince,qcprovince,skprovince,ytprovince;


    TextView abadjust,bcadjust,mbadjust,nbadjust,nladjust,ntadjust,nsadjust,
             nuadjust,onadjust,peadjust,qcadjust,skadjust,ytadjust;

    EditText fromdate, todate;
    Calendar calendar;
    Button searchbtn, rangebutton;

    String url = "http://10.0.0.154/excise/testingreport.php";

    SharedPreferences sharedPreferences;




    TextView onvalue;


    private List<EditText> openingInventoryEditTextList;
    private static final int REQUEST_CODE_WRITE_EXTERNAL_STORAGE = 1;

    String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

  String FILE_NAME = currentDate +"_report_data" +".xls";
    int closingValueOfPreviousMonth = 0;
   // private static final int REQUEST_CODE_STORAGE_PERMISSION = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excise_report);


        Toolbar toolbar = findViewById(R.id.report_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Excise Report");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

onvalue=findViewById(R.id.onvalue);


        onstatevalue = findViewById(R.id.onstatevalue);
        abstatevalue = findViewById(R.id.abstatevalue);
        bcstatevalue = findViewById(R.id.bcstatevalue);
        qcstatevalue = findViewById(R.id.qcstatevalue);
        nbstatevalue = findViewById(R.id.nbstatevalue);
        nsstatevalue = findViewById(R.id.nsstatevalue);
        mbstatevalue = findViewById(R.id.mbstatevalue);
        pestatevalue = findViewById(R.id.pestatevalue);
        skstatevalue = findViewById(R.id.skstatevalue);
        nlstatevalue = findViewById(R.id.nlstatevalue);
        ytstatevalue = findViewById(R.id.ytstatevalue);
        nustatevalue = findViewById(R.id.nustatevalue);
        ntstatevalue = findViewById(R.id.ntstatevalue);


        onusedvalue = findViewById(R.id.onusedvalue);
        abusedvalue = findViewById(R.id.abusedvalue);
        bcusedvalue = findViewById(R.id.bcusedvalue);
        qcusedvalue = findViewById(R.id.qcusedvalue);
        nbusedvalue = findViewById(R.id.nbusedvalue);
        nsusedvalue = findViewById(R.id.nsusedvalue);
        mbusedvalue = findViewById(R.id.mbusedvalue);
        peusedvalue = findViewById(R.id.peusedvalue);
        skusedvalue = findViewById(R.id.skusedvalue);
        nlusedvalue = findViewById(R.id.nlusedvalue);
        ytusedvalue = findViewById(R.id.ytusedvalue);
        nuusedvalue = findViewById(R.id.nuusedvalue);
        ntusedvalue = findViewById(R.id.ntusedvalue);


        ondamage = findViewById(R.id.ondamagevalue);
        abdamage = findViewById(R.id.abdamagevalue);
        bcdamage = findViewById(R.id.bcdamagevalue);
        qcdamage = findViewById(R.id.qcdamagevalue);
        nbdamage = findViewById(R.id.nbdamagevalue);
        nsdamage = findViewById(R.id.nsdamagevalue);
        mbdamage = findViewById(R.id.mbdamagevalue);
        pedamage = findViewById(R.id.pedamagevalue);
        skdamage = findViewById(R.id.skdamagevalue);
        nldamage = findViewById(R.id.nldamagevalue);
        ytdamage = findViewById(R.id.ytdamagevalue);
        nudamage = findViewById(R.id.nudamagevalue);
        ntdamage = findViewById(R.id.ntdamagevalue);


        onclosing = findViewById(R.id.onclosing);
        abclosing = findViewById(R.id.abclosing);
        bcclosing = findViewById(R.id.bcclosing);
        qcclosing = findViewById(R.id.qcclosing);
        nbclosing = findViewById(R.id.nbclosing);
        nsclosing = findViewById(R.id.nsclosing);
        mbclosing = findViewById(R.id.mbclosing);
        peclosing = findViewById(R.id.peclosing);
        skclosing = findViewById(R.id.skclosing);
        nlclosing = findViewById(R.id.nlclosing);
        ytclosing = findViewById(R.id.ytclosing);
        nuclosing = findViewById(R.id.nuclosing);
        ntclosing = findViewById(R.id.ntclosing);


        onpening = findViewById(R.id.onopening);
        abopening = findViewById(R.id.abopening);
        bcopening = findViewById(R.id.bcopening);
        qcopening = findViewById(R.id.qcopening);
        mbopening = findViewById(R.id.mbopening);
        nbopening = findViewById(R.id.nbopening);
        nsopening = findViewById(R.id.nsopening);
        peopening = findViewById(R.id.peopening);
        skopening = findViewById(R.id.skopening1);
        nlopening = findViewById(R.id.nlopening);
        ytopening = findViewById(R.id.ytopening);
        nuopening = findViewById(R.id.nuopening);
        ntopening = findViewById(R.id.ntopening);

        abprovincename=findViewById(R.id.abprovince);
        bcprovince=findViewById(R.id.bcprovince);
        mbprovince=findViewById(R.id.mbprovince);
        nbprovince=findViewById(R.id.nbprovince);
        nlprovince=findViewById(R.id.nlprovince);
        ntprovince=findViewById(R.id.ntprovince);
        nsprovince=findViewById(R.id.nsprovince);
        nuprovince=findViewById(R.id.nuprovince);
        peprovince=findViewById(R.id.peprovince);
        qcprovince=findViewById(R.id.qcprovince);
        skprovince=findViewById(R.id.skprovince);
        ytprovince=findViewById(R.id.ytprovince);




        abadjust=findViewById(R.id.abadjustvalue);
        bcadjust=findViewById(R.id.bcadjustvalue);
        mbadjust=findViewById(R.id.mbadjustvalue);
        nbadjust=findViewById(R.id.nbadjustvalue);
        nladjust=findViewById(R.id.nladjustvalue);
        ntadjust=findViewById(R.id.ntadjustvalue);
        nsadjust=findViewById(R.id.nsadjustvalue);
        nuadjust=findViewById(R.id.nuadjustvalue);
        peadjust=findViewById(R.id.peadjustvalue);
        qcadjust=findViewById(R.id.qcadjustvalue);
        skadjust=findViewById(R.id.skadjustvalue);
        ytadjust=findViewById(R.id.ytadjustvalue);
        onadjust=findViewById(R.id.onadjustvalue);


        fromdate = findViewById(R.id.fromdate);
        todate = findViewById(R.id.todate);
        searchbtn = findViewById(R.id.searchbtn);

      //  rangebutton=findViewById(R.id.daterange);

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exportToExcel();
            }
        });

        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                loadrcvdetails();
//                loadstrackstampdetaiils();

loadreportdetails();

            }



        });


//        fromdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MaterialDatePicker<Pair<Long, Long>> materialDatePicker = MaterialDatePicker.Builder.dateRangePicker()
//                        .setSelection(Pair.create(MaterialDatePicker.thisMonthInUtcMilliseconds(),
//                                MaterialDatePicker.todayInUtcMilliseconds()))
//                        .build();
//
//                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
//                    @Override
//                    public void onPositiveButtonClick(Pair<Long, Long> selection) {
//                        long startDateMillis = selection.first;
//                        long endDateMillis = selection.second;
//
//                        // Convert milliseconds to Calendar objects
//                        Calendar startCalendar = Calendar.getInstance();
//                        startCalendar.setTimeInMillis(startDateMillis);
//                        Calendar endCalendar = Calendar.getInstance();
//                        endCalendar.setTimeInMillis(endDateMillis);
//
//                        // Adjust for timezone offset
//                        TimeZone timeZone = TimeZone.getDefault();
//                        int offsetFromUTC = timeZone.getOffset(startDateMillis);
//
//                        // Adjust dates based on 0-based or 1-based indexing for months
//                        startCalendar.add(Calendar.MILLISECOND, offsetFromUTC);
//                        endCalendar.add(Calendar.MILLISECOND, offsetFromUTC);
//                        startCalendar.add(Calendar.DAY_OF_MONTH, 1); // Adjust by one day
//                        endCalendar.add(Calendar.DAY_OF_MONTH, 1); // Adjust by one day
//
//                        // Format the dates
//                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
//
//                        String startDateString = sdf.format(startCalendar.getTime());
//                        String endDateString = sdf.format(endCalendar.getTime());
//
//                        fromdate.setText(startDateString);
//                        todate.setText(endDateString);
//                    }
//                });
//
//                materialDatePicker.show(getSupportFragmentManager(), "");
//            }
//        });





        calendar = Calendar.getInstance();


    fromdate.setOnClickListener(v -> showDatePickerDialog());

        todate.setOnClickListener(v -> showDatePickerDialog1());
    }





    private void checkPermissionAndExport() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_WRITE_EXTERNAL_STORAGE);
        } else {
            exportToExcel();
        }
    }

    private void exportToExcel() {
        // Create a new workbook
        HSSFWorkbook workbook = new HSSFWorkbook();

        // Create a sheet
        Sheet sheet = workbook.createSheet("Report Data");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Province");
        headerRow.createCell(1).setCellValue("Opening Inventory");
        headerRow.createCell(2).setCellValue("Stamps Received");
        headerRow.createCell(3).setCellValue("Stamps Used For Products ");
        headerRow.createCell(4).setCellValue("Unusable Stamps ");
        headerRow.createCell(5).setCellValue("Inventory Adjustments ");

        headerRow.createCell(6).setCellValue("Closing Inventory");


     //   if (!abclosing.getText().toString().equals("")) {

            Row dataRow = sheet.createRow(1);
            dataRow.createCell(0).setCellValue(abprovincename.getText().toString());
            dataRow.createCell(1).setCellValue(abopening.getText().toString());
            dataRow.createCell(2).setCellValue(abstatevalue.getText().toString());
            dataRow.createCell(3).setCellValue(abusedvalue.getText().toString());
            dataRow.createCell(4).setCellValue(abdamage.getText().toString());
        dataRow.createCell(5).setCellValue(abadjust.getText().toString());
            dataRow.createCell(6).setCellValue(abclosing.getText().toString());

        Row dataRow1 = sheet.createRow(2);
        dataRow1.createCell(0).setCellValue(bcprovince.getText().toString());
        dataRow1.createCell(1).setCellValue(bcopening.getText().toString());
        dataRow1.createCell(2).setCellValue(bcstatevalue.getText().toString());
        dataRow1.createCell(3).setCellValue(bcusedvalue.getText().toString());
        dataRow1.createCell(4).setCellValue(bcdamage.getText().toString());
        dataRow1.createCell(5).setCellValue(bcadjust.getText().toString());
        dataRow1.createCell(6).setCellValue(bcclosing.getText().toString());

        Row dataRow2 = sheet.createRow(3);
        dataRow2.createCell(0).setCellValue(mbprovince.getText().toString());
        dataRow2.createCell(1).setCellValue(mbopening.getText().toString());
        dataRow2.createCell(2).setCellValue(mbstatevalue.getText().toString());
        dataRow2.createCell(3).setCellValue(mbusedvalue.getText().toString());
        dataRow2.createCell(4).setCellValue(mbdamage.getText().toString());
        dataRow2.createCell(5).setCellValue(mbadjust.getText().toString());
        dataRow2.createCell(6).setCellValue(mbclosing.getText().toString());

        Row dataRow3 = sheet.createRow(4);
        dataRow3.createCell(0).setCellValue(nbprovince.getText().toString());
        dataRow3.createCell(1).setCellValue(nbopening.getText().toString());
        dataRow3.createCell(2).setCellValue(nbstatevalue.getText().toString());
        dataRow3.createCell(3).setCellValue(nbusedvalue.getText().toString());
        dataRow3.createCell(4).setCellValue(nbdamage.getText().toString());
        dataRow3.createCell(5).setCellValue(nbadjust.getText().toString());
        dataRow3.createCell(6).setCellValue(nbclosing.getText().toString());

        Row dataRow4 = sheet.createRow(5);
        dataRow4.createCell(0).setCellValue(nlprovince.getText().toString());
        dataRow4.createCell(1).setCellValue(nlopening.getText().toString());
        dataRow4.createCell(2).setCellValue(nlstatevalue.getText().toString());
        dataRow4.createCell(3).setCellValue(nlusedvalue.getText().toString());
        dataRow4.createCell(4).setCellValue(nldamage.getText().toString());
        dataRow4.createCell(5).setCellValue(nladjust.getText().toString());
        dataRow4.createCell(6).setCellValue(nlclosing.getText().toString());

        Row dataRow5 = sheet.createRow(6);
        dataRow5.createCell(0).setCellValue(nsprovince.getText().toString());
        dataRow5.createCell(1).setCellValue(nsopening.getText().toString());
        dataRow5.createCell(2).setCellValue(nsstatevalue.getText().toString());
        dataRow5.createCell(3).setCellValue(nsusedvalue.getText().toString());
        dataRow5.createCell(4).setCellValue(nsdamage.getText().toString());
        dataRow5.createCell(5).setCellValue(nsadjust.getText().toString());
        dataRow5.createCell(6).setCellValue(nsclosing.getText().toString());

        Row dataRow6 = sheet.createRow(7);
        dataRow6.createCell(0).setCellValue(nuprovince.getText().toString());
        dataRow6.createCell(1).setCellValue(nuopening.getText().toString());
        dataRow6.createCell(2).setCellValue(nustatevalue.getText().toString());
        dataRow6.createCell(3).setCellValue(nuusedvalue.getText().toString());
        dataRow6.createCell(4).setCellValue(nudamage.getText().toString());
        dataRow6.createCell(5).setCellValue(nuadjust.getText().toString());
        dataRow6.createCell(6).setCellValue(nuclosing.getText().toString());

        Row dataRow7 = sheet.createRow(8);
        dataRow7.createCell(0).setCellValue(onvalue.getText().toString());
        dataRow7.createCell(1).setCellValue(onpening.getText().toString());
        dataRow7.createCell(2).setCellValue(onstatevalue.getText().toString());
        dataRow7.createCell(3).setCellValue(onusedvalue.getText().toString());
        dataRow7.createCell(4).setCellValue(ondamage.getText().toString());
        dataRow7.createCell(5).setCellValue(onadjust.getText().toString());
        dataRow7.createCell(6).setCellValue(onclosing.getText().toString());

        Row dataRow8 = sheet.createRow(9);
        dataRow8.createCell(0).setCellValue(peprovince.getText().toString());
        dataRow8.createCell(1).setCellValue(peopening.getText().toString());
        dataRow8.createCell(2).setCellValue(pestatevalue.getText().toString());
        dataRow8.createCell(3).setCellValue(peusedvalue.getText().toString());
        dataRow8.createCell(4).setCellValue(pedamage.getText().toString());
        dataRow8.createCell(5).setCellValue(peadjust.getText().toString());
        dataRow8.createCell(6).setCellValue(peclosing.getText().toString());

        Row dataRow9 = sheet.createRow(10);
        dataRow9.createCell(0).setCellValue(qcprovince.getText().toString());
        dataRow9.createCell(1).setCellValue(qcopening.getText().toString());
        dataRow9.createCell(2).setCellValue(qcstatevalue.getText().toString());
        dataRow9.createCell(3).setCellValue(qcusedvalue.getText().toString());
        dataRow9.createCell(4).setCellValue(qcdamage.getText().toString());
        dataRow9.createCell(5).setCellValue(qcadjust.getText().toString());
        dataRow9.createCell(6).setCellValue(qcclosing.getText().toString());

        Row dataRow10 = sheet.createRow(11);
        dataRow10.createCell(0).setCellValue(skprovince.getText().toString());
        dataRow10.createCell(1).setCellValue(skopening.getText().toString());
        dataRow10.createCell(2).setCellValue(skstatevalue.getText().toString());
        dataRow10.createCell(3).setCellValue(skusedvalue.getText().toString());
        dataRow10.createCell(4).setCellValue(skdamage.getText().toString());
        dataRow10.createCell(5).setCellValue(skadjust.getText().toString());
        dataRow10.createCell(6).setCellValue(skclosing.getText().toString());

        Row dataRow11 = sheet.createRow(12);
        dataRow11.createCell(0).setCellValue(ytprovince.getText().toString());
        dataRow11.createCell(1).setCellValue(ytopening.getText().toString());
        dataRow11.createCell(2).setCellValue(ytstatevalue.getText().toString());
        dataRow11.createCell(3).setCellValue(ytusedvalue.getText().toString());
        dataRow11.createCell(4).setCellValue(ytdamage.getText().toString());
        dataRow11.createCell(5).setCellValue(ytadjust.getText().toString());
        dataRow11.createCell(6).setCellValue(ytclosing.getText().toString());














        // Save workbook to file
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), FILE_NAME);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            workbook.write(fileOutputStream);
            fileOutputStream.close();
            workbook.close();

            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri = FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName() + ".provider", file);
            intent.setDataAndType(uri, "application/vnd.ms-excel");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(intent);


            Toast.makeText(getApplicationContext(), "Excel file exported to " + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Failed to export Excel file", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_WRITE_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                exportToExcel();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void loadreportdetails() {
        String fromDateValue = fromdate.getText().toString();
        String toDateValue = todate.getText().toString();
        String requestUrl = url + "?start_date=" + fromDateValue ;
        Log.d("Request URL", requestUrl);

     //   Log.d("Request", "fromdate: " + fromDateValue + ", todate: " + toDateValue);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, requestUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("Response", "Received response: " + response.toString());
                            String status = response.getString("status");
                            if (status.equals("success")) {
                                JSONArray dataArray = response.getJSONArray("data");

                                // Process each JSON object in the "data" array
                                for (int i = 0; i < dataArray.length(); i++) {
                                    JSONObject itemObject = dataArray.getJSONObject(i);
                                    String provinceName = itemObject.getString("provincename");
                                    int totalStamprcv = itemObject.getInt("total_rcv");
                                    String openiningvalue=itemObject.getString("total_opening");
                                    int totaldifference=itemObject.getInt("total_used");
                                    int totaldamagestamp=itemObject.getInt("total_damage");
                                   int totaladjustedvalue=itemObject.getInt("total_adjust");
                                    int closingvalue=itemObject.getInt("current_closing");

                                    if (provinceName.equals("ON")) {
                                        onstatevalue.setText(String.valueOf(totalStamprcv));
                                        onpening.setText(openiningvalue);


                                        onusedvalue.setText(String.valueOf(totaldifference));
                                        ondamage.setText(String.valueOf(totaldamagestamp));
                                        onadjust.setText(String.valueOf(totaladjustedvalue));
                                        onclosing.setText(String.valueOf(closingvalue));

                                    }else  if (provinceName.equals("AB")) {
                                        abstatevalue.setText(String.valueOf(totalStamprcv));
                                        abopening.setText(openiningvalue);

                                        abusedvalue.setText(String.valueOf(totaldifference));
                                        abdamage.setText(String.valueOf(totaldamagestamp));
                                      abadjust.setText(String.valueOf(totaladjustedvalue));
                                        abclosing.setText(String.valueOf(closingvalue));

                                    }else if (provinceName.equals("BC")) {
                                        bcstatevalue.setText(String.valueOf(totalStamprcv));
                                        bcopening.setText(openiningvalue);

                                        bcusedvalue.setText(String.valueOf(totaldifference));
                                        bcdamage.setText(String.valueOf(totaldamagestamp));
                                     bcadjust.setText(String.valueOf(totaladjustedvalue));
                                        bcclosing.setText(String.valueOf(closingvalue));

                                    }else if (provinceName.equals("QC")) {
                                        qcstatevalue.setText(String.valueOf(totalStamprcv));
                                        qcopening.setText(openiningvalue);
//                                        if(openiningvalue.equals("null")){
//                                            qcopening.setText("0");
//                                        }
                                        qcusedvalue.setText(String.valueOf(totaldifference));
                                        qcdamage.setText(String.valueOf(totaldamagestamp));
                                     qcadjust.setText(String.valueOf(totaladjustedvalue));
                                        qcclosing.setText(String.valueOf(closingvalue));

                                    }else if (provinceName.equals("MB")) {
                                        mbstatevalue.setText(String.valueOf(totalStamprcv));
                                        mbopening.setText(openiningvalue);

                                        mbusedvalue.setText(String.valueOf(totaldifference));
                                        mbdamage.setText(String.valueOf(totaldamagestamp));
                                     mbadjust.setText(String.valueOf(totaladjustedvalue));
                                        mbclosing.setText(String.valueOf(closingvalue));

                                    }else if (provinceName.equals("NB")) {
                                        nbstatevalue.setText(String.valueOf(totalStamprcv));
                                        nbopening.setText(openiningvalue);

                                        nbusedvalue.setText(String.valueOf(totaldifference));
                                        nbdamage.setText(String.valueOf(totaldamagestamp));
                                     nbadjust.setText(String.valueOf(totaladjustedvalue));
                                        nbclosing.setText(String.valueOf(closingvalue));

                                    }else if (provinceName.equals("NS")) {
                                        nsstatevalue.setText(String.valueOf(totalStamprcv));
                                        nsopening.setText(openiningvalue);

                                        nsusedvalue.setText(String.valueOf(totaldifference));
                                        nsdamage.setText(String.valueOf(totaldamagestamp));
                                        nsadjust.setText(String.valueOf(totaladjustedvalue));
                                        nsclosing.setText(String.valueOf(closingvalue));

                                    }else if (provinceName.equals("PE")) {
                                        pestatevalue.setText(String.valueOf(totalStamprcv));
                                        peopening.setText(openiningvalue);

                                        peusedvalue.setText(String.valueOf(totaldifference));
                                        pedamage.setText(String.valueOf(totaldamagestamp));
                                     peadjust.setText(String.valueOf(totaladjustedvalue));
                                        peclosing.setText(String.valueOf(closingvalue));

                                    }else if (provinceName.equals("SK")) {
                                        skstatevalue.setText(String.valueOf(totalStamprcv));
                                        skopening.setText(openiningvalue);

                                        skusedvalue.setText(String.valueOf(totaldifference));
                                        skdamage.setText(String.valueOf(totaldamagestamp));
                                      skadjust.setText(String.valueOf(totaladjustedvalue));
                                        skclosing.setText(String.valueOf(closingvalue));

                                    }else if (provinceName.equals("NL")) {
                                        nlstatevalue.setText(String.valueOf(totalStamprcv));
                                        nlopening.setText(openiningvalue);

                                        nlusedvalue.setText(String.valueOf(totaldifference));
                                        nldamage.setText(String.valueOf(totaldamagestamp));
                                        nladjust.setText(String.valueOf(totaladjustedvalue));
                                        nlclosing.setText(String.valueOf(closingvalue));

                                    }else if (provinceName.equals("YT")) {
                                        ytstatevalue.setText(String.valueOf(totalStamprcv));
                                        ytopening.setText(openiningvalue);

                                        ytusedvalue.setText(String.valueOf(totaldifference));
                                        ytdamage.setText(String.valueOf(totaldamagestamp));
                                       ytadjust.setText(String.valueOf(totaladjustedvalue));
                                        ytclosing.setText(String.valueOf(closingvalue));

                                    }else if (provinceName.equals("NU")) {
                                        nustatevalue.setText(String.valueOf(totalStamprcv));
                                        nuopening.setText(openiningvalue);

                                        nuusedvalue.setText(String.valueOf(totaldifference));
                                        nudamage.setText(String.valueOf(totaldamagestamp));
                                       nuadjust.setText(String.valueOf(totaladjustedvalue));
                                        nuclosing.setText(String.valueOf(closingvalue));

                                    }else if (provinceName.equals("NT")) {
                                        ntstatevalue.setText(String.valueOf(totalStamprcv));
                                        ntopening.setText(openiningvalue);

                                        ntusedvalue.setText(String.valueOf(totaldifference));
                                        ntdamage.setText(String.valueOf(totaldamagestamp));
                                        ntadjust.setText(String.valueOf(totaladjustedvalue));
                                        ntclosing.setText(String.valueOf(closingvalue));

                                    }




                                    // For testing, you can log the values
                                    Log.d("Province", provinceName);
                                    Log.d("Total Stamprcv", String.valueOf(totalStamprcv));
                                }
                            } else {
                                // Handle error case
                                Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_SHORT).show();
                            }

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
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Parameters to be sent in the POST request
                Map<String, String> params = new HashMap<>();
                params.put("start_date", fromDateValue);
                params.put("end_date", toDateValue);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        // Add the request to the RequestQueue
        requestQueue.add(jsonObjectRequest);
    }





    private void StatusComparator() {

        sharedPreferences = getSharedPreferences("Inventory", MODE_PRIVATE);
        String inventory = sharedPreferences.getString(fromdate.getText().toString(), "");

        Toast.makeText(getApplicationContext(),inventory,Toast.LENGTH_SHORT).show();

        String[] parts = inventory.split(",");

        if (parts.length == 3) {
            String status = parts[0]; // ON
            String dateStr = parts[1]; // 2024-02-01
            String closingValue = parts[2]; // 1920

            if (status.equals(onvalue.getText().toString())) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date storedDate = sdf.parse(dateStr);
                    Date fromDateObj = sdf.parse(fromdate.getText().toString());

                    // Check if fromDate is after the stored date
                    if (fromDateObj.after(storedDate)) {
                        // Set the closing value in the onopening field
                        // For now, printing the closing value
                        onpening.setText(closingValue);
                        System.out.println("Closing Value for onopening field: " + closingValue);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (java.text.ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            System.out.println("Invalid stored value format");
        }

        }


    private void showDatePickerDialog() {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year1, monthOfYear, dayOfMonth1) -> {
                    // Set the selected date in the EditText field
                    String formattedDate = String.format("%04d-%02d-%02d", year1, monthOfYear + 1, dayOfMonth1);
                    // Set the formatted date in the EditText field
                    fromdate.setText(formattedDate);
                    //  fromdate.setText(year1+"-"+(monthOfYear + 1)+"-"+ dayOfMonth1 );
                },
                year, month, dayOfMonth);
        datePickerDialog.show();
    }

    private void showDatePickerDialog1() {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year1, monthOfYear, dayOfMonth1) -> {
                    // Set the selected date in the EditText field
                    String formattedDate = String.format("%04d-%02d-%02d", year1, monthOfYear + 1, dayOfMonth1);
                    // Set the formatted date in the EditText field
                    todate.setText(formattedDate);
                    //  todate.setText(year1+"-"+(monthOfYear + 1)+"-"+ dayOfMonth1 );  // Corrected line
                },
                year, month, dayOfMonth);
        datePickerDialog.show();
    }



}