package app.myapp.restuantadmin.HomeAdmin;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

import app.myapp.restuantadmin.Model.Item_request;
import app.myapp.restuantadmin.R;
import app.myapp.restuantadmin.RecyclerAdapter.Adapter_Request;
import app.myapp.restuantadmin.ServerApi.Serverapi;
import app.myapp.restuantadmin.VollyLibary.VollyLib;
import app.myapp.restuantadmin.VollySinglistion.VollySing;

public class RequestFromUser extends Fragment {


    ProgressBar progressBar;
    ImageView button;
    static Adapter_Request adapter_request;
    public RequestFromUser() {

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View view=inflater.inflate(R.layout.fragment_request_from_user, container, false);

       progressBar=view.findViewById(R.id.progress_request_user);
       button=view.findViewById(R.id.but_loaded);
       RecyclerView r=view.findViewById(R.id.recycler);

       SwipeRefreshLayout swipeRefreshLayout=view.findViewById(R.id.swip_1);

       button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {



               ArrayList<Item_request> arrayList=dataMessage();



               adapter_request=new Adapter_Request(arrayList,getActivity(), new EditFood() {
                   @Override
                   public void Edit(int id, String name, String image) {
                       Intent intent=new Intent(getActivity(), EditFoodAdmin.class);
                       intent.putExtra("id",id);
                       intent.putExtra("name",name);
                       intent.putExtra("image",image);
                       startActivity(intent);
                       getActivity().finish();
                   }
               });
               r.setAdapter(adapter_request);
               RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity());
               r.setLayoutManager(layoutManager);
               r.setHasFixedSize(true);

                button.setVisibility(View.GONE);
           }
       });



        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {



                ArrayList<Item_request> arrayList=dataMessage();



                adapter_request=new Adapter_Request(arrayList,getActivity(), new EditFood() {
                    @Override
                    public void Edit(int id, String name, String image) {
                        Intent intent=new Intent(getActivity(), EditFoodAdmin.class);
                        intent.putExtra("id",id);
                        intent.putExtra("name",name);
                        intent.putExtra("image",image);
                        startActivity(intent);
                        getActivity().finish();
                    }
                });
                r.setAdapter(adapter_request);
                RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity());
                r.setLayoutManager(layoutManager);
                r.setHasFixedSize(true);
                swipeRefreshLayout.setRefreshing(false);





            }
        });










       ArrayList<Item_request> arrayList=dataMessage();



        adapter_request=new Adapter_Request(arrayList,getActivity(), new EditFood() {
            @Override
            public void Edit(int id, String name, String image) {
                Intent intent=new Intent(getActivity(), EditFoodAdmin.class);
                intent.putExtra("id",id);
                intent.putExtra("name",name);
                intent.putExtra("image",image);
                startActivity(intent);
                getActivity().finish();
            }
        });
        r.setAdapter(adapter_request);
                    RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity());
                    r.setLayoutManager(layoutManager);
                    r.setHasFixedSize(true);




        return view;

    }





    private ArrayList<Item_request> dataMessage() {

        final String token = VollySing.getInstanse(getActivity()).getToken().getToken();

        ArrayList<Item_request> arrayList1 =new ArrayList<>();



        progressBar.setVisibility(View.VISIBLE);


        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Serverapi.MAINMENU, null, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {

                try {


                    JSONArray jsonArray = response.getJSONArray("data");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject screen = jsonArray.getJSONObject(i);
                        Item_request item_request=new Item_request();
                        item_request.setId(screen.getInt("id"));
                       item_request.setName(screen.getString("name"));
                       item_request.setImage(screen.getString("image"));
                        arrayList1.add(item_request);
                        adapter_request.notifyDataSetChanged();
                    }

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
        VollyLib.getInstance(getActivity()).addRequest(jsonObjectRequest);


        return arrayList1;

    }


    public static void noty(){

        adapter_request.notifyDataSetChanged();


    }














}