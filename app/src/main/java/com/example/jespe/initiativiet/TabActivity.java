package com.example.jespe.initiativiet;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class TabActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnTouchListener {


    //private SectionPageAdapter adapter;

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;


    private ViewPager mViewPager;
    private ActionBar tools;
    private FragmentTabHost tabHost;
    private FrameLayout frameLayout;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);

        setContentView(R.layout.activity_tab);
        frameLayout = (FrameLayout) findViewById(android.R.id.tabcontent);

        frameLayout.setOnTouchListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        tabHost = (FragmentTabHost) findViewById(R.id.tab_host);
        tabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        tabHost.addTab(
            tabHost.newTabSpec("valg").setIndicator(null, getResources().getDrawable(R.drawable.ic_mode_edit_black_24dp)),
            ValgFragment.class, null
        );
        tabHost.addTab(
            tabHost.newTabSpec("stat").setIndicator(null, getResources().getDrawable(R.drawable.ic_poll_black_24dp)),
            StatistikFragment.class, null
        );
        tabHost.addTab(
            tabHost.newTabSpec("forum").setIndicator(null,getResources().getDrawable(R.drawable.ic_forum_black_24dp)),
            ForumFragment.class, null
        );
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.burger_settings, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            // Handle the camera action
        } else if (id == R.id.nav_posts) {

        } else if (id == R.id.nav_debates) {

        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_logout) {
            System.out.println("id: "+auth.getCurrentUser());
            auth.signOut();
            System.out.println(auth.getCurrentUser());
            try {
                if(auth.getCurrentUser() == null) {

                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.fragmentContainer, new LoginFragment())
                            .commit();
                    finish();
                }
            } catch (Exception e) {e.printStackTrace();}
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        float pos1 = 0, pos2 = 0;
        System.out.println("OnTouch called...");
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                System.out.println("Action Down");
                pos1 = event.getX();
                break;
            }
            case MotionEvent.ACTION_UP: {
                System.out.println("Action Up");
                pos2 = event.getX();
                System.out.println("pos1: " + pos1 + " pos2: " + pos2);
                if (pos1 < pos2) {
                    tabSwitcher(false);
                }
                else if (pos1 > pos2) {
                    tabSwitcher(true);
                }
                break;
            }
        }
        return true;
    }

    public void tabSwitcher(boolean bool) {
        System.out.println("tabSwitcher called...");
        /*if (bool) { // move left
            if (tabHost.getCurrentTab() != 2)
            tabHost.setCurrentTab(tabHost.getCurrentTab() + 1);
        }
        else if (!bool) { // move right
            if (tabHost.getCurrentTab() != 0)
            tabHost.setCurrentTab(tabHost.getCurrentTab() - 1);
        } */

        if (bool) {
            if (tabHost.getCurrentTab() == 0)
                tabHost.setCurrentTab(1);
            else if (tabHost.getCurrentTab() == 1)
                tabHost.setCurrentTab(2);
        }
        else if (!bool) {
            if (tabHost.getCurrentTab() == 2)
                tabHost.setCurrentTab(1);
            else if (tabHost.getCurrentTab() == 1)
                tabHost.setCurrentTab(0);
        }

    }
}