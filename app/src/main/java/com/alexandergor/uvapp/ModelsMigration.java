package com.alexandergor.uvapp;

import java.util.Date;
import java.util.UUID;

import io.realm.DynamicRealm;
import io.realm.DynamicRealmObject;
import io.realm.FieldAttribute;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

/**
 * Created by ornot on 11/4/17.
 */

public class ModelsMigration implements RealmMigration {

    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        RealmSchema schema = realm.getSchema();

        if(oldVersion == 0) {
            schema.get("modelMission")
                .addField("telegram_chat", String.class);

            oldVersion++;
        }
    }
}
