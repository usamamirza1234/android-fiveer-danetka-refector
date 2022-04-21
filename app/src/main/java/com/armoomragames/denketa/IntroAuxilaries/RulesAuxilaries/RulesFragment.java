package com.armoomragames.denketa.IntroAuxilaries.RulesAuxilaries;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
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

public class RulesFragment extends Fragment implements View.OnClickListener {


    TextView txvDesc1, txvDesc2;
    TextView txvGameStart;
    TextView txvInvestigator;
    RelativeLayout rlToolbar, rlBack, rlCross;

    RelativeLayout rlRules, rlExtraRules, rlGameplay, rlChallenge;

    private static final String KEY_POSITION = "position";

    public static Fragment newInstance(int position) {
        Fragment frag = new RulesFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_POSITION, position);
        frag.setArguments(args);
        return (frag);
    }

    IBadgeUpdateListener mBadgeUpdateListener;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_rules, container, false);
        init();

        bindViews(frg);

        return frg;
    }

    private void bindViews(View frg) {
        rlCross = frg.findViewById(R.id.act_intro_lay_toolbar_rlCross);

        rlCross.setOnClickListener(this);

        txvDesc1 = (TextView) frg.findViewById(R.id.frg_rules_txvDescription1);
        txvDesc2 = (TextView) frg.findViewById(R.id.frg_rules_txvDescription2);
        txvGameStart = (TextView) frg.findViewById(R.id.frg_rules_txvGameStart);
        txvInvestigator = (TextView) frg.findViewById(R.id.frg_rules_txvInvestigator1);

        rlRules = frg.findViewById(R.id.rules);
        rlExtraRules = frg.findViewById(R.id.extra_rules);
        rlGameplay = frg.findViewById(R.id.gameplay);
        rlChallenge = frg.findViewById(R.id.challenge);

        rlRules.setOnClickListener(this);
        rlExtraRules.setOnClickListener(this);
        rlGameplay.setOnClickListener(this);
        rlChallenge.setOnClickListener(this);

        try {


            String desc = getString(R.string.frg_rules_desc);
            String desc1 = getString(R.string.frg_rules_desc2);
            SpannableString ss = new SpannableString(desc);
            StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
            ss.setSpan(boldSpan, 166, 177, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            txvDesc1.setText(ss);

            ss = new SpannableString(desc1);
            boldSpan = new StyleSpan(Typeface.BOLD);
            ss.setSpan(boldSpan, 131, 138, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            txvDesc2.setText(ss);


            String strGame = getString(R.string.frg_rules_game_start);
            ss = new SpannableString(strGame);
            boldSpan = new StyleSpan(Typeface.BOLD);
            ss.setSpan(boldSpan, 1, 16, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            txvGameStart.setText(ss);


            String strinvestigator2 = getString(R.string.frg_rules_investigator2);
            ss = new SpannableString(strinvestigator2);
            boldSpan = new StyleSpan(Typeface.BOLD);
            ss.setSpan(boldSpan, 24, 34, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            txvInvestigator.setText(ss);
        } catch (Exception e) {

        }

    }

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

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
//            case R.id.rules:
//                navToRulesFragment();
//                break;

            case R.id.act_intro_lay_toolbar_rlCross:
                ((IntroActivity) getActivity()).navToPreSignInVAFragment();
                break;
            case R.id.extra_rules:
                navToExtraRulesFragment();
                break;
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


}
