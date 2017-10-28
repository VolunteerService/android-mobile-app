package com.alexandergor.uvapp;

import com.google.firebase.iid.FirebaseInstanceIdService;

public class UVSFirebaseInstanceIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        // TODO: 10/28/17  register FCM on token change
    }
}
