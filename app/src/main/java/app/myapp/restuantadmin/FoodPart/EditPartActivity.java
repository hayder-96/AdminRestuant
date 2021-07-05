package app.myapp.restuantadmin.FoodPart;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import app.myapp.restuantadmin.HomeAdmin.RequestFromUser;
import app.myapp.restuantadmin.MainActivity;
import app.myapp.restuantadmin.R;
import app.myapp.restuantadmin.ServerApi.MultipartRequest;
import app.myapp.restuantadmin.ServerApi.Serverapi;
import app.myapp.restuantadmin.VollyLibary.VollyLib;
import app.myapp.restuantadmin.VollySinglistion.VollySing;

public class EditPartActivity extends AppCompatActivity {


    ImageView imageView;
    EditText name,price;
    Button button;
    String namee,image,pricee;
    Uri uri;
    int id,i;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_part);

        imageView=findViewById(R.id.image_edit_part);
        name=findViewById(R.id.text_edit_part);
        price=findViewById(R.id.price_edit_part);
        button=findViewById(R.id.but_edit_part);







         Intent intent=getIntent();
        id=intent.getIntExtra("id",-1);
        namee=intent.getStringExtra("name");
        image=intent.getStringExtra("image");
         pricee=intent.getStringExtra("price");
         i=intent.getIntExtra("i",-1);

         name.setText(namee);
         price.setText(pricee);
        if (image.equals("no")){
            imageView.setImageResource(R.drawable.ic_home);
        }else {
            Picasso.get().load(image).resize(200,200).into(imageView);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mulityy();
            }
        });





        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 2);
            }
        });

    }






    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == RESULT_OK) {
            if (data != null) {

                uri = data.getData();
                Picasso.get().load(uri.toString()).resize(200, 200).into(imageView);

            }
        }
    }






    private void mulityy() {


        String token = VollySing.getInstanse(getBaseContext()).getToken().getToken();
        Map<String, String> map1 = new HashMap<>();
        map1.put("Accept", "application/json");
        map1.put("Authorization", "Bearer " + token);



        MultipartRequest request = new MultipartRequest(Serverapi.MENUPART_UPDATE, map1,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });


        final String name1=name.getText().toString().trim();
        final String p=price.getText().toString().trim();


        if (TextUtils.isEmpty(name1)) {
            name.setError("Enter title");
            name.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(p)) {
            price.setError("Enter price");
            price.requestFocus();
            return;
        }


        request.addPart(new MultipartRequest.FormPart("id",id+""));


        Toast.makeText(getApplicationContext(),id+"",Toast.LENGTH_LONG).show();

        request.addPart(new MultipartRequest.FormPart("name",name1));
        request.addPart(new MultipartRequest.FormPart("price",p));

        if (uri != null) {
            try {

                Bitmap bitmapp;
                bitmapp = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);



                Bitmap bitmap = Bitmap.createScaledBitmap(bitmapp,200,200, false);



                request.addPart(new MultipartRequest.FilePart("image", "*/*", uri.toString(), getFileDataFromDrawable(bitmap)));

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        Intent intent=new Intent(getBaseContext(),FoodPartActivity.class);
        intent.putExtra("ii",i);
        startActivity(intent);
        finish();
        VollyLib.getInstance(getBaseContext()).addRequest(request);


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(getBaseContext(),FoodPartActivity.class);
        intent.putExtra("ii",i);
        startActivity(intent);
        finish();
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap1) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap1.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();

    }


    public void delete_part(View view) {

        Alert();

    }






    private void Alert(){


        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("هل تريد فعلا الحذف");
        builder.setCancelable(false);
        builder.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteItem(id);

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






    public void deleteItem(int idi) {

        final String token = VollySing.getInstanse(getBaseContext()).getToken().getToken();


        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE, Serverapi.PARTFOOD+ "/" + idi, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    if (response.getBoolean("success")) {
                        Toast.makeText(getBaseContext(), response.getString("message"), Toast.LENGTH_SHORT).show();

                        Intent intent=new Intent(getBaseContext(),FoodPartActivity.class);
                        intent.putExtra("ii",i);
                        startActivity(intent);
                        finish();


                    } else {

                        Toast.makeText(getBaseContext(), "error", Toast.LENGTH_SHORT).show();

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
}
