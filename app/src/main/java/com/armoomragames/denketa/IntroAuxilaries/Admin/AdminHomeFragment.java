package com.armoomragames.denketa.IntroAuxilaries.Admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.armoomragames.denketa.AppConfig;
import com.armoomragames.denketa.IntroActivity;
import com.armoomragames.denketa.IntroAuxilaries.Admin.DanetkaDetails.DanetkaDetailsFragment;
import com.armoomragames.denketa.IntroAuxilaries.Admin.PromoCode.AddPromoFragment;
import com.armoomragames.denketa.IntroAuxilaries.Admin.PromoCode.PromoCodesFragment;
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.IBadgeUpdateListener;

public class AdminHomeFragment extends Fragment implements View.OnClickListener {


    RelativeLayout rlToolbar, rlBack, rlCross;
    RelativeLayout rlDenketaDetails, rlPromo;
    TextView txv_Nationality, txv_Gend, txvSignout, txv_dob;
    IBadgeUpdateListener mBadgeUpdateListener;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_admin, container, false);

        init();
        bindViews(frg);

        return frg;
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


    private void bindViews(View frg) {
        rlToolbar = frg.findViewById(R.id.act_intro_rl_toolbar);
        rlBack = frg.findViewById(R.id.act_intro_lay_toolbar_rlBack);
        rlCross = frg.findViewById(R.id.act_intro_lay_toolbar_rlCross);

        rlBack.setOnClickListener(this);
        rlCross.setOnClickListener(this);


        rlToolbar.setVisibility(View.VISIBLE);
        txvSignout = frg.findViewById(R.id.fg_signup_complete_txvSignOut);

        rlDenketaDetails = frg.findViewById(R.id.frg_admin_rlDenketaDetails);
        rlPromo = frg.findViewById(R.id.frg_admin_rlPromo);
        txvSignout.setOnClickListener(this);
        rlDenketaDetails.setOnClickListener(this);
        rlPromo.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.frg_admin_rlPromo:
                navtoAddPromoFragment();
                break;

            case R.id.frg_admin_rlDenketaDetails:
                navtoDanetkaDetailsFragment();
                break;
            case R.id.act_intro_lay_toolbar_rlBack:
                getActivity().onBackPressed();
                break;
            case R.id.act_intro_lay_toolbar_rlCross:
                ((IntroActivity) getActivity()).navToPreSignInVAFragment();
                break;
            case R.id.fg_signup_complete_txvSignOut:
                AppConfig.getInstance().navtoLogin();
                break;
        }
    }


    private void navtoAddPromoFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new PromoCodesFragment();
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_PromoCodesFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_PromoCodesFragment);
        ft.hide(this);
        ft.commit();
    }

    private void navtoDanetkaDetailsFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new DanetkaDetailsFragment();
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_DanetkaDetailsFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_DanetkaDetailsFragment);
        ft.hide(this);
        ft.commit();
    }

}
