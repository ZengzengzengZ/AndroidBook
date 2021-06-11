package com.example.secondprogram;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class RateAdapter  extends ArrayAdapter {

    private static final String TAG ="RateAdapter";
    public RateAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Rate> list) {
        super(context, resource, list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView=convertView;
        if (itemView==null){
            itemView= LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }
        Rate item=(Rate) getItem(position);
        Log.i(TAG, "getView: item="+item);
        Log.i(TAG, "getView: itemView="+itemView);
        TextView title=(TextView) itemView.findViewById(R.id.itemTitle);
        Log.i(TAG, "getView: title="+title);
        TextView detail = (TextView) itemView.findViewById(R.id.itemDetail);
        Log.i(TAG, "getView: detail="+detail);
        title.setText("A:"+item.getCname());
        detail.setText("B:"+item.getCval());
        return itemView;
    }

    }

