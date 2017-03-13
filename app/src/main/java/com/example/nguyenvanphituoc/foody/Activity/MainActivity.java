package com.example.nguyenvanphituoc.foody.Activity;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nguyenvanphituoc.foody.Adapter.FragmentOntopAdapterClass;
import com.example.nguyenvanphituoc.foody.R;

public class MainActivity extends AppCompatActivity {
    Context mContext;
    Resources res;
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    FragmentOntopAdapterClass fragmentAdapter;
    static private OnBottomStatus bottomToolbarStatus = OnBottomStatus.News;
    private enum OnBottomStatus {
        News,
        Nofi,
        Sear,
        Prof,
        Gall
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        res = getResources();
        mContext = getApplicationContext();

        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout_ontop);
        viewPager = (ViewPager) findViewById(R.id.pager1);

 //       onClickBottomToolbar();

        setSupportActionBar(toolbar);
        initialOnTopTab();
    }

    private void onClickBottomToolbar(){
        for(int i = 1; i <= 5; i++){
            int resID = getResources().getIdentifier("imgBtnOnBottomToolBar"+i,
                    "id", getPackageName());
            ImageButton mybtn = (ImageButton) findViewById(resID);
            mybtn.setOnClickListener(myToolbarBottomClick());
        }
    }

    private View.OnClickListener myToolbarBottomClick() {
        return new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                try {
                    //older
                    changeImgColorFilter(res.getColor(R.color.clBlack, null),
                            PorterDuff.Mode.SRC_ATOP, bottomToolbarStatus);
                    // news
                    switch (v.getId()) {
                        case R.id.imgBtnOnBottomToolBar1:
                            bottomToolbarStatus = OnBottomStatus.News;
                            break;
                        case R.id.imgBtnOnBottomToolBar2:
                            bottomToolbarStatus = OnBottomStatus.Gall;
                            break;
                        case R.id.imgBtnOnBottomToolBar3:
                            bottomToolbarStatus = OnBottomStatus.Sear;
                            break;
                        case R.id.imgBtnOnBottomToolBar4:
                            bottomToolbarStatus = OnBottomStatus.Nofi;
                            break;
                        case R.id.imgBtnOnBottomToolBar5:
                            bottomToolbarStatus = OnBottomStatus.Prof;
                            break;
                        default:
                            break;
                    }
                    // new
                    changeImgColorFilter(res.getColor(R.color.clRed, null),
                            PorterDuff.Mode.SRC_ATOP, bottomToolbarStatus);
                }
                catch (Exception ex){
                    showCustomAlert(ex.getMessage());
                    ex.printStackTrace();
                }
            }
        };
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void changeImgColorFilter(int colorID, PorterDuff.Mode mode, OnBottomStatus stt){
        switch (stt){
            case News:
                changeColorFilter(colorID,
                        mode, (ImageButton) findViewById(R.id.imgBtnOnBottomToolBar1));
                break;
            case Gall:
                changeColorFilter(colorID,
                        mode, (ImageButton) findViewById(R.id.imgBtnOnBottomToolBar2));
                break;
            case Sear:
                changeColorFilter(colorID,
                        mode, (ImageButton) findViewById(R.id.imgBtnOnBottomToolBar3));
                break;
            case Nofi:
                changeColorFilter(colorID,
                        mode, (ImageButton) findViewById(R.id.imgBtnOnBottomToolBar4));
                break;
            case Prof:
                changeColorFilter(colorID,
                        mode, (ImageButton) findViewById(R.id.imgBtnOnBottomToolBar5));
                break;
            default:
                showCustomAlert("Some thing error in Toolbar Bottom Clicked!");
                break;
        }
    }

    private void changeColorFilter(int colorID, PorterDuff.Mode mode, ImageButton imgBtn) {
            imgBtn.setColorFilter(colorID, mode);
    }

    //custom Toast alert dùng inflater(layout resource, viewgroup root) lấy layout toast
    private void showCustomAlert(String message) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_inline_item, (ViewGroup) findViewById(R.id.custom_inline_item_container));

        TextView text = (TextView) layout.findViewById(R.id.TextView);
        text.setText(message);

        final Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);

        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
        // set duration
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, 1000);
    }

    private void initialOnTopTab() {
        TypedArray typedArray = res.obtainTypedArray(R.array.MENU_PLACES);
        try {
            for (int i = 0, n = typedArray.length(); i < n; i++)
                tabLayout.addTab(tabLayout.newTab().setText(typedArray.getString(i)));
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
            fragmentAdapter = new FragmentOntopAdapterClass(getSupportFragmentManager(), tabLayout.getTabCount());

            viewPager.setAdapter(fragmentAdapter);

            tabLayout.setupWithViewPager(viewPager);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            typedArray.recycle();
        }
    }
}
