package com.alexandergor.uvapp;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by orn0t on 11/9/17.
 */

public class modelProduct extends RealmObject {

    @PrimaryKey
    String _id;

    Date created;
    Date updated;

    String name;
    String photo;
    String description;
    String giver;

    int price;
    double amount;
    String type;
    boolean active;
}
