package com.example.nguyenvanphituoc.foody.DAO;

import com.example.nguyenvanphituoc.foody.Model.PersonModel;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by PhiTuocPC on 4/21/2017.
 * nguyễn văn phi tước
 */

public class UserServiceImpl  extends ServiceAbs<PersonModel> {

    private OPERATION op;

    private  enum OPERATION {
        GetAllPerson,
        GetPersonByCondition,
        InsertPerson,
        UpdatePerson,
        UpdateAccount,
        DeletePerson
    }
    @Override
    public void SwitchOperation(String op) {
        OPERATION dump = (OPERATION) hasOperation(op);
        if(dump != null) {
            SOAP_ADDRESS = "http://10.0.2.2:57700/Service/PersonService.asmx";
            SOAP_ACTION = DUMP_SOAP_ACTION + dump.toString();
            OPERATION_NAME = dump.toString();
            this.op = dump;
        }
    }


    @Override
    public Object DoOperation(Object sender) {
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
    public Enum hasOperation(String op) {
        for (OPERATION operation: OPERATION.values()) {
            if(operation.toString().toUpperCase().equals(op.toUpperCase())){
                return operation;
            }
        }
        return null;
    }

    @Override
    public void acceptACKInitial(ServiceIF model, Object sender) {

    }

    private Object GetAllPerson() {

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

    }
}
