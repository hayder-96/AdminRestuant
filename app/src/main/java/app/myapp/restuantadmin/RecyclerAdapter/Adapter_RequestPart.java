package app.myapp.restuantadmin.RecyclerAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

import app.myapp.restuantadmin.FoodPart.EditPart;
import app.myapp.restuantadmin.HomeAdmin.RequestFromUser;
import app.myapp.restuantadmin.Model.ItemPart;
import app.myapp.restuantadmin.R;
import app.myapp.restuantadmin.ServerApi.Serverapi;
import app.myapp.restuantadmin.VollyLibary.VollyLib;
import app.myapp.restuantadmin.VollySinglistion.VollySing;

public class Adapter_RequestPart extends RecyclerView.Adapter<Adapter_RequestPart.MyHolder> {


    ArrayList<ItemPart> arrayList;
    Context context;
   EditPart editPart;
    public Adapter_RequestPart(ArrayList<ItemPart> arrayList, Context context,EditPart editPart) {
        this.arrayList = arrayList;
        this.editPart=editPart;
        this.context=context;
    }

    @NonNull
    @Override
    public Adapter_RequestPart.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.split_part, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_RequestPart.MyHolder holder, int position) {

        ItemPart s=arrayList.get(position);

        int id=s.getId();
        holder.name.setText(s.getName());
        holder.price.setText(s.getPrice()+"\t"+" دع");
        if (s.getImage().equals("no")){

            holder.imageView.setImageResource(R.drawable.ic_home);
        }else {
            Picasso.get().load(s.getImage()).resize(300,200).into(holder.imageView);
        }



        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editPart.EditPart(id,s.getName(),s.getPrice(),s.getImage());
            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView name,price;
        ImageView imageView;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.text_part);
            price=itemView.findViewById(R.id.price_part);
            imageView=itemView.findViewById(R.id.image_food_part);

        }
    }























}
