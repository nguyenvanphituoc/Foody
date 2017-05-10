package com.example.nguyenvanphituoc.foody.DAO;

import android.content.Context;

import com.example.nguyenvanphituoc.foody.Activity.UIUtils;
import com.example.nguyenvanphituoc.foody.DAO.ExtraSupport.StaticSupportResources;
import com.example.nguyenvanphituoc.foody.Model.DisplayModel;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by PhiTuocPC on 4/5/2017.
 * nguyễn văn phi tước
 */

public class PlaceServiceImpl extends ServiceAbs<DisplayModel> {

    private OPERATION op;
    private Context mContext;

    public enum OPERATION {
        GetAllPlaces,
        GetPlacesByCondition,
        GetImages,
        InsertPlaces,
        UpdatePlaces,
        DeletePlaces
    }

    private int currentPage = 0;



    public PlaceServiceImpl(Context context) {
        this.mContext = context;
        currentPage = 0;
    }

    public PlaceServiceImpl(String op, Context context) {
        this.mContext = context;
        SwitchOperation(op);
        currentPage = 0;
    }

    @Override
    public void SwitchOperation(String op) {
        OPERATION dump = (OPERATION) hasOperation(op);
        if (dump != null) {
            SOAP_ADDRESS = "http://10.0.2.2:57700/Service/PlaceService.asmx";
            SOAP_ACTION = DUMP_SOAP_ACTION + dump.toString();
            OPERATION_NAME = dump.toString();
            this.op = dump;
        }
    }

    @Override
    public Object DoOperation(Object sender) {
        switch (op) {
            case GetAllPlaces:
                return GetAllPlaces();
            case GetImages:
                return GetImages((int[]) sender);
            case GetPlacesByCondition:
                return GetAllPlaces();
            case InsertPlaces:
                return GetAllPlaces();
            case UpdatePlaces:
                return GetAllPlaces();
            case DeletePlaces:
                return GetAllPlaces();
            default:
                return null;
        }
    }

    @Override
    public Enum hasOperation(String op) {
        for (OPERATION operation : OPERATION.values()) {
            if (operation.toString().toUpperCase().equals(op.toUpperCase())) {
                return operation;
            }
        }
        return null;
    }

    private Object GetImages(int[] ids) {

        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE httpTransport = new HttpTransportSE(this.SOAP_ADDRESS, 3 * 60 * 1000);

        SoapObject soapIds = new SoapObject(WSDL_TARGET_NAMESPACE, "ids");
        int numberofPage = 80;
        for (int i = 0; i < numberofPage; i++) {
            int index = numberofPage * currentPage + i;
            if (index < ids.length) {
                PropertyInfo pi = new PropertyInfo();
                pi.setName("int");
                pi.setValue(ids[index]);
                pi.setType(Integer.TYPE);
                soapIds.addProperty(pi);
            }
        }
        currentPage++;
        request.addProperty("ids", soapIds);

        Object response;
        try {
            httpTransport.call(SOAP_ACTION, envelope);
            response = envelope.getResponse();
        } catch (Exception exception) {
            response = exception.toString();
        }
        return response;
    }

    private Object GetAllPlaces() {

        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE httpTransport = new HttpTransportSE(this.SOAP_ADDRESS);

        Object response;
        try {
            httpTransport.call(SOAP_ACTION, envelope);
            response = envelope.getResponse();
        } catch (Exception exception) {
            response = exception.toString();
        }
        return response;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void receiveData(Object data) {
        if(data != null) {
            switch (op) {
                case GetAllPlaces:
                    StaticSupportResources.saveInterFile(StaticSupportResources.FILEPLACES, (SoapObject) data, mContext);
                    break;
                default:
                    break;
            }
            listData = this.InitialData((SoapObject) data);
            this.dataMode = true;
        }
    }

    @Override
    public void getData() {
        if (StaticSupportResources.ISLOADEDPLACES) {
            Object data = StaticSupportResources.readInterFile(StaticSupportResources.FILEPLACES, mContext);
            listData = this.InitialData((SoapObject) data);
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

    private  List<DisplayModel> InitialData(SoapObject data) {
        List<DisplayModel> list = new ArrayList<>();
        try {
            for (int i = 0; i < data.getPropertyCount(); i++) {
                Object property = data.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject placeObj = (SoapObject) property;
                    DisplayModel model = new DisplayModel();
                    for (int j = 0; j < placeObj.getPropertyCount(); j++) {

                        //Inside your for loop
                        PropertyInfo itemData = new PropertyInfo();
                        placeObj.getPropertyInfo(j, itemData);

                        if (itemData.getName().equals("img")) {

                            String base64 = itemData.getValue().toString();
                            byte[] bytes = UIUtils.getByteFromBase64(base64);
                            model.setProperty(itemData.getName(), bytes);
                        } else
                            model.setProperty(itemData.getName(), itemData.getValue().toString());
                    }
                    list.add(model);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    private  int findDisplayModelIndex(List<DisplayModel> models, int id) {
        for (int i = 0, n = models.size(); i < n; i++) {
            DisplayModel displayModel = models.get(i);
            if (displayModel.getId() == id)
                return i;
        }
        return -1;
    }

    private int[] getIds(List<DisplayModel> data) {
        int[] result = new int[data.size()];
        for (int i = 0, n = data.size(); i < n; i++) {
            result[i] = data.get(i).getId();
        }
        return result;
    }
}
