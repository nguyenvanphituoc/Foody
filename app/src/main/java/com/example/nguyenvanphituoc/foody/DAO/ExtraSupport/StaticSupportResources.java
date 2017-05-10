package com.example.nguyenvanphituoc.foody.DAO.ExtraSupport;

import android.content.Context;
import android.util.Xml;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;

/**
 * Created by PhiTuocPC on 5/10/2017.
 */

public class StaticSupportResources {

    private static final int READ_BLOCK_SIZE = 1024;

    static public boolean ISLOADEDPLACES = false;
    static public boolean ISLOADEDSERVICES = false;
    static public boolean ISLOADEDCATEGORIES = false;
    static public boolean ISLOADEDCITY = false;

    static final public String FILEPLACES = "places";
    static final public String FILESERVICES = "services";
    static final public String FILECATEGORIES = "category";
    static final public String FILECITY = "city";

    static public void saveInterFile (String fileName, SoapObject data, Context context){
        FileOutputStream outputStream;
        try {
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(data);
            XmlSerializer aSerializer = Xml.newSerializer();
            ByteArrayOutputStream os = new ByteArrayOutputStream();

            aSerializer.setOutput(os, "UTF-8");
            envelope.write(aSerializer);
            aSerializer.flush();

            byte[] bytes = os.toByteArray();

            outputStream  = context.openFileOutput(fileName+".xml", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(outputStream);
            oos.writeObject(bytes);
            oos.close();

            switch (fileName) {
                case FILEPLACES:
                    ISLOADEDPLACES = true;
                    break;
                case FILESERVICES:
                    ISLOADEDSERVICES = true;
                    break;
                case FILECATEGORIES:
                    ISLOADEDCATEGORIES = true;
                    break;
                case FILECITY:
                    ISLOADEDCITY = true;
                    break;
                default:
                    break;
            }
//            outputStream.write(data.getBytes());
//            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static public SoapObject readInterFile (String fileName, Context context){
        FileInputStream fileIn;
        ObjectInputStream input;
        SoapObject data = null;
        try {
            fileIn  = context.openFileInput(fileName+".xml");
            input = new ObjectInputStream(fileIn);
            byte[] bytes = (byte[]) input.readObject();
            input.close();

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            SoapObject soap = null;

            ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
            XmlPullParser p = Xml.newPullParser();
            p.setInput(inputStream, "UTF-8");
            envelope.parse(p);
            soap = (SoapObject) envelope.bodyIn;

            data = soap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}
