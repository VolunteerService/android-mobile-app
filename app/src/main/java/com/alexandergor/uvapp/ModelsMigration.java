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

        if(oldVersion == 1) {
            schema.create("modelProduct")
                .addField("_id", String.class, FieldAttribute.PRIMARY_KEY)
                .addField("title", String.class)
                .addField("description", String.class)
                .addField("photo", String.class)
                .addField("type", String.class)
                .addField("giver", String.class)
                .addField("price", double.class)
                .addField("amount", double.class)
                .addField("created", Date.class)
                .addField("updated", Date.class)
                .addField("active", boolean.class);

            oldVersion++;
        }

        if(oldVersion == 2) {
            schema.get("modelProduct")
                .addField("name", String.class)
                .removeField("title");

            oldVersion++;
        }

        if(oldVersion == 3) {
            schema.get("modelProduct")
                .removeField("price")
                .addField("price", int.class);

            oldVersion++;
        }

        if(oldVersion == 4) {
            schema.get("modelProduct")
                .addField("ordered", boolean.class);

            oldVersion++;
        }
    }
}
