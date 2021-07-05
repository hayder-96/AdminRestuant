package app.myapp.restuantadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import app.myapp.restuantadmin.Model.InformUser;
import app.myapp.restuantadmin.Model.Item_Menu_User;
import app.myapp.restuantadmin.RecyclerAdapter.Adapter_Accept;
import app.myapp.restuantadmin.RecyclerAdapter.Adapter_MenuUser;
import app.myapp.restuantadmin.ServerApi.Serverapi;
import app.myapp.restuantadmin.VollyLibary.VollyLib;
import app.myapp.restuantadmin.VollySinglistion.VollySing;

public class MenuUser extends AppCompatActivity {


    RecyclerView recyclerView;
    Context context;
    TextView textView;
    Adapter_MenuUser adapter_menuUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_user);



        context=this;
        Intent intent=getIntent();

        int id=intent.getIntExtra("id", -1);

        textView=findViewById(R.id.text_all_price);
        recyclerView=findViewById(R.id.recycler_menu_requser);


       ArrayList<Item_Menu_User> arrayList= dataMessage(id);

       dataMessag(id);



        adapter_menuUser=new Adapter_MenuUser(arrayList);
          recyclerView.setAdapter(adapter_menuUser);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(context);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setHasFixedSize(true);

    }











    private ArrayList<Item_Menu_User> dataMessage(int i) {

        final String token = VollySing.getInstanse(this).getToken().getToken();

        ArrayList<Item_Menu_User> arrayList1 =new ArrayList<>();

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Serverapi.GETREQUEST_FOOD_USER+i, null, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {

                try {


                    JSONArray jsonArray = response.getJSONArray("data");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject screen = jsonArray.getJSONObject(i);
                       Item_Menu_User item_menu_user=new Item_Menu_User();
                        item_menu_user.setName(screen.getString("name"));
                        item_menu_user.setImage(screen.getString("image"));
                        item_menu_user.setPrice(screen.getString("price"));
                        item_menu_user.setNumber(screen.getString("number"));
                        arrayList1.add(item_menu_user);

                    }

                    adapter_menuUser.notifyDataSetChanged();


//                    Adapter_MenuUser adapter_menuUser=new Adapter_MenuUser(arrayList1);
//
//                    recyclerView.setAdapter(adapter_menuUser);
//                    RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(context);
//                    recyclerView.setLayoutManager(layoutManager);
//                    recyclerView.setHasFixedSize(true);
//                          adapter_menuUser.notifyDataSetChanged();
//                    for(Item_Menu_User i:arrayList1){
//
//                        int p=Integer.parseInt(i.getPrice());
//                        Toast.makeText(getBaseContext(),i.getPrice()+"ioi",Toast.LENGTH_SHORT).show();
//                        int pp=Integer.parseInt(textView.getText().toString().trim());
//                        textView.setText(p+pp);
//                    }
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
                map.put("Authorization", "Bearer  " + token);
                return map;
            }

        };
        VollyLib.getInstance(getBaseContext()).addRequest(jsonObjectRequest);


        return arrayList1;


    }


















    private void dataMessag(int i) {

        final String token = VollySing.getInstanse(this).getToken().getToken();

        ArrayList<String> arrayList1 =new ArrayList<>();

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Serverapi.GETREQUEST_FOOD_USER+i, null, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {

                try {


                    JSONArray jsonArray = response.getJSONArray("data");
                    double dd=0.0;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject screen = jsonArray.getJSONObject(i);



                     double d=Double.parseDouble(screen.getString("price"));



                dd=dd+d;
                      textView.setText(dd+"\t"+"د ع");

                    }

                    adapter_menuUser.notifyDataSetChanged();


//                    Adapter_MenuUser adapter_menuUser=new Adapter_MenuUser(arrayList1);
//
//                    recyclerView.setAdapter(adapter_menuUser);
//                    RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(context);
//                    recyclerView.setLayoutManager(layoutManager);
//                    recyclerView.setHasFixedSize(true);
//                          adapter_menuUser.notifyDataSetChanged();
//                    for(Item_Menu_User i:arrayList1){
//
//                        int p=Integer.parseInt(i.getPrice());
//                        Toast.makeText(getBaseContext(),i.getPrice()+"ioi",Toast.LENGTH_SHORT).show();
//                        int pp=Integer.parseInt(textView.getText().toString().trim());
//                        textView.setText(p+pp);
//                    }
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
                map.put("Authorization", "Bearer  " + token);
                return map;
            }

        };
        VollyLib.getInstance(getBaseContext()).addRequest(jsonObjectRequest);





    }
}