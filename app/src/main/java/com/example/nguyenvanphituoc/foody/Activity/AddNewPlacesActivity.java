package com.example.nguyenvanphituoc.foody.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nguyenvanphituoc.foody.Activity.Fragment.FoodyAddNewDialogFragment;
import com.example.nguyenvanphituoc.foody.DAO.OtherServiceImpl;
import com.example.nguyenvanphituoc.foody.Interface.GetDataFromChildFragment;
import com.example.nguyenvanphituoc.foody.Interface.SendDataToChildFragment;
import com.example.nguyenvanphituoc.foody.Model.WardModel;
import com.example.nguyenvanphituoc.foody.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AddNewPlacesActivity extends AppCompatActivity implements GetDataFromChildFragment, Serializable {

    AddNewPlacesActivity myActivity;
    Button btnCity;
    Button btnDistrict;
    TextView btnStreet;
    TextView btnCategory;
    TextView btnService;
    TextView btnNewName;
    OtherServiceImpl.OPERATION storedOp;

    @Override
    public void getTabName(String name) {
        String[] category = name.split(":");
        switch (storedOp){
            case GetAllCategories:
                btnCategory.setText(category[1]);
                btnCategory.setTag(category[0]);
                break;
            case GetAllServices:
                btnService.setText(category[1]);
                btnService.setTag(category[0]);
                break;
            default:
                break;
        }
    }

    @Override
    public void getAddress(WardModel ward) {
        switch (storedOp){
            case GetAllCitys:
                btnCity.setText(ward.getCity());
                btnCity.setTag(ward.getId());
                break;
            case GetAllDistrictByCity:
                btnDistrict.setText(ward.getDistrict());
                break;
            case GetAllStreetByDistrict:
                btnStreet.setText(ward.getStreet());
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_places);

        myActivity = this;

        TextView txtBack = (TextView) findViewById(R.id.add_new_back);

        btnCity = (Button) findViewById(R.id.add_new_btn_city);
        btnDistrict = (Button) findViewById(R.id.add_new_btn_district);
        btnStreet = (TextView) findViewById(R.id.add_new_btn_street);
        btnCategory = (TextView) findViewById(R.id.add_new_category);
        btnService = (TextView) findViewById(R.id.add_new_service);
        btnNewName = (TextView) findViewById(R.id.add_new_name);

        txtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btnCity.setOnClickListener(onClicked(OtherServiceImpl.OPERATION.GetAllCitys));
        btnDistrict.setOnClickListener(onClicked(OtherServiceImpl.OPERATION.GetAllDistrictByCity));
        btnStreet.setOnClickListener(onClicked(OtherServiceImpl.OPERATION.GetAllStreetByDistrict));
        btnCategory.setOnClickListener(onClicked(OtherServiceImpl.OPERATION.GetAllCategories));
        btnService.setOnClickListener(onClicked(OtherServiceImpl.OPERATION.GetAllServices));

        btnNewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Creating alert Dialog with one Button
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(myActivity);

                //AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();

                // Setting Dialog Title
                alertDialog.setTitle("Name");

                // Setting Dialog Message
                alertDialog.setMessage("Enter Name");
                final EditText input = new EditText(myActivity);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialog.setView(input); // uncomment this line

                // Setting Positive "Yes" Button
                alertDialog.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int which) {
                                // Write your code here to execute after dialog
                                btnNewName.setText(input.getText());
                            }
                        });
                // Setting Negative "NO" Button
                alertDialog.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog
                                dialog.cancel();
                            }
                        });

                // closed

                // Showing Alert Message
                alertDialog.show();
            }
        });
    }

    private View.OnClickListener onClicked(final OtherServiceImpl.OPERATION op) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storedOp = op;
                showDialog(op);
            }
        };
    }

    public void showDialog(OtherServiceImpl.OPERATION op){
        Bundle myBundle = new Bundle();

        myBundle.putSerializable("myCallBack", myActivity);

        int cityId ;
        WardModel ward ;
        switch (op){
            case GetAllCitys:
                break;
            case GetAllDistrictByCity:
                cityId = Integer.parseInt(btnCity.getTag().toString());
                ward = new WardModel(cityId, btnCity.getText().toString());
                myBundle.putSerializable("ward", ward);
                break;
            case GetAllStreetByDistrict:
                cityId = Integer.parseInt(btnCity.getTag().toString());
                ward = new WardModel(cityId, btnCity.getText().toString(), btnDistrict.getText().toString(), null);
                myBundle.putSerializable("ward", ward);
                break;
            case GetAllCategories:
                break;
            case GetAllServices:
                break;
            default:
                break;
        }

        myBundle.putString("operation", op.toString());

        DialogFragment newFragment = new FoodyAddNewDialogFragment();
        ((SendDataToChildFragment) newFragment).sendBundleToChild(myBundle);
        ((SendDataToChildFragment) newFragment).sendACKInitialData();

        newFragment.show(getFragmentManager(), "choose");
    }

/*    @Override
    public void onBackPressed() {
        android.app.FragmentManager fm = getFragmentManager();
        fm.popBackStack();
        super.onBackPressed();
    }*/
}
