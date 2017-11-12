package com.alexandergor.uvapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by ornot on 11/12/17.
 */

public class ProductBuyButton extends Fragment {
    private static final String PRICE = "price";
    private static final String PRODUCT_ID = "product_id";

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
        View view = inflater.inflate(R.layout.fragment_product_buy_button, container, false);

        TextView productPrice = (TextView) view.findViewById(R.id.productPrice);
        productPrice.setText(String.valueOf(price));

        return view;
    }
}
