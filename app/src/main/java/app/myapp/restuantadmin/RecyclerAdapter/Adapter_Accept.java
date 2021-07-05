package app.myapp.restuantadmin.RecyclerAdapter;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import app.myapp.restuantadmin.FragmentTran;
import app.myapp.restuantadmin.HomeAdmin.RequestFromUser;
import app.myapp.restuantadmin.HomeFragment;
import app.myapp.restuantadmin.MapsActivity;
import app.myapp.restuantadmin.MenuUser;
import app.myapp.restuantadmin.Model.InformUser;
import app.myapp.restuantadmin.R;
import app.myapp.restuantadmin.ServerApi.Serverapi;
import app.myapp.restuantadmin.VollyLibary.VollyLib;
import app.myapp.restuantadmin.VollySinglistion.VollySing;

public class Adapter_Accept extends RecyclerView.Adapter<Adapter_Accept.MyHolder> {


    ArrayList<InformUser> arrayList;
    Context context;
    MediaPlayer mediaPlayer;

    public Adapter_Accept(ArrayList<InformUser> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public Adapter_Accept.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.split_accsept, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Accept.MyHolder holder, int position) {

        InformUser informUser=arrayList.get(position);

        holder.name.setText(informUser.getName());
         int id=informUser.getId();
        Toast.makeText(context,informUser.getName()+"nn",Toast.LENGTH_SHORT).show();


        String s=informUser.getDelivery();
        if(s.equals("no")) {
            holder.done.setText("تم التوصيل");
        }else {
            holder.done.setText("حذف");
        }


        holder.gotoloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mediaPlayer=MediaPlayer.create(context,R.raw.sound_click);
                mediaPlayer.start();
                Intent intent=new Intent(context, MapsActivity.class);
                intent.putExtra("name",informUser.getName());
                intent.putExtra("lat",informUser.getLatitude());
                intent.putExtra("log",informUser.getLongtude());
                context.startActivity(intent);

            }
        });


        holder.request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer=MediaPlayer.create(context,R.raw.sound_click);
                mediaPlayer.start();
                Intent intent=new Intent(context, MenuUser.class);
                intent.putExtra("id",id);
                context.startActivity(intent);
            }
        });



        holder.done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mediaPlayer=MediaPlayer.create(context,R.raw.sound_click);
                mediaPlayer.start();
                String b=((Button)v).getText().toString();
                if (b.equals("تم التوصيل")){
                    Toast.makeText(context,"تمت الاضافة الى قائمة تم التوصيل",Toast.LENGTH_SHORT).show();
                    upDate(id,position);
                }else {
                    Toast.makeText(context,"yes",Toast.LENGTH_SHORT).show();
                    deleteItem(id,position);

                }
            }
        });








    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView name;
        Button gotoloc,request,done;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.name_accept);
            gotoloc=itemView.findViewById(R.id.but_locuser);
            request=itemView.findViewById(R.id.but_requser);
            done=itemView.findViewById(R.id.but_done_tran);
        }


















    }

    public void deleteItem(int id,int po) {

        final String token = VollySing.getInstanse(context).getToken().getToken();


        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE, Serverapi.DELETE_USER+id, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    if (response.getBoolean("success")) {
                        Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();

                        arrayList.remove(po);


                    } else {

                        Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();

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
        VollyLib.getInstance(context).addRequest(jsonObjectRequest);

        FragmentTran.nnoo();


    }



























        private void upDate(int id,int p) {


        final String token = VollySing.getInstanse(context).getToken().getToken();








        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("delivery","yes");
        } catch (Exception e) {
            e.printStackTrace();
        }

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, Serverapi.UPDATE_DELIVERY+id, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {

                    if (response.getBoolean("success")) {
                        Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();

                        //progressDialog.dismiss();
                        arrayList.remove(p);
                    } else {

                        Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
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
        VollyLib.getInstance(context).addRequest(jsonObjectRequest);


            HomeFragment.notyA();
    }
}
