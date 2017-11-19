package com.alexandergor.uvapp;

import android.app.FragmentTransaction;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import io.realm.Realm;


public class ProductScreenActivity extends AppCompatActivity {

    private Realm realm;
    private UVSApp app;
    private modelProduct product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        app = (UVSApp) this.getApplicationContext();
        realm = Realm.getDefaultInstance();

        setContentView(R.layout.activity_product_screen);

        String product_id = getIntent().getStringExtra("product");
        product = realm.where(modelProduct.class).equalTo("_id", product_id).findFirst();

        TextView vcoinsUserAccount = (TextView) findViewById(R.id.productVCoinsUserAccount);
        vcoinsUserAccount.setText(
            String.format("На вашому рахунку %d балів", app.UserProfile.vCoin)
        );

        TextView productTitle = (TextView) findViewById(R.id.productTitle);
        TextView productDescription = (TextView) findViewById(R.id.productDescription);
        TextView productGiver = (TextView) findViewById(R.id.productGiver);
        ImageView productPhoto = (ImageView) findViewById(R.id.productPhoto);

        productTitle.setText(product.name);
        productDescription.setText(product.description);
        productGiver.setText(product.giver);

        Picasso.with(this)
                .load(product.photo)
                .into(productPhoto);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        if (product.ordered) {
            fragmentTransaction.add(
                    R.id.productActionHolder,
                    new ProductPurchased()
            );
        } else if (app.UserProfile.vCoin < product.price) {
            fragmentTransaction.add(
                R.id.productActionHolder,
                ProductInsufficientFunds.newInstance(product.price)
            );
        } else {
            fragmentTransaction.add(
                R.id.productActionHolder,
                ProductBuyButton.newInstance(product.price, product._id)
            );
        }

        fragmentTransaction.commit();
    }

}
