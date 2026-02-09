package com.example.signup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Dietform extends AppCompatActivity {

    private EditText editTextAge, editTextGender, editTextWeight, editTextHeight, editTextDiabetes, editTextGoal;
    private Button buttonPredict;
    private TextView textViewResult;
    private static final String API_URL = "http://10.0.2.2:5000/predict"; // Replace with your Flask server IP

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dietform);

         editTextAge=findViewById(R.id.editTextAge);

        editTextWeight = findViewById(R.id.editTextWeight);
        editTextHeight = findViewById(R.id.editTextHeight);

        buttonPredict = findViewById(R.id.buttonPredict);
        textViewResult = findViewById(R.id.textViewResult);

        buttonPredict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String age = editTextAge.getText().toString().trim();
              String weight = editTextWeight.getText().toString().trim();
                String height = editTextHeight.getText().toString().trim();

                // Validate input fields
                if (age.isEmpty()  || weight.isEmpty() || height.isEmpty() ) {
                    Toast.makeText(getApplicationContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Make API request using AsyncTask
                new PredictDietTask().execute(age, weight, height);
            }
        });
    }

    private class PredictDietTask extends AsyncTask<String, Void, String> {

        OkHttpClient client = new OkHttpClient();

        @Override
        protected String doInBackground(String... params) {
            String age = params[0];
            String weight = params[1];
            String height = params[2];


            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            JSONObject jsonBody = new JSONObject();
            try {
                jsonBody.put("age", age);
                jsonBody.put("weight", weight);
                jsonBody.put("height", height);


            } catch (JSONException e) {
                e.printStackTrace();
            }

            RequestBody requestBody = RequestBody.create(jsonBody.toString(), JSON);
            Request request = new Request.Builder()
                    .url(API_URL)
                    .post(requestBody)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result != null) {
                Log.d("Response", result); // Log the response for debugging purposes
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.has("prediction_diet")) {
                        String prediction = jsonObject.getString("prediction_diet");

                        // Save prediction to SharedPreferences
                        SharedPreferences sh = getSharedPreferences("MySharedPref", 0);
                        SharedPreferences.Editor editor = sh.edit();
                        editor.putString("prediction", prediction);
                        editor.apply();

                        // Start outputDiet_plan activity
                        Intent i = new Intent(getApplicationContext(), outputDiet_plan.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(getApplicationContext(), "Prediction key not found in response", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error parsing response: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Error making API request", Toast.LENGTH_SHORT).show();
            }
        }

    }
}

