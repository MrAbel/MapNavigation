package com.example.mapnavigation.ui.main;

import com.example.mapnavigation.base.BasePresenter;
import com.example.mapnavigation.base.BaseView;


/**
 * Created by zzg on 17-4-3.
 */

public interface MainContract {

    public interface View extends BaseView<Presenter>{

        public void showCurLocation();

    }

    public interface Presenter extends BasePresenter{

    }
}
