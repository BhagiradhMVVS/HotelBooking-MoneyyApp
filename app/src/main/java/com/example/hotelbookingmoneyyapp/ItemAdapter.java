package com.example.hotelbookingmoneyyapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ItemAdapter extends ArrayAdapter<Item> {
    public ItemAdapter(Context context, ArrayList<Item> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_layout, parent, false);
        }

        Item currentItem = getItem(position);

        TextView Name = convertView.findViewById(R.id.name);
        Name.setText(currentItem.getName());

//        ImageView Image = convertView.findViewById(R.id.image);
//        Picasso.get().load(currentItem.getImage()).into(Image);

        TextView City = convertView.findViewById(R.id.city);
        City.setText(currentItem.getCity());

        TextView Country = convertView.findViewById(R.id.country);
        Country.setText(""+currentItem.getCountry());

        TextView Price = convertView.findViewById(R.id.price);
        Price.setText(""+currentItem.getPrice());

        RatingBar ratingBar = convertView.findViewById(R.id.ratingBar);
        ratingBar.setRating(currentItem.getRating());


        return convertView;
    }
}
