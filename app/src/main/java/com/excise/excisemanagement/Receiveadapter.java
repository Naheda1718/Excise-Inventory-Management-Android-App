package com.excise.excisemanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Receiveadapter extends BaseAdapter {
    private Context context;
    private ArrayList<Receiveitems> rcvitems;
    private ArrayList<Receiveitems> filteredList;

    public Receiveadapter(Context context, ArrayList<Receiveitems> rcvitems) {
        this.context = context;
        this.rcvitems = rcvitems;
        this.filteredList = new ArrayList<>(rcvitems);
    }

    public void filterlist(ArrayList<Receiveitems> filteredList) {
        rcvitems=filteredList;

        // Update both itemList and filteredList
        notifyDataSetChanged();
    }

    public ArrayList<Receiveitems> getRcvitems() {
        return rcvitems;
    }
    @Override
    public int getCount() {
        return rcvitems.size();
    }

    @Override
    public Object getItem(int position) {
        return rcvitems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.receive_row_item, parent, false);
            holder = new ViewHolder();
            holder.datetime = convertView.findViewById(R.id.txtrcvdatetime);
            holder.txtrcvprname = convertView.findViewById(R.id.txtrcvprname);
            holder.txtrcvexcise = convertView.findViewById(R.id.txtrcvexcise);
          //  holder.txtrcvcomnt = convertView.findViewById(R.id.txtrcvcommt);
            holder.txtrcvstamp = convertView.findViewById(R.id.txtrcvstamp);
            holder.txtrcvlocation = convertView.findViewById(R.id.txtrcvlocation);
          //  holder.txtrcvdocument = convertView.findViewById(R.id.txtrcvdocument);
            holder.txtrcvaddcmt = convertView.findViewById(R.id.txtrcvaddcmt);
            holder.txtrcvdoneby = convertView.findViewById(R.id.txtrcvdoneby);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Receiveitems receiveitems = rcvitems.get(position);
        holder.datetime.setText(receiveitems.getDatetime());
        holder.txtrcvprname.setText(receiveitems.getProvince());
        holder.txtrcvexcise.setText(receiveitems.getExcise());
       // holder.txtrcvcomnt.setText(receiveitems.getCmnt());
        holder.txtrcvstamp.setText(receiveitems.getStamp());
        holder.txtrcvlocation.setText(receiveitems.getLocation());
      //  holder.txtrcvdocument.setText(receiveitems.getDocument());
        holder.txtrcvaddcmt.setText(receiveitems.getAddcommnt());
        holder.txtrcvdoneby.setText(receiveitems.getDoneby());


        return convertView;
    }

    public ArrayList<Receiveitems> getFilteredList() {
        return filteredList;
    }

    static class ViewHolder {
        TextView datetime;
        TextView txtrcvprname;
        TextView txtrcvexcise;
       // TextView txtrcvcomnt;
        TextView txtrcvstamp;
        TextView txtrcvlocation;
    //    TextView txtrcvdocument;
        TextView txtrcvaddcmt;
        TextView txtrcvdoneby;

    }
}
