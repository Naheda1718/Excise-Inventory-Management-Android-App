package com.excise.excisemanagement;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Inprocessadapter extends BaseAdapter {
    //ImageView inedit,indelete;
    private Context context;
    private ArrayList<Inprocessitems> inprocesslist;

    private ArrayList<Inprocessitems> filteredList;

    public Inprocessadapter(Context context, ArrayList<Inprocessitems> inprocesslist) {
        this.context = context;
        this.inprocesslist = inprocesslist;
        this.filteredList = new ArrayList<>(inprocesslist);
    }

    public void filterlist(ArrayList<Inprocessitems> filteredList) {
        inprocesslist=filteredList;

        // Update both itemList and filteredList
        notifyDataSetChanged();
    }

    public ArrayList<Inprocessitems> getInprocesslist() {
        return inprocesslist;
    }
    @Override
    public int getCount() {
        return inprocesslist.size();
    }

    @Override
    public Object getItem(int position) {
        return inprocesslist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.inprocess_row_item, parent, false);
            holder = new ViewHolder();

            holder.inprocessid=convertView.findViewById(R.id.inprocessid);
            holder.datetime = convertView.findViewById(R.id.txtindatetime);
            holder.txtinprname = convertView.findViewById(R.id.txtinprname);
            holder.txtinstamp = convertView.findViewById(R.id.txtinstamp);
            holder.txtinstampmatches = convertView.findViewById(R.id.txtinstampmatches);
            holder.txtinproduct = convertView.findViewById(R.id.txtinproduct);
            holder.txtinlot = convertView.findViewById(R.id.txtinlot);
            holder.txtinpackagedate = convertView.findViewById(R.id.txtinpackagedate);
            holder.txtinvlocation = convertView.findViewById(R.id.txtinlocation);
            holder.txtinvstamptoinv = convertView.findViewById(R.id.txtinstamptoinv);
            holder.txtinvdamagestam = convertView.findViewById(R.id.txtindamagestamp);
            holder.txtinvreasondamage = convertView.findViewById(R.id.txtinreasondamag);
            holder.txtinvdoneby = convertView.findViewById(R.id.txtindoneby);
         //   holder.inedit=convertView.findViewById(R.id.inprocessedit);

//            inedit.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    showdialoge();
//                }
//            });



            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Inprocessitems inprocessitems = inprocesslist.get(position);

        holder.inprocessid.setText(inprocessitems.getInprocessid());
        holder.datetime.setText(inprocessitems.getDatetime());
        holder.txtinprname.setText(inprocessitems.getProvince());
        holder.txtinstamp.setText(inprocessitems.getStamptaken());
        holder.txtinstampmatches.setText(inprocessitems.getStampmatches());
        holder.txtinproduct.setText(inprocessitems.getProduct());
        holder.txtinlot.setText(inprocessitems.getLot());
        holder.txtinpackagedate.setText(inprocessitems.getPackagedate());
        holder.txtinvlocation.setText(inprocessitems.getLocation());
        holder.txtinvstamptoinv.setText(inprocessitems.getStamptoinv());
        holder.txtinvdamagestam.setText(inprocessitems.getDamagestamp());
        holder.txtinvreasondamage.setText(inprocessitems.getReasndamage());
        holder.txtinvdoneby.setText(inprocessitems.getDoneby());


        return convertView;
    }

    private void showdialoge() {
        final Dialog dialog = new Dialog(context.getApplicationContext());
        dialog.setContentView(R.layout.dialoge_blank);

        // Optionally, you can set a title for the dialog
        dialog.setTitle("Blank Dialog");

        dialog.show(); // Show the dialog

    }

    public ArrayList<Inprocessitems> getFilteredList() {
        return filteredList;
    }

    static class ViewHolder {
        TextView datetime;
        TextView txtinprname;
        TextView txtinstamp;
        TextView txtinstampmatches;
        TextView txtinproduct;
        TextView txtinlot;
        TextView txtinpackagedate;
        TextView txtinvlocation;
        TextView txtinvstamptoinv;
        TextView txtinvdamagestam;
        TextView txtinvreasondamage;
        TextView txtinvdoneby;

        TextView inprocessid;

       // ImageView inedit;

    }

}
