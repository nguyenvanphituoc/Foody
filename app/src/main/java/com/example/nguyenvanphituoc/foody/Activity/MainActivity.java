package com.example.nguyenvanphituoc.foody.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nguyenvanphituoc.foody.Activity.Fragment.TabPlacesBookingFragment;
import com.example.nguyenvanphituoc.foody.Activity.Fragment.TabPlacesCityFragment;
import com.example.nguyenvanphituoc.foody.Adapter.FragmentAdapterToolbarOnTop;
import com.example.nguyenvanphituoc.foody.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Context mContext;
    Resources res;
    //
    Toolbar toolbarMain;
    TabLayout tabMain;
    ViewPager viewPageMain;
    TabHost ontopToolbar;
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

        toolbarMain = (Toolbar) findViewById(R.id.toolBarMain);
        setSupportActionBar(toolbarMain);

        initialTabMain();

        onClickBottomToolbar();
    }

    private void initialOntopToolbar(){
        fragmentClassName.clear();
        //Assign id to Tabhost.
        ontopToolbar = (TabHost)findViewById(android.R.id.tabhost);

        //Creating tab menu.
        TabHost.TabSpec TabMenu1 = ontopToolbar.newTabSpec("First tab");
        TabHost.TabSpec TabMenu2 = ontopToolbar.newTabSpec("Second Tab");
        TabHost.TabSpec TabMenu3 = ontopToolbar.newTabSpec("Third Tab");

        //Setting up tab 1 name.
        TabMenu1.setIndicator("Tab1");
        //Set tab 1 activity to tab 1 menu.
        TabMenu1.setContent(new Intent(this,TabPlacesBookingFragment.class));

        //Setting up tab 2 name.
        TabMenu2.setIndicator("Tab2");
        //Set tab 3 activity to tab 1 menu.
        TabMenu2.setContent(new Intent(this,TabPlacesBookingFragment.class));

        //Setting up tab 2 name.
        TabMenu3.setIndicator("Tab3");
        //Set tab 3 activity to tab 3 menu.
        TabMenu3.setContent(new Intent(this,TabPlacesBookingFragment.class));

        //Adding tab1, tab2, tab3 to tabhost view.

        ontopToolbar.addTab(TabMenu1);
        ontopToolbar.addTab(TabMenu2);
        ontopToolbar.addTab(TabMenu3);
    }

    private void initialTabMain() {
        tabMain = (TabLayout) findViewById(R.id.tabMain);
        viewPageMain = (ViewPager) findViewById(R.id.pagerMain);
        TypedArray typedArray = res.obtainTypedArray(R.array.TABMAIN);
        try {
            for (int i = 0, n = typedArray.length(); i < n; i++) {
                tabMain.addTab(tabMain.newTab().setText(typedArray.getString(i)));
            }

            setupViewPager(viewPageMain);
            viewPageMain.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabMain));

            tabMain.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
        finally {
            typedArray.recycle();
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        FragmentAdapterToolbarOnTop adapter = new FragmentAdapterToolbarOnTop(getSupportFragmentManager(), mContext);
        adapter.addFragment(new TabPlacesBookingFragment(), "TODAY");
        adapter.addFragment(new TabPlacesCityFragment(), "WEEKLY");
        adapter.addFragment(new TabPlacesBookingFragment(), "MONTHLY");
        for (int i = 0, n = tabMain.getTabCount(); i < n; i++) {
            TabLayout.Tab tab = tabMain.getTabAt(i);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                assert tab != null;
                tab.setCustomView(adapter.getTabView(i, tab));
            }
        }
        viewPager.setAdapter(adapter);
    }

    private void onClickBottomToolbar() {
        for (int i = 1; i <= 5; i++) {
            int resID = getResources().getIdentifier("imgBtnOnBottomToolBar" + i,
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
                    changeImgColorFilter(res.getColor(R.color.grey05, null),
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
                            bottomToolbarStatus = OnBottomStatus.Noti;
                            break;
                        case R.id.imgBtnOnBottomToolBar5:
                            bottomToolbarStatus = OnBottomStatus.Prof;
                            break;
                        default:
                            break;
                    }
                    // new
                    changeImgColorFilter(res.getColor(R.color.clRed, null),
                            PorterDuff.Mode.SRC_IN, bottomToolbarStatus);
                } catch (Exception ex) {
                    showCustomAlert(ex.getMessage());
                    ex.printStackTrace();
                }
            }
        };
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void changeImgColorFilter(int colorID, PorterDuff.Mode mode, OnBottomStatus stt) {
        switch (stt) {
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
            case Noti:
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
        View layout = inflater.inflate(R.layout.custom_list_inline, (ViewGroup) findViewById(R.id.custom_inline_item_container));

        TextView text = (TextView) layout.findViewById(R.id.customListTextView);
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(TABLOAD_ONTOP_POSITION, tabMain.getSelectedTabPosition());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        viewPageMain.setCurrentItem(savedInstanceState.getInt(TABLOAD_ONTOP_POSITION));
    }
}
