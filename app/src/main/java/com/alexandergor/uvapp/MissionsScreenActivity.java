package com.alexandergor.uvapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.facebook.AccessToken;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MissionsScreenActivity extends BaseBottomNavigationActivity {

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missions_screen);

        realm = Realm.getDefaultInstance();

        String authToken = AccessToken.getCurrentAccessToken().getToken();
        UVSApiClient api = UVSApp.getUvsApiClient(authToken);

        Call<List<modelMission>> reqMissions = api.listMissions();
        reqMissions.enqueue(new Callback<List<modelMission>>() {
            @Override
            public void onResponse(Call<List<modelMission>> call, Response<List<modelMission>> response) {
                Log.i("API", "Missions list retrieved");
                Log.i("API", response.toString());

                List<modelMission> missions = response.body();

                realm.beginTransaction();
                realm.copyToRealmOrUpdate(missions);
                realm.commitTransaction();

                Log.i("Realm", "Saved to realm");
            }

            @Override
            public void onFailure(Call<List<modelMission>> call, Throwable t) {
                Log.e("API", "Getting missions list failed");
            }
        });

        ListView listView = (ListView) findViewById(R.id.allMissionsList);

        RealmResults<modelMission> missions = realm.where(modelMission.class).findAllAsync();
        modelMissionListAdapter missionListAdapter = new modelMissionListAdapter(missions);
        listView.setAdapter(missionListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent missionActivity = new Intent(
                        MissionsScreenActivity.this, MissionScreenActivity.class
                );

                modelMission mission = (modelMission) parent.getItemAtPosition(position);
                Log.i("Mission", mission.toString());

                missionActivity.putExtra("mission", mission._id);
                startActivity(missionActivity);
            }
        });

        initNavigation(R.id.action_mission);
    }

    @Override
    public void onBackPressed() {
        Intent profileActivity = new Intent(
                MissionsScreenActivity.this,
                ProfileScreenActivity.class
        );
        startActivity(profileActivity);
    }
}
