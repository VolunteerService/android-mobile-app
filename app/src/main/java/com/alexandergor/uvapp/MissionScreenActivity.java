package com.alexandergor.uvapp;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import javax.annotation.Nullable;

import io.realm.ObjectChangeSet;
import io.realm.Realm;
import io.realm.RealmObjectChangeListener;

public class MissionScreenActivity extends AppCompatActivity {

    private Realm realm;
    private UVSApp app;
    private modelMission mission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        app = (UVSApp) this.getApplicationContext();
        realm = Realm.getDefaultInstance();

        Log.i("MissionScreen/onCreate", "app.UserProfile._id: " + app.UserProfile._id);

        setContentView(R.layout.activity_mission_screen);


        String mission_id = getIntent().getStringExtra("mission");
        mission = realm.where(modelMission.class).equalTo("_id", mission_id).findFirst();

        TextView missionTitle = (TextView) findViewById(R.id.missionTitle);
        TextView missionDescription = (TextView) findViewById(R.id.missionDescription);
        TextView missionDateTime = (TextView) findViewById(R.id.missionDateTime);
        TextView missionCity = (TextView) findViewById(R.id.missionCity);

        Log.i("MissionScreen/onCreate", "mission.participants: " + mission.participants.toString());

        modelMissionParticipantListAdatper participantListAdatper = new modelMissionParticipantListAdatper(mission.participants);

        // TODO: 11/4/17  find better solution for listview inside scrollable
        LinearLayout participantsList = (LinearLayout) findViewById(R.id.missionParticipants);
        final int pacticipantsCount = participantListAdatper.getCount();

        modelParticipant currentUserParticipant = null;

        for (int i = 0; i < pacticipantsCount; i++) {
            modelParticipant participant = participantListAdatper.getItem(i);
            Log.i("MissionScreen/onCreate", String.format("mission.participants[%d].id: %s", i, participant.id));
            Log.i("MissionScreen/onCreate", String.format("mission.participants[%d].user_id: %s", i, participant.user_id));

            if(participant.user_id.equals(app.UserProfile._id)) {
                currentUserParticipant = participant;
            }

            if(participant.status.equals("APPROVED")) {
                participantsList.addView(
                        participantListAdatper.getView(i, null, participantsList)
                );
            }
        }

        if(pacticipantsCount > 0) {
            TextView participantsLabel = (TextView) findViewById(R.id.participantsLabel);
            participantsLabel.setText(
                    String.format(
                            "Задіяно волонтерів: %d з можливих %d",
                            pacticipantsCount,
                            mission.max_participants
                    )
            );
        }

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        if (currentUserParticipant == null || currentUserParticipant.status.equals("REFUSED")) {
            fragmentTransaction.add(
                    R.id.missionActionsHolder, MissionApplyButton.newInstance(mission._id)
            );
        } else {
            Log.i("MissionScreen/onCreate", "currentUserParticipant: " + currentUserParticipant.toString());
            switch (currentUserParticipant.status) {
                case "APPROVED":
                    fragmentTransaction.add(
                            R.id.missionActionsHolder,
                            MissionApprovedBlock.newInstance(mission.telegram_chat)
                    );
                    break;
                case "DECLINED":
                    fragmentTransaction.add(
                            R.id.missionActionsHolder,
                            MissionDeclinedBlock.newInstance(currentUserParticipant.comment)
                    );
                    break;
                case "NEW":
                    fragmentTransaction.add(
                            R.id.missionActionsHolder,
                            MissionRefuseButton.newInstance(mission._id)
                    );
                    break;
            }
        }
        fragmentTransaction.commit();

        missionDateTime.setText(mission.getDate());
        missionCity.setText(mission.city);
        missionTitle.setText(mission.title);
        missionDescription.setText(mission.description);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
