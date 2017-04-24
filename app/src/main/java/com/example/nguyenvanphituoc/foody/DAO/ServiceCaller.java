package com.example.nguyenvanphituoc.foody.DAO;

import android.app.ProgressDialog;

import com.example.nguyenvanphituoc.foody.Interface.GetDataFromChildFragment;
import com.example.nguyenvanphituoc.foody.Interface.GetDataFromService;

import java.io.Serializable;

/**
 * Created by PhiTuocPC on 4/5/2017.
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

    //    public static void Caller(ServiceIF service, Object sender) {
//        if (service != null) ServiceCaller.service = service;
//        ServiceCaller.sender = sender;
//        if (service != null)
//            ServiceCaller.mCallback = service;
//    }

    public void run() {
        try {
            Object result = service.DoOperation(sender);
            mCallback.receiveData(result);
        } catch (Exception ex) {
            mCallback.receiveData(ex.toString());
        }
//
//        EditText ed1=(EditText)findViewById(R.id.editText1);
//        EditText ed2=(EditText)findViewById(R.id.editText2);
//        int a=Integer.parseInt(ed1.getText().toString());
//        int b=Integer.parseInt(ed2.getText().toString());
//        rslt="START";
//        ServiceCaller c=new ServiceCaller(); c.a=a;
//        c.b=b;
//        c.join(); c.start();
//        while(rslt.equals("START")) {
//            try {
//                Thread.sleep(10);
//            }catch(Exception ex) {
//                ex.printStackTrace();
//            }
//        }
//        ad.setTitle("RESULT OF ADD of "+a+" and "+b);
//        ad.setMessage(rslt);

    }
}
