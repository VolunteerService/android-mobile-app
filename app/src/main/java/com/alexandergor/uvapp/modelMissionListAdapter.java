package com.alexandergor.uvapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;


class modelMissionListAdapter extends RealmBaseAdapter<modelMission> {
    private static class ViewHolder {
        TextView title;
        TextView teaser;
        TextView additional;
    }

    public modelMissionListAdapter(RealmResults<modelMission> missions) {
        super(missions);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(null == convertView) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.mission_list_item, parent, false
            );

            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.missionListItemTitle);
            viewHolder.teaser = (TextView) convertView.findViewById(R.id.missionListItemTeaser);
            viewHolder.additional = (TextView) convertView.findViewById(R.id.missionListItemAdditional);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(null != adapterData) {
            modelMission item = adapterData.get(position);
            viewHolder.title.setText(item.title);
            viewHolder.teaser.setText(item.teaser);
            viewHolder.additional.setText(
                    item.getDate() + ", " + item.city
            );
        }

        return convertView;
    }
}
