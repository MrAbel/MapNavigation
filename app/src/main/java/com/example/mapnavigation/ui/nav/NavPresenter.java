package com.example.mapnavigation.ui.nav;

import android.support.annotation.NonNull;

import static dagger.internal.Preconditions.checkNotNull;

/**
 * Created by zzg on 17-4-11.
 */

public class NavPresenter implements NavContract.Presenter {

    private NavContract.View mNavView;

    public NavPresenter(@NonNull NavContract.View navView) {
        mNavView = checkNotNull(navView, "mainView cannot be null!");
        mNavView.setPresenter(this);
    }
    @Override
    public void start() {

    }
}
