package com.alexandergor.uvapp;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MissionDeclinedBlock extends Fragment {
    private static final String COMMENT = "comment";

    private String comment;

    public static MissionDeclinedBlock newInstance(String comment) {
        MissionDeclinedBlock fragment = new MissionDeclinedBlock();
        Bundle args = new Bundle();
        args.putString(COMMENT, comment);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            comment = getArguments().getString(COMMENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mission_declined_block, container, false);
        TextView commentTextView = (TextView) view.findViewById(R.id.missionDeclineComment);
        commentTextView.setText(comment);

        return view;
    }
}
