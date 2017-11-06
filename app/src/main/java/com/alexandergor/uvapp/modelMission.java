package com.alexandergor.uvapp;

import android.util.Log;

import java.text.SimpleDateFormat;
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
    String telegram_chat;

    Date start;
    Date finish;

    String city;

    double hours;
    int max_participants;

    RealmList<modelParticipant> participants;

    boolean active;

    public String getDate() {
        Log.i("ModelMission", start.toString());
        String from = new SimpleDateFormat("dd.MM H:m").format(start);
        String to = new SimpleDateFormat("dd.MM H:m").format(finish);
        return from + " — " + to;
    }
}