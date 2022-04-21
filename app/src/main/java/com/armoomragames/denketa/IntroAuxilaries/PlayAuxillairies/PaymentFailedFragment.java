package com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies;

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

public class PaymentFailedFragment extends Fragment implements View.OnClickListener {

    RelativeLayout rlToolbar, rlBack, rlCross;
    TextView txvPaymentFailed;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_payment_failed, container, false);

        init();
        bindViewss(frg);

        return frg;
    }

    private void init() {

    }

    private void bindViewss(View frg) {
        txvPaymentFailed = frg.findViewById(R.id.txvPaymentFailed);
        rlToolbar = frg.findViewById(R.id.act_intro_rl_toolbar);
        rlBack = frg.findViewById(R.id.act_intro_lay_toolbar_rlBack);
        rlCross = frg.findViewById(R.id.act_intro_lay_toolbar_rlCross);

        txvPaymentFailed.setOnClickListener(this);
        rlBack.setOnClickListener(this);
        rlCross.setOnClickListener(this);
        String strFirst="There was a problem with your payment. Would you like to ";
        String strThird="try again?";
        String strHighlighted=  getColoredSpanned(strThird.toUpperCase(),getResources().getColor(R.color.thm_blue_app));
        txvPaymentFailed.setText(Html.fromHtml(strFirst+"<b> <u>" + strHighlighted +  " </u></b> "));
    }

    private String getColoredSpanned(String text, int color) {
        String input = "<font color=" + color + ">" + text + "</font>";
        return input;
    }




    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.txvPaymentFailed:
            case R.id.act_intro_lay_toolbar_rlBack:
                ((IntroActivity)getActivity()).  onBackPressed();

                break;
            case R.id.act_intro_lay_toolbar_rlCross:
                ((IntroActivity)getActivity()). navToPreSignInVAFragment();

                break;
        }
    }


}
