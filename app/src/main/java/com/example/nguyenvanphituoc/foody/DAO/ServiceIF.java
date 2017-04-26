package com.example.nguyenvanphituoc.foody.DAO;

import com.example.nguyenvanphituoc.foody.Interface.GetDataFromService;

import org.ksoap2.serialization.PropertyInfo;

import java.util.Dictionary;

/**
 * Created by PhiTuocPC on 4/21/2017.
 * nguyễn văn phi tước
 */

public  interface ServiceIF extends GetDataFromService {
    void SwitchOperation(String op);
    Object DoOperation(Object sender) ;
    Enum hasOperation(String op);
}
