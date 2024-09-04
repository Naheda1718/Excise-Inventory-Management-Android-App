package com.excise.excisemanagement;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class Inprocess_section extends AppCompatActivity {

    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inprocess_section);



        Toolbar toolbar = findViewById(R.id.inprocesstrack_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("In-process Excise Tracking");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Button button1 = findViewById(R.id.inprocessstart);
        Button button2 = findViewById(R.id.inprocessend);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentFragment = new Inprocess_start();
                loadFragment(currentFragment);
                // loadFragment(new receiveFragment());
                //   button1.setBackgroundColor(getResources().getColor(R.color.lavender));
                button1.setBackgroundColor(getResources().getColor(R.color.green));
                button2.setBackgroundColor(getResources().getColor(R.color.grey)); // Reset button2 color if needed

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentFragment = new Inprocess_end();
                loadFragment(currentFragment);
                //   loadFragment(new inprocessfragment());
                button2.setBackgroundColor(getResources().getColor(R.color.green));
                button1.setBackgroundColor(getResources().getColor(R.color.grey)); // Reset button2 color if needed

            }
        });
        currentFragment = new Inprocess_start(); // Change to the desired default fragment
        loadFragment(currentFragment);
        button1.setBackgroundColor(getResources().getColor(R.color.green));
        button2.setBackgroundColor(getResources().getColor(R.color.grey));
    }
    @Override
    public void onBackPressed() {
        // Check if currentFragment is not null and is an instance of inprocessfragment
        if (currentFragment != null && currentFragment instanceof inprocessfragment) {
            currentFragment = null; // Set currentFragment to null
            //   button2.setBackgroundColor(getResources().getColor(android.R.color.darker_gray)); // Reset button2 color
        } else {
            super.onBackPressed(); // Default behavior for back button press
        }
    }


    private void openrcvfragment() {

    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.Fragment_inprocess, fragment);

        fragmentTransaction.commit();
    }


}