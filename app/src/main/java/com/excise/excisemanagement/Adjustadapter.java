package com.excise.excisemanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Adjustadapter extends BaseAdapter {
    private Context context;
    private ArrayList<Adjustitems> adjustlist;

    private ArrayList<Adjustitems> filteredList;
    public Adjustadapter(Context context, ArrayList<Adjustitems> adjustlist) {
        this.context = context;
        this.adjustlist = adjustlist;
        this.filteredList = new ArrayList<>(adjustlist);
    }

    public void filterlist(ArrayList<Adjustitems> filteredList) {
        adjustlist=filteredList;

        // Update both itemList and filteredList
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return adjustlist.size();
    }

    @Override
    public Object getItem(int position) {
        return adjustlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adjust_row_item, parent, false);
            holder = new ViewHolder();

            holder.date=convertView.findViewById(R.id.txtadjustdate);
            holder.province = convertView.findViewById(R.id.txtadjustprovince);
            holder.adjustvalue = convertView.findViewById(R.id.txtadjustvalue);
            holder.adjustreason = convertView.findViewById(R.id.txtadjustreason);
            holder.doneby = convertView.findViewById(R.id.txtadjustdoneby);



            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Adjustitems adjustitems = adjustlist.get(position);

        holder.date.setText(adjustitems.getDate());
        holder.province.setText(adjustitems.getProvincename());
        holder.adjustvalue.setText(adjustitems.getAdjustvalue());
        holder.adjustreason.setText(adjustitems.getAdjustreason());
        holder.doneby.setText(adjustitems.getDoneby());


        return convertView;
    }
    public ArrayList<Adjustitems> getFilteredList() {
        return filteredList;
    }

    static class ViewHolder {
        TextView date;
        TextView province;
        TextView adjustvalue;
        TextView adjustreason;
      TextView doneby;

    }
}