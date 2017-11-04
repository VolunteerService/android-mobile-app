package com.alexandergor.uvapp;

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

        if (oldVersion == 0) {
            schema.create("modelParticipant")
                    .addField("name", String.class)
                    .addField("fb_id", String.class, FieldAttribute.PRIMARY_KEY);

            schema.get("modelMission")
                    .addRealmListField("participants", schema.get("modelParticipant"));

            oldVersion++;
        }

        if (oldVersion == 1) {
            schema.get("modelParticipant")
                    .removePrimaryKey()
                    .addField("id", String.class)
                    .transform(new RealmObjectSchema.Function() {
                        @Override
                        public void apply(DynamicRealmObject obj) {
                            obj.setString("id", UUID.randomUUID().toString());
                        }
                    })
                    .addPrimaryKey("id")
                    .addField("status", String.class)
                    .addField("comment", String.class);

        }
    }
}
