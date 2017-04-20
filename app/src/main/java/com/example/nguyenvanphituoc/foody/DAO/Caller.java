package com.example.nguyenvanphituoc.foody.DAO;

import com.example.nguyenvanphituoc.foody.Activity.MainActivity;

/**
 * Created by PhiTuocPC on 4/5/2017.
 */

public class Caller  extends Thread
{
    public CallSoap cs;
    public int a,b;

    public void run(){
//        try{
//            cs=new CallSoap();
//            String resp=cs.Call(a, b);
//            MainActivity.rslt=resp;
//        }catch(Exception ex)
//        {MainActivity.rslt=ex.toString();}
//
//        EditText ed1=(EditText)findViewById(R.id.editText1);
//        EditText ed2=(EditText)findViewById(R.id.editText2);
//        int a=Integer.parseInt(ed1.getText().toString());
//        int b=Integer.parseInt(ed2.getText().toString());
//        rslt="START";
//        Caller c=new Caller(); c.a=a;
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
