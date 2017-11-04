package com.alexandergor.uvapp;

import io.realm.DynamicRealm;
import io.realm.FieldAttribute;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

/**
 * Created by ornot on 11/4/17.
 */

public class ModelsMigration implements RealmMigration {

    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        RealmSchema schema = realm.getSchema();

        if (oldVersion == 0) {
            schema.create("modelParticipant")
                    .addField("name", String.class)
                    .addField("fb_id", String.class, FieldAttribute.PRIMARY_KEY);

            schema.get("modelMission")
                    .addRealmListField("participants", schema.get("modelParticipant"));

            oldVersion++;
        }
    }
}
