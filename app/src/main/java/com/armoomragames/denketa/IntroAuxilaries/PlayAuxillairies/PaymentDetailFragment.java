package com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
import com.armoomragames.denketa.IntroAuxilaries.WebServices.Intro_WebHit_Post_AddUserCredits;
import com.armoomragames.denketa.IntroAuxilaries.WebServices.Intro_WebHit_Post_AddUserDanetkas;
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.CustomToast;
import com.armoomragames.denketa.Utils.IWebCallback;
import com.armoomragames.denketa.Utils.RModel_Paypal;
//import com.braintreepayments.api.BraintreeClient;
//import com.braintreepayments.api.BrowserSwitchResult;
//import com.braintreepayments.api.Card;
//import com.braintreepayments.api.CardClient;
//import com.braintreepayments.api.PayPalCheckoutRequest;
//import com.braintreepayments.api.PayPalClient;
//import com.braintreepayments.api.PayPalPaymentIntent;
//import com.braintreepayments.api.PayPalVaultRequest;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;

public class PaymentDetailFragment extends Fragment implements View.OnClickListener {
    public static final String clientKey = "AQxyBWkhclOXBj9jlkr3eV_F9PQ2O6yBD5f8i1oO2fJNQ5Xy_Ir6N45881igN7lyfIPvxr59JSGnH0B1";
    private static final PayPalConfiguration config = new PayPalConfiguration()
            // Start with mock environment.
            // When ready, switch to sandbox (ENVIRONMENT_SANDBOX)
            // or live (ENVIRONMENT_PRODUCTION)
            .environment(PayPalConfiguration.ENVIRONMENT_NO_NETWORK)
            .clientId(clientKey).merchantName("Armoomra games")
            .acceptCreditCards(true)
            .defaultUserEmail("armoomragames@gamil.com")
            .rememberUser(true)
            .merchantPrivacyPolicyUri(Uri.parse("https://www.example.com/privacy"))
            .merchantUserAgreementUri(Uri.parse("https://www.example.com/legal"));
    RelativeLayout rlToolbar, rlBack, rlCross;
    RelativeLayout rlPaypal;
    RelativeLayout rlPaypalCredit;
    RelativeLayout rlUseGameCredits;
    LinearLayout getmore;
    Bundle bundle;
    String danetkaID = "";
    String sub_total = "";
    String totalAmount = "";
    String numberOfDanetka = "";
    String totalDiscount = "";
    boolean is_coming_from_bundle = false;
    GoogleSignInClient mGoogleSignInClient;
    TextView txvPaymentDescription;
    TextView txvUseGameCredits;
//    CardClient cardClient;
//    PayPalClient payPalClient;
    private Dialog progressDialog;
//    private BraintreeClient braintreeClient;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_payment, container, false);

        init();
        bindViewss(frg);

        if (is_coming_from_bundle)
            txvPaymentDescription.setText(numberOfDanetka + " Game Credits " + totalAmount + "€");
        else {
            {
                txvPaymentDescription.setText("1 Danetka 0,99€");
                numberOfDanetka = "1";
                totalAmount = "0.99";

            }

        }

        txvUseGameCredits.setText("Game Credits available -- " + AppConfig.getInstance().mUser.getGameCredits());
        return frg;
    }

    //region init
    private void init() {
        bundle = this.getArguments();
        if (bundle != null) {
            danetkaID = bundle.getString("key_danetka_danetkaID");
            is_coming_from_bundle = bundle.getBoolean("key_is_coming_from_bundle");
            sub_total = bundle.getString("key_danetka_sub_total");
            totalAmount = bundle.getString("key_danetka_total");
            numberOfDanetka = bundle.getString("key_danetka_number");
            totalDiscount = bundle.getString("key_danetka_discount");
        }
        paypalInit();

        braintreeInit();

    }

    private void braintreeInit() {
//        braintreeClient = new BraintreeClient(getContext(), "sandbox_v2nf5t6c_mybf9tq8g5qv92zw");
//        cardClient = new CardClient(braintreeClient);
//        payPalClient = new PayPalClient(braintreeClient);
    }
    //endregion

    private void bindViewss(View frg) {
        rlToolbar = frg.findViewById(R.id.act_intro_rl_toolbar);
        rlBack = frg.findViewById(R.id.act_intro_lay_toolbar_rlBack);
        rlCross = frg.findViewById(R.id.act_intro_lay_toolbar_rlCross);
        rlPaypal = frg.findViewById(R.id.rlPaypal);
        rlPaypalCredit = frg.findViewById(R.id.rlPaypalCredit);
        txvPaymentDescription = frg.findViewById(R.id.txvPaymentDescription);
        txvUseGameCredits = frg.findViewById(R.id.txvUseGameCredits);
        rlUseGameCredits = frg.findViewById(R.id.rlUseGameCredits);
        getmore = frg.findViewById(R.id.frg_getmore);

        rlBack.setOnClickListener(this);
        rlCross.setOnClickListener(this);
        rlPaypal.setOnClickListener(this);
        rlPaypalCredit.setOnClickListener(this);
        getmore.setOnClickListener(this);
        rlUseGameCredits.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.act_intro_lay_toolbar_rlBack:
                getActivity().onBackPressed();

                break;
            case R.id.act_intro_lay_toolbar_rlCross:
                ((IntroActivity) getActivity()).navToPreSignInVAFragment();

                break;
            case R.id.rlPaypal:
            case R.id.rlPaypalCredit:
                onBuyPressed(danetkaID);
//                initPayPalDropRequest();
                break;

            case R.id.frg_getmore:
                navToBundleDiscountFragment(danetkaID);
                break;

            case R.id.rlUseGameCredits:
                if (!AppConfig.getInstance().mUser.GameCredits.equalsIgnoreCase("0")) {
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("danetkasId", danetkaID);
                    requestAddUserDanetkas(jsonObject.toString());
                } else
                    CustomToast.showToastMessage(getActivity(), "Insufficient Game Credits Buy Now", Toast.LENGTH_SHORT);
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

    //region Paypal Callbacks Google Callbacks
    private void requestAddUserDanetkas(String _signUpEntity) {
        showProgDialog();
        Intro_WebHit_Post_AddUserDanetkas intro_webHit_post_addUserDanetkas = new Intro_WebHit_Post_AddUserDanetkas();
        intro_webHit_post_addUserDanetkas.postAddUserDanetkas(getActivity(), new IWebCallback() {
            @Override
            public void onWebResult(boolean isSuccess, String strMsg) {
                if (isSuccess) {
                    dismissProgDialog();
                    int gCredits = Integer.parseInt(AppConfig.getInstance().mUser.GameCredits);
                    AppConfig.getInstance().mUser.GameCredits = "" + (gCredits - 1);
                    txvUseGameCredits.setText("Game Credits available -- " + AppConfig.getInstance().mUser.getGameCredits());
                    AppConfig.getInstance().saveUserProfile();
                    ((IntroActivity) getActivity()).navToPreSignInVAFragment();
                } else {
                    dismissProgDialog();
                    CustomToast.showToastMessage(getActivity(), strMsg, Toast.LENGTH_SHORT);

                }
            }

            @Override
            public void onWebException(Exception ex) {
                dismissProgDialog();
                CustomToast.showToastMessage(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT);

            }
        }, _signUpEntity);
    }


    private void requestPostGameCredits(String _signUpEntity) {
        showProgDialog();
        navToPayentApprovedFragment(numberOfDanetka, totalAmount);
        Intro_WebHit_Post_AddUserCredits intro_webHit_post_addUserCredits = new Intro_WebHit_Post_AddUserCredits();
        intro_webHit_post_addUserCredits.postAddCredit(getActivity(), new IWebCallback() {
            @Override
            public void onWebResult(boolean isSuccess, String strMsg) {
                if (isSuccess) {
                    dismissProgDialog();
                    String gCredits = Intro_WebHit_Post_AddUserCredits.responseObject.getData().getGameCredits() + "";
                    AppConfig.getInstance().mUser.GameCredits = "" + (gCredits);
                    txvUseGameCredits.setText("Game Credits available -- " + AppConfig.getInstance().mUser.getGameCredits());
                    AppConfig.getInstance().saveUserProfile();
                    danetkaID = "0";

                } else {
                    dismissProgDialog();
                    CustomToast.showToastMessage(getActivity(), strMsg, Toast.LENGTH_SHORT);

                }
            }

            @Override
            public void onWebException(Exception ex) {
                dismissProgDialog();
                CustomToast.showToastMessage(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT);

            }
        }, _signUpEntity);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
            if (confirm != null) {
                try {
                    String strResponse = confirm.toJSONObject().toString(4);
                    Log.i("paymentExample", strResponse);
//                    CustomToast.showToastMessage(getActivity(), "Congragulations! you Paid for Danetka(s). ", Toast.LENGTH_SHORT);
                    Gson gson = new Gson();
                    Log.d("LOG_AS", "postSignIn: strResponse" + strResponse);
                    AppConfig.getInstance().responseObject = gson.fromJson(strResponse, RModel_Paypal.class);
                    if (
                            AppConfig.getInstance().responseObject != null &&
                                    AppConfig.getInstance().responseObject.getResponse() != null
                    ) {
                        if (AppConfig.getInstance().responseObject.getResponse().getState().equalsIgnoreCase("approved")) {
                            JsonObject jsonObject = new JsonObject();
//                            jsonObject.addProperty("danetkasId", danetkaID);
                            jsonObject.addProperty("gameCredits", numberOfDanetka);
                            jsonObject.addProperty("subtotal", sub_total);
                            jsonObject.addProperty("discount", totalDiscount);
                            jsonObject.addProperty("totalAmount", totalAmount);


                            requestPostGameCredits(jsonObject.toString());


                        }
                    }


                } catch (JSONException e) {
                    navToPayentDisapprovedFragment();
//                    CustomToast.showToastMessage(getActivity(), "an extremely unlikely failure occurred: " + e, Toast.LENGTH_SHORT);
                    Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                }
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Log.i("paymentExample", "The user canceled.");
            navToPayentDisapprovedFragment();
//            CustomToast.showToastMessage(getActivity(), "The user canceled.: ", Toast.LENGTH_SHORT);

        } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            navToPayentDisapprovedFragment();
//            CustomToast.showToastMessage(getActivity(), "An invalid Payment or PayPalConfiguration was submitted. Please see the docs. ", Toast.LENGTH_SHORT);
            Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
        }

    }

    public void onBuyPressed(String _danetkaID) {
        danetkaID = _danetkaID;
        // PAYMENT_INTENT_SALE will cause the payment to complete immediately.
        // Change PAYMENT_INTENT_SALE to
        //   - PAYMENT_INTENT_AUTHORIZE to only authorize payment and capture funds later.
        //   - PAYMENT_INTENT_ORDER to create a payment for authorization and capture
        //     later via calls from your server.

        PayPalPayment payment = new PayPalPayment(new BigDecimal("" + totalAmount), "EUR", numberOfDanetka + " Danetka(s)",
                PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(getActivity(), PaymentActivity.class);

        // send the same configuration for restart resiliency
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

        startActivityForResult(intent, 0);
    }


    //endregion

    private void paypalInit() {
        Intent intent = new Intent(getContext(), PayPalService.class);

        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        getActivity().startService(intent);
    }

    //region Navigation
    private void navToPayentDisapprovedFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new PaymentFailedFragment();
//        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
//                R.anim.enter_from_left, R.anim.exit_to_right);//not required
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_PaymentFailedFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_PaymentFailedFragment);
        ft.hide(this);
        ft.commit();
    }
    //endregion

    private void navToPayentApprovedFragment(String credit, String total) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new PaymentApprovedFragment();
        Bundle bundle = new Bundle();

        bundle.putString("key_danetka_credit", credit);
        bundle.putString("key_danetka_total", total);
        frag.setArguments(bundle);
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_PaymentApprovedFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_PaymentApprovedFragment);
        ft.hide(this);
        ft.commit();
    }

    //region progdialog
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





}
