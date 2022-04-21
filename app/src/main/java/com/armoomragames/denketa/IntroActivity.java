package com.armoomragames.denketa;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.BundleDiscountFragment;
import com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.DModel_MyDenketa;
import com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.GameSession.AnswerFragment;
import com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.GameSession.InvestigatorFragment;
import com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.GameSession.LearnMoreFragment;
import com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.GameSession.QuestionFragment;
import com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.PaymentApprovedFragment;
import com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.PaymentDetailFragment;
import com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.PaymentFailedFragment;
import com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.Results.MyResultsFragment;
import com.armoomragames.denketa.IntroAuxilaries.PlayMianFragment;
import com.armoomragames.denketa.IntroAuxilaries.PreSignInFragment;
import com.armoomragames.denketa.IntroAuxilaries.RulesAuxilaries.ChallengeFragment;
import com.armoomragames.denketa.IntroAuxilaries.RulesAuxilaries.ExtraRulesFragment;
import com.armoomragames.denketa.IntroAuxilaries.RulesAuxilaries.GamePlayFragment;
import com.armoomragames.denketa.IntroAuxilaries.RulesAuxilaries.RulesFragment;
import com.armoomragames.denketa.IntroAuxilaries.SettingsAuxillaries.SignInFragment;
import com.armoomragames.denketa.IntroAuxilaries.SplashFragment;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.IBadgeUpdateListener;
import com.armoomragames.denketa.Utils.LocaleHelper;
//import com.braintreepayments.api.BraintreeClient;
//import com.braintreepayments.api.BraintreeRequestCodes;
//import com.braintreepayments.api.BrowserSwitchResult;
//import com.braintreepayments.api.Card;
//import com.braintreepayments.api.CardClient;
//import com.braintreepayments.api.PayPalCheckoutRequest;
//import com.braintreepayments.api.PayPalClient;
//import com.braintreepayments.api.PayPalPaymentIntent;
//import com.braintreepayments.api.PayPalVaultRequest;
import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class IntroActivity extends AppCompatActivity implements IBadgeUpdateListener, View.OnClickListener {

    RelativeLayout rlToolbar, rlBack, rlCross;
    String danetkaID = "";
    LoginButton loginButton;
    ImageView imageView;
    TextView txtUsername, txtEmail;
    private FragmentManager fm;


//    CardClient cardClient;
//    PayPalClient payPalClient;
//    private BraintreeClient braintreeClient;



    @Override
    protected void onResume() {
        super.onResume();
        if (AppConfig.getInstance() != null)//id some view  !=null => activity in initialized
            performActionAgainstFCM();

//        BrowserSwitchResult browserSwitchResult = braintreeClient.deliverBrowserSwitchResult(this);
//
//
//        if (browserSwitchResult != null)
//        {
//            payPalClient.onBrowserSwitchResult(browserSwitchResult, (payPalAccountNonce, error) -> {
//                if (payPalAccountNonce != null) {
//                    // send payPalNonce.getString() to server
//                }
//
//                try {
//                    Log.d("PaymentTesting", "myTokenizePayPalAccountWithCheckoutMethod:browserSwitchResult " + browserSwitchResult.toString());
//                    Log.d("PaymentTesting", "myTokenizePayPalAccountWithCheckoutMethod:error " + error.toString());
//                    Log.d("PaymentTesting", "myTokenizePayPalAccountWithCheckoutMethod:request payPalAccountNonce " + payPalAccountNonce.toString());
//                }
//                catch (Exception e)
//                {}
//
//
//            });
//        }
    }

    private void myTokenizePayPalAccountWithCheckoutMethod() {

//        PayPalCheckoutRequest request = new PayPalCheckoutRequest("1.00");
//        request.setCurrencyCode("USD");
//        request.setIntent(PayPalPaymentIntent.AUTHORIZE);
//        payPalClient.tokenizePayPalAccount(this, request, (error) -> {
//            if (error != null) {
//                // Handle error
//            }
//
//
//        });
//

    }

    private void myTokenizePayPalAccountWithVaultMethod() {
//        PayPalVaultRequest request = new PayPalVaultRequest();
//        request.setBillingAgreementDescription("Your agreement description");
//
//        payPalClient.tokenizePayPalAccount(this, request, (error) -> {
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        AppConfig.getInstance().performLangCheck(getWindow());
        AppConfig.getInstance().regulateFontScale(getResources().getConfiguration(), getBaseContext());
        setContentView(R.layout.activity_intro);

//        braintreeClient = new BraintreeClient(this, "sandbox_v2nf5t6c_mybf9tq8g5qv92zw");
//        cardClient = new CardClient(braintreeClient);
//        payPalClient = new PayPalClient(braintreeClient);
//
////        tokenizeCard();
//
//        myTokenizePayPalAccountWithCheckoutMethod();
//
        init();
        bindViews();
//        facebook();
    }


    //region progdialog
    private Dialog progressDialog;


    private void getUserProfile(AccessToken currentAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(
                currentAccessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.d("TAG", object.toString());
                        try {
                            String first_name = object.getString("first_name");
                            String last_name = object.getString("last_name");
                            String email = object.getString("email");
                            String id = object.getString("id");
                            String image_url = "https://graph.facebook.com/" + id + "/picture?type=normal";

                            txtUsername.setText("First Name: " + first_name + "\nLast Name: " + last_name);
                            txtEmail.setText(email);
                            Glide.with(IntroActivity.this)
                                    .load(image_url)
                                    .into(imageView);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name,last_name,email,id");
        request.setParameters(parameters);
        request.executeAsync();

    }

    //region App Necessary
    private void init() {


        fm = getSupportFragmentManager();
        getAppVersion();

        AppConfig.getInstance().loadUserProfile();

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        if (!AppConfig.getInstance().getDateSet()) {
            AppConfig.getInstance().saveInstallDate(df.format(c));
            AppConfig.getInstance().setDateSet(true);
        }


        if (AppConfig.getInstance().mLanguage.equalsIgnoreCase(AppConstt.AppLang.LANG_UR)) {
            MyApplication.getInstance().setAppLanguage(AppConstt.AppLang.LANG_UR);
        } else {
            MyApplication.getInstance().setAppLanguage(AppConstt.AppLang.LANG_EN);
        }


        navToSplash();

        // Add code to print out the key hash
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.armoomragames.denketa",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

        setMyScreenSize();
//
//        bindViews();
//        Intent intent = new Intent(this, PayPalService.class);
//
//        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
//
//        startService(intent);
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", "key  >" + Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
        } catch (NoSuchAlgorithmException e) {
        }
    }

    private void bindViews() {
        loginButton = findViewById(R.id.login_button);
        imageView = findViewById(R.id.imageView);
        txtUsername = findViewById(R.id.txtUsername);
        txtEmail = findViewById(R.id.txtEmail);
    }

    @Override
    protected void attachBaseContext(final Context baseContext) {
        //Handle custom font settings and screen size
        super.attachBaseContext(LocaleHelper.wrap(AppConfig.getInstance().regulateDisplayScale(baseContext),
                new Locale(AppConfig.getInstance().loadDefLanguage())));
    }

    void getAppVersion() {
        try {

            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            AppConfig.getInstance().currentAppVersion = String.valueOf(pInfo.versionCode);


        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        //now getIntent() should always return the last received intent
    }


    private void performActionAgainstFCM() {
        try {
            Intent intent = getIntent();
            SplashFragment.notificationId = intent.getIntExtra(AppConstt.Notifications.PUSH_TYPE, AppConstt.Notifications.TYPE_NIL);
            SplashFragment.orderId = intent.getIntExtra(AppConstt.Notifications.PUSH_ORDER_ID, AppConstt.Notifications.TYPE_NIL);
//            getIntent().removeExtra("notification_id");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setMyScreenSize() {

        //For Full screen Mode
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        AppConfig.getInstance().scrnWidth = metrics.widthPixels;
        AppConfig.getInstance().scrnHeight = metrics.heightPixels - getStatusBarHeight();
        Log.d("Screen Width", "" + AppConfig.getInstance().scrnWidth);
        Log.d("Screen Height", "" + AppConfig.getInstance().scrnHeight);
    }

    //endregion

    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return (int) (result * this.getResources().getDisplayMetrics().density + 0.5f);
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
    //endregion

    //region IBadgeUpdateListener

    @Override
    public void setBottomTabVisiblity(int mVisibility) {

    }

    @Override
    public void setToolbarVisiblity(int mVisibility) {

    }

    @Override
    public void setToolbarState(byte mState) {
        switch (mState) {


//            case AppConstt.INTRO_ToolbarStates.TOOLBAR_HIDDEN:
//                rlToolbar.setVisibility(View.GONE);
//                break;
//
//
//            case AppConstt.INTRO_ToolbarStates.TOOLBAR_VISIBLE:
//                rlToolbar.setVisibility(View.VISIBLE);
//                rlBack.setVisibility(View.VISIBLE);
//                rlCross.setVisibility(View.VISIBLE);
//                break;
//
//            case AppConstt.INTRO_ToolbarStates.TOOLBAR_BACK_HIDDEN:
//                rlToolbar.setVisibility(View.VISIBLE);
//                rlBack.setVisibility(View.GONE);
//                rlCross.setVisibility(View.VISIBLE);
//                break;
//
//            case AppConstt.INTRO_ToolbarStates.TOOLBAR_CROSS_HIDDEN:
//                rlToolbar.setVisibility(View.VISIBLE);
//                rlBack.setVisibility(View.VISIBLE);
//                rlCross.setVisibility(View.GONE);
//
//                break;

            default:
                break;

        }

    }

    @Override
    public void switchStatusBarColor(boolean isDark) {

    }

    @Override
    public boolean setHeaderTitle(String strAppTitle) {
        return false;
    }

    //endregion

    //region Navigations

    private void navToPayentDisapprovedFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new PaymentFailedFragment();
//        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
//                R.anim.enter_from_left, R.anim.exit_to_right);//not required
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_PaymentFailedFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_PaymentFailedFragment);
        hideLastStackFragment(ft);
        ft.commit();
    }

    private void navToPayentApprovedFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new PaymentApprovedFragment();
//        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
//                R.anim.enter_from_left, R.anim.exit_to_right);//not required
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_PaymentApprovedFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_PaymentApprovedFragment);
        hideLastStackFragment(ft);
        ft.commit();
    }


    public void navToMyResultsFragment(String name, String strId) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new MyResultsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("key_danetka_name", name);
        bundle.putString("key_danetka_id", strId);
        frag.setArguments(bundle);
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_MyResultsFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_RulesMianFragment);

//            ft.hide(this);
        hideLastStackFragment(ft);
        ft.commit();

    }


    public void navToLearnmoreFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new LearnMoreFragment();


//        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
//                R.anim.enter_from_left, R.anim.exit_to_right);//not required
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_LearnMoreFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_LearnMoreFragment);

        hideLastStackFragment(ft);
//        ft.hide(this);
        ft.commit();

    }


    public void navToDenketaInvestigatorQuestionFragment(int position, boolean isInvestigator, boolean isMoreDanetka, String danetkaID, ArrayList<DModel_MyDenketa> lst_MyDenketa) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new QuestionFragment();
//        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
//                R.anim.enter_from_left, R.anim.exit_to_right);//not required
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_DenketaInvestigatorQuestionFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_DenketaInvestigatorQuestionFragment);
        Bundle bundle = new Bundle();
        bundle.putInt("key_danetka_position", position);
        bundle.putBoolean("key_danetka_is_investigator", isInvestigator);
        bundle.putBoolean("key_danetka_is_more_danetka", isMoreDanetka);
        bundle.putString("key_danetka_is_more_danetka_danetkaID", danetkaID);
        bundle.putParcelableArrayList("list", lst_MyDenketa);
        frag.setArguments(bundle);
        hideLastStackFragment(ft);
//        ft.hide(this);
        ft.commit();
    }


    public void navToDenketaAnswerFragment(int position, boolean isInvestigator, boolean isMoreDanetka, String danetkaID, ArrayList<DModel_MyDenketa> lst_MyDenketa) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new AnswerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("key_danetka_position", position);
        bundle.putBoolean("key_danetka_is_investigator", isInvestigator);
        bundle.putBoolean("key_danetka_is_more_danetka", isMoreDanetka);
        bundle.putParcelableArrayList("list", lst_MyDenketa);
        frag.setArguments(bundle);
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_DenketaAnswerFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_DenketaAnswerFragment);
        hideLastStackFragment(ft);
        ft.commit();
    }

    public void navToDenketaInvestigatorQuestionFragment(int position, boolean isInvestigator, boolean isMoreDanetka, ArrayList<DModel_MyDenketa> lst_MyDenketa) {
        String danetkaID = "0";
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new QuestionFragment();
//        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
//                R.anim.enter_from_left, R.anim.exit_to_right);//not required
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_DenketaInvestigatorQuestionFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_DenketaInvestigatorQuestionFragment);
        Bundle bundle = new Bundle();
        bundle.putInt("key_danetka_position", position);
        bundle.putBoolean("key_danetka_is_investigator", isInvestigator);
        bundle.putBoolean("key_danetka_is_more_danetka", isMoreDanetka);
        bundle.putString("key_danetka_is_more_danetka_danetkaID", danetkaID);
        bundle.putParcelableArrayList("list", lst_MyDenketa);
        frag.setArguments(bundle);
        hideLastStackFragment(ft);
//        ft.hide(this);
        ft.commit();
    }

    public void navToRulesFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new RulesFragment();
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_RulesFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_RulesFragment);
//        ft.hide(this);
        hideLastStackFragment(ft);
        ft.commit();
    }


    public void navToSigninFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new SignInFragment();
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_SiginInFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_SiginInFragment);
//        ft.hide(this);
        hideLastStackFragment(ft);
        ft.commit();
    }


    public void navToPreSignInVAFragment() {
        clearMyBackStack();
        PreSignInFragment frg = new PreSignInFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.act_intro_content_frg, frg, AppConstt.FragTag.FN_PreSignInFragment);

        ft.commit();

    }

    public void navtoMainActivity() {
//        Toast.makeText(this, "MainActivity", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        //   Intent intent = new Intent(this, MainActivityOLD.class);
        startActivity(intent);
//        IntroActivity.this.finish();
//        if (AppConfig.getInstance().  mUser.isLoggedIn()) {
//
//        } else {
//            navToSplash();
//        }
    }

    public void navtoMainActivity(int position, boolean isMoreDanetka, boolean isInvestigator) {
//        Toast.makeText(this, "MainActivity", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        //   Intent intent = new Intent(this, MainActivityOLD.class);
        startActivity(intent);
//        IntroActivity.this.finish();
//        if (AppConfig.getInstance().  mUser.isLoggedIn()) {
//
//        } else {
//            navToSplash();
//        }
    }

    public void navtoMainActivity(int position, boolean isMoreDanetka, boolean isInvestigator, boolean isPayment) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("key_danetka_position", position);
        intent.putExtra("key_danetka_is_investigator", isInvestigator);
        intent.putExtra("key_danetka_is_more_danetka", isMoreDanetka);
        intent.putExtra("key_danetka_isPayment", isPayment);
        startActivity(intent);
    }

    public String returnStackFragmentTag() {
        int index = fm.getBackStackEntryCount() - 1;
        FragmentManager.BackStackEntry backEntry = null;
        String tag = "";
        if (index >= 0) {
            backEntry = fm.getBackStackEntryAt(index);
            tag = backEntry.getName();
        }
        return tag;
    }

    public void clearMyBackStack() {
        int count = fm.getBackStackEntryCount();
        for (int i = 0; i < count; ++i) {
            fm.popBackStackImmediate();

        }
//        setBackButtonVisibility(View.GONE);
//        txvTitle.setText(getResources().getString(R.string.frg_hom_ttl));
    }

    private void navToSplash() {
        Fragment frg = new PreSignInFragment();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.act_intro_content_frg, frg, AppConstt.FragTag.FN_SplashFragment);
        ft.commit();
    }

    private void navtoSignInFragment() {
        SplashFragment frg = new SplashFragment();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.act_intro_content_frg, frg, AppConstt.FragTag.FN_SplashFragment);
        ft.commit();
    }


    public void hideLastStackFragment(FragmentTransaction ft) {
        Fragment frg = null;
        frg = getSupportFragmentManager().findFragmentById(R.id.act_intro_content_frg);

        if (frg != null) {
            if (frg instanceof PreSignInFragment && frg.isVisible()) {
                ft.hide(frg);
            }
            if (frg instanceof ChallengeFragment && frg.isVisible()) {
                ft.hide(frg);
            } else if (frg instanceof RulesFragment && frg.isVisible()) {
                ft.hide(frg);
            } else if (frg instanceof QuestionFragment && frg.isVisible()) {
                ft.hide(frg);
            } else if (frg instanceof ExtraRulesFragment && frg.isVisible()) {
                ft.hide(frg);
            } else if (frg instanceof GamePlayFragment && frg.isVisible()) {
                ft.hide(frg);
            } else if (frg instanceof LearnMoreFragment && frg.isVisible()) {
                ft.hide(frg);
            } else if (frg instanceof PlayMianFragment && frg.isVisible()) {
                ft.hide(frg);
            } else if (frg instanceof MyResultsFragment && frg.isVisible()) {
                ft.hide(frg);
            } else if (frg instanceof InvestigatorFragment && frg.isVisible()) {
                ft.hide(frg);
            } else if (frg instanceof BundleDiscountFragment && frg.isVisible()) {
                ft.hide(frg);
            } else if (frg instanceof PaymentDetailFragment && frg.isVisible()) {
                ft.hide(frg);
            } else if (frg instanceof PaymentFailedFragment && frg.isVisible()) {
                ft.hide(frg);
            } else if (frg instanceof PaymentApprovedFragment && frg.isVisible()) {
                ft.hide(frg);
            } else if (frg instanceof SignInFragment && frg.isVisible()) {
                ft.hide(frg);
            }

        }


    }

//
//    public void sendPayment(PayPalPayment payment) {
//        // Creating Paypal Payment activity intent
//        Intent intent = new Intent(this, PaymentActivity.class);
//        //putting the paypal configuration to the intent
//        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
//
//        // Putting paypal payment to the intent
//        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
//
//        // Starting the intent activity for result
//        // the request code will be used on the method onActivityResult
//        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
//    }


    //endregion


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.act_intro_lay_toolbar_rlBack:
                onBackPressed();

                break;
            case R.id.act_intro_lay_toolbar_rlCross:
                navToPreSignInVAFragment();

                break;


        }
    }

    @Override
    public void onBackPressed() {

        if (returnStackFragmentTag().equalsIgnoreCase(AppConstt.FragTag.FN_RulesFragment)) {
            navToPreSignInVAFragment();
        } else if (returnStackFragmentTag().equalsIgnoreCase(AppConstt.FragTag.FN_ChallengeFragment) ||
                returnStackFragmentTag().equalsIgnoreCase(AppConstt.FragTag.FN_ExtraRulesFragment) ||
                returnStackFragmentTag().equalsIgnoreCase(AppConstt.FragTag.FN_GamePlayFragment)) {
            navToRulesFragment();
        }
        else if (
                returnStackFragmentTag().equalsIgnoreCase(AppConstt.FragTag.FN_DanetkaDetailsFragment)
        )
        {
            super.onBackPressed();
        }
        else {
            if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                getSupportFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
            }
        }


    }
    //region Navigations


    //endregion
}
