package com.alexandergor.uvapp;

import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by ornot on 11/4/17.
 */

public class modelMissionParticipantListAdatper extends RealmBaseAdapter<modelParticipant> {
    private static class ViewHolder {
        ImageView userpic;
        TextView name;
    }

    public modelMissionParticipantListAdatper(RealmList<modelParticipant> participants) {
        super(participants);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(null == convertView) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.mission_participant_list_item, parent, false
            );

            viewHolder = new modelMissionParticipantListAdatper.ViewHolder();
            viewHolder.userpic = (ImageView) convertView.findViewById(R.id.participantUserPic);
            viewHolder.name = (TextView) convertView.findViewById(R.id.participantName);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (modelMissionParticipantListAdatper.ViewHolder) convertView.getTag();
        }

        if(null != adapterData) {
            modelParticipant item = adapterData.get(position);
            Picasso.with(viewHolder.userpic.getContext())
                    .load("https://graph.facebook.com/"+item.fb_id+"/picture?type=small")
                    .transform(new CircleTransform())
                    .into(viewHolder.userpic);

            viewHolder.name.setText(item.name);

            Log.i("Participant", item.name);
        }

        return convertView;
    }
}
