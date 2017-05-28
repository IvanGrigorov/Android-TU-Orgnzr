package com.androidprojects.tudevs.tu_orgnzr.Presenters;

import android.app.Activity;

public interface IPresenter {
    void attach(Activity activity);

    void detach();
}