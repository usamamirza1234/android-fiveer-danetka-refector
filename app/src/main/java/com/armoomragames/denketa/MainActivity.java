package com.armoomragames.denketa;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.armoomragames.denketa.Example.BraintreePaymentsFragment;
import com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.BundleDiscountFragment;
import com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.PaymentDetailFragment;
import com.armoomragames.denketa.IntroAuxilaries.PreSignInFragment;
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
import com.google.android.material.navigation.NavigationView;


import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.IBadgeUpdateListener;

public class MainActivity extends AppCompatActivity  {

    final int REQUEST_CODE = 1;
    private Dialog progressDialog;
    String btToken = "sandbox_v2nf5t6c_mybf9tq8g5qv92zw";
    String btToken1 = "sandbox_f252zhq7_hh4cpc39zq4rgjcg";
    private FragmentManager fm;
//    BraintreeClient braintreeClient;
//    PayPalClient payPalClient;
//    CardClient cardClient;
//    DataCollector dataCollector;
    RelativeLayout rlPaypal;
    RelativeLayout rlPaypalCredit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        AppConfig.getInstance().performLangCheck(getWindow());
        if (savedInstanceState != null) {
            return;
        }

        init();
        String position = getIntent().getStringExtra("key_danetka_position");
        Boolean is_investigator = getIntent().getBooleanExtra("key_danetka_is_investigator",false);
        Boolean is_more_danetka = getIntent().getBooleanExtra("key_danetka_is_more_danetka",false);
        Boolean isPayment = getIntent().getBooleanExtra("key_danetka_isPayment",false);
        if (isPayment)
        navToPaymentDetailFragment();
        else navToBundleFragment();

    }
    private void init() {
        fm = getSupportFragmentManager();

    }


    private void navToPaymentDetailFragment() {
        Fragment frg = new BraintreePaymentsFragment();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.act_main_content_frg, frg, AppConstt.FragTag.FN_PaymentDetailFragment);
        ft.commit();
    }

    private void navToBundleFragment() {
        Fragment frg = new BundleDiscountFragment();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.act_main_content_frg, frg, AppConstt.FragTag.FN_BundleDiscountFragment);
        ft.commit();
    }


}
