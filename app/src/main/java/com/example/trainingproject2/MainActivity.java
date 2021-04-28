package com.example.trainingproject2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    Retrofit retrofit;
    String term =null;
    SearchView search_btn ;
    MyCustomAdapter dataAdapter = null;
    ListView listView;
    List<Bobj.Business> businesses_List;
    private View contactview ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView1);
        listView.setAdapter(dataAdapter);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
                Request newRequest  = chain.request().newBuilder()
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

        Call<Bobj> call = (Call<Bobj>) yelp_service.getResturents();
        call.enqueue(new Callback<Bobj>() {
            @Override
            public void onResponse(Call<Bobj> call, Response<Bobj> response) {
                Bobj obj_list = response.body();
              businesses_List =obj_list.getBusinesses();
                dataAdapter = new MyCustomAdapter(MainActivity.this, R.layout.resturent_list, businesses_List);
                listView.setAdapter(dataAdapter);
                Log.d("result", new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));


            }



            @Override
            public void onFailure(Call<Bobj> call, Throwable t) {
                t.printStackTrace();

            }

        });


        search_btn =(SearchView) findViewById(R.id.search_view);
        search_btn.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query != null)
                    call_Search(query,retrofit);
                search_btn.setQuery(null,false);
                return true ;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

    }
    public  void call_Search(String query,Retrofit retrofit){
        if (query.isEmpty() == false ) {
            Yelp_Service yelp_service = retrofit.create(Yelp_Service.class);
            Call<Bobj> call = (Call<Bobj>) yelp_service.getResturentswhithterm(query);
            call.enqueue(new Callback<Bobj>() {
                @Override
                public void onResponse(Call<Bobj> call, Response<Bobj> response) {
                    Bobj obj_list = response.body();
                    businesses_List =obj_list.getBusinesses();
                    dataAdapter = new MyCustomAdapter(MainActivity.this, R.layout.resturent_list, businesses_List);
                    listView.setAdapter(dataAdapter);
                    Log.d("result", new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));


                }

                @Override
                public void onFailure(Call<Bobj> call, Throwable t) {
                    t.printStackTrace();

                }

            });
        }else{
            Log.d("result","No query ");

        }


    }
}