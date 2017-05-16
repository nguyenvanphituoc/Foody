package com.example.nguyenvanphituoc.foody.DAO;

import android.content.Context;

import com.example.nguyenvanphituoc.foody.Activity.UIUtils;
import com.example.nguyenvanphituoc.foody.DAO.ExtraSupport.StaticSupportResources;
import com.example.nguyenvanphituoc.foody.Model.CategoriesModel;
import com.example.nguyenvanphituoc.foody.Model.DisplayModel;
import com.example.nguyenvanphituoc.foody.Model.WardModel;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by PhiTuocPC on 4/24/2017.
 * nguyễn văn phi tước
 */

public class OtherServiceImpl extends ServiceAbs {

    private OPERATION op;
    private Context mContext;

    public enum OPERATION {
        GetAllCategories,
        GetAllServices,
        GetAllStreetsByCity,
        GetAllStreetByDistrict,
        GetAllDistrictByCity,
        GetAllCitys,
    }

    private int currentPage = 0;

    public OtherServiceImpl(Context context) {

        this.mContext = context;
        currentPage = 0;
    }

    public OtherServiceImpl(String op, Context context) {

        SwitchOperation(op);
        this.mContext = context;
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
            case GetAllStreetByDistrict:
                WardModel wardDistrict = (WardModel) sender;
                return GetAllStreetByDistrict(wardDistrict.getId(), wardDistrict.getDistrict());
            case GetAllDistrictByCity:
                WardModel wardCity = (WardModel) sender;
                return GetAllDistrictByCity(wardCity.getId());
            case GetAllCitys:
                return GetAllCitys();
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

    private Object GetAllStreetByDistrict(int cityId, String districtName) {
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS, 3 * 60 * 1000);

        PropertyInfo piId = new PropertyInfo();
        piId.setName("cityId");
        piId.setValue(cityId);
        piId.setType(Integer.class);
        request.addProperty(piId);

        PropertyInfo piDistrict = new PropertyInfo();
        piDistrict.setName("districtName");
        piDistrict.setValue(districtName);
        piDistrict.setType(String.class);
        request.addProperty(piDistrict);

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

    private Object GetAllCitys() {

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

    private Object GetAllDistrictByCity(int id) {
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS, 3 * 60 * 1000);

        PropertyInfo pi = new PropertyInfo();
        pi.setName("cityId");
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

    @Override
    public void receiveData(Object data) {
        if (data != null) {
            switch (op) {
                case GetAllServices:
                    StaticSupportResources.saveInterFile(StaticSupportResources.FILESERVICES, (SoapObject) data, mContext);
                    break;
                case GetAllStreetsByCity:
                    StaticSupportResources.saveInterFile(StaticSupportResources.FILECITY, (SoapObject) data, mContext);
                    break;
                case GetAllCategories:
                    StaticSupportResources.saveInterFile(StaticSupportResources.FILECATEGORIES, (SoapObject) data, mContext);
                    break;
                case GetAllStreetByDistrict:
                    break;
                case GetAllDistrictByCity:
                    break;
                case GetAllCitys:
                    break;
                default:
                    break;
            }
            listData = initialData(data);
            this.dataMode = true;
        }
    }

    @Override
    public void getData() {
        switch (op) {
            case GetAllServices:
                if (StaticSupportResources.ISLOADEDSERVICES) {
                    Object data = StaticSupportResources.readInterFile(StaticSupportResources.FILESERVICES, mContext);
                    listData = this.GetAllCategories((SoapObject) data);
                    this.dataMode = true;
                }
                break;
            case GetAllStreetsByCity:
                if (StaticSupportResources.ISLOADEDCITY) {
                    Object data = StaticSupportResources.readInterFile(StaticSupportResources.FILECITY, mContext);
                    listData = this.GetAllStreetsByCity((SoapObject) data);
                    this.dataMode = true;
                }
                break;
            case GetAllCategories:
                if (StaticSupportResources.ISLOADEDCATEGORIES) {
                    Object data = StaticSupportResources.readInterFile(StaticSupportResources.FILECATEGORIES, mContext);
                    listData = this.GetAllCategories((SoapObject) data);
                    this.dataMode = true;
                }
                break;
            case GetAllStreetByDistrict:

            case GetAllDistrictByCity:

            case GetAllCitys:

            default:
                break;
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
            case GetAllStreetByDistrict:
                return this.GetAllStreetByDistrict((SoapObject) data);
            case GetAllDistrictByCity:
                return this.GetAllDistrictByCity((SoapObject) data);
            case GetAllCitys:
                return this.GetAllCitys((SoapObject) data);
            default:
                return null;
        }
    }

    private  List<WardModel> GetAllStreetsByCity(SoapObject data) {

        List<WardModel> list = new ArrayList<>();
        try {
            for (int i = 0; i < data.getPropertyCount(); i++) {

                Object property = data.getProperty(i);
                if (property instanceof SoapObject) {

                    SoapObject placeObj = (SoapObject) property;
                    WardModel model = new WardModel();

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

    private List<WardModel> GetAllStreetByDistrict(SoapObject data) {

        List<WardModel> list = new ArrayList<>();
        try {
            for (int i = 0; i < data.getPropertyCount(); i++) {

                Object property = data.getProperty(i);
                if (property instanceof SoapObject) {

                    SoapObject placeObj = (SoapObject) property;
                    WardModel model = new WardModel();

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

    private List<WardModel> GetAllCitys(SoapObject data) {

        List<WardModel> list = new ArrayList<>();
        try {
            for (int i = 0; i < data.getPropertyCount(); i++) {

                Object property = data.getProperty(i);
                if (property instanceof SoapObject) {

                    SoapObject placeObj = (SoapObject) property;
                    WardModel model = new WardModel();

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

    private List<WardModel> GetAllDistrictByCity(SoapObject data) {

        List<WardModel> list = new ArrayList<>();
        try {
            for (int i = 0; i < data.getPropertyCount(); i++) {

                Object property = data.getProperty(i);
                if (property instanceof SoapObject) {

                    SoapObject placeObj = (SoapObject) property;
                    WardModel model = new WardModel();

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
