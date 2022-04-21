package com.armoomragames.denketa.IntroAuxilaries;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.armoomragames.denketa.AppConfig;
import com.armoomragames.denketa.IntroActivity;
import com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.PlayViewPagerAdapter;
import com.armoomragames.denketa.IntroAuxilaries.RulesAuxilaries.RulesViewPagerAdapter;
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.IBadgeUpdateListener;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class PlayMianFragment extends Fragment implements View.OnClickListener {
    RelativeLayout rlToolbar, rlBack, rlCross;
    private ArrayList<String> listTitle;
    ViewPager viewPager;
    TabLayout tabLayout;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_play_main, container, false);

        listTitle = new ArrayList<>();

        Typeface tfEng = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Aladin_Regular.ttf");


        viewPager = frg.findViewById(R.id.frag_review_view_pgr);
        tabLayout = frg.findViewById(R.id.frag_review_tab_reviewTab);

        bindViews(frg);

        tabLayout.setVisibility(View.VISIBLE);

        listTitle.add(getString(R.string.my_denketa));
        listTitle.add(getString(R.string.more_denketa));
        if (AppConfig.getInstance().mUser.isLoggedIn())
            listTitle.add(getString(R.string.make_deneketa));


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
            mBadgeUpdateListener.setToolbarState(AppConstt.INTRO_ToolbarStates.TOOLBAR_BACK_HIDDEN);

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
        rlToolbar = frg.findViewById(R.id.act_intro_rl_toolbar);
        rlBack = frg.findViewById(R.id.act_intro_lay_toolbar_rlBack);
        rlCross = frg.findViewById(R.id.act_intro_lay_toolbar_rlCross);

        rlBack.setOnClickListener(this);
        rlCross.setOnClickListener(this);


        //        rlToolbar.setVisibility(View.GONE);
        rlToolbar.setVisibility(View.VISIBLE);
        rlBack.setVisibility(View.GONE);
        rlCross.setVisibility(View.VISIBLE);
//        rlCross.setVisibility(View.GONE);
//        rlBack.setVisibility(View.VISIBLE);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.act_intro_lay_toolbar_rlCross:
                ((IntroActivity) getActivity()).navToPreSignInVAFragment();

                break;

        }
    }


    private PagerAdapter buildAdapter() {
        return (new PlayViewPagerAdapter(getActivity(), getChildFragmentManager(), listTitle));
    }


}
