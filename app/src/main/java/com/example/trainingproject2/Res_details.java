package com.example.trainingproject2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Res_details extends AppCompatActivity {
    private ImageView res_image, photo1, photo2, photo3,ic_closed_image;
    private TextView res_name, res_rating, time, is_closed, phone;
    Retrofit retrofit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_res_details);
        String id_from_intent = getIntent().getStringExtra("id");
        Log.d("id", id_from_intent);
        res_image = findViewById(R.id.res_image);
        photo1 = findViewById(R.id.res_photo1);
        photo2 = findViewById(R.id.res_photo2);
        photo3 = findViewById(R.id.res_photo3);
        res_name = findViewById(R.id.res_name);
        res_rating = findViewById(R.id.res_rating);
        time = findViewById(R.id.res_time);
        is_closed = findViewById(R.id.res_avalibality);
        phone = findViewById(R.id.res_phone);
        ic_closed_image=findViewById(R.id.image_avaliable);




        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
                Request newRequest = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + "MMMQuWnLGwlTYdSMtKujs774rvSF8-g78jwHgo35nIoahZ1df13ph_HxFOTGIpGD-_CVpb--RMaPA2clqd8koS8x58EPk6H9fkhW791Uws_LRizh44UyVyGh4feNX3Yx")
                        .build();
                return chain.proceed(newRequest);
            }
        }).build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Yelp_Service.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        Yelp_Service yelp_service = retrofit.create(Yelp_Service.class);

        Call<ResturentInfo> call = (Call<ResturentInfo>) yelp_service.getResturentInfo(id_from_intent);
       call.enqueue(new Callback<ResturentInfo>() {
           @Override
           public void onResponse(Call<ResturentInfo> call, Response<ResturentInfo> response) {
               ResturentInfo res_details=response.body();
               Log.d("result22", new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
               Picasso.get().load(res_details.getImage_url()).into(res_image);
               ArrayList<String> photos=(ArrayList<String>) res_details.getPhotos();

               Picasso.get().load(photos.get(0)).into(photo1);
               Picasso.get().load(photos.get(1)).into(photo2);
               Picasso.get().load(photos.get(2)).into(photo3);

               res_name.setText(res_details.getName());
               if(res_details.getIs_closed()==true){
                   is_closed.setText("Closed");
                   ic_closed_image.setImageDrawable(getDrawable(R.drawable.ic_baseline_close_24));

               }
               else{
                   is_closed.setText("Open");
                   ic_closed_image.setImageDrawable(getDrawable(R.drawable.ic_yes));
               }
               res_rating.setText(getString(R.string.star_text,res_details.getRating()));

           }

           @Override
           public void onFailure(Call<ResturentInfo> call, Throwable t) {
               t.printStackTrace();

           }
       });




    }

}