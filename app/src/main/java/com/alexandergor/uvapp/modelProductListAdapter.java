package com.alexandergor.uvapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

/**
 * Created by orn0t on 11/9/17.
 */

public class modelProductListAdapter extends RealmBaseAdapter<modelProduct> {

    private static class ViewHolder {
        TextView title;
        TextView price;
        ImageView photo;
        TextView giver;
    }

    public modelProductListAdapter(RealmResults<modelProduct> products) { super(products); }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        modelProductListAdapter.ViewHolder viewHolder;
        if(null == convertView) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.product_list_item, parent, false
            );

            viewHolder = new modelProductListAdapter.ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.productListItemTitle);
            viewHolder.price = (TextView) convertView.findViewById(R.id.productListItemPrice);
            viewHolder.giver = (TextView) convertView.findViewById(R.id.productListItemGiver);
            viewHolder.photo = (ImageView) convertView.findViewById(R.id.productListItemPhoto);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (modelProductListAdapter.ViewHolder) convertView.getTag();
        }

        if(null != adapterData) {
            modelProduct item = adapterData.get(position);
            viewHolder.title.setText(item.name);
            viewHolder.price.setText(String.valueOf(item.price));
            viewHolder.giver.setText(item.giver);

            Picasso.with(parent.getContext())
                    .load(item.photo)
                    .into(viewHolder.photo);
        }

        return convertView;
    }
}
