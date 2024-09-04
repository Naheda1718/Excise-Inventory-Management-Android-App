package com.excise.excisemanagement;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.text.SimpleDateFormat;
import java.util.Date;

public class rtvproduct extends AppCompatActivity {

    EditText rtvnumber,rtvproduct,rtvlot,rtvpackeddate,rtvqty,rtvreason,disposition,comment,excisenumber,rtvdate;

    Button submitrtv;
    SharedPreferences sharedPreferences;
    Date currentDate = new Date();

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // Format the current date and time as a string
    String formattedDateTime = dateFormat.format(currentDate);

    String url="http://10.0.0.154/excise/addtrackstamp.php";

    String[] options = {"Select Province", "ON", "QC", "NS", "NB", "MB", "BC", "PE", "SK", "AB", "NL", "NT", "YT", "NU"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rtvproduct);

        Toolbar toolbar = findViewById(R.id.rtvtoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("RTV Product Excise");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Spinner spinner=findViewById(R.id.statespinner);
        rtvnumber=findViewById(R.id.rtvnumber);
        rtvdate=findViewById(R.id.datereturn);
        submitrtv=findViewById(R.id.rtvsubmit);
        rtvproduct=findViewById(R.id.rtvproductname);
        rtvlot=findViewById(R.id.rtvlot);
        rtvpackeddate=findViewById(R.id.rtvpackeddate);
        rtvqty=findViewById(R.id.rtvquantity);
        rtvreason=findViewById(R.id.rtvreason);
        disposition=findViewById(R.id.rtvdisposition);
        comment=findViewById(R.id.rtvcomment);


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, options);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);


        submitrtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addrtvform();
            }
        });
    }

    private void addrtvform() {



    }
}