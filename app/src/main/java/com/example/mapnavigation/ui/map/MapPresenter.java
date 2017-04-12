package com.example.mapnavigation.ui.map;

import android.support.annotation.NonNull;

import static dagger.internal.Preconditions.checkNotNull;

/**
 * Created by zzg on 17-4-5.
 */

public class MapPresenter implements MapContract.Presenter {

    private MapContract.View mMainView;

    public MapPresenter(@NonNull MapContract.View mainView) {
        mMainView = checkNotNull(mainView, "mainView cannot be null!");
        mMainView.setPresenter(this);
    }


    @Override
    public void start() {
    }
}
