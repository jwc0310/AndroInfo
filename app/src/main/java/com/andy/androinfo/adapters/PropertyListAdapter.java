package com.andy.androinfo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Property;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andy.androinfo.R;
import com.andy.androinfo.beans.Properys;

import java.util.List;

public class PropertyListAdapter extends RecyclerView.Adapter<PropertyListAdapter.PropertyListVH>{

    List<Properys> list;
    Context context;

    public PropertyListAdapter(Context context, List<Properys> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public PropertyListVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        PropertyListVH propertyListVH = new PropertyListVH(view);
        return propertyListVH;
    }

    @Override
    public void onBindViewHolder(PropertyListVH holder, int position) {
        holder.tv_key.setText(list.get(position).getKey());
        holder.tv_value.setText(list.get(position).getValue());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static final class PropertyListVH extends RecyclerView.ViewHolder {

        private TextView tv_key;
        private TextView tv_value;
        public PropertyListVH(View itemView) {
            super(itemView);
            tv_key = (TextView) itemView.findViewById(R.id.andy_key);
            tv_value = (TextView) itemView.findViewById(R.id.andy_value);
        }
    }

}
