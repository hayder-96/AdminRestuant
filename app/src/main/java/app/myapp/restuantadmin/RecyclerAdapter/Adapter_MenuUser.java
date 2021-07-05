package app.myapp.restuantadmin.RecyclerAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import app.myapp.restuantadmin.MapsActivity;
import app.myapp.restuantadmin.MenuUser;
import app.myapp.restuantadmin.Model.InformUser;
import app.myapp.restuantadmin.Model.Item_Menu_User;
import app.myapp.restuantadmin.R;

public class Adapter_MenuUser extends RecyclerView.Adapter<Adapter_MenuUser.MyHolder> {


    ArrayList<Item_Menu_User> arrayList;


    public Adapter_MenuUser(ArrayList<Item_Menu_User> arrayList) {
        this.arrayList = arrayList;

    }

    @NonNull
    @Override
    public Adapter_MenuUser.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.split_menu, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_MenuUser.MyHolder holder, int position) {

        Item_Menu_User item_menu_user=arrayList.get(position);

        holder.name.setText(item_menu_user.getName());
         holder.price.setText(item_menu_user.getPrice()+"\t"+"دع");
         holder.number.setText(item_menu_user.getNumber());

         if (item_menu_user.equals("no")){
             holder.imageView.setImageResource(R.drawable.ic_home);
         }else {

             Picasso.get().load(item_menu_user.getImage()).resize(200,200).into(holder.imageView);
         }



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView name,price,number;
        ImageView imageView;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.image_menu_user);
            name=itemView.findViewById(R.id.name_user);
           price=itemView.findViewById(R.id.price_user);
           number=itemView.findViewById(R.id.number_user);

        }
    }
}
