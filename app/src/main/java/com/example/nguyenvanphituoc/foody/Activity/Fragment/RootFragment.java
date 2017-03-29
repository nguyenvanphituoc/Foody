package com.example.nguyenvanphituoc.foody.Activity.Fragment;

import android.support.v4.app.Fragment;

import com.example.nguyenvanphituoc.foody.Activity.Listener.BackPressImpl;
import com.example.nguyenvanphituoc.foody.Activity.Listener.IOnBackPressListener;

/**
 * Created by Admin on 3/27/2017.
 */

public class RootFragment extends Fragment implements IOnBackPressListener {
    @Override
    public boolean onBackPressed() {
        return new BackPressImpl(this).onBackPressed();
    }
}
