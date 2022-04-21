package com.armoomragames.denketa.IntroAuxilaries.RulesAuxilaries;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.armoomragames.denketa.IntroActivity;
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.IBadgeUpdateListener;

public class ExtraRulesFragment extends Fragment implements View.OnClickListener {

    TextView txv_title;
    TextView txv_Desc1;
    TextView txv_Desc2;
    TextView txv_Message;
    RelativeLayout rlToolbar, rlBack, rlCross;
    IBadgeUpdateListener mBadgeUpdateListener;
    RelativeLayout rlRules, rlExtraRules, rlGameplay, rlChallenge;

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

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_extra_rules, container, false);
        init();
        bindViews(frg);

        initData();


        return frg;
    }

    private void initData()
    {
        String str_firstText = getColoredSpanned(getContext().getString(R.string.frg_extra_desc), "#1A5876");
        String str_coloredText = getColoredSpanned("(or maybe not)", "#9D3630");
        txv_title.setText(Html.fromHtml(str_firstText + " " + str_coloredText + "."));

        ///////////////
        str_firstText = getColoredSpanned(getContext().getString(R.string.frg_extra_desc2), "#000000");
        str_coloredText = getColoredSpanned(" Mr. Rigoletto", "#9D3630");
        txv_Desc1.setText(Html.fromHtml(str_firstText + " <b>" + str_coloredText + "</b> ."));

        ///////////////
        str_firstText = getColoredSpanned(getContext().getString(R.string.frg_extra_desc_3_01), "#000000");
        String str_lastText = getColoredSpanned(getContext().getString(R.string.frg_extra_desc_3_02), "#000000");
        str_coloredText = getColoredSpanned("picture", "#9D3630");
        String str_coloredText_2 = getColoredSpanned("title", "#9D3630");
        txv_Desc2.setText(Html.fromHtml(str_firstText + " <b>" + str_coloredText  + "</b> and the  <b>" +str_coloredText_2 + "</b> "+ str_lastText));


        ///////////////
        String strPara1 =getContext().getString(R.string.frg_extra_message_4_01);
        String strPara2 =getContext().getString(R.string.frg_extra_message_4_02);
        String completedStringPara1 = strPara1 +"<b>" +  " ONLY" + "</b> ";
        String completedStringPara2 = " correct answer is the one provided in the app. The Master "+"<b>" + " MUST " + "</b> " +strPara2;
        txv_Message.setText(Html.fromHtml(completedStringPara1 + completedStringPara2));

    }

    private String getColoredSpanned(String text, String color) {
        String input = "<font color=" + color + ">" + text + "</font>";
        return input;
    }

    private void bindViews(View frg) {
        rlCross = frg.findViewById(R.id.act_intro_lay_toolbar_rlCross);
        rlCross.setOnClickListener(this);

        txv_title = frg.findViewById(R.id.frg_extra_rules_txvtitle);
        txv_Desc1 = frg.findViewById(R.id.frg_extra_rules_txvDescription2);
        txv_Desc2 = frg.findViewById(R.id.frg_extra_rules_txvDescription3);
        txv_Message = frg.findViewById(R.id.frg_extra_rules_txvMessage);

        rlRules = frg.findViewById(R.id.rules);
        rlExtraRules = frg.findViewById(R.id.extra_rules);
        rlGameplay = frg.findViewById(R.id.gameplay);
        rlChallenge = frg.findViewById(R.id.challenge);

        rlRules.setOnClickListener(this);
        rlExtraRules.setOnClickListener(this);
        rlGameplay.setOnClickListener(this);
        rlChallenge.setOnClickListener(this);
    }

    private static final String KEY_POSITION = "position";

    public static Fragment newInstance(int position) {
        Fragment frag = new ExtraRulesFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_POSITION, position);
        frag.setArguments(args);
        return (frag);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.act_intro_lay_toolbar_rlCross:
                ((IntroActivity) getActivity()).navToPreSignInVAFragment();
                break;
            case R.id.rules:
                navToRulesFragment();
                break;
//            case R.id.extra_rules:
//                navToExtraRulesFragment();
//                break;
            case R.id.gameplay:
                navToGameplayFragment();
                break;
            case R.id.challenge:
                navToChallengeFragment();
                break;
        }
    }


    private void navToRulesFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new RulesFragment();
        ft.replace(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_RulesMianFragment);
//        ft.addToBackStack(AppConstt.FragTag.FN_RulesMianFragment);
        ft.hide(this);
        ft.commit();
    }

    private void navToExtraRulesFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new ExtraRulesFragment();
        ft.replace(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_ExtraRulesFragment);
//        ft.addToBackStack(AppConstt.FragTag.FN_RulesMianFragment);
        ft.hide(this);
        ft.commit();
    }

    private void navToGameplayFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new GamePlayFragment();
        ft.replace(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_GamePlayFragment);
//        ft.addToBackStack(AppConstt.FragTag.FN_RulesMianFragment);
        ft.hide(this);
        ft.commit();
    }

    private void navToChallengeFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new ChallengeFragment();
        ft.replace(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_ChallengeFragment);
//        ft.addToBackStack(AppConstt.FragTag.FN_ChallengeFragment);
        ft.hide(this);
        ft.commit();
    }


//    private void navToRulesFragment() {
//        FragmentManager fm = getFragmentManager();
//        FragmentTransaction ft = fm.beginTransaction();
//        Fragment frag = new RulesFragment();
//        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_RulesMianFragment);
////        ft.addToBackStack(AppConstt.FragTag.FN_RulesMianFragment);
//        ft.hide(this);
//        ft.commit();
//    }
//
//    private void navToExtraRulesFragment() {
//        FragmentManager fm = getFragmentManager();
//        FragmentTransaction ft = fm.beginTransaction();
//        Fragment frag = new ExtraRulesFragment();
//        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_ExtraRulesFragment);
////        ft.addToBackStack(AppConstt.FragTag.FN_RulesMianFragment);
//        ft.hide(this);
//        ft.commit();
//    }
//
//    private void navToGameplayFragment() {
//        FragmentManager fm = getFragmentManager();
//        FragmentTransaction ft = fm.beginTransaction();
//        Fragment frag = new GamePlayFragment();
//        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_GamePlayFragment);
////        ft.addToBackStack(AppConstt.FragTag.FN_RulesMianFragment);
//        ft.hide(this);
//        ft.commit();
//    }
//
//    private void navToChallengeFragment() {
//        FragmentManager fm = getFragmentManager();
//        FragmentTransaction ft = fm.beginTransaction();
//        Fragment frag = new ChallengeFragment();
//        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_ChallengeFragment);
////        ft.addToBackStack(AppConstt.FragTag.FN_ChallengeFragment);
//        ft.hide(this);
//        ft.commit();
//    }


}
