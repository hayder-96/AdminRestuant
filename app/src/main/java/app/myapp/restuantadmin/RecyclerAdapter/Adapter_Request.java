package app.myapp.restuantadmin.RecyclerAdapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import app.myapp.restuantadmin.FoodPart.FoodPartActivity;
import app.myapp.restuantadmin.HomeAdmin.EditFood;
import app.myapp.restuantadmin.HomeAdmin.RequestFromUser;
import app.myapp.restuantadmin.Model.Item_request;
import app.myapp.restuantadmin.R;
import app.myapp.restuantadmin.ServerApi.Serverapi;
import app.myapp.restuantadmin.VollyLibary.VollyLib;
import app.myapp.restuantadmin.VollySinglistion.VollySing;

public class Adapter_Request extends RecyclerView.Adapter<Adapter_Request.MyHolder> {


    ArrayList<Item_request> arrayList;
    Context context;
    EditFood editFood;
    public Adapter_Request(ArrayList<Item_request> arrayList,Context context,EditFood editFood) {
        this.arrayList = arrayList;
        this.editFood=editFood;
        this.context=context;
    }

    @NonNull
    @Override
    public Adapter_Request.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.split_user_request, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Request.MyHolder holder, int position) {

        Item_request s=arrayList.get(position);

        int id=s.getId();
        holder.textView.setText(s.getName());

        if (s.getImage().equals("no")){

            holder.imageView.setImageResource(R.drawable.ic_home);
        }else {
            Picasso.get().load(s.getImage()).resize(500,300).into(holder.imageView);
        }




        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                editFood.Edit(id,s.getName(),s.getImage());


            }
        });

        holder.delet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Alert(id,position);
            }
        });

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context, FoodPartActivity.class);
                intent.putExtra("ii",s.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;
        ImageView edit,delet;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            textView=itemView.findViewById(R.id.text);
            imageView=itemView.findViewById(R.id.image_food);
            edit=itemView.findViewById(R.id.but_edit);
            delet=itemView.findViewById(R.id.but_delete);
        }
    }





    public void deleteItem(int id,int po) {

        final String token = VollySing.getInstanse(context).getToken().getToken();

        arrayList.remove(po);
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE, Serverapi.MAINMENU+ "/" + id, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    if (response.getBoolean("success")) {
                        Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();



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

        RequestFromUser.noty();


    }














    private void Alert(int id,int pos){


        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setMessage("هل تريد فعلا الحذف");
        builder.setCancelable(false);
        builder.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteItem(id,pos);

            }
        });
        builder.setNegativeButton("لا", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });
        builder.show();
    }


}
