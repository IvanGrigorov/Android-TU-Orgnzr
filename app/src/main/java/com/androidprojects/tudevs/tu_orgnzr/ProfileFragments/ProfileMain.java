package com.androidprojects.tudevs.tu_orgnzr.ProfileFragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidprojects.tudevs.tu_orgnzr.R;

public class ProfileMain extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_view_main, container, false);
        return view;
    }
}
