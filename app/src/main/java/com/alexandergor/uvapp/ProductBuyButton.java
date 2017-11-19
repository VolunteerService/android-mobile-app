package com.alexandergor.uvapp;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.AccessToken;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ornot on 11/12/17.
 */

public class ProductBuyButton extends Fragment {
    private static final String PRICE = "price";
    private static final String PRODUCT_ID = "product_id";

    private Realm realm;
    private int price;
    private String product_id;

    public static ProductBuyButton newInstance(int price, String product_id) {
        ProductBuyButton fragment = new ProductBuyButton();
        Bundle args = new Bundle();
        args.putInt(PRICE, price);
        args.putString(PRODUCT_ID, product_id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            price = getArguments().getInt(PRICE);
            product_id = getArguments().getString(PRODUCT_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        realm = Realm.getDefaultInstance();

        View view = inflater.inflate(R.layout.fragment_product_buy_button, container, false);

        TextView productPrice = (TextView) view.findViewById(R.id.productPrice);
        productPrice.setText(String.valueOf(price));

        Button buttonBuy = (Button) view.findViewById(R.id.productBuyButton);
        buttonBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String authToken = AccessToken.getCurrentAccessToken().getToken();
                UVSApiClient api = UVSApp.getUvsApiClient(authToken);

                Call<modelProduct> reqBuy = api.buyProduct(product_id);
                reqBuy.enqueue(new Callback<modelProduct>() {
                    @Override
                    public void onResponse(Call<modelProduct> call, Response<modelProduct> response) {
                        if(response.isSuccessful()) {
                            modelProduct product = response.body();

                            realm.beginTransaction();
                            realm.insertOrUpdate(product);
                            realm.commitTransaction();

                            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

                            fragmentTransaction.replace(
                                R.id.productActionHolder,
                                new ProductPurchased()
                            );
                            fragmentTransaction.commit();
                        }
                    }

                    @Override
                    public void onFailure(Call<modelProduct> call, Throwable t) {

                    }
                });
            }
        });

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
