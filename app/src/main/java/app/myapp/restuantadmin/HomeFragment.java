package app.myapp.restuantadmin;

import android.icu.text.IDNA;
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
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

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
import app.myapp.restuantadmin.Model.Item_request;
import app.myapp.restuantadmin.RecyclerAdapter.Adapter_Accept;
import app.myapp.restuantadmin.ServerApi.Serverapi;
import app.myapp.restuantadmin.VollyLibary.VollyLib;
import app.myapp.restuantadmin.VollySinglistion.VollySing;

public class HomeFragment extends Fragment {


   static Adapter_Accept adapter_accept;


   ProgressBar progressBar;
   ImageView button;

    public HomeFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_home, container, false);

        progressBar=view.findViewById(R.id.progress_tran);
        RecyclerView r=view.findViewById(R.id.recycler_accept);
        button=view.findViewById(R.id.but_loaded_tran);
        SwipeRefreshLayout swipeRefreshLayout=view.findViewById(R.id.swip_tran);
        TextView t=view.findViewById(R.id.text_test);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<InformUser> arrayList=dataMessage(t);


                adapter_accept=new Adapter_Accept(arrayList,getActivity());

                r.setAdapter(adapter_accept);
                RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity());
                r.setLayoutManager(layoutManager);
                r.setHasFixedSize(true);
                adapter_accept.notifyDataSetChanged();
                button.setVisibility(View.GONE);
            }
        });




        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {


                ArrayList<InformUser> arrayList=dataMessage(t);


                adapter_accept=new Adapter_Accept(arrayList,getActivity());

                r.setAdapter(adapter_accept);
                RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity());
                r.setLayoutManager(layoutManager);
                r.setHasFixedSize(true);
                adapter_accept.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });










        ArrayList<InformUser> arrayList=dataMessage(t);


         adapter_accept=new Adapter_Accept(arrayList,getActivity());

        r.setAdapter(adapter_accept);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity());
        r.setLayoutManager(layoutManager);
        r.setHasFixedSize(true);
        adapter_accept.notifyDataSetChanged();

        return view;
    }






    private ArrayList<InformUser> dataMessage(TextView t) {

        final String token = VollySing.getInstanse(getActivity()).getToken().getToken();

        ArrayList<InformUser> arrayList1 =new ArrayList<>();



        progressBar.setVisibility(View.VISIBLE);


        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Serverapi.GETREQUEST_NO, null, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {

                try {


                    JSONArray jsonArray = response.getJSONArray("data");

                    if (jsonArray.length()==0) {
                        t.setVisibility(View.VISIBLE);
                    }
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject screen = jsonArray.getJSONObject(i);
                        InformUser item_menu_user=new InformUser();
                        item_menu_user.setId(screen.getInt("id"));
                        item_menu_user.setName(screen.getString("name"));
                        item_menu_user.setLatitude(screen.getString("latidtude"));
                        item_menu_user.setLongtude(screen.getString("longtude"));
                        item_menu_user.setDelivery(screen.getString("delivery"));

                        arrayList1.add(item_menu_user);

                        adapter_accept.notifyDataSetChanged();
                    }

                    adapter_accept.notifyDataSetChanged();
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



    public static void notyA(){

        adapter_accept.notifyDataSetChanged();
    }






}