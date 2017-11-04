package com.alexandergor.uvapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
        ListView participantsList = (ListView) findViewById(R.id.missionParticipants);


        Log.i("Mission", item.participants.toString());

        modelMissionParticipantListAdatper participantListAdatper = new modelMissionParticipantListAdatper(item.participants);
        participantsList.setAdapter(participantListAdatper);

        missionTitle.setText(item.title);
        missionDescription.setText(item.description);
    }
}
