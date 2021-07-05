package app.myapp.restuantadmin.RegAndLog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import app.myapp.restuantadmin.MainActivity;
import app.myapp.restuantadmin.Model.User;
import app.myapp.restuantadmin.R;
import app.myapp.restuantadmin.ServerApi.Serverapi;
import app.myapp.restuantadmin.VollyLibary.VollyLib;
import app.myapp.restuantadmin.VollySinglistion.VollySing;

public class RegisterActivity extends AppCompatActivity {

    EditText usernameEditText;
    EditText passwordEditText;
    Button loginButton;
    ProgressBar loadingProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        usernameEditText = findViewById(R.id.username_reg);
        passwordEditText = findViewById(R.id.password_reg);
        loginButton = findViewById(R.id.login_go);
        loadingProgressBar = findViewById(R.id.loading_reg);
    }














    public void register_go(View view) {
        saveRegi();
    }















    private void saveRegi(){

        final String name=usernameEditText.getText().toString().trim();
        final String password=passwordEditText.getText().toString();


        if (TextUtils.isEmpty(name)){
            usernameEditText.setError("Enter your name");
            usernameEditText.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)){
            passwordEditText.setError("Enter your password");
            passwordEditText.requestFocus();
            return;
        }

        loadingProgressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Serverapi.REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getBoolean("success")) {

                                JSONObject object = jsonObject.getJSONObject("data");
                                VollySing.getInstanse(getBaseContext()).saveUser(new User(object.getString("token")));
                                startActivity(new Intent(getBaseContext(), MainActivity.class));
                                finish();
                                loadingProgressBar.setVisibility(View.GONE);
                            } else {
                                Toast.makeText(getBaseContext(), "message", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        loadingProgressBar.setVisibility(View.GONE);


                    }

                } , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingProgressBar.setVisibility(View.GONE);
            }


        }
        )

        {

            @Override
            protected Map<String, String> getParams() {

                Map<String, String> map=new HashMap<>();
                map.put("Content-Type","application/json");
                map.put("name",name);
                map.put("password",password);

                return map ;
            }
        };
        VollyLib.getInstance(getBaseContext()).addRequest(stringRequest);

    }

}