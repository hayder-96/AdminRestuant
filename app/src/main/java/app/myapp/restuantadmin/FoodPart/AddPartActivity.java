package app.myapp.restuantadmin.FoodPart;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import app.myapp.restuantadmin.MainActivity;
import app.myapp.restuantadmin.R;
import app.myapp.restuantadmin.ServerApi.MultipartRequest;
import app.myapp.restuantadmin.ServerApi.Serverapi;
import app.myapp.restuantadmin.VollyLibary.VollyLib;
import app.myapp.restuantadmin.VollySinglistion.VollySing;

public class AddPartActivity extends AppCompatActivity {


    ImageView imageView;
    EditText name,price;
    Uri uri;
    int food_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_part);

        imageView=findViewById(R.id.imageView_add_part);
        name=findViewById(R.id.name_add_part);
        price=findViewById(R.id.price_add_part);


        imageView.setImageResource(R.drawable.ic_home);



        Intent intent=getIntent();
        food_id=intent.getIntExtra("id",-1);


        Toast.makeText(this,food_id+"id",Toast.LENGTH_SHORT).show();






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







    public void addPart(View view) {

        mulityy();
    }




















    private void mulityy() {


        String token = VollySing.getInstanse(getBaseContext()).getToken().getToken();
        Map<String, String> map1 = new HashMap<>();
        map1.put("Accept", "application/json");
        map1.put("Authorization", "Bearer " + token);



        MultipartRequest request = new MultipartRequest(Serverapi.PARTFOOD, map1,
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




        request.addPart(new MultipartRequest.FormPart("name",name1));
        request.addPart(new MultipartRequest.FormPart("price",p));
        request.addPart(new MultipartRequest.FormPart("food_id",food_id+""));
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
        intent.putExtra("ii",food_id);
        startActivity(intent);
        finish();
        VollyLib.getInstance(getBaseContext()).addRequest(request);


    }














    public byte[] getFileDataFromDrawable(Bitmap bitmap1) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap1.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();

    }






    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(getBaseContext(),FoodPartActivity.class);
        intent.putExtra("ii",food_id);
        startActivity(intent);
        finish();
    }

















}