package com.example.trainingproject2;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;


import com.squareup.picasso.Picasso;

import java.util.List;


public class MyCustomAdapter extends RecyclerView.Adapter<MyCustomAdapter.My_ViewHolder>{
    private List<Bobj.Business> resturentList;

      class My_ViewHolder extends RecyclerView.ViewHolder  {
        TextView displayName;
        TextView rating;
        ImageView resPic;

        public My_ViewHolder(@NonNull View itemView) {
            super(itemView);
            displayName=itemView.findViewById(R.id.displayName);
            rating=itemView.findViewById(R.id.display_rating);
            resPic=itemView.findViewById(R.id.idIVContact);

        }
    }
    public MyCustomAdapter(List<Bobj.Business> resturentList) {
        this.resturentList = resturentList;
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
                business_obj.getRating()));
        Picasso.get().load(business_obj.getImage_url()).into(holder.resPic);

    }

    @Override
    public int getItemCount() {
        return resturentList.size();
    }



}



