package com.armoomragames.denketa.Example;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.armoomragames.denketa.AppConfig;
import com.armoomragames.denketa.IntroActivity;
import com.armoomragames.denketa.IntroAuxilaries.Admin.AdminHomeFragment;
import com.armoomragames.denketa.IntroAuxilaries.SettingsAuxillaries.ForgotPasswordFragment;
import com.armoomragames.denketa.IntroAuxilaries.SettingsAuxillaries.SignInFragment;
import com.armoomragames.denketa.IntroAuxilaries.SettingsAuxillaries.SignUpCompleteProfileFragment;
import com.armoomragames.denketa.IntroAuxilaries.WebServices.Intro_WebHit_Post_LogIn;
import com.armoomragames.denketa.IntroAuxilaries.WebServices.Intro_WebHit_Post_SignUp;
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.CustomToast;
import com.armoomragames.denketa.Utils.IBadgeUpdateListener;
import com.armoomragames.denketa.Utils.IWebCallback;
//import com.braintreepayments.api.BraintreeClient;
//import com.braintreepayments.api.Card;
//import com.braintreepayments.api.CardClient;
import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class PaymentCardFragment extends Fragment implements View.OnClickListener {
    private static final int RC_SIGN_IN = 9001;
//    RelativeLayout rlToolbar, rlBack, rlCross;
    GoogleSignInClient mGoogleSignInClient;

    RelativeLayout rlRegister;
    GoogleSignInAccount acct, account;

    CallbackManager callbackManager;
    EditText  edtEmail, editConfirmPass;
    TextView txvMonth,txvYear;
    private Dialog progressDialog;
//    CardClient cardClient;
//
//    BraintreeClient braintreeClient;
    private String token ="";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_credit_card, container, false);
        init();

        bindViews(frg);
        return frg;
    }

    //region init
    void init() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            token = bundle.getString("key_danetka_danetkaID");

        }
//        braintreeClient = new BraintreeClient(getContext(), token);
    }

    private void tokenizeCard() {
        showProgDialog();
//        Card card = new Card();
//        card.setNumber("4111111111111111");
//        card.setExpirationDate("09/2028");
//
//        cardClient = new CardClient(braintreeClient);
//        cardClient.tokenize(card, (cardNonce, error) -> {
//            dismissProgDialog();
//            // send cardNonce.getString() to your server
//            CustomToast.showToastMessage(getActivity(),"Paid from card: " + cardNonce.getString(),Toast.LENGTH_LONG);
//            if (cardNonce !=null){
//
//
//            Log.d("mylog", "streetAddress: " + cardNonce.getCardholderName());
//            Log.d("mylog", "streetAddress: " + cardNonce.getCardType());
//            Log.d("mylog", "streetAddress: " + cardNonce.getLastFour());
//            Log.d("mylog", "streetAddress: " + cardNonce.getCardType());
//            Log.d("mylog", "streetAddress: " + cardNonce.getExpirationMonth());
//            Log.d("mylog", "streetAddress: " + cardNonce.getExpirationYear());
//            Log.d("mylog", "cardNonce.getString(): " + cardNonce.getString());
//            }
//        });
    }



    private void bindViews(View frg) {
//        rlToolbar = frg.findViewById(R.id.act_intro_rl_toolbar);
//        rlBack = frg.findViewById(R.id.act_intro_lay_toolbar_rlBack);
//        rlCross = frg.findViewById(R.id.act_intro_lay_toolbar_rlCross);
//
//        rlBack.setOnClickListener(this);
//        rlCross.setOnClickListener(this);
//        Animation shake;
//        shake = AnimationUtils.loadAnimation(getContext(), R.anim.shake);
//        TextView txvPlay = frg.findViewById(R.id.frg_presigin_txvPlay);
//        txvPlay.startAnimation(shake); // starts animation



        rlRegister = frg.findViewById(R.id.frg_my_account_rlRegister);
        editConfirmPass = frg.findViewById(R.id.frg_signup_edtcvc);
        edtEmail = frg.findViewById(R.id.frg_signup_edtcard);
        txvMonth = frg.findViewById(R.id.txvMonth);
        txvYear = frg.findViewById(R.id.txvYear);


        rlRegister.setOnClickListener(this);
        editTextWatchers();



        edtEmail.setText("4111111111111111");
        txvMonth.setText("09");
        txvYear.setText("2028");
        editConfirmPass.setText("123");
    }

    void setToolbar() {

    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!isHidden()) {
            setToolbar();
        }
    }

    //endregion

    //region ProgDialog
    private void dismissProgDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    private void showProgDialog() {

        progressDialog = new Dialog(getActivity(), R.style.AppTheme);
//        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.dialog_progress_loading);
        WindowManager.LayoutParams wmlp = progressDialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.CENTER | Gravity.CENTER;
        wmlp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wmlp.height = ViewGroup.LayoutParams.MATCH_PARENT;

        ImageView imageView = progressDialog.findViewById(R.id.img_anim);
        Glide.with(getContext()).asGif().load(R.raw.loading).into(imageView);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();


    }
    //endregion

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
//            case R.id.act_intro_lay_toolbar_rlBack:
//                getActivity().onBackPressed();
//
//                break;
//            case R.id.act_intro_lay_toolbar_rlCross:
//                ((IntroActivity) getActivity()).navToPreSignInVAFragment();

//                break;
            case R.id.frg_my_account_rlRegister:
                tokenizeCard();
                break;
        }
    }

    //region Validation
    private void editTextWatchers() {

        edtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().startsWith(" ")) {
                    edtEmail.setText("");
                }
            }
        });

        editConfirmPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().startsWith(" ")) {
                    editConfirmPass.setText("");
                }
            }
        });

    }

    private void closeKeyboard() {
        AppConfig.getInstance().closeKeyboard(getActivity());

    }


}