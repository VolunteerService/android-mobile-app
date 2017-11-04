package com.alexandergor.uvapp;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import io.realm.Realm;

public class MissionScreenActivity extends AppCompatActivity {

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_screen);

        realm = Realm.getDefaultInstance();

        String mission_id = getIntent().getStringExtra("mission");
        modelMission item = realm.where(modelMission.class).equalTo("_id", mission_id).findFirst();

        TextView missionTitle = (TextView) findViewById(R.id.missionTitle);
        TextView missionDescription = (TextView) findViewById(R.id.missionDescription);

        Log.i("Mission", item.participants.toString());

        Fragment acctionApply = new MissionApplyButton();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.missionActionsHolder, acctionApply);
        fragmentTransaction.commit();

        modelMissionParticipantListAdatper participantListAdatper = new modelMissionParticipantListAdatper(item.participants);

        // TODO: 11/4/17  find better solution for listview inside scrollable
        LinearLayout participantsList = (LinearLayout) findViewById(R.id.missionParticipants);
        for (int i = 0; i < participantListAdatper.getCount(); i++) {
            participantsList.addView(
                    participantListAdatper.getView(i, null, participantsList)
            );
        }

        missionTitle.setText(item.title);
        missionDescription.setText(item.description);
    }
}
