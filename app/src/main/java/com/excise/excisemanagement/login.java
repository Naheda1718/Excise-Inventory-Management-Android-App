package com.excise.excisemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class login extends AppCompatActivity {

    EditText loginuser,lginpassword;
    TextView signup,forgotpassword;
    Button login;
    String url="http://10.0.0.154/excise/login.php";

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        loginuser=findViewById(R.id.loginuser);
        lginpassword=findViewById(R.id.loginpassword);
        signup=findViewById(R.id.loginsignup);
        login=findViewById(R.id.buttonLogin);
        forgotpassword=findViewById(R.id.forgotpassword);

        sharedPreferences = getSharedPreferences("logindetails", Context.MODE_PRIVATE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), forgotpassword.class);
                startActivity(intent);

            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user=loginuser.getText().toString().trim();
                String password = lginpassword.getText().toString().trim();

                if (loginuser.getText().toString().isEmpty()){


                    loginuser.setError("Please enter username");
                    return  ;
                }

                if (password.isEmpty()){

                    lginpassword.setError("Please enter valid Password");
                    return  ;
                }

//                if (!isValidPassword(password)) {
//                    Toast.makeText(getApplicationContext(), "Password must be at least 6 characters long and contain alphanumeric characters and special characters", Toast.LENGTH_SHORT).show();
//                    return;
//                }

//                if (!login(user, password)) {
//                    Toast.makeText(getApplicationContext(), "Login failed. Invalid email or password.", Toast.LENGTH_SHORT).show();
//                    return;
//                }


                loginuser();
            }
        });

        if (isLoggedIn()) {
            // User is logged in, so go to home screen
            Intent intent = new Intent(getApplicationContext(), drawer.class);
            startActivity(intent);
            finish(); // Finish the main activity to prevent going back
        }

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), register.class);
                startActivity(intent);


            }
        });
    }

    private boolean login(String user, String password) {

        return  false;
    }

    private boolean isValidPassword(String password) {

        if (password.length() < 6) {
            return false;
        }

        // Password must contain at least one digit
        if (!Pattern.compile("[0-9]").matcher(password).find()) {
            return false;
        }

        // Password must contain at least one letter
        if (!Pattern.compile("[a-zA-Z]").matcher(password).find()) {
            return false;
        }

        // Password must contain at least one special character
        if (!Pattern.compile("[!@#$%^&*()_+=|<>?{}\\[\\]~-]").matcher(password).find()) {
            return false;
        }

        return true;
    }



    private boolean isLoggedIn() {
        SharedPreferences sharedPreferences = getSharedPreferences("logindetails", Context.MODE_PRIVATE);
        return sharedPreferences.contains("username");
    }

    private void loginuser() {
        String user = loginuser.getText().toString();
        String password = lginpassword.getText().toString();

        // Create a StringRequest to make a POST request to the login URL
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String result=jsonObject.getString("status");
                            if (result.equals("success")){

                                Toast.makeText(login.this, "login successfull", Toast.LENGTH_SHORT).show();


                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("username", user);
                                editor.putString("password",password);
                                editor.apply(); // Apply changes

                                Intent intent = new Intent(getApplicationContext(), drawer.class);
                                startActivity(intent);

                                loginuser.setText("");
                                lginpassword.setText("");

                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Error handling
                        //   Log.e("LoginActivity", "Volley Error: " + error.getMessage());
                        Toast.makeText(getApplicationContext(), "Login failed. Invalid email or password.", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Parameters to be sent in the POST request
                Map<String, String> params = new HashMap<>();
                params.put("username", user);
                params.put("password", password);
                return params;
            }
        };

        // Add the request to the RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        // Call super to perform default back press action
        super.onBackPressed();

        // Add your custom logic here if needed
    }
}