package com.example.mapnavigation.ui.nav;

import com.example.mapnavigation.base.BasePresenter;
import com.example.mapnavigation.base.BaseView;

/**
 * Created by zzg on 17-4-11.
 */

public interface NavContract {
    public interface View extends BaseView<NavContract.Presenter> {


    }

    public interface Presenter extends BasePresenter {

    }
}
