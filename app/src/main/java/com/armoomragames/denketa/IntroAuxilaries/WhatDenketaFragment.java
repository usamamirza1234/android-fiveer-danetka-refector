package com.armoomragames.denketa.IntroAuxilaries;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.armoomragames.denketa.IntroActivity;
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.IBadgeUpdateListener;

public class WhatDenketaFragment extends Fragment implements View.OnClickListener {
    TextView txv_Desc, txv_Desc2;

    RelativeLayout rlToolbar, rlBack, rlCross;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_denketa_what, container, false);

        init();
        bindViews(frg);


        String strPara1 = "is a quiz cooperative game where you need to find stories based on a hint and a picture. You don’t need to  be a musician. You just need to think critically and make smart questions. Even if you have a PhD in music history, you will still discover new fascinating things since many stories are not widely known. And even if you miraculously do know all the stories, the enigmatic hints will still make the game interesting.";
        String strPara2 = "you will find real stories from classical composer’s lives, learn musical facts, instruments and also how to behave at a concert. Find out how a balloon inspired people to learn music, how a dead composer made a woman poison herself or how a symphony sent everyone on holidays. You will end up knowing facts about classical music and be able to impress your friends with fun anecdotes. You will find a whole  new world, where classical music is interesting, weird and unexpected. Don’t hesitate to go online and listen to the music you just learnt about.\n";

        String completedStringPara1 = "\t \t <b>" + "Danetkas - musical stories" + "</b> " +strPara1;
        String completedStringPara2 = "\t \t"+"In<b>" + " Danetkas" + "</b> " +strPara2;

        txv_Desc.setText(Html.fromHtml(completedStringPara1));
        txv_Desc2.setText(Html.fromHtml(completedStringPara2));

        return frg;
    }

    private void bindViews(View frg) {
        txv_Desc = frg.findViewById(R.id.frg_what_denketa_txvDesc);
        txv_Desc2 = frg.findViewById(R.id.frg_what_denketa_txvDesc2);
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

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.act_intro_lay_toolbar_rlBack:
                ((IntroActivity)getActivity()).  onBackPressed();

                break;
            case R.id.act_intro_lay_toolbar_rlCross:
                ((IntroActivity)getActivity()). navToPreSignInVAFragment();

                break;
        }
    }
}
