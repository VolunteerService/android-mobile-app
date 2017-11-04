package com.alexandergor.uvapp;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
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
        TextView missionDateTime = (TextView) findViewById(R.id.missionDateTime);
        TextView missionCity = (TextView) findViewById(R.id.missionCity);

        Log.i("Mission", item.participants.toString());

        Fragment acctionApply = new MissionApplyButton();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.missionActionsHolder, acctionApply);
        fragmentTransaction.commit();

        modelMissionParticipantListAdatper participantListAdatper = new modelMissionParticipantListAdatper(item.participants);

        // TODO: 11/4/17  find better solution for listview inside scrollable
        LinearLayout participantsList = (LinearLayout) findViewById(R.id.missionParticipants);
        final int pacticipantsCount = participantListAdatper.getCount();

        if(pacticipantsCount > 0) {
            TextView participantsLabel = (TextView) findViewById(R.id.participantsLabel);
            participantsLabel.setText(
                    String.format(
                            "Задіяно волонтерів: %d з можливих %d",
                            pacticipantsCount,
                            item.max_participants
                    )
            );
        }

        for (int i = 0; i < pacticipantsCount; i++) {
            participantsList.addView(
                    participantListAdatper.getView(i, null, participantsList)
            );
        }

        missionDateTime.setText(item.date_start + " — " + item.date_finish);
        missionCity.setText(item.city);
        missionTitle.setText(item.title);
        missionDescription.setText(item.description);
    }
}
