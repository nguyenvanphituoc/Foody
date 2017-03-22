package com.example.nguyenvanphituoc.foody.Activity;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.ArrayRes;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nguyenvanphituoc.foody.Activity.Fragment.FoodyPage1Fragment;
import com.example.nguyenvanphituoc.foody.Activity.Fragment.FoodyPage2Fragment;
import com.example.nguyenvanphituoc.foody.Adapter.FragmentAdapter;
import com.example.nguyenvanphituoc.foody.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

public class MainActivity extends AppCompatActivity {
    Context mContext;
    Resources res;
    //
    Toolbar toolbarMain;
    ViewPager viewPageMain;
    ViewGroup tab_onBottom;
    //
    public static String TABLOAD_ONTOP_POSITION = "POSITION";
    static private OnBottomStatus bottomToolbarStatus = OnBottomStatus.News;

    private enum OnBottomStatus {
        News,
        Noti,
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

        toolbarMain = (Toolbar) findViewById(R.id.toolBar_main);//
        viewPageMain = (ViewPager) findViewById(R.id.pagerMain);
        tab_onBottom = (ViewGroup) findViewById(R.id.tabbar_onBottom);

        initialToolbarOnBottom();//trigger
    }

    //-----------------------------------------------------------------------------------------
    //....first create onBottom toolbar and set click
    private void initialToolbarOnBottom() {
        for (int i = 1; i <= 5; i++) {
            int resID = getResources().getIdentifier("toolbar_onBottom_imgBtn" + i,
                    "id", getPackageName());
            ImageButton mybtn = (ImageButton) findViewById(resID);
            mybtn.setOnClickListener(myTabBarOnrBottomClick());
        }
    }

    //....second inner funcClick change color and create view respond
    private View.OnClickListener myTabBarOnrBottomClick() {
        return new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                try {
                    //older click will return grey color
                    changeImgColorFilter(res.getColor(R.color.grey05, null),
                            PorterDuff.Mode.SRC_ATOP, bottomToolbarStatus);
                    //clear toolbarMain and viewPaper
                    toolbarMain.removeAllViews();
                    viewPageMain.clearOnPageChangeListeners();
                    viewPageMain.removeAllViews();
                    //change page clicked
                    //.get inflater
                    LayoutInflater inflater = getLayoutInflater();
                    //.store toolbar_main layout in toolbarMain (activity.xml)
                    View actionBar = inflater.inflate(R.layout.toolbar_main, toolbarMain);
                    //.set imgHome
                    ImageButton imgHome = (ImageButton) actionBar.findViewById(R.id.toolbar_main_imgBtnHome);
                    imgHome.setImageResource(android.R.color.transparent);
                    //.set imgPlus
                    ImageButton imgPlus = (ImageButton) actionBar.findViewById(R.id.toolbar_main_imgBtnPlus);
                    imgPlus.setImageResource(android.R.color.transparent);
                    // check click button
                    switch (v.getId()) {
                        case R.id.toolbar_onBottom_imgBtn1:
                            imgHome.setImageResource(R.mipmap.foody_logo);
                            imgPlus.setImageResource(R.drawable.ic_add_empty_32dp);
                            bottomToolbarStatus = OnBottomStatus.News;
                            break;
                        case R.id.toolbar_onBottom_imgBtn2:
                            bottomToolbarStatus = OnBottomStatus.Gall;
                            break;
                        case R.id.toolbar_onBottom_imgBtn3:
                            bottomToolbarStatus = OnBottomStatus.Sear;
                            break;
                        case R.id.toolbar_onBottom_imgBtn4:
                            bottomToolbarStatus = OnBottomStatus.Noti;
                            break;
                        case R.id.toolbar_onBottom_imgBtn5:
                            bottomToolbarStatus = OnBottomStatus.Prof;
                            break;
                        default:
                            break;
                    }
                    // new click will enable red color
                    changeImgColorFilter(res.getColor(R.color.clRed, null),
                            PorterDuff.Mode.SRC_IN, bottomToolbarStatus);
                    //.get tabMain from toolbar_main layout
                    TabLayout mainTab = (TabLayout) actionBar.findViewById(R.id.toolbar_main_tab);
                    //.set view to tabMain and viewPagerMain
                    changePageClick(bottomToolbarStatus, mainTab);
                } catch (Exception ex) {
                    showCustomAlert(ex.getMessage());
                    ex.printStackTrace();
                }
            }
        };
    }

    //..some func support for this session
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void changeImgColorFilter(int colorID, PorterDuff.Mode mode, OnBottomStatus stt) {
        switch (stt) {
            case News:
                changeColorFilter(colorID,
                        mode, (ImageButton) findViewById(R.id.toolbar_onBottom_imgBtn1));
                break;
            case Gall:
                changeColorFilter(colorID,
                        mode, (ImageButton) findViewById(R.id.toolbar_onBottom_imgBtn2));
                break;
            case Sear:
                changeColorFilter(colorID,
                        mode, (ImageButton) findViewById(R.id.toolbar_onBottom_imgBtn3));
                break;
            case Noti:
                changeColorFilter(colorID,
                        mode, (ImageButton) findViewById(R.id.toolbar_onBottom_imgBtn4));
                break;
            case Prof:
                changeColorFilter(colorID,
                        mode, (ImageButton) findViewById(R.id.toolbar_onBottom_imgBtn5));
                break;
            default:
                showCustomAlert("Some thing error in Toolbar Bottom Clicked!");
                break;
        }
    }

    private void changeColorFilter(int colorID, PorterDuff.Mode mode, ImageButton imgBtn) {
        imgBtn.setColorFilter(colorID, mode);
    }

    //....second inner funcClick create view (tabMain + viewPagerMain) respond
    private void changePageClick(OnBottomStatus bottomToolbarStatus, TabLayout mainTab) {
        //change toolbarMain first
        //create toolbarOnTop second
        //set viewpager third
        mainTab.removeAllTabs();
        mainTab.clearOnTabSelectedListeners();
        Bundle sendData = new Bundle();
        String myModel;
        switch (bottomToolbarStatus) {
            case News:
                myModel = getJSONFromAsset("foody_home_tabontop/home.json");
                sendData.putString("model", myModel);
                initialTabMain(mainTab, getResourceOnBottom(R.array.FOODY_HOME),
                      new String[]{FoodyPage1Fragment.class.getName(),
                              FoodyPage2Fragment.class.getName()}, sendData);
                break;
            case Gall:
                initialTabMain(mainTab, getResourceOnBottom(R.array.FOODY_GALLERY),
                        new String[]{FoodyPage1Fragment.class.getName(),
                                FoodyPage2Fragment.class.getName()}, sendData);
                break;
            case Sear:
                break;
            case Noti:
                initialTabMain(mainTab, getResourceOnBottom(R.array.FOODY_NOTI),
                        new String[]{FoodyPage1Fragment.class.getName()}, sendData);
                break;
            case Prof:
                break;
            default:
                showCustomAlert("Some thing error in Toolbar Bottom Clicked!");
                break;
        }
        setSupportActionBar(toolbarMain);
    }

    public String getJSONFromAsset(String jsonPath) {
        String json;
        try {
            InputStream is = getAssets().open(jsonPath);
            Charset cs = Charset.forName("utf8");
            CharsetDecoder decoder = cs.newDecoder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, decoder));
            StringBuilder out = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                out.append(line);
            }
            reader.close();
//            int size = is.available();
//            byte[] buffer = new byte[size];
//            is.read(buffer);
//            is.close();
//
            json = out.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    private String[][] getResourceOnBottom(@ArrayRes int arrayId) {
        TypedArray myTypedArray = res.obtainTypedArray(arrayId);
        String[][] myTab_TopToolbar = new String[myTypedArray.length()][];
        for (int i = 0, n = myTypedArray.length(); i < n; ++i) {
            int id = myTypedArray.getResourceId(i, -1);
            if (id > 0)
                myTab_TopToolbar[i] = res.getStringArray(id);
            else
                showCustomAlert("R.array.FOODY_NOTI not found in func change page");
        }
        myTypedArray.recycle();
        return myTab_TopToolbar;
    }

    //....second inner funcClick create view (tabMain + viewPagerMain) respond
    private void initialTabMain(TabLayout toolbar_main_tab, String[][] mainTabData,
                                String[] fragmentMain, Bundle sendData) {
        try {
            for (String[] aTabMain_TopToolBar : mainTabData) {
                toolbar_main_tab.addTab(toolbar_main_tab.newTab().setText(aTabMain_TopToolBar[0]));
            }
            setupViewPager(toolbar_main_tab, mainTabData, viewPageMain, fragmentMain, sendData);
            viewPageMain.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(toolbar_main_tab));
            toolbar_main_tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab LayoutTab) {
                    viewPageMain.setCurrentItem(LayoutTab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab LayoutTab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab LayoutTab) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupViewPager(TabLayout toolbar_main_tab, String[][] mainTabData,
                                ViewPager viewPager, String[] fragmentMain, Bundle sendData)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        try {
            FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), mContext);
            for (int i = 0, n = toolbar_main_tab.getTabCount(); i < n; i++) {
                TabLayout.Tab tab = toolbar_main_tab.getTabAt(i);
                assert tab != null && tab.getText() != null;
                //"com.example.nguyenvanphituoc.foody.Activity.Fragment.FoodyPage1Fragment"
                Class<?> clazz = Class.forName(fragmentMain[i]);
                Fragment mainFragment = (Fragment) clazz.newInstance();
                // send data to fragment
                sendData.putStringArray("tab", mainTabData[i]);
                mainFragment.setArguments(sendData);
                mainFragment.onCreate(sendData);
                //
                adapter.addFragment(mainFragment, tab.getText().toString());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (n > 1)
                        tab.setCustomView(adapter.getTabMainView(i, tab));
                    else
                        tab.setCustomView(adapter.getTabMainView(tab));
                }
            }
            viewPager.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    //--------------------------------------------------------------------------------------------------
    //custom Toast alert dùng inflater(layout resource, viewgroup root) lấy layout toast
    private void showCustomAlert(String message) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_list_inline, (ViewGroup) findViewById(R.id.custom_inline_item));

        TextView text = (TextView) layout.findViewById(R.id.custom_inline_item_TextView);
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

    //.........................when button on bottom click create new ontop menu
    public boolean tabOnTopPrepareOption() {
        //
        LayoutInflater inflater = getLayoutInflater();
        inflater.inflate(R.layout.inflate_tab_onbottom_news, toolbarMain, false);
        return true;
    }

    //......................................
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        outState.putInt(TABLOAD_ONTOP_POSITION, tabMain.getSelectedTabPosition());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        viewPageMain.setCurrentItem(savedInstanceState.getInt(TABLOAD_ONTOP_POSITION));
    }
}
