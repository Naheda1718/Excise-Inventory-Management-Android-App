package com.excise.excisemanagement;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

public class CustomAdapter extends BaseAdapter {
    private Context context;

        private final String name[];
        private  final int images[];
    private int mSelectedItem = -1;


    public CustomAdapter(String[] name, int[] images, Context context) {
        this.name = name;
        this.images = images;
        this.context = context;
    }



    @Override
    public int getCount() {
        return name.length;
    }

    @Override
    public Object getItem(int position) {
        return name.length;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.grid_item, null);
        }

        TextView itemNameTextView = view.findViewById(R.id.itemname);
        ImageView itemImageView = view.findViewById(R.id.itemimage);


        itemNameTextView.setText(name[position]);
        itemImageView.setImageResource(images[position]);

        if (position == mSelectedItem) {

            view.setBackgroundColor(ContextCompat.getColor(context, R.color.green));
        } else {
            // Reset background color for non-selected items
            view.setBackgroundColor(Color.TRANSPARENT);
        }

        // Load image using Picasso



       
        return view;
    }


}


