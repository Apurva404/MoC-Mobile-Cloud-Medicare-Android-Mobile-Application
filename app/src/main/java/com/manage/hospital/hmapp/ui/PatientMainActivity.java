package com.manage.hospital.hmapp.ui;

import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.manage.hospital.hmapp.Extras.Interface.PatientDashboardFragmentToActivity;
import com.manage.hospital.hmapp.R;
import com.manage.hospital.hmapp.adapter.NavigationListAdapter;
import com.manage.hospital.hmapp.data.NavDrawerItem;

import java.util.ArrayList;

public class PatientMainActivity extends AppCompatActivity implements PatientDashboardFragmentToActivity {

    private String[] drawerTitleArray;
    private TypedArray drawerIconsArray;
    private ArrayList<NavDrawerItem> navDrawerItems;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationListAdapter menuListAdapter;
    private ListView drawerList;
    private DrawerLayout drawerMenuLayout;
    private Boolean isMenuItemClicked=false;
    SessionManager sessionManager;

    Toolbar toolbar;
    TextView textViewToolbarTitle;
    TextView textUserName;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_main);

        setListeners();

        if(savedInstanceState==null){
            Fragment fragment=new PatientDashboardFragment();
            FragmentManager fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.patient_content_frame,fragment).commit();
        }

        sessionManager=new SessionManager(PatientMainActivity.this);

    }

    private void setListeners(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        textViewToolbarTitle=(TextView)findViewById(R.id.toolbar_title);

        textViewToolbarTitle.setText(getResources().getString(R.string.home_activity_title));
        textUserName=(TextView)findViewById(R.id.username);

        drawerMenuLayout=(DrawerLayout)findViewById(R.id.drawer_menu_layout);
        drawerList = (ListView) findViewById(R.id.drawer_list);
        drawerTitleArray=getResources().getStringArray(R.array.pat_nav_drawer_items);
        drawerIconsArray=getResources().obtainTypedArray(R.array.pat_nav_drawer_icons);

        navDrawerItems=new ArrayList<NavDrawerItem>();

        navDrawerItems.add(new NavDrawerItem(drawerTitleArray[0], drawerIconsArray.getResourceId(0, -1)));
        navDrawerItems.add(new NavDrawerItem(drawerTitleArray[1], drawerIconsArray.getResourceId(1, -1)));
        navDrawerItems.add(new NavDrawerItem(drawerTitleArray[2], drawerIconsArray.getResourceId(2, -1)));
        navDrawerItems.add(new NavDrawerItem(drawerTitleArray[3], drawerIconsArray.getResourceId(3,-1)));

        menuListAdapter=new NavigationListAdapter(getApplicationContext(),navDrawerItems);
        drawerList.setAdapter(menuListAdapter);

        drawerToggle=new ActionBarDrawerToggle(this,drawerMenuLayout,toolbar,R.string.app_name,R.string.app_name){

            public void onDrawerClosed(View view) {

                if(isMenuItemClicked) {
                    int position=drawerList.getCheckedItemPosition();
                    displayActivity(position);
                    isMenuItemClicked=false;
                }
                invalidateOptionsMenu();
            }
            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu();
            }
        };
        drawerMenuLayout.addDrawerListener(drawerToggle);
        drawerList.setOnItemClickListener(new MenuItemClickListener());
    }

    private class MenuItemClickListener implements ListView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id){

            isMenuItemClicked=true;
            //drawerList.setItemChecked(position,true);
            //drawerList.setSelection(position);
            drawerMenuLayout.closeDrawer(GravityCompat.START);
            //displayActivity(position);
        }
    }

    public void displayActivity(int position){
        switch (position){
            case 2:
                Intent intent=new Intent(PatientMainActivity.this,PatientSourceActivity.class);
                startActivity(intent);
                break;
            case 3:
                sessionManager.logoutUser();
                break;
        }

    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onFragmentInteraction() {

    }
}
