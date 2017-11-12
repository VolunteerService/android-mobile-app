package com.alexandergor.uvapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by ornot on 11/12/17.
 */

public class ProductInsufficientFunds extends Fragment {
    private static final String PRICE = "price";

    private int price;

    public static ProductInsufficientFunds newInstance(int price) {
        ProductInsufficientFunds fragment = new ProductInsufficientFunds();
        Bundle args = new Bundle();
        args.putInt(PRICE, price);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            price = getArguments().getInt(PRICE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_insufficient, container, false);

        TextView productPrice = (TextView) view.findViewById(R.id.productPrice);
        productPrice.setText(String.valueOf(price));

        return view;
    }
}
