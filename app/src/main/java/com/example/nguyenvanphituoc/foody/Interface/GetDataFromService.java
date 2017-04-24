package com.example.nguyenvanphituoc.foody.Interface;

import org.ksoap2.serialization.SoapObject;

import java.io.Serializable;

/**
 * Created by PhiTuocPC on 4/21/2017.
 */

public interface GetDataFromService extends Serializable {
    void receiveData(Object data);
}
