package app.myapp.restuantadmin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import app.myapp.restuantadmin.RecyclerAdapter.Adapter_Accept;
import app.myapp.restuantadmin.ServerApi.Serverapi;
import app.myapp.restuantadmin.VollyLibary.VollyLib;
import app.myapp.restuantadmin.VollySinglistion.VollySing;

public class FragmentTran extends Fragment {


   static Adapter_Accept adapter_accept;
   ImageView button;

   ProgressBar progressBar;
    public FragmentTran() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);

        progressBar=view.findViewById(R.id.progress_tran);
        RecyclerView r=view.findViewById(R.id.recycler_accept);
         TextView t=view.findViewById(R.id.text_test);
         button=view.findViewById(R.id.but_loaded_tran);
         SwipeRefreshLayout swipeRefreshLayout=view.findViewById(R.id.swip_tran);


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

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Serverapi.GETREQUEST_YES, null, new Response.Listener<JSONObject>() {


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
                        Toast.makeText(getActivity(),screen.getString("latidtude"),Toast.LENGTH_SHORT).show();

                        arrayList1.add(item_menu_user);

                        adapter_accept.notifyDataSetChanged();
                    }

                    progressBar.setVisibility(View.GONE);
                    adapter_accept.notifyDataSetChanged();


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


    public static void nnoo(){

        adapter_accept.notifyDataSetChanged();
    }




}