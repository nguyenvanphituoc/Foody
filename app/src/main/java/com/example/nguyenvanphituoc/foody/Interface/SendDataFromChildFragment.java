package com.example.nguyenvanphituoc.foody.Interface;

import android.view.View;

import com.example.nguyenvanphituoc.foody.Model.CityModel;
import com.example.nguyenvanphituoc.foody.Model.DistrictModel;
import com.example.nguyenvanphituoc.foody.Model.WardModel;

/**
 * Created by PhiTuocPC on 4/2/2017.
 */

public interface SendDataFromChildFragment {
    void sendTabName(String name);
    void sendAddress(final int city, final String district, final String ward);
}
