package com.armoomragames.denketa.IntroAuxilaries;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.armoomragames.denketa.IntroActivity;
import com.armoomragames.denketa.IntroAuxilaries.RulesAuxilaries.RulesViewPagerAdapter;
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.IBadgeUpdateListener;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class RulesMianFragment extends Fragment implements View.OnClickListener {

    private ArrayList<String> listTitle;
    ViewPager viewPager;
    TabLayout tabLayout;
    RelativeLayout rlToolbar, rlBack, rlCross;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_rules_main, container, false);

        listTitle = new ArrayList<>();

        Typeface tfEng = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Aladin_Regular.ttf");


        viewPager = frg.findViewById(R.id.frag_review_view_pgr);
        tabLayout = frg.findViewById(R.id.frag_review_tab_reviewTab);

        bindViews(frg);

        tabLayout.setVisibility(View.VISIBLE);

        listTitle.add(getString(R.string.rules));
        listTitle.add(getString(R.string.gameplay));
        listTitle.add(getString(R.string.extra_rules));
        listTitle.add(getString(R.string.challenge));


        displayFragments();

        init();
        return frg;
    }

    IBadgeUpdateListener mBadgeUpdateListener;

    void setToolbar() {

        try {
            mBadgeUpdateListener = (IBadgeUpdateListener) getActivity();
        } catch (ClassCastException castException) {
            castException.printStackTrace(); // The activity does not implement the listener
        }
        if (getActivity() != null && isAdded()) {
            mBadgeUpdateListener.setToolbarState(AppConstt.INTRO_ToolbarStates.TOOLBAR_HIDDEN);

        }

    }

    void init() {
        setToolbar();
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!isHidden()) {
            setToolbar();
        }
    }


    private void displayFragments() {
        viewPager.setOffscreenPageLimit(1);
        viewPager.setAdapter(buildAdapter());
        tabLayout.setupWithViewPager(viewPager);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                if (position == 1) {
//                    rlRate.setVisibility(View.GONE);
//                } else {
//                    rlRate.setVisibility(View.VISIBLE);
//
//                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void bindViews(View frg) {
        rlCross = frg.findViewById(R.id.act_intro_lay_toolbar_rlCross);

        rlCross.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.act_intro_lay_toolbar_rlCross:
                ((IntroActivity) getActivity()).onBackPressed();
                break;
        }
    }


    private PagerAdapter buildAdapter() {
        return (new RulesViewPagerAdapter(getActivity(), getChildFragmentManager(), listTitle));
    }


}
