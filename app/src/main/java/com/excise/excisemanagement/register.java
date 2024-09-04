package com.excise.excisemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class register extends AppCompatActivity {


    EditText registerusername,registeremail,registerpassword;
    Button register;

    TextView alreadyaccount;


    String url="http://10.0.0.154/excise/register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



        registerusername=findViewById(R.id.username);
        registeremail=findViewById(R.id.registeremail);
        registerpassword=findViewById(R.id.registerpassword);
        alreadyaccount=findViewById(R.id.alreadylogin);

        register=findViewById(R.id.buttonRegister);


        alreadyaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), login.class);
                startActivity(intent);

            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = registeremail.getText().toString().trim();
                String password = registerpassword.getText().toString().trim();


                if (registerusername.getText().toString().isEmpty()){


                    registerusername.setError("Please enter username");
                    return ;
                }


                 if (email.isEmpty()){


                    registeremail.setError("Please enter valid EmailId");
                     return ;
                 }

                if (password.isEmpty()){


                    registerpassword.setError("Please enter valid Password");
                    return ;
                }
                if (!isValidEmail(email)) {


                    Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!isValidPassword(password)) {
                    Toast.makeText(getApplicationContext(), "Password must be at least 6 characters long and contain alphanumeric characters and special characters", Toast.LENGTH_SHORT).show();
                    return;
                }
                
                registervalue();
            }
        });

    }

    private boolean isValidPassword(String password) {
        // Password must be at least 6 characters long
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


    private boolean isValidEmail(CharSequence target) {
        return Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    private void registervalue() {
        RequestQueue queue = Volley.newRequestQueue(this);

        // Get values from EditText fields
        String uname = registerusername.getText().toString();
        String email = registeremail.getText().toString();
        String password = registerpassword.getText().toString();

        // Create a String request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle response from the server

                        Toast.makeText(register.this, "register successfully", Toast.LENGTH_SHORT).show();

                        registerusername.setText("");
                        registeremail.setText("");
                        registerpassword.setText("");
                        Intent intent = new Intent(getApplicationContext(), login.class);
                        startActivity(intent);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error occurred", Toast.LENGTH_SHORT).show();
            }


        }) {
            @Override
            protected Map<String, String> getParams() {
                // Parameters to be sent to the PHP script
                Map<String, String> params = new HashMap<>();
                params.put("username", uname);
                params.put("email", email);
                params.put("password",password);
                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public void onBackPressed() {
        // Call super to perform default back press action
        super.onBackPressed();

        // Add your custom logic here if needed
    }
}
