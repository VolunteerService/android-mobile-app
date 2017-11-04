package com.alexandergor.uvapp;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class modelMission extends RealmObject {
    @PrimaryKey
    String _id;

    Date created;
    Date updated;

    String title;
    String teaser;
    String description;

    Date date_start;
    Date date_finish;

    String city;

    double hours;
    int max_participants;

    RealmList<modelParticipant> participants;

    boolean active;
}