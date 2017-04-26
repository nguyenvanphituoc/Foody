package com.example.nguyenvanphituoc.foody.DAO;

import com.example.nguyenvanphituoc.foody.Activity.UIUtils;
import com.example.nguyenvanphituoc.foody.Model.CategoriesModel;
import com.example.nguyenvanphituoc.foody.Model.DisplayModel;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PhiTuocPC on 4/24/2017.
 * nguyễn văn phi tước
 */

public class OtherServiceImpl extends ServiceAbs {

    private OPERATION op;

    public enum OPERATION {
        GetAllCategories,
        GetAllServices,
        GetAllStreetsByCity,
    }

    private int currentPage = 0;

    public OtherServiceImpl() {

        currentPage = 0;
    }

    public OtherServiceImpl(String op) {

        SwitchOperation(op);
        currentPage = 0;
    }

    @Override
    public void SwitchOperation(String op) {
        OPERATION dump = (OPERATION) hasOperation(op);
        if (dump != null) {
            SOAP_ADDRESS = "http://10.0.2.2:57700/Service/OtherService.asmx";
            SOAP_ACTION = DUMP_SOAP_ACTION + dump.toString();
            OPERATION_NAME = dump.toString();
            this.op = dump;
        }
    }

    @Override
    public Object DoOperation(Object sender) {
        switch (op) {
            case GetAllCategories:
                return GetAllCategories();
            case GetAllServices:
                return GetAllServices();
            case GetAllStreetsByCity:
                return GetAllStreetsByCity((int) sender);
            default:
                return null;
        }
    }

    @Override
    public Enum hasOperation(String op) {
        for (OtherServiceImpl.OPERATION operation : OtherServiceImpl.OPERATION.values()) {
            if (operation.toString().toUpperCase().equals(op.toUpperCase())) {
                return operation;
            }
        }
        return null;
    }

    private Object GetAllStreetsByCity(int id) {

        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS, 3 * 60 * 1000);

        PropertyInfo pi = new PropertyInfo();
        pi.setName("city_id");
        pi.setValue(id);
        pi.setType(Integer.class);
        request.addProperty(pi);

        Object response;
        try {
            httpTransport.call(SOAP_ACTION, envelope);
            response = envelope.getResponse();
        } catch (Exception exception) {
            response = exception.toString();
        }
        return response;
    }

    private Object GetAllServices() {

        return GetAllCategories();
    }

    private Object GetAllCategories() {

        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);

        Object response;
        try {
            httpTransport.call(SOAP_ACTION, envelope);
            response = envelope.getResponse();
        } catch (Exception exception) {
            response = exception.toString();
        }
        return response;
    }

    @Override
    public void receiveData(Object data) {
        if (data != null) {
            listData = initialData(data);
            this.dataMode = true;
        }
    }

    @Override
    public void acceptACKInitial(ServiceIF model, Object sender) {
        ServiceCaller sv = new ServiceCaller(model, sender);
        this.dataMode = false;
        try {
            sv.join();
            sv.start();
            while (!this.dataMode) {
                try {
                    Thread.sleep(10);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private List<?> initialData(Object data) {

        switch (op) {
            case GetAllCategories:
                return this.GetAllCategories((SoapObject) data);
            case GetAllServices:
                return this.GetAllCategories((SoapObject) data);
            case GetAllStreetsByCity:
                return this.GetAllStreetsByCity((SoapObject) data);
            default:
                return null;
        }
    }

    private  List<CategoriesModel> GetAllStreetsByCity(SoapObject data) {

        List<CategoriesModel> list = new ArrayList<>();
        try {
            for (int i = 0; i < data.getPropertyCount(); i++) {

                Object property = data.getProperty(i);
                if (property instanceof SoapObject) {

                    SoapObject placeObj = (SoapObject) property;
                    CategoriesModel model = new CategoriesModel();

                    for (int j = 0; j < placeObj.getPropertyCount(); j++) {

                        //Inside your for loop
                        PropertyInfo itemData = new PropertyInfo();
                        placeObj.getPropertyInfo(j, itemData);


                        if (itemData.getName().equals("image")) {

                            String base64 = itemData.getValue().toString();
                            byte[] bytes = UIUtils.getByteFromBase64(base64);
                            model.setProperty(itemData.getName(), bytes);
                        } else
                            model.setProperty(itemData.getName(), itemData.getValue().toString());
                    }
                    list.add(model);
                    Thread.sleep(50);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    private  List<CategoriesModel> GetAllCategories(SoapObject data) {

        List<CategoriesModel> list = new ArrayList<>();
        try {
            for (int i = 0; i < data.getPropertyCount(); i++) {

                Object property = data.getProperty(i);
                if (property instanceof SoapObject) {

                    SoapObject placeObj = (SoapObject) property;
                    CategoriesModel model = new CategoriesModel();

                    for (int j = 0; j < placeObj.getPropertyCount(); j++) {

                        //Inside your for loop
                        PropertyInfo itemData = new PropertyInfo();
                        placeObj.getPropertyInfo(j, itemData);


                        if (itemData.getName().equals("image")) {

                            String base64 = itemData.getValue().toString();
                            byte[] bytes = UIUtils.getByteFromBase64(base64);
                            model.setProperty(itemData.getName(), bytes);
                        } else
                            model.setProperty(itemData.getName(), itemData.getValue().toString());
                    }
                    list.add(model);
                    Thread.sleep(50);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

}
