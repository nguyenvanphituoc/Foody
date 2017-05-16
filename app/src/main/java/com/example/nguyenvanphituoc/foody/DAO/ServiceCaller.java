package com.example.nguyenvanphituoc.foody.DAO;

import android.app.ProgressDialog;

import com.example.nguyenvanphituoc.foody.Activity.MainActivity;
import com.example.nguyenvanphituoc.foody.Interface.GetDataFromChildFragment;
import com.example.nguyenvanphituoc.foody.Interface.GetDataFromService;

import java.io.Serializable;

/**
 * Created by PhiTuocPC on 4/5/2017.
 * nguyễn văn phi tước
 */

public class ServiceCaller extends Thread {
    static ServiceIF service;
    static private GetDataFromService mCallback;
    static private Object sender;
    private ProgressDialog pDialog;

    ServiceCaller(ServiceIF service, Object sender) {
        if (service != null) ServiceCaller.service = service;
        ServiceCaller.sender = sender;
        if (service != null)
            ServiceCaller.mCallback = service;
    }

    public void run() {

        try {
            Object result = service.DoOperation(sender);
            mCallback.receiveData(result);
        } catch (Exception ex) {
            mCallback.receiveData(ex.toString());
        }
    }
}
