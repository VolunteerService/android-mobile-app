package com.alexandergor.uvapp;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.facebook.AccessToken;
import com.google.gson.Gson;

import io.realm.Realm;
import io.realm.RealmList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MissionApplyButton extends Fragment {
    private static final String MISSION_ID = "mission_id";

    private String mission_id;
    private Realm realm;

    public static MissionApplyButton newInstance(String mission_id) {
        MissionApplyButton fragment = new MissionApplyButton();
        Bundle args = new Bundle();
        args.putString(MISSION_ID, mission_id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mission_id = getArguments().getString(MISSION_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        realm = Realm.getDefaultInstance();
        View view = inflater.inflate(R.layout.fragment_mission_apply_button, container, false);
        Button applyButton = (Button) view.findViewById(R.id.missionApplyButton);
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                String authToken = AccessToken.getCurrentAccessToken().getToken();
                UVSApiClient api = UVSApp.getUvsApiClient(authToken);

                Call<modelMission> reqAppty = api.applyMission(mission_id);
                reqAppty.enqueue(new Callback<modelMission>() {
                    @Override
                    public void onResponse(Call<modelMission> call, Response<modelMission> response) {
                        if(response.isSuccessful()) {
                            modelMission mission = response.body();

                            Log.i("ApplyResponse", new Gson().toJson(response));

                            realm.beginTransaction();
                            realm.insertOrUpdate(mission);
                            realm.commitTransaction();
                        }
                    }

                    @Override
                    public void onFailure(Call<modelMission> call, Throwable t) {
                        Log.i("MissionAction", "Apply response Failure");
                        Log.i("MissionAction", t.toString());
                    }
                });
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        realm.close();
    }
}
