package app.myapp.restuantadmin.FoodPart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import app.myapp.restuantadmin.Model.ItemPart;
import app.myapp.restuantadmin.R;
import app.myapp.restuantadmin.RecyclerAdapter.Adapter_RequestPart;
import app.myapp.restuantadmin.ServerApi.Serverapi;
import app.myapp.restuantadmin.VollyLibary.VollyLib;
import app.myapp.restuantadmin.VollySinglistion.VollySing;

public class FoodPartActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;
    Adapter_RequestPart adapter_requestPart;
    Context context;
    int id;
    ProgressBar progressBar;
    ImageView button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_part);


        recyclerView=findViewById(R.id.recy_part);
        floatingActionButton=findViewById(R.id.floting);

        SwipeRefreshLayout swipeRefreshLayout=findViewById(R.id.swip_part);
        progressBar=findViewById(R.id.progress_part);
        button=findViewById(R.id.but_loaded_part);
        context=this;




        Intent intent=getIntent();
        id=intent.getIntExtra("ii",-1);
        dataMessage(id);

        Toast.makeText(this,id+"id",Toast.LENGTH_SHORT).show();



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dataMessage(id);
                button.setVisibility(View.GONE);

            }
        });






        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                dataMessage(id);
                swipeRefreshLayout.setRefreshing(false);
            }
        });












        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getBaseContext(),AddPartActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);
                finish();
            }
        });

    }



















    private void dataMessage(int i) {

        final String token = VollySing.getInstanse(this).getToken().getToken();

        ArrayList<ItemPart> arrayList1 =new ArrayList<>();




        progressBar.setVisibility(View.VISIBLE);


        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Serverapi.GETPART+i, null, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {

                try {


                    JSONArray jsonArray = response.getJSONArray("data");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject screen = jsonArray.getJSONObject(i);
                        ItemPart item_request=new ItemPart();
                        item_request.setId(screen.getInt("id"));
                        item_request.setName(screen.getString("name"));
                        item_request.setImage(screen.getString("image"));
                        item_request.setPrice(screen.getString("price"));
                        arrayList1.add(item_request);

                    }




                   adapter_requestPart=new Adapter_RequestPart(arrayList1, context, new EditPart() {
                       @Override
                       public void EditPart(int id, String name, String price, String image) {

                           Intent intent=new Intent(getBaseContext(),EditPartActivity.class);
                           intent.putExtra("id",id);
                           intent.putExtra("name",name);
                           intent.putExtra("image",image);
                           intent.putExtra("price",price);
                           intent.putExtra("i",i);
                           startActivity(intent);
                           finish();
                       }
                   });

                    recyclerView.setAdapter(adapter_requestPart);
                    RecyclerView.LayoutManager layoutManager=new GridLayoutManager(getBaseContext(),2);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setHasFixedSize(true);
                    adapter_requestPart.notifyDataSetChanged();

                    progressBar.setVisibility(View.GONE);
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressBar.setVisibility(View.GONE);
                button.setVisibility(View.VISIBLE);
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