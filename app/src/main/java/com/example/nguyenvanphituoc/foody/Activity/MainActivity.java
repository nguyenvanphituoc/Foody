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
import android.support.v4.app.FragmentTabHost;
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

import com.example.nguyenvanphituoc.foody.Activity.Fragment.FoodyHomePageFragment;
import com.example.nguyenvanphituoc.foody.Adapter.FragmentAdapter;
import com.example.nguyenvanphituoc.foody.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Context mContext;
    Resources res;
    //
    Toolbar toolbarMain;
    ViewPager viewPageMain;
    ViewGroup toolbar_onBottom;
    //
    List<String> fragmentClassName = new ArrayList<>();
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
        toolbar_onBottom = (ViewGroup) findViewById(R.id.toolbar_onBottom);

        initialToolbarOnBottom();//trigger
    }

    //-----------------------------------------------------------------------------------------
    //....first create onBottom toolbar and set click
    private void initialToolbarOnBottom() {
        for (int i = 1; i <= 5; i++) {
            int resID = getResources().getIdentifier("toolbar_onBottom_imgBtn" + i,
                    "id", getPackageName());
            ImageButton mybtn = (ImageButton) findViewById(resID);
            mybtn.setOnClickListener(myToolbaOnrBottomClick());
        }
    }

    //....second inner funcClick change color and create view respond
    private View.OnClickListener myToolbaOnrBottomClick() {
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
                    ImageButton imgHome =(ImageButton) actionBar.findViewById(R.id.toolbar_main_imgBtnHome);
                    imgHome.setImageResource(android.R.color.transparent);
                    //.set imgPlus
                    ImageButton imgPlus =(ImageButton) actionBar.findViewById(R.id.toolbar_main_imgBtnPlus);
                    imgPlus.setImageResource(android.R.color.transparent);
                    // check click button
                    switch (v.getId()) {
                        case R.id.toolbar_onBottom_imgBtn1:
                            imgHome.setImageResource(R.mipmap.foody_logo);
                            imgPlus.setImageResource(R.drawable.ic_add_empty_24dp);
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
                    //.get toolbarMainTab from toolbar_main layout
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

    //....second inner funcClick create view (tabMain + viewPagerMain) respond
    private void changePageClick(OnBottomStatus bottomToolbarStatus, TabLayout mainTab) {
        //change toolbarMain first
        //create toolbarOnTop second
        //set viewpager third
        mainTab.removeAllTabs();
        mainTab.clearOnTabSelectedListeners();
        switch (bottomToolbarStatus) {
            case News:
                initialTabMain(mainTab, R.array.TOOLBAR_MAIN_TAB, FoodyHomePageFragment.class.getName());
                break;
            case Gall:
                break;
            case Sear:
                break;
            case Noti:
                break;
            case Prof:
                break;
            default:
                showCustomAlert("Some thing error in Toolbar Bottom Clicked!");
                break;
        }
        setSupportActionBar(toolbarMain);
    }

    //....second inner funcClick create view (tabMain + viewPagerMain) respond
    private void initialTabMain(TabLayout toolbar_main_tab, @ArrayRes int arrayID, String fragmentMain) {
        TypedArray typedArray = res.obtainTypedArray(arrayID);
        try {
            for (int i = 0, n = typedArray.length(); i < n; i++) {
                toolbar_main_tab.addTab(toolbar_main_tab.newTab().setText(typedArray.getString(i)));
            }
            setupViewPager(toolbar_main_tab, viewPageMain, fragmentMain);
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
        } finally {
            typedArray.recycle();
        }
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

    private void setupViewPager(TabLayout toolbar_main_tab, ViewPager viewPager, String fragmentMain)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), mContext);
        for (int i = 0, n = toolbar_main_tab.getTabCount(); i < n; i++) {
            TabLayout.Tab tab = toolbar_main_tab.getTabAt(i);
            assert tab != null && tab.getText() != null;
            //"com.example.nguyenvanphituoc.foody.Activity.Fragment.FoodyHomePageFragment"
            Class<?> clazz = Class.forName(fragmentMain);
            Fragment mainFragment = (Fragment) clazz.newInstance();

            adapter.addFragment(mainFragment, tab.getText().toString());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tab.setCustomView(adapter.getTabMainView(i));
            }
        }
        viewPager.setAdapter(adapter);
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
        inflater.inflate(R.layout.homepage, toolbarMain, false);
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
