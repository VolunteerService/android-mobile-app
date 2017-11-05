package com.alexandergor.uvapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.firebase.iid.FirebaseInstanceId;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class LoginScreenActivity extends AppCompatActivity {

    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;

    private FacebookCallback<LoginResult> fbLoginCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            Log.i("Login", "Success login calback");

            String authToken = AccessToken.getCurrentAccessToken().getToken();
            UVSApiClient api = UVSApp.getUvsApiClient(authToken);

            Call<modelProfile> request = api.getProfile();
            request.enqueue(new Callback<modelProfile>() {
                @Override
                public void onResponse(Call<modelProfile> call, Response<modelProfile> response) {
                    Log.i("API", response.message());

                    Log.i("API", response.toString());

                    UVSApp.UserProfile = response.body();
                    initProfileActivity(UVSApp.UserProfile);
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
        }

        @Override
        public void onCancel() {
            Log.i("Login", "Facebook login was canceled ?");
        }

        @Override
        public void onError(FacebookException error) {
            Log.i("Login", error.toString());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login_screen);


        callbackManager = CallbackManager.Factory.create();
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            }
        };

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                String authToken = AccessToken.getCurrentAccessToken().getToken();
                UVSApiClient api = UVSApp.getUvsApiClient(authToken);
                Call<modelProfile> request = api.getProfile();
                request.enqueue(new Callback<modelProfile>() {
                    @Override
                    public void onResponse(Call<modelProfile> call, Response<modelProfile> response) {
                        Log.i("API", response.message());

                        Log.i("API", response.toString());

                        UVSApp.UserProfile = response.body();
                        initProfileActivity(UVSApp.UserProfile);
                    }

                    @Override
                    public void onFailure(Call<modelProfile> call, Throwable t) {
                        Log.i("API", "Failed");
                    }
                });
            }
        };

        accessTokenTracker.startTracking();
        profileTracker.startTracking();

        LoginManager.getInstance().registerCallback(callbackManager, fbLoginCallback);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if( AccessToken.getCurrentAccessToken() != null) {
            String authToken = AccessToken.getCurrentAccessToken().getToken();
            UVSApiClient api = UVSApp.getUvsApiClient(authToken);
            Call<modelProfile> request = api.getProfile();
            request.enqueue(new Callback<modelProfile>() {
                @Override
                public void onResponse(Call<modelProfile> call, Response<modelProfile> response) {
                    Log.i("API", response.message());

                    Log.i("API", response.toString());

                    UVSApp.UserProfile = response.body();
                    initProfileActivity(UVSApp.UserProfile);
                }

                @Override
                public void onFailure(Call<modelProfile> call, Throwable t) {
                    Log.i("API", "Failed");
                }
            });
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        //Facebook login
        accessTokenTracker.stopTracking();
        profileTracker.stopTracking();
    }

    private  void initProfileActivity(modelProfile profile) {
        Log.i("Login", "init profile activity called");

        if(profile != null) {
            Log.i("Login", "Profile present - so loading profile activity");

            Intent profileActivity = new Intent(LoginScreenActivity.this, ProfileScreenActivity.class);
            startActivity(profileActivity);
        }
    }
}
