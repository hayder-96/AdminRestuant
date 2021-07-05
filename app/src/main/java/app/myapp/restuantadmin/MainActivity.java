package app.myapp.restuantadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import app.myapp.restuantadmin.HomeAdmin.AddFodd;
import app.myapp.restuantadmin.HomeAdmin.RequestFromUser;
import app.myapp.restuantadmin.HomeFragment;
import app.myapp.restuantadmin.Model.InformUser;
import app.myapp.restuantadmin.R;
import app.myapp.restuantadmin.RegAndLog.LoginActivity;
import app.myapp.restuantadmin.ServerApi.MyIntentService;
import app.myapp.restuantadmin.ServerApi.MyService;
import app.myapp.restuantadmin.ServerApi.Notifaction;
import app.myapp.restuantadmin.ServerApi.Serverapi;
import app.myapp.restuantadmin.ServerApi.SqLiteData;
import app.myapp.restuantadmin.VollyLibary.VollyLib;
import app.myapp.restuantadmin.VollySinglistion.ClassTime;
import app.myapp.restuantadmin.VollySinglistion.ClassTimeClose;
import app.myapp.restuantadmin.VollySinglistion.VollySing;
import me.leolin.shortcutbadger.ShortcutBadger;

public class MainActivity extends AppCompatActivity {


    private RequestQueue requestQueue;
    String token;
    LinearLayout toolbar;
    Intent intent;

    Switch switchCompat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        requestQueue = Volley.newRequestQueue(this);
        if (VollySing.getInstanse(this).is_Login()) {

            if (VollySing.getInstanse(this).getToken() != null) {
                token = VollySing.getInstanse(this).getToken().getToken();

            }
        } else {
            finish();
            Toast.makeText(getBaseContext(), "No save token", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(this, LoginActivity.class));
            return;
        }




        switchCompat=findViewById(R.id.switch_main);

       Intent intent1=new Intent(this,Notifaction.class);

        startService(intent1);





        toolbar=findViewById(R.id.toolpar);



        //setSupportActionBar(toolbar);





        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layot,
                new RequestFromUser()).commit();


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment=null;
                switch (item.getItemId()) {
                    case R.id.home:


                        fragment=new RequestFromUser();
                        toolbar.setVisibility(View.VISIBLE);
                        break;

                    case R.id.star:

                        fragment=new HomeFragment();
                        toolbar.setVisibility(View.GONE);
                        break;



                case R.id.done:

                fragment=new FragmentTran();
                toolbar.setVisibility(View.GONE);
                break;

            }

                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layot,
                        fragment).commit();
                return true;
            }
        });







        String b=load();
        Toast.makeText(getBaseContext(),b+"inside",Toast.LENGTH_SHORT).show();
        boolean bb=Boolean.parseBoolean(b);
        switchCompat.setChecked(bb);




        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Toast.makeText(getApplicationContext(),"true",Toast.LENGTH_SHORT).show();

                    dataMessage("true");
                    save("true");
                }else {
                    dataMessage("false");
                    save("false");

                }
            }
        });















    }












    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.add_menu,menu);


        MenuItem menuItem = menu.findItem(R.id.bar_switch);



        SwitchCompat switchCompat= (SwitchCompat) menuItem.getActionView();

        String b=load();
        Toast.makeText(getBaseContext(),b+"inside",Toast.LENGTH_SHORT).show();
        boolean bb=Boolean.parseBoolean(b);
            switchCompat.setChecked(bb);




        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Toast.makeText(getApplicationContext(),"true",Toast.LENGTH_SHORT).show();

                    dataMessage("true");
                    save("true");
                }else {
                    dataMessage("false");
                      save("false");

                }
            }
        });



        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.add_item:

                Intent intent=new Intent(getBaseContext(), AddFodd.class);
                finish();
                startActivity(intent);
                return true;
            case R.id.out_log:
                VollySing.getInstanse(this).user_Logout();
                startActivity(new Intent(getBaseContext(),LoginActivity.class));
                finish();
        }

        return false;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        intent=new Intent(this, MyService.class);

        startService(intent);

        if (MyService.tf==false){
            MyService.tf=true;
        }


    }

    @Override
    protected void onPause() {
        super.onPause();


        intent=new Intent(this, MyService.class);

        startService(intent);
        if (MyService.tf==false){
            MyService.tf=true;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        intent = new Intent(this, MyService.class);

        stopService(intent);
        SqLiteData sqLiteData = new SqLiteData(this);
        sqLiteData.delete();
        if (MyService.tf == true) {
            MyService.tf = false;
        }


        ArrayList<String> arrayList = sqLiteData.getData();
        if (arrayList == null) {
            Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
        }



        try {

            ShortcutBadger.removeCount(this);

        }catch (Exception e){

        }
    }


    @Override
    protected void onRestart() {
        super.onRestart();

        intent=new Intent(this, MyService.class);
        stopService(intent);
        SqLiteData sqLiteData=new SqLiteData(this);
        sqLiteData.delete();
        if (MyService.tf==true){
            MyService.tf=false;
        }


        try {

            ShortcutBadger.removeCount(this);

        }catch (Exception e){

        }



    }




















    private void dataMessage(String o) {






        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Serverapi.GET_OPCL, null, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {

                try {


                    JSONArray jsonArray = response.getJSONArray("data");


                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject screen = jsonArray.getJSONObject(i);

                        int id=screen.getInt("id");
                        Toast.makeText(getBaseContext(),id+"idid",Toast.LENGTH_SHORT).show();
                        upDate(id,o);
                    }


                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }


        }) {
            public Map<String, String> getHeaders() {
                Map<String, String> map = new HashMap<>();
                map.put("Accept", "application/json");

                return map;
            }

        };
        VollyLib.getInstance(getBaseContext()).addRequest(jsonObjectRequest);


    }




    private void upDate(int id,String open) {


        final String token = VollySing.getInstanse(getBaseContext()).getToken().getToken();







        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("open",open);

        } catch (Exception e) {
            e.printStackTrace();
        }

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, Serverapi.OPCL+id, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {

                    if (response.getBoolean("success")) {
                        Toast.makeText(getBaseContext(), response.getString("message"), Toast.LENGTH_SHORT).show();

                        //progressDialog.dismiss();

                    } else {

                        Toast.makeText(getBaseContext(), "error", Toast.LENGTH_SHORT).show();
                        //  progressDialog.dismiss();
                    }
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }


        }) {
            public Map<String, String> getHeaders() {
                Map<String, String> map = new HashMap<>();
                map.put("Accept", "application/json");
                map.put("Authorization", "Bearer " + token);
                return map;
            }

        };
        VollyLib.getInstance(getBaseContext()).addRequest(jsonObjectRequest);


    }

    private void save(String s){


        SharedPreferences sharedPreferences=getSharedPreferences("switch",MODE_PRIVATE);

        SharedPreferences.Editor editor=sharedPreferences.edit();

        editor.putString("choose",s);

        editor.apply();
    }



    private String load(){

        SharedPreferences sharedPreferences=getSharedPreferences("switch",MODE_PRIVATE);


        return sharedPreferences.getString("choose",null);

    }


    public void outreg(View view) {


        VollySing.getInstanse(this).user_Logout();
        startActivity(new Intent(getBaseContext(),LoginActivity.class));
        finish();
    }

    public void addIt(View view) {


        Intent intent=new Intent(getBaseContext(), AddFodd.class);
        finish();
        startActivity(intent);
    }
}





