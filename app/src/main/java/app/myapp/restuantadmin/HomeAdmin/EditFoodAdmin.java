package app.myapp.restuantadmin.HomeAdmin;

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

public class EditFoodAdmin extends AppCompatActivity {

    EditText editText;
    ImageView imageView;
    Intent intent;
    String name,image;
    Uri uri;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_food_admin);

        editText=findViewById(R.id.text_edit_main);
        imageView=findViewById(R.id.image_edit_main);


        intent=getIntent();
         id=intent.getIntExtra("id",-1);
         name=intent.getStringExtra("name");
         image=intent.getStringExtra("image");


         editText.setText(name);

         if (image.equals("no")){
             imageView.setImageResource(R.drawable.ic_home);
         }else {
             Picasso.get().load(image).resize(200,200).into(imageView);
         }




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




    public void edit(View view) {

        mulityy();
    }





    private void mulityy() {


        String token = VollySing.getInstanse(getBaseContext()).getToken().getToken();
        Map<String, String> map1 = new HashMap<>();
        map1.put("Accept", "application/json");
        map1.put("Authorization", "Bearer " + token);



        MultipartRequest request = new MultipartRequest(Serverapi.MAINMENU_UPDATE, map1,
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


        final String name1=editText.getText().toString().trim();



        if (TextUtils.isEmpty(name1)) {
            editText.setError("Enter title");
            editText.requestFocus();
            return;
        }


        request.addPart(new MultipartRequest.FormPart("id",id+""));



        request.addPart(new MultipartRequest.FormPart("name",name1));


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
        Intent intent=new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
        finish();
        VollyLib.getInstance(getBaseContext()).addRequest(request);


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getBaseContext(),MainActivity.class));
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap1) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap1.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();

    }
















}