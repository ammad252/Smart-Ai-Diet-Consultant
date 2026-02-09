package com.example.signup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignInAdmin extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    SignInButton signInButton;
    private GoogleApiClient googleApiClient;
    private EditText username, password;
    private TextView PLogin;
    private Button login_btn;
    private ProgressBar progressbar;
    private static final int RC_SIGN_IN = 1;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://fypproject-256f3-default-rtdb.firebaseio.com/");

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_admin);


        username = findViewById(R.id.Username);
        password = findViewById(R.id.password);
        login_btn = findViewById(R.id.btn_login);
        PLogin = findViewById(R.id.LoginP);
        progressbar = findViewById(R.id.progress_Bar);


        PLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignInAdmin.this, Login.class);
                startActivity(i);
                finish();
            }
        });


        login_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final String User = username.getText().toString();
                final String pass = password.getText().toString();

                // Check for Internet Connection
                if (isConnected()) {

                    if (User.isEmpty() && pass.isEmpty()) {
                        Toast.makeText(SignInAdmin.this, "Please add your credentials", Toast.LENGTH_SHORT).show();
                    } else if (!User.isEmpty() && pass.isEmpty()) {
                        Toast.makeText(SignInAdmin.this, "Please add your password", Toast.LENGTH_SHORT).show();
                    } else if (User.isEmpty() && !pass.isEmpty()) {
                        Toast.makeText(SignInAdmin.this, "Please add your Username", Toast.LENGTH_SHORT).show();
                    } else {
                        progressbar.setVisibility(View.VISIBLE);
                        databaseReference.child("Admin").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.hasChild(User)) {
                                    final String getpassword = snapshot.child(User).child("password").getValue(String.class);
                                    if (getpassword.equals(pass)) {
                                        Toast.makeText(SignInAdmin.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(SignInAdmin.this, Dashboard_d.class);
                                        startActivity(i);
                                        progressbar.setVisibility(View.GONE);
                                        //------------------------------------
                                        SharedPreferences preferences = getSharedPreferences("login", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = preferences.edit();
                                        editor.putBoolean("isAdminLogin", true);
                                        editor.commit();
                                        //------------------------------------
                                        // fields clear
                                        username.getText().clear();
                                        password.getText().clear();
                                        finish();
                                    } else {
                                        Toast.makeText(SignInAdmin.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(SignInAdmin.this, "Login failed! check your credentials!", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build();
    googleApiClient=new GoogleApiClient.Builder(this).

    enableAutoManage(this,this).

    addApi(Auth.GOOGLE_SIGN_IN_API, gso).

    build();


    signInButton=(SignInButton)

    findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View view){
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
        if(requestCode==RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }
    private void handleSignInResult(GoogleSignInResult result){
        if(result.isSuccess()){

        }else
            Toast.makeText(getApplicationContext(), "Sign in cancel", Toast.LENGTH_LONG).show();
    }
    public boolean isConnected() {
        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) getApplicationContext()
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