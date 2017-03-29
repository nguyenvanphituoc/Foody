package com.example.nguyenvanphituoc.foody.Activity.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nguyenvanphituoc.foody.R;

/**
 * Created by Admin on 3/26/2017.
 */

public class FoodyNewsDisplayFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.inflate_onbottom_news_display, container, false);
    }
}
