package com.alexandergor.uvapp;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.facebook.AccessToken;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MissionRefuseButton extends Fragment {
    private static final String MISSION_ID = "mission_id";

    private String mission_id;
    private Realm realm;

    public static MissionRefuseButton newInstance(String mission_id) {
        MissionRefuseButton fragment = new MissionRefuseButton();
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
        View view = inflater.inflate(R.layout.fragment_mission_refuse_button, container, false);
        Button refuseButton = (Button) view.findViewById(R.id.missionRefuseButton);
        refuseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String authToken = AccessToken.getCurrentAccessToken().getToken();
                UVSApiClient api = UVSApp.getUvsApiClient(authToken);

                Call<modelMission> reqRefuse = api.refuseMission(mission_id, "");
                reqRefuse.enqueue(new Callback<modelMission>() {
                    @Override
                    public void onResponse(Call<modelMission> call, Response<modelMission> response) {
                        if(response.isSuccessful()) {
                            modelMission mission = response.body();
                            realm.beginTransaction();
                            realm.insertOrUpdate(mission);
                            realm.commitTransaction();

                            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

                            fragmentTransaction.replace(
                                    R.id.missionActionsHolder,
                                    MissionApplyButton.newInstance(mission_id)
                            );
                            fragmentTransaction.commit();
                        }
                    }

                    @Override
                    public void onFailure(Call<modelMission> call, Throwable t) {
                        Log.i("MissionAction", "Refuse response Failure");
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
