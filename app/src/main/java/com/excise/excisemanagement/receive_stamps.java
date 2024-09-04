package com.excise.excisemanagement;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class receive_stamps extends AppCompatActivity {
    private ImageView imageView;
    private CheckBox checkBoxYes;
    private CheckBox checkBoxNo;

    EditText  scanimage,comment,noofstamp,stamploc,add_comment;
    TextView imagename,text1;

    Button addbutton;
    Calendar calendar;

    ImageView documentimage;

    SharedPreferences sharedPreferences;
    Date currentDate = new Date();

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // Format the current date and time as a string
    String formattedDateTime = dateFormat.format(currentDate);
    String url="http://10.0.0.154/excise/addrcvstamp.php";
CheckBox yes,no;


    ProgressDialog progressDialog;

// Initialize the progress dialog

String encodedimage;
    private static final int WRITE_EXTERNAL_STORAGE= 101;
    private Uri imageUri;
    private static final int REQUEST_IMAGE_CAPTURE = 101;
    private static final int PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE = 101;
    private Bitmap imageBitmap;

    String selectedProvince;

    Spinner spinner;

    EditText rcvdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_stamps);


        Toolbar toolbar = findViewById(R.id.Excise_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Received Stamps");
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Set the parent activity for proper navigation
        // Replace ".DrawerActivity" with the name of your parent activity
        // This will handle the up navigation when the back arrow is clicked
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        progressDialog = new ProgressDialog(this);



        String[] options = {"Select Province", "AB", "BC", "MB", "NB", "NL", "NT", "NS", "NU", "ON", "PE", "QC", "SK", "YT"};



        // Find the spinner view
        spinner = findViewById(R.id.statespinner);
     //  selectedProvince = spinner.getSelectedItem().toString();

        imageView = findViewById(R.id.stateimage);

      //  scanimage = findViewById(R.id.document);
      //  imagename = findViewById(R.id.imagename);
        yes=findViewById(R.id.checkbox_yes);
    //    no=findViewById(R.id.checkbox_no);
       // comment=findViewById(R.id.comment);
        noofstamp=findViewById(R.id.stamprcv);
        stamploc=findViewById(R.id.stamplocat);
        add_comment=findViewById(R.id.add_comment);
addbutton=findViewById(R.id.addbutton);
//documentimage=findViewById(R.id.documentimage);

rcvdate=findViewById(R.id.receivedate);

        // Create an ArrayAdapter using the defined options array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.spinnertext, options);

        // Specify the layout to use when the list of choices appears
       adapter.setDropDownViewResource(R.layout.spinner_item);

        // Apply the adapter to the spinner
       spinner.setAdapter(adapter);

        checkBoxYes = findViewById(R.id.checkbox_yes);
    //    checkBoxNo = findViewById(R.id.checkbox_no);

        calendar = Calendar.getInstance();

        rcvdate.setOnClickListener(v -> showDatePickerDialog1());


// Inside your activity or fragment, where you request permissions



        checkBoxYes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Display a toast message when "Yes" is selected
//                if (isChecked) {
                  //  Toast.makeText(getApplicationContext(), "Yes is selected", Toast.LENGTH_SHORT).show();
                    // Uncheck the "No" CheckBox if "Yes" is selected
//                    checkBoxNo.setChecked(false);
//                    comment.setVisibility(View.GONE);

            }
        });

        // Set up listener for No CheckBox
//        checkBoxNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                // Display a toast message when "No" is selected
//                if (isChecked) {
//                    Toast.makeText(getApplicationContext(), "No is selected", Toast.LENGTH_SHORT).show();
//                    // Uncheck the "Yes" CheckBox if "No" is selected
//                    checkBoxYes.setChecked(false);
//                    comment.setVisibility(View.VISIBLE);
//                }
//            }
//        });


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Handle the selection here
                String selectedItem = options[position];
                // Update the image based on the selection
                updateImage(selectedItem);
                String selectedProvince = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });



    ///to capture image

//        scanimage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dispatchTakePictureIntent();
//            }
//        });

   addbutton.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {


           if (validateInput()) {
               // If input is valid, insert data
               insertrcvstanpdata();
           }
         //
           //  insertrcvstanpdata();
       }
   });
    }

    private boolean validateInput() {

        String receivedDate = rcvdate.getText().toString();
        String numberOfStamps = noofstamp.getText().toString();
        String stampLocation = stamploc.getText().toString();
      //  String additionalComment = add_comment.getText().toString();



        if (spinner.getSelectedItem().toString().equals("Select Province")) {
            // Show error message

            ((TextView) spinner.getSelectedView()).setError("Please select a province");
            //  Toast.makeText(getApplicationContext(), "Please select a province", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!checkBoxYes.isChecked()) {
            // Show error message if checkbox is not checked
            // Toast.makeText(getApplicationContext(), "Please check the checkbox", Toast.LENGTH_SHORT).show();
            checkBoxYes.setError("Please check the checkbox ");
            return false;
        }

        if (numberOfStamps.isEmpty()) {
            // Show error message if number of stamps is empty
            noofstamp.setError("Please enter number of stamps");
            return false;
        }

        // Validate received date
        if (receivedDate.isEmpty()) {
            // Show error message if received date is empty
            rcvdate.setError("Please select received date");
            return false;
        }

        // Validate number of stamps


        // Validate stamp location
        if (stampLocation.isEmpty()) {
            // Show error message if stamp location is empty
            stamploc.setError("Please enter stamp location");
            return false;
        }





        // All input fields are valid
        return true;

    }

    private void showDatePickerDialog1() {
        int year = calendar.get(java.util.Calendar.YEAR);
        int month = calendar.get(java.util.Calendar.MONTH);
        int dayOfMonth = calendar.get(java.util.Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year1, monthOfYear, dayOfMonth1) -> {
                    // Set the selected date in the EditText field
                    String formattedDate = String.format("%04d-%02d-%02d", year1, monthOfYear + 1, dayOfMonth1);
                    // Set the formatted date in the EditText field
                    rcvdate.setText(formattedDate);
                    //  fromdate.setText(year1+"-"+(monthOfYear + 1)+"-"+ dayOfMonth1 );
                },
                year, month, dayOfMonth);
        datePickerDialog.show();
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null) {
//            Bundle extras = data.getExtras();
//            if (extras != null) {
//                imageBitmap = (Bitmap) extras.get("data");  // Assign to class-level variable
//                documentimage.setImageBitmap(imageBitmap);
//                encodebitmap(imageBitmap);
//                String imageName = "Image File Attached"; // Generate a unique image name
//                // Display the image name in the TextView
//                imagename.setText(imageName);
//                // You can also save the image to storage or upload it to a server here
//            }
//        }
//    }

//    private void encodebitmap(Bitmap imageBitmap) {
//
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
//        byte[] byteOfImages = byteArrayOutputStream.toByteArray();
//        encodedimage = Base64.encodeToString(byteOfImages, Base64.DEFAULT);
//    }


    // Method to convert Bitmap to Uri
//    private Uri getImageUri(Bitmap bitmap) {
//        Uri uri = null;
//        try {
//            String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Image", null);
//            uri = Uri.parse(path);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return uri;
//    }

    // Method to get image name from Uri
//    private String getImageName(Uri uri) {
//        String imageName = null;
//        if (uri != null) {
//            Cursor cursor = null;
//            try {
//                String[] projection = {MediaStore.Images.Media.DISPLAY_NAME};
//                cursor = getContentResolver().query(uri, projection, null, null, null);
//                if (cursor != null && cursor.moveToFirst()) {
//                    int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);
//                    imageName = cursor.getString(columnIndex);
//                    Log.d("ImageNameDebug", "Image Name: " + imageName);
//                } else {
//                    Log.d("ImageNameDebug", "Cursor is null or empty");
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                if (cursor != null) {
//                    cursor.close();
//                }
//            }
//        } else {
//            Log.d("ImageNameDebug", "Uri is null");
//        }
//        return imageName;
//    }
//        // Set up listener for Yes CheckBox


    private void updateImage(String selectedItem) {
        int imageResource;
        switch (selectedItem) {
            case "AB":
                imageResource = R.drawable.ab;
                break;
            case "BC":
                imageResource = R.drawable.bc;
                break;
            case "MB":
                imageResource = R.drawable.mb;
                break;
            case "NB":
                imageResource = R.drawable.nb;
                break;
            case "NL":
                imageResource = R.drawable.nl;
                break;
            case "NT":
                imageResource = R.drawable.nt;
                break;
            case "NS":
                imageResource = R.drawable.ns;
                break;
            case "NU":
                imageResource = R.drawable.nu;
                break;
            case "ON":
                imageResource = R.drawable.onstamp;
                break;
            case "PE":
                imageResource = R.drawable.pe;
                break;
            case "QC":
                imageResource = R.drawable.qc;
                break;
            case "SK":
                imageResource = R.drawable.sk;
                break;
            case "YT":
                imageResource = R.drawable.yt;
                break;


            default:
                // If the selected option does not match, hide the ImageView
                imageView.setVisibility(View.GONE);
                // Also hide the CheckBoxes and TextView
              //  checkBoxYes.setVisibility(View.GONE);
              //  checkBoxNo.setVisibility(View.GONE);
              //  comment.setVisibility(View.GONE);
             //   findViewById(R.id.checkboxtextview).setVisibility(View.GONE); // ID of the layout containing the CheckBoxes
                return;
        }

        // Show the ImageView and set the corresponding image resource
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageResource(imageResource);

        // Show the CheckBoxes and TextView when the image is visible
      //  checkBoxYes.setVisibility(View.VISIBLE);
      //  checkBoxNo.setVisibility(View.VISIBLE);
       // findViewById(R.id.checkboxtextview).setVisibility(View.VISIBLE);


    }
    private void insertrcvstanpdata() {




        RequestQueue queue = Volley.newRequestQueue(this);



        //String commnt=comment.getText().toString();
        String stmprc=noofstamp.getText().toString();
        String locat=stamploc.getText().toString();
        String adddc=add_comment.getText().toString();

        String selectedProvince = spinner.getSelectedItem().toString();
        Log.d("SelectedProvince", selectedProvince);




        sharedPreferences = getApplicationContext().getSharedPreferences("logindetails", MODE_PRIVATE);

        String recvby = sharedPreferences.getString("username", "");

//        if (imageBitmap == null) {
//            Toast.makeText(this, "Image is null. Capture an image first.", Toast.LENGTH_SHORT).show();
//            return;
//        }


        // Create a String request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle response from the server
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
Log.d("response",response);

                        rcvdate.setText("");
                        noofstamp.setText("");
                        stamploc.setText("");
                        add_comment.setText("");

                        // Clear CheckBox state
                        checkBoxYes.setChecked(false);

                        // Set Spinner selection to default
                        spinner.setSelection(0);




//                        Intent intent = new Intent(getApplicationContext(), Excise_Section.class);
//                        startActivity(intent);




                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Error occurred", Toast.LENGTH_SHORT).show();
            }


        }) {
            @Override
            protected Map<String, String> getParams() {
                // Parameters to be sent to the PHP script
                Map<String, String> params = new HashMap<>();
                params.put("datetime",rcvdate.getText().toString() );
                params.put("provincename",selectedProvince);
                if (checkBoxYes.isChecked()) {
                    params.put("excisematches", "Yes");
                } else {
                    params.put("excisematches", "No");
                }
             //   params.put("comment",commnt );
                params.put("stamprcv", stmprc);
                params.put("location",locat );
//                if (imageBitmap != null) {
//                    // Encode the bitmap to base64 and add it to the parameters
//                    encodebitmap(imageBitmap);
//                    params.put("document", encodedimage);
//                } else {
//                    // Set a default value for the document parameter when imageBitmap is null
//                    params.put("document", "No image"); // Change "default_image" to your desired default value
//                }
                params.put("addcomment",adddc );

                params.put("rcvby",recvby );

                return params;
            }
        };
        progressDialog.setMessage("Loading... Please wait");
        progressDialog.setCancelable(false);
        progressDialog.show();
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }


}