package com.alexandergor.uvapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.facebook.AccessToken;

import java.util.List;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrophyScreenActivity extends BaseBottomNavigationActivity {

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trophy_screen);

        realm = Realm.getDefaultInstance();

        String authToken = AccessToken.getCurrentAccessToken().getToken();
        UVSApiClient api = UVSApp.getUvsApiClient(authToken);
        Call<List<modelProduct>> reqProducts = api.listProducts();
        reqProducts.enqueue(new Callback<List<modelProduct>>() {
            @Override
            public void onResponse(Call<List<modelProduct>> call, Response<List<modelProduct>> response) {
                List<modelProduct> products = response.body();

                realm.beginTransaction();
                realm.copyToRealmOrUpdate(products);
                realm.commitTransaction();
            }

            @Override
            public void onFailure(Call<List<modelProduct>> call, Throwable t) {

            }
        });

        ListView listView = (ListView) findViewById(R.id.allProductsList);
        RealmResults<modelProduct> products = realm.where(modelProduct.class).findAllAsync();
        modelProductListAdapter productListAdapter = new modelProductListAdapter(products);

        listView.setAdapter(productListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent productActivity = new Intent(
                        TrophyScreenActivity.this, ProductScreenActivity.class
                );

                modelProduct product = (modelProduct) parent.getItemAtPosition(position);
                productActivity.putExtra("product", product._id);
                startActivity(productActivity);
            }
        });

        initNavigation(R.id.action_trophy);
    }

    @Override
    public void onBackPressed() {
        Intent profileActivity = new Intent(
                TrophyScreenActivity.this,
                ProfileScreenActivity.class
        );
        startActivity(profileActivity);
    }
}
