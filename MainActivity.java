package com.example.signup;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity   {

    private EditText emailTextView, passwordTextView,cpasd;
    private Button Btn,login;
    DatabaseReference databaseReference;
    TextView Signup;
    private ProgressBar progressbar;
    private FirebaseAuth mAuth;

    // defining our own password pattern
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[@#$%^&+=])" +     // at least 1 special character
                    "(?=\\S+$)" +            // no white spaces
                    "(?=.*[A-Z])"+           // Uppercase
                    "(?=.*[0-9])"+           // Number
                    ".{4,}" +                // at least 4 characters
                    "$");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        cpasd = findViewById(R.id.cpassword);
        login = findViewById(R.id.Login);
        Signup = findViewById(R.id.SignupScreen);
        emailTextView = findViewById(R.id.email);
        passwordTextView = findViewById(R.id.passwd);
        Btn = findViewById(R.id.register);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Login.class);
                startActivity(i);
                finish();
            }
        });

//        Signup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i=new Intent(MainActivity.this,MainActivity.class);
//                startActivity(i);
//            }
//        });

        progressbar = findViewById(R.id.progressbar);

        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password, cpass;
                email = emailTextView.getText().toString();
                password = passwordTextView.getText().toString();
                cpass = cpasd.getText().toString();

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailTextView.setError("Check your Email");
                    emailTextView.requestFocus();
                    return;
                }
                if (password.isEmpty()) {
                    passwordTextView.setError("Password Required");
                    passwordTextView.requestFocus();
                    return;
                }
                // if password does not matches to the pattern
                // it will display an error message "Password is too weak"
                if (!PASSWORD_PATTERN.matcher(password).matches()) {
                    passwordTextView.setError("At least 1 special symbol & number ");
                    passwordTextView.requestFocus();
                    Toast.makeText(getApplicationContext(), "Please enter strong password!", Toast.LENGTH_LONG).show();
                    return;
                }


                if (password.length() < 6) {
                    passwordTextView.setError("Minimum length 6");
                    passwordTextView.requestFocus();
                    return;
                }
                if (!cpass.equals(password)) {
                    cpasd.setError("Confirm your Password");
                    cpasd.requestFocus();
                    return;

                }
                // Check for Internet Connection
                if (isConnected()) {

                    progressbar.setVisibility(View.VISIBLE);

                    // create new user or register new user
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseDatabase.getInstance().getReference("Users");
                                Toast.makeText(getApplicationContext(), "Registration successful!", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(MainActivity.this, thanku.class);
                                startActivity(intent);
                                progressbar.setVisibility(View.GONE);
                                finish();
                            } else {

                                // Registration failed
                                Toast.makeText(getApplicationContext(), "Registration failed!!" + " Email already exist", Toast.LENGTH_LONG).show();

                                // hide the progress bar
                                progressbar.setVisibility(View.GONE);
                            }
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean isConnected() {
        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager)getApplicationContext()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        } catch (Exception e) {
            Log.e("Connectivity Exception", e.getMessage());
        }
        return connected;
    }
}