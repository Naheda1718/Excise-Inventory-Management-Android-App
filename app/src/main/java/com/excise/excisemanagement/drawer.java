package com.excise.excisemanagement;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;

public class drawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;

    SharedPreferences sharedPreferences;
    private boolean isHomePageFragment = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        if (isDarkModeEnabled()) {
            setTheme(R.style.Base_Theme_MyApplication_Dark);
        } else {
            setTheme(R.style.Base_Theme_MyApplication);
        }
        sharedPreferences = getSharedPreferences("logindetails", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        View headerview = navigationView.getHeaderView(0);
        TextView textusername = headerview.findViewById(R.id.usernameheader);
        textusername.setText(username);

        getSupportActionBar().setHomeButtonEnabled(true);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
      //  toggle.setDrawerIndicatorEnabled(false);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Excise_management()).commit();

            navigationView.setCheckedItem(R.id.nav_home);

        }
    }


    

    private boolean isDarkModeEnabled() {
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Homefragment()).addToBackStack(null).commit();

        } else if (id == R.id.nav_gallery) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Add_inventory()).addToBackStack(null).commit();
        } else if (id == R.id.Nav_viewinv) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new View_Inventory()).addToBackStack(null).commit();

        } else if (id == R.id.logout) {
            logout();

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    private void logout() {
        SharedPreferences sharedPreferences = getSharedPreferences("logindetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear(); // Clear all stored data
        editor.apply();

        // Navigate back to the login screen
        Intent intent = new Intent(this, login.class);
        startActivity(intent);
        finish(); // Finish the current activity to prevent going back
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            // Check if the current fragment is the home page
            if (isHomePage() && isLoggedIn()) {
                // If the current fragment is the home page and the user is logged in, close the app
                finishAffinity(); // Finish all activities in the current task, effectively closing the app
            } else
                {
                    super.onBackPressed();
            }
        }

    }

    private boolean isLoggedIn() {
        SharedPreferences sharedPreferences = getSharedPreferences("logindetails", Context.MODE_PRIVATE);
        return sharedPreferences.contains("username");
    }

    private boolean isHomePage() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        return currentFragment instanceof Excise_management;
    }
}

