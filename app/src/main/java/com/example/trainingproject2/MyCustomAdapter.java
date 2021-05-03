package com.example.trainingproject2;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;


import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


public class MyCustomAdapter extends RecyclerView.Adapter<MyCustomAdapter.My_ViewHolder> {
    private List<Bobj.Business> resturentList;



    public MyCustomAdapter(List<Bobj.Business> resturentList) {
        this.resturentList = resturentList;
    }

      class My_ViewHolder extends RecyclerView.ViewHolder{
          TextView displayName;
        TextView rating;
        ImageView resPic;



        public My_ViewHolder(@NonNull View itemView ) {
            super(itemView);
           // this.mAdapter = adapter;
            displayName=itemView.findViewById(R.id.displayName);
            rating=itemView.findViewById(R.id.display_rating);
            resPic=itemView.findViewById(R.id.idIVContact);
           // itemView.setOnClickListener(this);


        }



      }


    @NonNull
    @Override
    public My_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.resturent_list, parent, false);

        return new My_ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull My_ViewHolder holder, int position) {
        Bobj.Business business_obj = (Bobj.Business) resturentList.get(position);
        holder.displayName.setText(business_obj.getName());
        holder.rating.setText(holder.itemView.getContext().getString(R.string.res_text,
                business_obj.getRating(),business_obj.getReview_count()));
        Picasso.get().load(business_obj.getImage_url()).into(holder.resPic);
        ViewCompat.setTransitionName(holder.resPic,business_obj.getName() );
        holder.itemView.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),Res_details.class);
                intent.putExtra("id",business_obj.getId());
               intent.putExtra("EXTRA_ITEM", business_obj.getImage_url());
                intent.putExtra("EXTRA_IMAGE_TRANSITION_NAME", ViewCompat.getTransitionName(holder.resPic));

                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        (Activity) holder.itemView.getContext(),
                        holder.resPic,
                        ViewCompat.getTransitionName(holder.resPic));
                v.getContext().startActivity(intent,options.toBundle());



            }
        });




    }

    @Override
    public int getItemCount() {
        return resturentList.size();
    }



}



