package com.excise.excisemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class forgotpassword extends AppCompatActivity {

    
EditText restemail,resetpassword;



Button resetbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);

        restemail=findViewById(R.id.resetemail);
        resetpassword=findViewById(R.id.resetpassword);
        resetbutton=findViewById(R.id.resetbutton);
        
        resetbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String password = resetpassword.getText().toString().trim();
                if (restemail.getText().toString().isEmpty()){


                    restemail.setError("Please enter Email Id");
                    return ;
                }

                if (resetpassword.getText().toString().isEmpty()){

                    resetpassword.setError("Please enter valid password");
                    return  ;
                }

                if (!isValidPassword(password)) {
                    Toast.makeText(getApplicationContext(), "Password must be at least 6 characters long and contain alphanumeric characters and special characters", Toast.LENGTH_SHORT).show();
                    return;
                }

                resetpassword();
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

    private void resetpassword() {

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String url="http://10.0.0.154/excise/updatepassword.php";



        String email = restemail.getText().toString();
         String password = resetpassword.getText().toString();


        String requestUrl = url + "?email=" + email ;
        // Create a StringRequest to make a POST request to the login URL
        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle response
                        Toast.makeText(getApplicationContext(), "Password Updated Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), login.class);
                        startActivity(intent);
                        restemail.setText("");
                        resetpassword.setText("");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Error handling
                        Toast.makeText(getApplicationContext(), "Reset failed. Incorrect email Id", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Parameters to be sent in the POST request body
                Map<String, String> params = new HashMap<>();
                params.put("password", resetpassword.getText().toString());
                params.put("email", restemail.getText().toString());
                return params;
            }
        };

        // Add the request to the RequestQueue

        requestQueue.add(stringRequest);
    }


}