package com.alexandergor.uvapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.Profile;
import com.squareup.picasso.Picasso;


public class ProfileScreenActivity extends AppCompatActivity {

    private Profile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_screen);

        profile = Profile.getCurrentProfile();
        TextView profileUserNameView = (TextView)findViewById(R.id.profileUserName);
        profileUserNameView.setText(profile.getName());

        ImageView profileUserPictureView = (ImageView)findViewById(R.id.profileUserPicture);
        Picasso.with(this)
                .load(profile.getProfilePictureUri(200, 200))
                .transform(new CircleTransform())
                .into(profileUserPictureView);
    }
}
