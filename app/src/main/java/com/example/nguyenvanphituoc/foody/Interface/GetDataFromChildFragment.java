package com.example.nguyenvanphituoc.foody.Interface;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.nguyenvanphituoc.foody.Model.CityModel;
import com.example.nguyenvanphituoc.foody.Model.DistrictModel;
import com.example.nguyenvanphituoc.foody.Model.WardModel;

/**
 * Created by PhiTuocPC on 4/2/2017.
 */

public interface GetDataFromChildFragment {
    void getTabName(String name);
    void getAddress(final int city,@Nullable final String district,@Nullable final String ward);

}
