package com.hamdan.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private Context context;
    private ProgressDialog pDialog;
    private AppCompatButton buttonLogin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        context = Login.this;

        //Initializing views
        pDialog = new ProgressDialog(context);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        buttonLogin = (AppCompatButton) findViewById(R.id.buttonLogin);

        //Adding click listener
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { login();
            }
        });
        //editTextEmail.setText("hamdan");
        //editTextPassword.setText("hamdan");
    }

    private void login() {
        //Getting values from edit texts
        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        pDialog.setMessage("Login Process...");
        showDialog();
        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppVar.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //If we are getting success from server
                        if (response.contains(AppVar.LOGIN_SUCCESS)) {
                            hideDialog();
                            gotoCourseActivity();

                        } else {
                            hideDialog();
                            //Displaying an error message on toast
                            Toast.makeText(context, "Email atau Password Salah", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                        hideDialog();
                        Toast.makeText(context, "Server Tidak Dapat Dijangkau", Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request
                params.put(AppVar.KEY_EMAIL, email);
                params.put(AppVar.KEY_PASSWORD, password);

                //returning parameter
                return params;
            }
        };

        //Adding the string request to the queue
        Volley.newRequestQueue(this).add(stringRequest);

    }

    private void gotoCourseActivity() {
        Intent intent = new Intent(context, Login.class);
        startActivity(intent);
        finish();
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


}