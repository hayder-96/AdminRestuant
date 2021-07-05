package app.myapp.restuantadmin.HomeAdmin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import java.security.Permission;
import java.util.HashMap;
import java.util.Map;

import app.myapp.restuantadmin.MainActivity;
import app.myapp.restuantadmin.R;
import app.myapp.restuantadmin.ServerApi.MultipartRequest;
import app.myapp.restuantadmin.ServerApi.Serverapi;
import app.myapp.restuantadmin.VollyLibary.VollyLib;
import app.myapp.restuantadmin.VollySinglistion.VollySing;

public class AddFodd extends AppCompatActivity {

    EditText name;
    ImageView imageView;
     Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fodd);


        name=findViewById(R.id.text_add_main);
        imageView=findViewById(R.id.image_add_main);


        imageView.setImageResource(R.drawable.ic_home);




        imageView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {


            if (ContextCompat.checkSelfPermission(getBaseContext(),Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED) {

                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 2);
            }else {

                ActivityCompat.requestPermissions(AddFodd.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 4);



            }
        }
    });

    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 4) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 2);
            }
        }


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

    public void add(View view) {



        mulityy();

    }






    private void mulityy() {


        String token = VollySing.getInstanse(getBaseContext()).getToken().getToken();
        Map<String, String> map1 = new HashMap<>();
        map1.put("Accept", "application/json");
        map1.put("Authorization", "Bearer " + token);



        MultipartRequest request = new MultipartRequest(Serverapi.MAINMENU, map1,
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



        if (TextUtils.isEmpty(name1)) {
            name.setError("Enter title");
            name.requestFocus();
            return;
        }




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















    public byte[] getFileDataFromDrawable(Bitmap bitmap1) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap1.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();

    }



}