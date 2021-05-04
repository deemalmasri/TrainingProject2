package com.example.trainingproject2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

public class MainActivity extends AppCompatActivity implements LocationListener {
    Retrofit retrofit;
    SearchView search_btn;
    MyCustomAdapter dataAdapter = null;
    double lat;
    double lon;
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;

    RecyclerView cost_recyclerView, bit_recyclerView, big_recyclerView;
    List<Bobj.Business> businesses_List, cost_businesses_List, bit_businesses_List, big_businesses_List, businesses_List_search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cost_recyclerView = findViewById(R.id.Cost_effectiveView);
        bit_recyclerView = findViewById(R.id.Bit_PriceirveView);
        big_recyclerView = findViewById(R.id.Big_spendereView);
        cost_businesses_List = new ArrayList<>();
        bit_businesses_List = new ArrayList<>();
        big_businesses_List = new ArrayList<>();

//        LocationRequest locationRequest = LocationRequest.create();
//        locationRequest.setInterval(10000);
//        locationRequest.setFastestInterval(5000);
//        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            },100);
        }



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
                businesse_Filter(businesses_List);
                Log.d("result", new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                setRecyclerview(cost_businesses_List,cost_recyclerView);
                setRecyclerview(bit_businesses_List,bit_recyclerView);
                setRecyclerview(big_businesses_List,big_recyclerView);
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
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            return;
        }


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 0, (android.location.LocationListener) this);






    }
    public  void call_Search(String query,Retrofit retrofit){
        if (query.isEmpty() == false ) {
            Yelp_Service yelp_service = retrofit.create(Yelp_Service.class);
            Call<Bobj> call = (Call<Bobj>) yelp_service.getResturentswhithterm(query);
            call.enqueue(new Callback<Bobj>() {
                @Override
                public void onResponse(Call<Bobj> call, Response<Bobj> response) {
                    response.body();
                    Bobj obj_list = response.body();
                    businesses_List =obj_list.getBusinesses();
                    cost_businesses_List.clear();
                    bit_businesses_List .clear();
                    big_businesses_List.clear();
                    Log.d("resultsearch", new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                    businesse_Filter(businesses_List);
                    setRecyclerview(cost_businesses_List,cost_recyclerView);
                    setRecyclerview(bit_businesses_List,bit_recyclerView);
                    setRecyclerview(big_businesses_List,big_recyclerView);


                    Log.d("resultsearch", new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));


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
    public void businesse_Filter(List<Bobj.Business> list){

        for (Bobj.Business obj:list) {
            if (obj.getPrice() !=null) {

                if (obj.getPrice().equals("$")) {
                    cost_businesses_List.add(obj);
                } else {
                    if (obj.getPrice().equals("$$")) {
                        bit_businesses_List.add(obj);

                    } else {
                        if (obj.getPrice().equals("$$$")) {
                            big_businesses_List.add(obj);
                        }
                    }
                }

            }else {
                cost_businesses_List.add(obj);
            }
                }

            }


    public  void setRecyclerview (List<Bobj.Business> list ,RecyclerView list_recycle){
        dataAdapter = new MyCustomAdapter(list);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        list_recycle.setLayoutManager(mLayoutManager);
        list_recycle.setItemAnimator(new DefaultItemAnimator());
        list_recycle.setAdapter(dataAdapter);

    }

    @SuppressLint("MissingPermission")
    private void getLocation() {

        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30000, 100, (android.location.LocationListener) this);

//            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,5, (android.location.LocationListener) MainActivity.this);

        }catch (Exception e){
            e.printStackTrace();
        }

    }


    @Override
    public void onLocationChanged(@NonNull Location location) {
        Toast.makeText(this, ""+location.getLatitude()+","+location.getLongitude(), Toast.LENGTH_SHORT).show();

    }
}
