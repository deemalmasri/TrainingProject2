package com.example.trainingproject2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;


import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static android.provider.Settings.System.getString;

public class MyCustomAdapter extends ArrayAdapter {
    private List resturentList;
    private Context context;


    public MyCustomAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        this.context = context;
        resturentList = objects;
    }

    private class ViewHolder {
        TextView displayName;
        TextView rating;
        ImageView ivPic;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.resturent_list, null);

            holder = new ViewHolder();
            holder.displayName = (TextView) convertView.findViewById(R.id.displayName);
            holder.rating = (TextView) convertView.findViewById(R.id.display_rating);
            holder.ivPic = (ImageView) convertView.findViewById(R.id.idIVContact);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
       Bobj.Business business_obj = (Bobj.Business) resturentList.get(position);
        holder.displayName.setText(business_obj.getName());
        holder.rating.setText( context.getString(R.string.res_text,
                business_obj.getRating()));
        Picasso.get().load(business_obj.getImage_url()).into(holder.ivPic);


        return convertView;
    }

}


