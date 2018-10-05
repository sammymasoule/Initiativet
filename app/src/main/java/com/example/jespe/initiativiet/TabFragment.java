package com.example.jespe.initiativiet;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TabFragment extends Fragment {

    ViewPager vp;
    SectionsPageAdapter SPA;
    //https://stackoverflow.com/questions/41413150/fragment-tabs-inside-fragment kilde til layout

    @Override
    public View onCreateView(LayoutInflater i, ViewGroup container, Bundle savedInstanceState) {
        View v = i.inflate(R.layout.fragment_tab, container, false);
        Log.e("SAMMYERTYK", "RUN ME");

        vp = (ViewPager) v.findViewById(R.id.container);
        SPA = new SectionsPageAdapter(getFragmentManager());
        iniViewPager(vp);

        TabLayout tabs = (TabLayout) v.findViewById(R.id.tabs);
        tabs.setupWithViewPager(vp);

        return v;
    }

    private void iniViewPager(ViewPager vp) {
        Log.e("tab fragment", "view pager init");
        SPA.addFragment(new StatistikFragment(), "Statistik");
        SPA.addFragment(new ValgFragment(), "Valg");
        SPA.addFragment(new ForumFragment(), "Forum");
        vp.setAdapter(SPA);
        vp.setCurrentItem(1);


    }
}
