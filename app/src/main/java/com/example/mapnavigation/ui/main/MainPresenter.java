package com.example.mapnavigation.ui.main;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.test.espresso.core.deps.guava.base.Strings;

import static dagger.internal.Preconditions.checkNotNull;

/**
 * Created by zzg on 17-4-5.
 */

public class MainPresenter implements MainContract.Presenter {

    private String mTaskId;
    private MainContract.View mMainView;


    public MainPresenter(@Nullable String taskId,
                               @NonNull MainContract.View mainView) {
        mTaskId = taskId;
        mMainView = checkNotNull(mainView, "mainView cannot be null!");

        mMainView.setPresenter(this);
    }


    @Override
    public void start() {
        openTask();
    }

    private void openTask() {
        if (Strings.isNullOrEmpty(mTaskId)) {
            //mMainView.showMissingTask();
            return;
        }


    }
}
