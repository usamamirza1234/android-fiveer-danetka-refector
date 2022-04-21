package com.armoomragames.denketa;


import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.armoomragames.denketa.IntroAuxilaries.WebServices.Intro_WebHit_Get_Token;
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

import java.util.HashMap;


public class BraintreeActivity extends AppCompatActivity implements View.OnClickListener {

    final int REQUEST_CODE = 1;
    private Dialog progressDialog;
    String btToken = "sandbox_v2nf5t6c_mybf9tq8g5qv92zw";
    String btToken1 = "sandbox_f252zhq7_hh4cpc39zq4rgjcg";

//    BraintreeClient braintreeClient;
//    PayPalClient payPalClient;
//    CardClient cardClient;
//    DataCollector dataCollector;
    RelativeLayout rlPaypal;
    RelativeLayout rlPaypalCredit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_braintree);


        init();
        bindViews();


    }

    private void init() {
        requestFeedback();
    }

    private void bindViews() {
        rlPaypal = findViewById(R.id.rlPaypal);
        rlPaypalCredit = findViewById(R.id.rlPaypalCredit);


        rlPaypal.setOnClickListener(this);
        rlPaypalCredit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.act_intro_lay_toolbar_rlBack:
//                getActivity().onBackPressed();

                break;
            case R.id.act_intro_lay_toolbar_rlCross:
//                ((IntroActivity) getActivity()).navToPreSignInVAFragment();

                break;
            case R.id.rlPaypal:
                onPayPalButtonClick();
                break;
            case R.id.rlPaypalCredit:
                tokenizeCard();
                break;

            case R.id.frg_getmore:
//                navToBundleDiscountFragment(danetkaID);
                break;

            case R.id.rlUseGameCredits:
//                if (!AppConfig.getInstance().mUser.GameCredits.equalsIgnoreCase("0")) {
//                    JsonObject jsonObject = new JsonObject();
//                    jsonObject.addProperty("danetkasId", danetkaID);
//                    requestAddUserDanetkas(jsonObject.toString());
//                } else
//                    CustomToast.showToastMessage(getActivity(), "Insufficient Game Credits Buy Now", Toast.LENGTH_SHORT);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE) {
//            if (resultCode == Activity.RESULT_OK) {
//                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
//                PaymentMethodNonce nonce = result.getPaymentMethodNonce();
////                String stringNonce = nonce.getNonce();
//                Log.d("mylog", "Result: " + nonce);
//                // Send payment price with the nonce
//                // use the result to update your UI and send the payment method nonce to your server
////                if (!etAmount.getText().toString().isEmpty()) {
////                    amount = etAmount.getText().toString();
//////                    paramHash = new HashMap<>();
//////                    paramHash.put("amount", amount);
//////                    paramHash.put("nonce", stringNonce);
//////                    sendPaymentDetails();
////                } else
////                    Toast.makeText(BraintreeActivity.this, "Please enter a valid amount.", Toast.LENGTH_SHORT).show();
//
//            } else if (resultCode == Activity.RESULT_CANCELED) {
//                // the user canceled
//                Log.d("mylog", "user canceled");
//            } else {
//                // handle errors here, an exception may be available in
//                Exception error = (Exception) data.getSerializableExtra(DropInResult.EXTRA_ERROR);
//                Log.d("mylog", "Error : " + error.toString());
//            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onBraintreeSubmit(String token) {
//        braintreeClient = new BraintreeClient(this, token);
//        payPalClient = new PayPalClient(braintreeClient);
//        dataCollector = new DataCollector(braintreeClient);
//        DropInRequest dropInRequest = new DropInRequest();
//        dropInRequest.setGooglePayDisabled(true);
//        DropInClient dropInClient = new DropInClient(this, btToken, dropInRequest);
//        dropInClient.launchDropInForResult(this, REQUEST_CODE);
    }


    private void requestFeedback() {
     showProgDialog();
        Intro_WebHit_Get_Token intro_webHit_get_token = new Intro_WebHit_Get_Token();
        intro_webHit_get_token.getToken(new IWebCallback() {
            @Override
            public void onWebResult(boolean isSuccess, String strMsg) {
                if (isSuccess) {
                    Toast.makeText(BraintreeActivity.this, "Success", Toast.LENGTH_SHORT).show();
                   dismissProgDialog();
                    onBraintreeSubmit(Intro_WebHit_Get_Token.responseObject.getData().getClientToken());
                } else {
                    Toast.makeText(BraintreeActivity.this, strMsg, Toast.LENGTH_SHORT).show();
                    dismissProgDialog();
                }
            }

            @Override
            public void onWebException(Exception ex) {
                Toast.makeText(BraintreeActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                dismissProgDialog();
            }
        });
    }

    private void onPayPalButtonClick() {
        showProgDialog();
//        PayPalCheckoutRequest request = new PayPalCheckoutRequest("0.01");
//        request.setCurrencyCode("USD");
//        request.setIntent(PayPalPaymentIntent.AUTHORIZE);
//        // The PayPalRequest type will be based on integration type (Checkout vs. Vault)
//        payPalClient.tokenizePayPalAccount(this, request, (error) -> {
//            if (error != null) {
//                // handle error
//                dismissProgDialog();
//                Log.d("mylog", "Err: " + error.toString());
//            } else {
//                dismissProgDialog();
//                Log.d("mylog", "Req: " + request.toString());
//            }
//        });
    }

    private void tokenizeCard() {
//        showProgDialog();
//        Card card = new Card();
//        card.setNumber("4111111111111111");
//        card.setExpirationDate("09/2028");
//
//        cardClient = new CardClient(braintreeClient);
//        cardClient.tokenize(card, (cardNonce, error) -> {
//            dismissProgDialog();
//            // send cardNonce.getString() to your server
//            Log.d("mylog", "streetAddress: " + cardNonce.getCardholderName());
//            Log.d("mylog", "streetAddress: " + cardNonce.getCardType());
//            Log.d("mylog", "streetAddress: " + cardNonce.getLastFour());
//            Log.d("mylog", "streetAddress: " + cardNonce.getCardType());
//            Log.d("mylog", "streetAddress: " + cardNonce.getExpirationMonth());
//            Log.d("mylog", "streetAddress: " + cardNonce.getExpirationYear());
//            Log.d("mylog", "cardNonce.getString(): " + cardNonce.getString());
//        });
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (braintreeClient != null) {
//            BrowserSwitchResult browserSwitchResult = braintreeClient.deliverBrowserSwitchResult(this);
//            if (browserSwitchResult != null) {
//                payPalClient.onBrowserSwitchResult(browserSwitchResult, (payPalAccountNonce, browserSwitchError) -> {
//                    dataCollector.collectDeviceData(this, (deviceData, dataCollectorError) -> {
//                        // send paypalAccountNonce.getString() and deviceData to server
//                        PostalAddress billingAddress = payPalAccountNonce.getBillingAddress();
//                        Log.d("mylog", "streetAddress: " + payPalAccountNonce.getFirstName());
//                        Log.d("mylog", "streetAddress: " + payPalAccountNonce.getAuthenticateUrl());
//                        Log.d("mylog", "streetAddress: " + payPalAccountNonce.getClientMetadataId());
//                        Log.d("mylog", "streetAddress: " + payPalAccountNonce.getEmail());
//                        Log.d("mylog", "streetAddress: " + payPalAccountNonce.getLastName());
//                        Log.d("mylog", "streetAddress: " + payPalAccountNonce.getPayerId());
//                        Log.d("mylog", "streetAddress: " + payPalAccountNonce.getPhone());
//                        Log.d("mylog", "streetAddress: " + payPalAccountNonce.getCreditFinancing());
//                        Log.d("mylog", "streetAddress: " + payPalAccountNonce.getShippingAddress());
//                        Log.d("mylog", "paypalAccountNonce.getString(): " + payPalAccountNonce.getString());
//                    });
//                });
//            } else {
//                Log.d("mylog", "braintreeClient==nill ");
//            }
//        } else {
//            Log.d("mylog", "braintreeClient==nill ");
//        }

    }

    @Override
    protected void onNewIntent(Intent newIntent) {
        super.onNewIntent(newIntent);
        setIntent(newIntent);
    }
    private void dismissProgDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    private void showProgDialog() {

        progressDialog = new Dialog(this, R.style.AppTheme);
//        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.dialog_progress_loading);
        WindowManager.LayoutParams wmlp = progressDialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.CENTER | Gravity.CENTER;
        wmlp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wmlp.height = ViewGroup.LayoutParams.MATCH_PARENT;

        ImageView imageView = progressDialog.findViewById(R.id.img_anim);
        Glide.with(this).asGif().load(R.raw.loading).into(imageView);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();


    }
}