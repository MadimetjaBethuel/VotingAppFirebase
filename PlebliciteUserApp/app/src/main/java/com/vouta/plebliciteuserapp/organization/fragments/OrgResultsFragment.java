package com.vouta.plebliciteuserapp.organization.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.vouta.plebliciteuserapp.R;

public class OrgResultsFragment extends Fragment  {

    View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.org_results_fragment,container,false);
        return v;
    }
}