package com.example.nguyenvanphituoc.foody.DAO;

import java.io.Serializable;
import java.util.List;

/**
 * Created by PhiTuocPC on 4/24/2017.
 */

public abstract class ServiceAbs<T> implements  ServiceIF{


    public List<T> listData;

    public boolean dataMode = false;

    String SOAP_ACTION = "";

    String OPERATION_NAME = "";

    static  final String WSDL_TARGET_NAMESPACE = "http://nguyenvanphituoc.org/";

    String SOAP_ADDRESS = "http://10.0.2.2:57700/Service/PlaceService.asmx";

    static  final String DUMP_SOAP_ACTION = "http://nguyenvanphituoc.org/";

    public abstract void acceptACKInitial(ServiceIF model, Object sender);



    @Override
    public abstract void receiveData(Object data);

    @Override
    public abstract void SwitchOperation(String op);

    @Override
    public abstract  Object DoOperation(Object sender);

    @Override
    public abstract Enum hasOperation(String op);
}
