package com.alexandergor.uvapp;

import android.os.Bundle;

public class TrophyScreenActivity extends BaseBottomNavigationActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trophy_screen);

        initNavigation(R.id.action_trophy);
    }
}
