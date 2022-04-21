package com.armoomragames.denketa.IntroAuxilaries.RulesAuxilaries;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.armoomragames.denketa.IntroActivity;
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.IBadgeUpdateListener;

public class ChallengeFragment extends Fragment implements View.OnClickListener {

    RelativeLayout rlRules, rlExtraRules, rlGameplay, rlChallenge;
    RelativeLayout rlToolbar, rlBack, rlCross;
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

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_challenge, container, false);
        init();
        bindviews(frg);

        return frg;
    }

    private void bindviews(View frg) {
        rlCross = frg.findViewById(R.id.act_intro_lay_toolbar_rlCross);

        rlCross.setOnClickListener(this);
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
        Fragment frag = new ChallengeFragment();
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
            case R.id.extra_rules:
                navToExtraRulesFragment();
                break;
            case R.id.gameplay:
                navToGameplayFragment();
                break;
//            case R.id.challenge:
//                navToChallengeFragment();
//                break;
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
