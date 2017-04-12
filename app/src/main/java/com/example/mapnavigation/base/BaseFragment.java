package com.example.mapnavigation.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by zzg on 17-4-12.
 */

public class BaseFragment extends Fragment {

    public FragmentManager mFragmentManager;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentManager = (FragmentManager)getActivity().getSupportFragmentManager();
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
