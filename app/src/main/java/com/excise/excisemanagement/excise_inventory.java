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

public class excise_inventory extends AppCompatActivity {
    private Fragment currentFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excise_inventory);

        Toolbar toolbar = findViewById(R.id.exciseinventory_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Excise Inventory");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button butto3=findViewById(R.id.button3);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentFragment = new receiveFragment();
                loadFragment(currentFragment);
               // loadFragment(new receiveFragment());
             //   button1.setBackgroundColor(getResources().getColor(R.color.lavender));
                button1.setBackgroundColor(getResources().getColor(R.color.green));
                button2.setBackgroundColor(getResources().getColor(R.color.grey));
                butto3.setBackgroundColor(getResources().getColor(R.color.grey));// Reset button2 color if needed

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentFragment = new inprocessfragment();
                loadFragment(currentFragment);
             //   loadFragment(new inprocessfragment());
                button2.setBackgroundColor(getResources().getColor(R.color.green));
                butto3.setBackgroundColor(getResources().getColor(R.color.grey));
                button1.setBackgroundColor(getResources().getColor(R.color.grey)); // Reset button2 color if needed

            }
        });

        butto3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentFragment = new adjustvaluefragment();
                loadFragment(currentFragment);
                //   loadFragment(new inprocessfragment());
                butto3.setBackgroundColor(getResources().getColor(R.color.green));
                button2.setBackgroundColor(getResources().getColor(R.color.grey));
                button1.setBackgroundColor(getResources().getColor(R.color.grey));
            }
        });
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
        fragmentTransaction.replace(R.id.fragment_inventory, fragment);

        fragmentTransaction.commit();
    }


}