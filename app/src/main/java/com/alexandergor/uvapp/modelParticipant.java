package com.alexandergor.uvapp;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class modelParticipant extends RealmObject {
    @PrimaryKey
    String fb_id;

    String name;

}
