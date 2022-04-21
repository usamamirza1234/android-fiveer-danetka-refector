package com.armoomragames.denketa.IntroAuxilaries.RulesAuxilaries;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.armoomragames.denketa.IntroActivity;
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.IBadgeUpdateListener;

public class GamePlayFragment extends Fragment implements View.OnClickListener {

    Dialog dialog;
    RelativeLayout frg_rlFalse;
    RelativeLayout frg_rlSepecify;
    RelativeLayout rlRules, rlExtraRules, rlGameplay, rlChallenge;
    IBadgeUpdateListener mBadgeUpdateListener;
    RelativeLayout rlToolbar, rlBack, rlCross;
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
        View frg = inflater.inflate(R.layout.fragment_game_play, container, false);
        init();
        bindViews(frg);

        return frg;
    }

    private static final String KEY_POSITION = "position";

    public static Fragment newInstance(int position) {
        Fragment frag = new GamePlayFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_POSITION, position);
        frag.setArguments(args);
        return (frag);
    }
    private void bindViews(View frg) {

        rlCross = frg.findViewById(R.id.act_intro_lay_toolbar_rlCross);

        rlCross.setOnClickListener(this);
        frg_rlFalse = frg.findViewById(R.id.frg_rlFalse);
        frg_rlSepecify = frg.findViewById(R.id.frg_rlSepecify);

        frg_rlFalse.setOnClickListener(this);
        frg_rlSepecify.setOnClickListener(this);
        rlRules = frg.findViewById(R.id.rules);
        rlExtraRules = frg.findViewById(R.id.extra_rules);
        rlGameplay = frg.findViewById(R.id.gameplay);
        rlChallenge = frg.findViewById(R.id.challenge);

        rlRules.setOnClickListener(this);
        rlExtraRules.setOnClickListener(this);
        rlGameplay.setOnClickListener(this);
        rlChallenge.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.act_intro_lay_toolbar_rlCross:
                ((IntroActivity) getActivity()).navToPreSignInVAFragment();
                break;
            case R.id.frg_rlFalse:
                onClickFalse();
                break;
            case R.id.frg_rlSepecify:

                onClickSpecify();
                break;

            case R.id.rules:
                navToRulesFragment();
                break;
            case R.id.extra_rules:
                navToExtraRulesFragment();
                break;
//            case R.id.gameplay:
//                navToGameplayFragment();
//                break;
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


    public void onClickSpecify() {

        dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialogue_specify);
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        LinearLayout llOkay = dialog.findViewById(R.id.llParent);

        llOkay.setOnClickListener(this);
        llOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private String getColoredSpanned(String text, String color) {
        String input = "<font color=" + color + ">" + text + "</font>";
        return input;
    }

    public void onClickFalse() {

        dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialogue_false);
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        LinearLayout llOkay = dialog.findViewById(R.id.llParent);
        TextView txv_title = dialog.findViewById(R.id.txv_title);

        String str_firstText = getColoredSpanned(getContext().getString(R.string.frg_popup), "#1A5876");
        String str_coloredText = getColoredSpanned("Does\n he play an instrument?", "#9D3630");
        txv_title.setText(Html.fromHtml(str_firstText + " " + str_coloredText + "”"));

        llOkay.setOnClickListener(this);
        llOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


}
