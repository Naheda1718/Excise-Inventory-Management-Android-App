package com.excise.excisemanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class DetailsAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Item> itemList;
    private ArrayList<Item> filteredList;

    public DetailsAdapter(Context context, ArrayList<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
        this.filteredList = new ArrayList<>(itemList);
    }

    public void filterlist(ArrayList<Item> filteredList) {
        itemList=filteredList;

        // Update both itemList and filteredList
        notifyDataSetChanged();
    }

    public ArrayList<Item> getItemList() {
        return itemList;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.row_item_layout, parent, false);
            holder = new ViewHolder();
            holder.datetime = convertView.findViewById(R.id.Datetime);
            holder.reciveby = convertView.findViewById(R.id.Reciveby);
            holder.recievefrm = convertView.findViewById(R.id.recievefrm);
            holder.toteid = convertView.findViewById(R.id.toteid);
            holder.productname = convertView.findViewById(R.id.productname);
            holder.producttype = convertView.findViewById(R.id.producttype);
            holder.lot = convertView.findViewById(R.id.lot);
            holder.weight = convertView.findViewById(R.id.weight);
            holder.qty = convertView.findViewById(R.id.qty);
            holder.location = convertView.findViewById(R.id.Location);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Item item = itemList.get(position);
        holder.datetime.setText(item.getDatetime());
        holder.reciveby.setText(item.getRecvby());
        holder.recievefrm.setText(item.getRcvfrom());
        holder.toteid.setText(item.getToteid());
        holder.productname.setText(item.getProductname());
        holder.producttype.setText(item.getProducttype());
        holder.lot.setText(item.getLot());
        holder.weight.setText(item.getWeight());
        holder.qty.setText(item.getQuantity());
        holder.location.setText(item.getLocation());

        return convertView;
    }

    static class ViewHolder {
        TextView datetime;
        TextView reciveby;
        TextView recievefrm;
        TextView toteid;
        TextView productname;
        TextView producttype;
        TextView lot;
        TextView weight;
        TextView qty;
        TextView location;
    }
}
