package com.armoomragames.denketa.IntroAuxilaries;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.armoomragames.denketa.AppConfig;
import com.armoomragames.denketa.IntroActivity;
import com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.GameSession.InvestigatorFragment;
import com.armoomragames.denketa.IntroAuxilaries.RulesAuxilaries.RulesFragment;
import com.armoomragames.denketa.IntroAuxilaries.SettingsAuxillaries.SignInFragment;
import com.armoomragames.denketa.IntroAuxilaries.WebServices.Intro_WebHit_Get_GameCredits;
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.IBadgeUpdateListener;
import com.armoomragames.denketa.Utils.IWebCallback;
//import com.braintreepayments.api.BraintreeClient;
//import com.braintreepayments.api.BrowserSwitchResult;
//import com.braintreepayments.api.Card;
//import com.braintreepayments.api.CardClient;
//import com.braintreepayments.api.DropInResult;
//import com.braintreepayments.api.PayPalCheckoutRequest;
//import com.braintreepayments.api.PayPalClient;
//import com.braintreepayments.api.PayPalPaymentIntent;
//import com.braintreepayments.api.PayPalVaultRequest;


public class PreSignInFragment extends Fragment implements View.OnClickListener {
    RelativeLayout rlToolbar, rlBack, rlCross;
    RelativeLayout rlPlay, rlDenketa, rlRules, rlSettings, rlDictionary;
    ImageView imv_master, imv_master_hat;
    Dialog dialog;
    IBadgeUpdateListener mBadgeUpdateListener;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_pre_sign_in, container, false);
        init();
        bindViews(frg);
        requestGameCredits();
        return frg;
    }


    private void myTokenizePayPalAccountWithCheckoutMethod() {

//        PayPalCheckoutRequest request = new PayPalCheckoutRequest("0.01");
//        request.setCurrencyCode("USD");
//        request.setIntent(PayPalPaymentIntent.AUTHORIZE);
//        payPalClient.tokenizePayPalAccount(getActivity(), request, (error) -> {
//            if (error != null) {
//                // Handle error
//            }
//            else{
//                if (error != null)
//                Log.d("DROP_IN_REQUEST_CODE","Error: "+ error.toString());
//            }
//
//
//        });


    }

    private void myTokenizePayPalAccountWithVaultMethod() {
//        PayPalVaultRequest request = new PayPalVaultRequest();
//        request.setBillingAgreementDescription("Your agreement description");
//
//        payPalClient.tokenizePayPalAccount(getActivity(), request, (error) -> {
//            if (error != null) {
//                // Handle error
//            }
//        });
    }
    private void tokenizeCard() {
//        Card card = new Card();
//        card.setNumber("5555555555554444");
//        card.setExpirationDate("12/2026");
//
//        cardClient.tokenize(card, (cardNonce, error) -> {
//            if (cardNonce != null) {
//                // send this nonce to your server
//                String nonce = cardNonce.getString();
//            } else {
//                // handle error
//            }
//        });
    }



    //region init
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

    private void bindViews(View frg) {
        rlToolbar = frg.findViewById(R.id.act_intro_rl_toolbar);
        rlBack = frg.findViewById(R.id.act_intro_lay_toolbar_rlBack);
        rlCross = frg.findViewById(R.id.act_intro_lay_toolbar_rlCross);
        rlPlay = frg.findViewById(R.id.frg_presigin_rlPlay);
        rlDenketa = frg.findViewById(R.id.frg_presigin_rlDenketa);
        rlRules = frg.findViewById(R.id.frg_presigin_rlRules);
        rlSettings = frg.findViewById(R.id.frg_presigin_rlSettings);
        rlDictionary = frg.findViewById(R.id.frg_presigin_rldictionary);
        imv_master = frg.findViewById(R.id.imv_master);
        imv_master_hat = frg.findViewById(R.id.imv_master_hat);


        rlToolbar.setVisibility(View.GONE);
        rlBack.setOnClickListener(this);
        rlCross.setOnClickListener(this);
        rlPlay.setOnClickListener(this);
        rlDenketa.setOnClickListener(this);
        rlRules.setOnClickListener(this);
        rlSettings.setOnClickListener(this);
        rlDictionary.setOnClickListener(this);
        imv_master.setOnClickListener(this);
        imv_master_hat.setOnClickListener(this);

    }




    //region Onclicks
    public void openDialoguePlay() {

        dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialogue_play_as);
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        LinearLayout txvRules = dialog.findViewById(R.id.lay_item_play_txvRules);
        LinearLayout txvMaster = dialog.findViewById(R.id.lay_item_play_txvMaster);
        LinearLayout txvInvestigator = dialog.findViewById(R.id.lay_item_play_txvInvestigator);
        RelativeLayout rlCross = dialog.findViewById(R.id.lay_item_play_rlCross);


        txvInvestigator.setOnClickListener(this);
        txvMaster.setOnClickListener(this);
        txvRules.setOnClickListener(this);
        rlCross.setOnClickListener(this);

        dialog.show();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.frg_presigin_rlPlay:
                openDialoguePlay();
                break;

            case R.id.lay_item_play_txvRules:

                dialog.dismiss();
                navToRulesFragment();
                break;

            case R.id.lay_item_play_txvInvestigator:


                dialog.dismiss();
                navToInvestigatorFragment();
                break;
            case R.id.lay_item_play_txvMaster:

                dialog.dismiss();
                navToPlayFragment();
                break;
            case R.id.lay_item_play_rlCross:

                dialog.dismiss();

                break;

            case R.id.frg_presigin_rlDenketa:
                navToDenketaWhatFragment();
                break;

            case R.id.frg_presigin_rlRules:
                navToRulesFragment();
                break;
            case R.id.frg_presigin_rldictionary:
                navToDictionaryFragment();
                break;

            case R.id.frg_presigin_rlSettings:
                navToSettingsFragment();
                break;


            case R.id.imv_master:
                hideMaster();

                break;


            case R.id.imv_master_hat:
                showMaster();
                break;

            case R.id.act_intro_lay_toolbar_rlBack:
                getActivity().onBackPressed();

                break;
            case R.id.act_intro_lay_toolbar_rlCross:
                ((IntroActivity) getActivity()).navToPreSignInVAFragment();

                break;
        }
    }

    private void hideMaster() {
        Animation Upbottom = AnimationUtils.loadAnimation(getContext(), R.anim.bottom_down);
        imv_master.startAnimation(Upbottom);
        imv_master.setVisibility(View.GONE);
//        imv_master_hat.setVisibility(View.VISIBLE);

        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            imv_master_hat.setVisibility(View.VISIBLE);

        }, 1000);
    }

    private void showMaster() {
        Animation bottomUp = AnimationUtils.loadAnimation(getContext(), R.anim.bottom_up);
        imv_master_hat.setVisibility(View.GONE);
        imv_master.setVisibility(View.VISIBLE);
    }
    //endregion

    //region Navigations
    private void navToInvestigatorFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new InvestigatorFragment();
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_InvestigatorFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_InvestigatorFragment);
//        ft.hide(this);
        ft.hide(this);
        ft.commit();

    }

    private void navToDenketaWhatFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new WhatDenketaFragment();
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_WhatDenketaFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_WhatDenketaFragment);
        ft.hide(this);
        ft.commit();
    }

    public void navToPlayFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new PlayMianFragment();
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.PlayMianFragment);
        ft.addToBackStack(AppConstt.FragTag.PlayMianFragment);
//        ft.hide(this);
        ft.hide(this);
        ft.commit();

    }

    private void navToRulesFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new RulesFragment();
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_RulesFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_RulesFragment);
        ft.hide(this);
        ft.commit();
    }

    private void navToDictionaryFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new DictionaryFragment();
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_DictionaryFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_DictionaryFragment);
        ft.hide(this);
        ft.commit();
    }

    private void navToSettingsFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new SettingsFragment();
//        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
//                R.anim.enter_from_left, R.anim.exit_to_right);//not required
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_SettingsFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_SettingsFragment);
        ft.hide(this);
        ft.commit();
    }

    private void navtoSigninFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new SignInFragment();
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_SiginInFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_SiginInFragment);
        ft.hide(this);
        ft.commit();
    }
    //endregion

    private void requestGameCredits() {
        Intro_WebHit_Get_GameCredits intro_webHit_get_gameCredits = new Intro_WebHit_Get_GameCredits();
        intro_webHit_get_gameCredits.getGameCredits(new IWebCallback() {
            @Override
            public void onWebResult(boolean isSuccess, String strMsg) {
                if (isSuccess) {
                    AppConfig.getInstance().mUser.GameCredits = Intro_WebHit_Get_GameCredits.responseObject.getData().getUserCredits().getCredits() + "";
                    AppConfig.getInstance().mUser.DanetkaPurchased = Intro_WebHit_Get_GameCredits.responseObject.getData().getUserCredits().getDanetkasPurchased() + "";
                    AppConfig.getInstance().mUser.DanetkaPlayed = Intro_WebHit_Get_GameCredits.responseObject.getData().getUserCredits().getDanetkasPlayed() + "";
                    AppConfig.getInstance().mUser.DanetkaTotal = Intro_WebHit_Get_GameCredits.responseObject.getData().getToatalDanetkas() + "";
                    AppConfig.getInstance().saveUserProfile();

                } else {
                }

            }

            @Override
            public void onWebException(Exception ex) {

            }
        });
    }


}
