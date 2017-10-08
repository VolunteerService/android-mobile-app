package com.alexandergor.uvapp;

import android.os.Bundle;

public class MissionsScreenActivity extends BaseBottomNavigationActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missions_screen);

        initNavigation(R.id.action_mission);
    }
}
