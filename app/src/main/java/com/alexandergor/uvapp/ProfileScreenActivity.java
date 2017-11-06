package com.alexandergor.uvapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.google.firebase.iid.FirebaseInstanceId;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileScreenActivity extends BaseBottomNavigationActivity {

    private Profile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_screen);

        final UVSApp app = (UVSApp) this.getApplicationContext();

        String authToken = AccessToken.getCurrentAccessToken().getToken();
        UVSApiClient api = UVSApp.getUvsApiClient(authToken);

        Call<modelProfile> request = api.getProfile();

        request.enqueue(new Callback<modelProfile>() {
            @Override
            public void onResponse(Call<modelProfile> call, Response<modelProfile> response) {
                Log.i("API", response.message());

                Log.i("API", response.toString());

                app.UserProfile = response.body();
                TextView coinsView = (TextView) findViewById(R.id.profileCoins);
                coinsView.setText(String.valueOf(app.UserProfile.vCoin));
            }

            @Override
            public void onFailure(Call<modelProfile> call, Throwable t) {
                Log.i("API", "Failed");
            }
        });

        String fcm_id = FirebaseInstanceId.getInstance().getToken();
        Call<ResponseBody> reqFCMRegister = api.registerFCM(fcm_id);
        reqFCMRegister.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.i("API", "FCM id registered");
                Log.i("API", response.toString());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("API", "FCM registration failed");
            }
        });

        profile = Profile.getCurrentProfile();
        TextView profileUserNameView = (TextView)findViewById(R.id.profileUserName);
        profileUserNameView.setText(profile.getName());

        ImageView profileUserPictureView = (ImageView)findViewById(R.id.profileUserPicture);
        Picasso.with(this)
                .load(profile.getProfilePictureUri(200, 200))
                .transform(new CircleTransform())
                .into(profileUserPictureView);

        Button logout = (Button)findViewById(R.id.profileLogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logOut();
                Intent loginScreen = new Intent(ProfileScreenActivity.this, LoginScreenActivity.class);
                startActivity(loginScreen);
                finish();
            }
        });

        initNavigation(R.id.action_profile);
    }
}
