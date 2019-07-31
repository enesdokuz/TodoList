package com.enesdokuz.todolist.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.enesdokuz.todolist.utils.PreferenceSingleton;
import com.enesdokuz.todolist.R;
import com.enesdokuz.todolist.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private RequestQueue queue;
    private EditText editEmail, editPassword, editPasswordRe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        queue = Volley.newRequestQueue(RegisterActivity.this);
        editEmail = findViewById(R.id.editEmail_Register);
        editPassword = findViewById(R.id.editPassword_Register);
        editPasswordRe = findViewById(R.id.editPasswordRe_Register);
    }

    public void onClickRegister(View view) {

        if (editEmail.getText().toString().length() > 0 && editPassword.getText().toString().length() > 0 && editPasswordRe.getText().toString().length() > 0) {

            if (editPassword.getText().toString().equals(editPasswordRe.getText().toString())) {

                RequestRegister(editEmail.getText().toString().trim() + "", editPassword.getText().toString().trim() + "");
            } else {

                Toast.makeText(this, "" + getResources().getString(R.string.dont_match_pass), Toast.LENGTH_SHORT).show();
            }
        } else {

            Toast.makeText(this, "" + getResources().getString(R.string.fill_in_the_blanks), Toast.LENGTH_SHORT).show();
        }
    }

    public void RequestRegister(final String _email, final String _password) {
        String url = Constants.REGISTER_URL;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Register", "" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            int user_id = Integer.valueOf(jsonObject.getString("user_id"));
                            PreferenceSingleton.getInstance().setUserId(user_id);
                            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                if (networkResponse != null && networkResponse.data != null) {
                    Log.e("Register", " Code: " + networkResponse.statusCode);
                    if (networkResponse.statusCode == 401) {

                        Toast.makeText(RegisterActivity.this, "" + getResources().getString(R.string.alert_register_text), Toast.LENGTH_LONG).show();
                    } else if (networkResponse.statusCode == 405) {

                        Toast.makeText(RegisterActivity.this, "" + getResources().getString(R.string.alert_missing_parameter), Toast.LENGTH_LONG).show();
                    } else if (networkResponse.statusCode == 500) {

                        Toast.makeText(RegisterActivity.this, "" + getResources().getString(R.string.alert_server_error), Toast.LENGTH_LONG).show();
                    }
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", _email);
                params.put("password", _password);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                9000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }

    public void onClickOpenLogin(View view) {
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        finish();
    }
}
