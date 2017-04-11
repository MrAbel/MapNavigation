package com.example.mapnavigation.ui.main;

import android.support.annotation.NonNull;
import android.support.test.espresso.core.deps.guava.base.Strings;
import static dagger.internal.Preconditions.checkNotNull;

/**
 * Created by zzg on 17-4-5.
 */

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mMainView;

    public MainPresenter(@NonNull MainContract.View mainView) {
        mMainView = checkNotNull(mainView, "mainView cannot be null!");
        mMainView.setPresenter(this);
    }


    @Override
    public void start() {
    }
}
