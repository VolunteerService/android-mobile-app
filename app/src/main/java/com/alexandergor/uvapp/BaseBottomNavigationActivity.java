package com.alexandergor.uvapp;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

public class BaseBottomNavigationActivity  extends AppCompatActivity {

    protected void initNavigation(@IdRes int current) {

        BottomNavigationView bottomNavigation = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        bottomNavigation.setSelectedItemId(current);

        bottomNavigation.setOnNavigationItemSelectedListener(
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Log.i("Navigation", "Button clicked");

                    switch (item.getItemId()) {
                        case R.id.action_profile:
                            Log.i("Navigation", "Profile selected");
                            Intent profileActivity = new Intent(
                                    BaseBottomNavigationActivity.this,
                                    ProfileScreenActivity.class
                            );
                            startActivity(profileActivity);
                            return true;

                        case R.id.action_mission:
                            Log.i("Navigation", "Mission selected");
                            Intent missionsActivity = new Intent(
                                    BaseBottomNavigationActivity.this,
                                    MissionsScreenActivity.class
                            );
                            startActivity(missionsActivity);
                            return true;

                        case R.id.action_trophy:
                            Log.i("Navigation", "Trophy selected");
                            Intent trophyActivity = new Intent(
                                    BaseBottomNavigationActivity.this,
                                    TrophyScreenActivity.class
                            );
                            startActivity(trophyActivity);
                            return true;

                    }

                    return false;
                }
            }
        );
    }
}
