package com.excise.excisemanagement;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class productionend extends AppCompatActivity {

    EditText stamptoinv,damagestamp,damagereason;

    Button produsubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productionend);

        Toolbar toolbar = findViewById(R.id.production_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Production End");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        stamptoinv=findViewById(R.id.returntoinv);
        damagestamp=findViewById(R.id.stampdamaged);
        damagereason= findViewById(R.id.damagereasn);

        damagereason.setVisibility(View.GONE);


        damagestamp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString().trim();
                if (!text.isEmpty()) {
                    damagereason.setVisibility(View.VISIBLE);

                } else {
                    damagereason.setVisibility(View.GONE);

                }
            }
        });
        produsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!damagestamp.getText().toString().trim().isEmpty()
                        && damagereason.getText().toString().trim().isEmpty()) {
                    damagereason.setError("Please enter the damage reason.");
                    damagereason.requestFocus();
                    return;
                }

           productionendform();

            }
        });

    }

    private void productionendform() {
    }
}