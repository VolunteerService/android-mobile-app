package com.alexandergor.uvapp;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class MissionApprovedBlock extends Fragment {

    private static final String TELEGRAM_LINK = "telegramLink";

    private String telegramLink;

    public static MissionApprovedBlock newInstance(String telegramLink) {
        MissionApprovedBlock fragment = new MissionApprovedBlock();
        Bundle args = new Bundle();
        args.putString(TELEGRAM_LINK, telegramLink);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            telegramLink = getArguments().getString(TELEGRAM_LINK);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mission_approved_block, container, false);

        TextView telegramLinkView = (TextView) view.findViewById(R.id.missionTelegramLink);
        telegramLinkView.setText(telegramLink);
        return view;
    }
}
