package com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.Results;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.app.ShareCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.armoomragames.denketa.AppConfig;
import com.armoomragames.denketa.IntroActivity;
import com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.BundleDiscountFragment;
import com.armoomragames.denketa.IntroAuxilaries.WebServices.Intro_WebHit_Get_GameCredits;
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.IWebCallback;

public class MyProfileFragment extends Fragment implements View.OnClickListener {
    RelativeLayout rlToolbar, rlBack, rlCross;

    TextView txvDanetkaUnclocked, txvDanetkaLocked, txvDanetkaPlayed, txAvailableCredits, txvEditProfile, txvUsername, txvGetMore;
    LinearLayout frg_restPass_llConfirm;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_my_profile, container, false);

        init();
        bindViewss(frg);

        return frg;
    }

    private void init() {
        requestGameCredits();
    }

    private void requestGameCredits() {


        Intro_WebHit_Get_GameCredits intro_webHit_get_gameCredits = new Intro_WebHit_Get_GameCredits();
        intro_webHit_get_gameCredits.getGameCredits(new IWebCallback() {
            @Override
            public void onWebResult(boolean isSuccess, String strMsg) {
                if (isSuccess) {

                    int danetkaPurchased =3;
                    Intro_WebHit_Get_GameCredits.responseObject.getData().getUserCredits().getDanetkasPurchased();

                    if (Intro_WebHit_Get_GameCredits.responseObject.getData().getUserCredits().getDanetkasPurchased()==0)
                    {
                        danetkaPurchased = 3;
                    }
                    else {
                        danetkaPurchased+=Intro_WebHit_Get_GameCredits.responseObject.getData().getUserCredits().getDanetkasPurchased();
                    }




                    AppConfig.getInstance().mUser.GameCredits = Intro_WebHit_Get_GameCredits.responseObject.getData().getUserCredits().getCredits() + "";
                    AppConfig.getInstance().mUser.DanetkaPurchased = danetkaPurchased + "";
                    AppConfig.getInstance().mUser.DanetkaPlayed = Intro_WebHit_Get_GameCredits.responseObject.getData().getUserCredits().getDanetkasPlayed() + "";
                    AppConfig.getInstance().mUser.DanetkaTotal = Intro_WebHit_Get_GameCredits.responseObject.getData().getToatalDanetkas() + "";
                    AppConfig.getInstance().saveUserProfile();
                    int unlock = Integer.parseInt(AppConfig.getInstance().mUser.getDanetkaPurchased());
                    int lock = Integer.parseInt(AppConfig.getInstance().mUser.getDanetkaTotal());
                    txvUsername.setText(AppConfig.getInstance().mUser.getName());
                    txAvailableCredits.setText(AppConfig.getInstance().mUser.getGameCredits());
                    txvDanetkaLocked.setText((lock - danetkaPurchased) + "");
                    txvDanetkaUnclocked.setText(danetkaPurchased+"");
                    txvDanetkaPlayed.setText(AppConfig.getInstance().mUser.DanetkaPlayed);
                } else {
                }

            }

            @Override
            public void onWebException(Exception ex) {

            }
        });

    }

    private void bindViewss(View frg) {
        rlToolbar = frg.findViewById(R.id.act_intro_rl_toolbar);
        rlBack = frg.findViewById(R.id.act_intro_lay_toolbar_rlBack);
        rlCross = frg.findViewById(R.id.act_intro_lay_toolbar_rlCross);


        txAvailableCredits = frg.findViewById(R.id.txvDanetkaAvailable);
        txvDanetkaLocked = frg.findViewById(R.id.txvDanetkalocked);
        txvDanetkaUnclocked = frg.findViewById(R.id.txvDanetkaUnclocked);
        txvDanetkaPlayed = frg.findViewById(R.id.txvDanetkaPlayed);
        txvUsername = frg.findViewById(R.id.txvUsername);
        txvEditProfile = frg.findViewById(R.id.txvEditProfile);
        txvGetMore = frg.findViewById(R.id.txvGetMore);
        frg_restPass_llConfirm = frg.findViewById(R.id.frg_restPass_llConfirm);

        rlBack.setOnClickListener(this);
        rlCross.setOnClickListener(this);
        txvEditProfile.setOnClickListener(this);
        txvGetMore.setOnClickListener(this);
        frg_restPass_llConfirm.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.act_intro_lay_toolbar_rlBack:
            case R.id.txvEditProfile:
                ((IntroActivity) getActivity()).onBackPressed();

                break;
            case R.id.act_intro_lay_toolbar_rlCross:
                ((IntroActivity) getActivity()).navToPreSignInVAFragment();

                break;
            case R.id.txvGetMore:
                navToBundleDiscountFragment("0");

                break;


            case R.id.frg_restPass_llConfirm:
                ShareCompat.IntentBuilder.from(getActivity())
                        .setType("text/plain")
                        .setChooserTitle("Armoomra Games")
                        .setText("http://play.google.com/store/apps/details?id=" + getActivity().getPackageName())
                        .startChooser();
                break;
        }
    }

    private void navToBundleDiscountFragment(String danetka_danetkaID) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new BundleDiscountFragment();
//        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
//                R.anim.enter_from_left, R.anim.exit_to_right);//not required
        Bundle bundle = new Bundle();

        bundle.putString("key_danetka_danetkaID", danetka_danetkaID);
        frag.setArguments(bundle);
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_BundleDiscountFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_BundleDiscountFragment);
        ft.hide(this);
        ft.commit();
    }

}
