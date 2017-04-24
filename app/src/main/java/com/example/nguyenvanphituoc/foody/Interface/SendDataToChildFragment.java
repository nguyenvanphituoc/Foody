package com.example.nguyenvanphituoc.foody.Interface;

import android.os.Bundle;

/**
 * Created by PhiTuocPC on 4/4/2017.
 */

public interface SendDataToChildFragment  {
    void sendBundleToChild(Bundle savedInstanceState);
    void sendACKInitialData();
    boolean getWaitingACK();
}
