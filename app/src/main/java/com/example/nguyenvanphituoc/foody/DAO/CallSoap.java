package com.example.nguyenvanphituoc.foody.DAO;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by PhiTuocPC on 4/5/2017.
 */

public class CallSoap {
    public final String SOAP_ACTION = "http://tempuri.org/Add";

    public final String OPERATION_NAME = "Add";

    public final String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";

    public final String SOAP_ADDRESS = "http://10.0.2.2:57700/Service/MyWebService.asmx";

    public CallSoap() {
    }

    public String Call(int a, int b) {
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);
        PropertyInfo pi = new PropertyInfo();
        pi.setName("a");
        pi.setValue(a);
        pi.setType(Integer.class);
        request.addProperty(pi);
        pi = new PropertyInfo();
        pi.setName("b");
        pi.setValue(b);
        pi.setType(Integer.class);
        request.addProperty(pi);

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
        return response.toString();
    }
}
