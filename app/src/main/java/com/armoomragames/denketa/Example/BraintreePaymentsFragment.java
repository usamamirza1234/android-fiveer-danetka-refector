package com.armoomragames.denketa.Example;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
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
import com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.BundleDiscountFragment;
import com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.PaymentApprovedFragment;
import com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.PaymentFailedFragment;
import com.armoomragames.denketa.IntroAuxilaries.WebServices.Intro_WebHit_Get_Token;
import com.armoomragames.denketa.IntroAuxilaries.WebServices.Intro_WebHit_Post_AddUserCredits;
import com.armoomragames.denketa.IntroAuxilaries.WebServices.Intro_WebHit_Post_Card;
import com.armoomragames.denketa.IntroAuxilaries.WebServices.Intro_WebHit_Post_Noice;
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.CustomToast;
import com.armoomragames.denketa.Utils.IWebCallback;
//import com.braintreepayments.api.BraintreeClient;
//import com.braintreepayments.api.BrowserSwitchResult;
//import com.braintreepayments.api.Card;
//import com.braintreepayments.api.CardClient;
//import com.braintreepayments.api.DataCollector;
//import com.braintreepayments.api.DropInResult;
//import com.braintreepayments.api.PayPalCheckoutRequest;
//import com.braintreepayments.api.PayPalClient;
//import com.braintreepayments.api.PayPalPaymentIntent;
//import com.braintreepayments.api.PaymentMethodNonce;
//import com.braintreepayments.api.PostalAddress;
import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;

public class BraintreePaymentsFragment extends Fragment implements View.OnClickListener {
    final int REQUEST_CODE = 1;
    String btToken = "sandbox_v2nf5t6c_mybf9tq8g5qv92zw";
    String btToken1 = "sandbox_f252zhq7_hh4cpc39zq4rgjcg";
//    BraintreeClient braintreeClient;
//    PayPalClient payPalClient;
//    CardClient cardClient;
//    DataCollector dataCollector;
    RelativeLayout rlPaypal;
    RelativeLayout rlPaypalCredit;
    boolean is_coming_from_bundle = false;
    LinearLayout getmore;
    TextView txvPaymentDescription, txvUseGameCredits;
    String danetkaID = "", sub_total = "", totalAmount = "", numberOfDanetka = "", totalDiscount = "";
    private Dialog progressDialog;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_payment, container, false);
        init();
        bindViews(frg);
        return frg;
    }


    private void init() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            danetkaID = bundle.getString("key_danetka_danetkaID");
            is_coming_from_bundle = bundle.getBoolean("key_is_coming_from_bundle");
            sub_total = bundle.getString("key_danetka_sub_total");
            totalAmount = bundle.getString("key_danetka_total");
            numberOfDanetka = bundle.getString("key_danetka_number");
            totalDiscount = bundle.getString("key_danetka_discount");
        }
        requestFeedback();
    }

    private void bindViews(View frg) {
        rlPaypal = frg.findViewById(R.id.rlPaypal);
        rlPaypalCredit = frg.findViewById(R.id.rlPaypalCredit);
        getmore = frg.findViewById(R.id.frg_getmore);
        txvPaymentDescription = frg.findViewById(R.id.txvPaymentDescription);
        txvUseGameCredits = frg.findViewById(R.id.txvUseGameCredits);

        getmore.setOnClickListener(this);
        rlPaypal.setOnClickListener(this);
        rlPaypalCredit.setOnClickListener(this);


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
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.rlPaypal:
                onPayPalButtonClick();
                break;
            case R.id.rlPaypalCredit:
                if (Intro_WebHit_Get_Token.responseObject.getData().getClientToken() !=null)
                    navToCardFragment(Intro_WebHit_Get_Token.responseObject.getData().getClientToken()) ;
                break;
            case R.id.frg_getmore:
                String danetkaID = "2";
                navToBundleDiscountFragment(danetkaID);
                break;


        }
    }

    private void navToCardFragment(String clientToken) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new PaymentCardFragment();
        Bundle bundle = new Bundle();
        bundle.putString("key_danetka_clientToken", clientToken);
        frag.setArguments(bundle);
        ft.add(R.id.act_main_content_frg, frag, AppConstt.FragTag.FN_PaymentCardFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_PaymentDetailFragment);
        ft.hide(this);
        ft.commit();
    }


    public void onBraintreeSubmit(String token) {
//        braintreeClient = new BraintreeClient(getContext(), token);
//        payPalClient = new PayPalClient(braintreeClient);
//        dataCollector = new DataCollector(braintreeClient);
    }


    private void requestFeedback() {
        showProgDialog();
        Intro_WebHit_Get_Token intro_webHit_get_token = new Intro_WebHit_Get_Token();
        intro_webHit_get_token.getToken(new IWebCallback() {
            @Override
            public void onWebResult(boolean isSuccess, String strMsg) {
                if (isSuccess) {
                    Toast.makeText(getContext(), "TOKEN GOT: ", Toast.LENGTH_SHORT).show();
                    dismissProgDialog();
                    onBraintreeSubmit(Intro_WebHit_Get_Token.responseObject.getData().getClientToken());
                } else {
                    Toast.makeText(getContext(), strMsg, Toast.LENGTH_SHORT).show();
                    dismissProgDialog();
                }
            }

            @Override
            public void onWebException(Exception ex) {
                Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                dismissProgDialog();
            }
        });
    }

    private void requestPostToken(String noice) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("nonce", noice);
        jsonObject.addProperty("amount", totalAmount);
        requestPostPaymentProcess(jsonObject.toString());
    }

    private void requestPostPaymentProcess(String _signUpEntity) {
        showProgDialog();
        Intro_WebHit_Post_Noice intro_webHit_post_noice = new Intro_WebHit_Post_Noice();
        intro_webHit_post_noice.postSignIn(getContext(), new IWebCallback() {
            @Override
            public void onWebResult(boolean isSuccess, String strMsg) {
                if (isSuccess) {
                    dismissProgDialog();
                    CustomToast.showToastMessage(getActivity(), "Payment Sent", Toast.LENGTH_SHORT);
                    JsonObject jsonObject = new JsonObject();
//                            jsonObject.addProperty("danetkasId", danetkaID);
                    jsonObject.addProperty("gameCredits", numberOfDanetka);
                    jsonObject.addProperty("subtotal", sub_total);
                    jsonObject.addProperty("discount", totalDiscount);
                    jsonObject.addProperty("totalAmount", totalAmount);
                    requestPostGameCredits(jsonObject.toString());
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

    private void onPayPalButtonClick() {
        showProgDialog();
//        PayPalCheckoutRequest request = new PayPalCheckoutRequest(totalAmount);
//        request.setCurrencyCode("EUR");
//        request.setIntent(PayPalPaymentIntent.AUTHORIZE);
//        // The PayPalRequest type will be based on integration type (Checkout vs. Vault)
//        payPalClient.tokenizePayPalAccount(getActivity(), request, (error) -> {
//            if (error != null) {
//                // handle error
//                dismissProgDialog();
//                Log.d("mylog", "Err: " + error);
//                navToPayentDisapprovedFragment();
//            } else {
//                dismissProgDialog();
//                Log.d("mylog", "Req: " + request);
//            }
//        });
    }



    @Override
    public void onResume() {
        super.onResume();
//        if (braintreeClient != null) {
//            BrowserSwitchResult browserSwitchResult = braintreeClient.deliverBrowserSwitchResult(getActivity());
//            if (browserSwitchResult != null) {
//                payPalClient.onBrowserSwitchResult(browserSwitchResult, (payPalAccountNonce, browserSwitchError) -> {
//                    dataCollector.collectDeviceData(getContext(), (deviceData, dataCollectorError) -> {
//                        try {
//                            // send paypalAccountNonce.getString() and deviceData to server
//                            PostalAddress billingAddress = payPalAccountNonce.getBillingAddress();
//                            Log.d("mylog", "streetAddress: " + payPalAccountNonce.getFirstName());
//                            Log.d("mylog", "streetAddress: " + payPalAccountNonce.getAuthenticateUrl());
//                            Log.d("mylog", "streetAddress: " + payPalAccountNonce.getClientMetadataId());
//                            Log.d("mylog", "streetAddress: " + payPalAccountNonce.getEmail());
//                            Log.d("mylog", "streetAddress: " + payPalAccountNonce.getLastName());
//                            Log.d("mylog", "streetAddress: " + payPalAccountNonce.getPayerId());
//                            Log.d("mylog", "streetAddress: " + payPalAccountNonce.getPhone());
//                            Log.d("mylog", "streetAddress: " + payPalAccountNonce.getCreditFinancing());
//                            Log.d("mylog", "streetAddress: " + payPalAccountNonce.getShippingAddress());
//                            Log.d("mylog", "paypalAccountNonce.getString(): " + payPalAccountNonce.getString());
//
//                            requestPostToken(payPalAccountNonce.getString());
//                        } catch (Exception e) {
//                            navToPayentDisapprovedFragment();
//                        }
//
//
//                    });
//                });
//            } else {
//                Log.d("mylog", "braintreeClient==nill ");
//            }
//        } else {
//            Log.d("mylog", "braintreeClient==nill ");
//        }

    }

    private void dismissProgDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    private void showProgDialog() {
        progressDialog = new Dialog(getContext(), R.style.AppTheme);
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


    //region Navigation
    private void navToPayentDisapprovedFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new PaymentFailedFragment();
        ft.add(R.id.act_main_content_frg, frag, AppConstt.FragTag.FN_PaymentFailedFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_PaymentFailedFragment);
        ft.hide(this);
        ft.commit();
    }

    private void navToPayentApprovedFragment(String credit, String total) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new PaymentApprovedFragment();
        Bundle bundle = new Bundle();
        bundle.putString("key_danetka_credit", credit);
        bundle.putString("key_danetka_total", total);
        frag.setArguments(bundle);
        ft.add(R.id.act_main_content_frg, frag, AppConstt.FragTag.FN_PaymentApprovedFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_PaymentApprovedFragment);
        ft.hide(this);
        ft.commit();
    }

    private void navToBundleDiscountFragment(String danetka_danetkaID) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new BundleDiscountFragment();
        Bundle bundle = new Bundle();
        bundle.putString("key_danetka_danetkaID", danetka_danetkaID);
        frag.setArguments(bundle);
        ft.add(R.id.act_main_content_frg, frag, AppConstt.FragTag.FN_BundleDiscountFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_BundleDiscountFragment);
        ft.hide(this);
        ft.commit();
    }
    //endregion
}
