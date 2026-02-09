package com.example.signup;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity  implements GoogleApiClient.OnConnectionFailedListener{
    SignInButton signInButton;
    private GoogleApiClient googleApiClient;
    private EditText emailTextView, passwordTextView;
    private Button Btn, signup;
    TextView login, forgetpass, Admin_login;
    public ProgressDialog loginprogress;
    private static final int RC_SIGN_IN = 1;
    //    private ProgressBar progressbar;
    LottieAnimationView lottieAnimationView;

    private FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        forgetpass = findViewById(R.id.forgetpass);
        lottieAnimationView = findViewById(R.id.progress_Bar);
        // initialising all views through id defined above

        loginprogress = new ProgressDialog(this);
        emailTextView = findViewById(R.id.email);
        passwordTextView = findViewById(R.id.password);
        Btn = findViewById(R.id.btn_login);
//        progressbar = findViewById(R.id.progress_Bar);
        login = findViewById(R.id.LoginScreen);
        signup = findViewById(R.id.SignupScreen);
        Admin_login = findViewById(R.id.adminlogin);

        //Admin Login Screen
        Admin_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, SignInAdmin.class);
                startActivity(intent);
//                finish();
            }
        });

        //SignUp screen
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login.this, MainActivity.class);
                startActivity(i);
//
            }
        });
        //SignIn screen
//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i =new Intent(Login.this,Login.class);
//                startActivity(i);
//            }
//        });

        //Forgotten password
        forgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRecoverPasswordDialog();
            }

            ProgressDialog loadingBar;

            private void showRecoverPasswordDialog() {
                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                builder.setTitle("Recover Password");
                LinearLayout linearLayout = new LinearLayout(Login.this);
                final EditText emailet = new EditText(Login.this);

                // write the email using which you registered
                emailet.setText("Email");
                emailet.setMinEms(16);
                emailet.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                linearLayout.addView(emailet);
                linearLayout.setPadding(10, 10, 10, 10);
                builder.setView(linearLayout);

                // Click on Recover and a email will be sent to your registered email id
                builder.setPositiveButton("Recover", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String email = emailet.getText().toString().trim();
                        beginRecovery(email);
                    }
                });
///             //forget kalya
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }

            private void beginRecovery(String email) {
                loadingBar = new ProgressDialog(Login.this);
                loadingBar.setMessage("Sending Email....");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();

                // calling sendPasswordResetEmail
                // open your email and write the new
                // password and then you can login
                mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        loadingBar.dismiss();
                        if (task.isSuccessful()) {
                            // if isSuccessful then done message will be shown
                            // and you can change the password
                            Toast.makeText(Login.this, "Email Sent", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(Login.this, "Something went Wrong", Toast.LENGTH_LONG).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        loadingBar.dismiss();
                        Toast.makeText(Login.this, "Something went Wrong", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        lottieAnimationView.setVisibility(View.GONE);
        //Set ClickListener on Sign-in button
        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Take the value of two edit texts in Strings
                String email, password;
                email = emailTextView.getText().toString();
                password = passwordTextView.getText().toString();

                // validations for input email and password
                if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Please enter Email & Password", Toast.LENGTH_LONG).show();
//                    emailTextView.setError("Please Enter email");
//                    emailTextView.requestFocus();
//                    passwordTextView.setError("Password Enter password");
//                    passwordTextView.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Please enter email!", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_LONG).show();
                    return;
                }
                // Check for Internet Connection
                if (isConnected()) {

                    lottieAnimationView.setVisibility(View.VISIBLE);

                    // signin existing user
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(
                                        @NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(Login.this, dashboard_p.class);
                                        startActivity(intent);
                                        // hide the progress bar
                                        lottieAnimationView.setVisibility(View.GONE);
                                        //------------------------------------
                                        SharedPreferences preferences = getSharedPreferences("login", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = preferences.edit();
                                        editor.putBoolean("isUserLogin", true);
                                        editor.commit();
                                        //------------------------------------
                                        finish();
//
                                        // if sign-in is successful
                                        // fields clear
                                        emailTextView.getText().clear();
                                        passwordTextView.getText().clear();

                                    } else {
                                        // sign-in failed popup
                                        Toast.makeText(getApplicationContext(), "Login failed!" + " Check your credentials", Toast.LENGTH_LONG).show();

                                        // hide the progress bar
                                        lottieAnimationView.setVisibility(View.GONE);
                                    }
                                }
                            });

                } else {
                    Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this).

                enableAutoManage(this, this).

                addApi(Auth.GOOGLE_SIGN_IN_API, gso).

                build();


        signInButton = (SignInButton)

                findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, RC_SIGN_IN);


            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {

        } else
            Toast.makeText(getApplicationContext(), "Sign in cancel", Toast.LENGTH_LONG).show();
    }


    public boolean isConnected() {
        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        } catch (Exception e) {
            Log.e("Connectivity Exception", e.getMessage());
        }
        return connected;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}