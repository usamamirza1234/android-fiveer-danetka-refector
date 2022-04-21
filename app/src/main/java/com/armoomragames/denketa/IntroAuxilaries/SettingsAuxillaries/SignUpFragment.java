package com.armoomragames.denketa.IntroAuxilaries.SettingsAuxillaries;

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
import com.armoomragames.denketa.IntroAuxilaries.WebServices.Intro_WebHit_Post_LogIn;
import com.armoomragames.denketa.IntroAuxilaries.WebServices.Intro_WebHit_Post_SignUp;
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.CustomToast;
import com.armoomragames.denketa.Utils.IBadgeUpdateListener;
import com.armoomragames.denketa.Utils.IWebCallback;
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

public class SignUpFragment extends Fragment implements View.OnClickListener {
    private static final int RC_SIGN_IN = 9001;
    RelativeLayout rlToolbar, rlBack, rlCross;
    GoogleSignInClient mGoogleSignInClient;
    TextView txvLogin;
    RelativeLayout rlRegister;
    LinearLayout llGoogle, llFB;
    GoogleSignInAccount acct, account;
    IBadgeUpdateListener mBadgeUpdateListener;

    CallbackManager callbackManager;
    EditText edtPassword, edtEmail, editConfirmPass;
    private Dialog progressDialog;



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_sign_up, container, false);
        init();

        bindViews(frg);
        return frg;
    }

    //region init
    void init() {
        googleInit();
        setToolbar();
    }

    private void bindViews(View frg) {
        rlToolbar = frg.findViewById(R.id.act_intro_rl_toolbar);
        rlBack = frg.findViewById(R.id.act_intro_lay_toolbar_rlBack);
        rlCross = frg.findViewById(R.id.act_intro_lay_toolbar_rlCross);

        rlBack.setOnClickListener(this);
        rlCross.setOnClickListener(this);
//        Animation shake;
//        shake = AnimationUtils.loadAnimation(getContext(), R.anim.shake);
//        TextView txvPlay = frg.findViewById(R.id.frg_presigin_txvPlay);
//        txvPlay.startAnimation(shake); // starts animation

        llFB = frg.findViewById(R.id.frg_my_account_llFB);
        llGoogle = frg.findViewById(R.id.frg_my_account_llGoogle);
        txvLogin = frg.findViewById(R.id.frg_my_account_txvLogin);
        rlRegister = frg.findViewById(R.id.frg_my_account_rlRegister);


        editConfirmPass = frg.findViewById(R.id.frg_signup_edtCnfirmPassword);
        edtEmail = frg.findViewById(R.id.frg_signup_edtEmail);
        edtPassword = frg.findViewById(R.id.frg_signup_edtPassword);

        llFB.setOnClickListener(this);
        llGoogle.setOnClickListener(this);
        txvLogin.setOnClickListener(this);
        rlRegister.setOnClickListener(this);
        editTextWatchers();
    }

    void setToolbar() {

        try {
            mBadgeUpdateListener = (IBadgeUpdateListener) getActivity();
        } catch (ClassCastException castException) {
            castException.printStackTrace(); // The activity does not implement the listener
        }
        if (getActivity() != null && isAdded()) {
            mBadgeUpdateListener.setToolbarState(AppConstt.INTRO_ToolbarStates.TOOLBAR_VISIBLE);
        }

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
            case R.id.act_intro_lay_toolbar_rlBack:
                getActivity().onBackPressed();

                break;
            case R.id.act_intro_lay_toolbar_rlCross:
                ((IntroActivity) getActivity()).navToPreSignInVAFragment();

                break;
            case R.id.frg_my_account_rlRegister:
                checkErrorConditions();

//                navtoSignUpContFragment();
                break;


            case R.id.frg_my_account_txvLogin:
                navtoSigninFragment();
                break;
            case R.id.frg_my_account_llGoogle:
                googleSignIn();
                break;
            case R.id.frg_my_account_llFB:
                signUpFaceBook();
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
        edtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().startsWith(" ")) {
                    edtPassword.setText("");
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

    private boolean checkEmailErrorCondition() {
        if (edtEmail.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "Empty Email Field", Toast.LENGTH_SHORT).show();
//            AppConfig.getInstance().showErrorMessage(getContext(), "Empty email field");
            return false;
        } else if (!edtEmail.getText().toString().matches(AppConstt.EMAIL_PATTERN)) {
            Toast.makeText(getActivity(), "Invalid Email Pattern ", Toast.LENGTH_SHORT).show();
//            AppConfig.getInstance().showErrorMessage(getContext(), "Email pattern is incorrect");
            return false;
        } else {
            return true;
        }
    }

    private boolean comaparePassErrorCondition() {
        if (edtPassword.getText().toString().equals(editConfirmPass.getText().toString())) {
            return true;
        } else {
//            AppConfig.getInstance().showErrorMessage(getContext(), "The passwords entered do not match");
            Toast.makeText(getActivity(), "The passwords entered do not match", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean checkConfrmPassErrorCondition() {
        if (editConfirmPass.getText().toString().isEmpty()) {
//            AppConfig.getInstance().showErrorMessage(getContext(), "Empty confirm password field");
            Toast.makeText(getActivity(), "Empty confirm password field", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean checkPassErrorCondition() {
        if (edtPassword.getText().toString().isEmpty()) {
//            AppConfig.getInstance().showErrorMessage(getContext(), "Empty set password field");
            Toast.makeText(getActivity(), "Empty set password field", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private void checkErrorConditions() {


        if (checkEmailErrorCondition() && checkPassErrorCondition() && checkConfrmPassErrorCondition() && comaparePassErrorCondition()) {


            closeKeyboard();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("email", edtEmail.getText().toString());
            jsonObject.addProperty("password", edtPassword.getText().toString());
            jsonObject.addProperty("userType", "normal");
            requestUserRegister(jsonObject.toString());

//            AppConfig.getInstance().mUser.Email = edtEmail.getText().toString();
//            AppConfig.getInstance().mUser.isLoggedIn = true;
//            AppConfig.getInstance().saveUserProfile();
//            navtoSignUpContFragment();
        }
    }


//
//    private boolean termCndtionErrorCondition() {
//        if (chbTermCondition.isChecked()) {
//            return true;
//        } else {
//            AppConfig.getInstance().showErrorMessage(getContext(), "Terms and conditions are not checked");
//            return false;
//        }
//    }


//    private boolean checkDobErrorCondition() {
//        if (txvDob.getText().toString().equalsIgnoreCase("Date of Birth")) {
//            AppConfig.getInstance().showErrorMessage(getContext(), "Date of birth is not selected");
//
//            return false;
//        } else {
//            return true;
//        }
//    }
//
//    private boolean checkCityErrorCheck() {
//        if (txvSelectedCity.getText().toString().equalsIgnoreCase("Select City")) {
//            AppConfig.getInstance().showErrorMessage(getContext(), "City is not selected");
//            return false;
//        } else {
//            return true;
//        }
//    }
//
//    private boolean checkNumberErrorCondition() {
//        if (edtPhoneNum.getText().toString().isEmpty()) {
//            AppConfig.getInstance().showErrorMessage(getContext(), "Empty phone number field");
//            return false;
//        } else {
//            return true;
//        }
//    }


    //
//    private boolean checkNameErrorCondition() {
//        if (edtName.getText().toString().isEmpty()) {
//            AppConfig.getInstance().showErrorMessage(getContext(), "Empty name field");
////        Toast.makeText(getActivity(), "Empty Name Feild", Toast.LENGTH_SHORT).show();
//            return false;
//        } else {
//            return true;
//        }
//    }
//
//    private void showCalender() {
////        new SingleDateAndTimePickerDialog.Builder(getContext())
////                .bottomSheet()
////                .curved()
////                .displayMinutes(false)
////                .displayHours(false)
////                .displayDays(false)
////                .displayMonth(true)
////                .displayYears(true).mainColor(Color.BLACK)
////                .displayDaysOfMonth(true).listener(new SingleDateAndTimePickerDialog.Listener() {
////            @Override
////            public void onDateSelected(Date date) {
////                DateFormat format = new SimpleDateFormat("dd MMMM, yyyy");
////                String datee = format.parse(Date.parse( date.toString()));
////
////                txvDob.setText(date.toString());
//////                if (date.after(yesterday()))
//////                {
//////                    goalDate =datee;
//////                    txvCalender.setText(datee);
//////                    btnNext.setEnabled(true);
//////                }
////            }
////        }).defaultDate(calendar.getTime()).display();
//////
//////
//
//        new DatePickerDialog(getContext(), AlertDialog.THEME_HOLO_LIGHT, date, calendar
//                .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
//                calendar.get(Calendar.DAY_OF_MONTH)).show();
//    }
    //endregion

    //region Google Integration
    private void googleInit() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
    }

    public void  googleSignIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        } catch (Exception e) {

        }
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Log.d("LOG_AS", "onActivityResult: google sign in " + data.toString());
            // The Task returned from this call is always completed, no need to attach
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            account = completedTask.getResult(ApiException.class);
            acct = GoogleSignIn.getLastSignedInAccount(getActivity());
            Log.d("LOG_AS", "Google Obj : " + acct.getId());
            if (acct != null)
            {
                requestSocial(acct.getEmail());
            }
        } catch (ApiException e) {
            Log.d("LOG_AS", "signInResult:failed code=" + e.toString());
        }
    }

    //endregion

    //region FaceBook Integration
    private void signUpFaceBook() {
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        Log.i("LoginActivity", "FB Login Success");


                        AccessToken accessToken = loginResult.getAccessToken();
                        Profile profile = Profile.getCurrentProfile();

                        // Facebook Email address
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(
                                            JSONObject object,
                                            GraphResponse response) {
                                        Log.v("LoginActivity Response ", response.toString());
                                        String Name, FEmail;

                                        try {
                                            Name = object.getString("name");
                                            FEmail = object.getString("email");
                                            requestSocial(FEmail);

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,gender, birthday");
                        request.setParameters(parameters);
                        request.executeAsync();


                    }

                    @Override
                    public void onCancel() {
                        // App code
                        Log.i("LoginActivity", "FB Login Cancel");
                        LoginManager.getInstance().logOut();
                        Toast.makeText(getContext(), "Cancel", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Log.i("LoginActivity", "FB Login Error");
                        Toast.makeText(getContext(), "error_login", Toast.LENGTH_SHORT).show();
                    }
                });


        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();


        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email"));
    }
    //endregion

    //region RequestAPIS
    public void requestSocial(String EMAIL) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("email", EMAIL);
        jsonObject.addProperty("userType", "social");
        requestSocialRegister(jsonObject.toString());
    }

    private void requestSocialRegister(String _signUpEntity) {
//        showProgDialog();
        Intro_WebHit_Post_SignUp intro_webHit_post_signUp = new Intro_WebHit_Post_SignUp();
        intro_webHit_post_signUp.postSignIn(getContext(), new IWebCallback() {
            @Override
            public void onWebResult(boolean isSuccess, String strMsg) {
                if (isSuccess) {
                    dismissProgDialog();
                    AppConfig.getInstance().mUser.User_Id = Intro_WebHit_Post_SignUp.responseObject.getData().getUser().getId();
                    AppConfig.getInstance().mUser.Email = Intro_WebHit_Post_SignUp.responseObject.getData().getUser().getEmail();
                    AppConfig.getInstance().mUser.Authorization = Intro_WebHit_Post_SignUp.responseObject.getData().getUser().getAccessToken();
                    AppConfig.getInstance().mUser.setLoggedIn(true);
                    AppConfig.getInstance().saveUserProfile();

                    if (Intro_WebHit_Post_SignUp.responseObject.getData().getUser().getEmail().equalsIgnoreCase("armoomragamesdev@gmail.com")) {
                        AppConfig.getInstance().mUser.setAdmin(true);
                        AppConfig.getInstance().saveUserProfile();
                        navtoAdminHomeFragment();
                    } else
                        navtoSignUpContFragment();
                } else {
                    dismissProgDialog();
                    requestSocialLoggedin(_signUpEntity);
                }
            }

            @Override
            public void onWebException(Exception ex) {
                dismissProgDialog();
                requestSocialLoggedin(_signUpEntity);
            }
        }, _signUpEntity);
    }

    private void requestSocialLoggedin(String _signUpEntity) {
//        showProgDialog();
        Intro_WebHit_Post_LogIn intro_webHit_post_logIn = new Intro_WebHit_Post_LogIn();
        intro_webHit_post_logIn.postSignIn(getContext(), new IWebCallback() {
            @Override
            public void onWebResult(boolean isSuccess, String strMsg) {
                if (isSuccess) {
                    dismissProgDialog();
                    try {
                        //Save user login data
                        AppConfig.getInstance().mUser.User_Id = Intro_WebHit_Post_LogIn.responseObject.getData().getId();
                        AppConfig.getInstance().mUser.Email = Intro_WebHit_Post_LogIn.responseObject.getData().getEmail();
                        AppConfig.getInstance().mUser.GameCredits = 0 + "";


                        if (Intro_WebHit_Post_LogIn.responseObject.getData().getName() != null)
                            AppConfig.getInstance().mUser.Name = Intro_WebHit_Post_LogIn.responseObject.getData().getName();
                        if (Intro_WebHit_Post_LogIn.responseObject.getData().getNationality() != null)
                            AppConfig.getInstance().mUser.Nationality = Intro_WebHit_Post_LogIn.responseObject.getData().getNationality();

                        if (Intro_WebHit_Post_LogIn.responseObject.getData().getGender() != null)
                            AppConfig.getInstance().mUser.Gender = Intro_WebHit_Post_LogIn.responseObject.getData().getGender();

                        if (Intro_WebHit_Post_LogIn.responseObject.getData().getDateOfBirth() != null)
                            AppConfig.getInstance().mUser.DOB = Intro_WebHit_Post_LogIn.responseObject.getData().getDateOfBirth();
                        AppConfig.getInstance().mUser.Authorization = Intro_WebHit_Post_LogIn.responseObject.getData().getAccessToken();

                        AppConfig.getInstance().mUser.setLoggedIn(true);
                        AppConfig.getInstance().saveUserProfile();

                        if (Intro_WebHit_Post_LogIn.responseObject.getData().getEmail().equalsIgnoreCase("armoomragamesdev@gmail.com")) {
                            AppConfig.getInstance().mUser.setAdmin(true);
                            AppConfig.getInstance().saveUserProfile();
                            navtoAdminHomeFragment();
                        } else
                            navtoSignUpContFragment();


                    } catch (Exception e) {

                    }

                }
            }

            @Override
            public void onWebException(Exception ex) {
                Log.d("LOG_AS", "postSignIn: Exception" + ex.getMessage());
                CustomToast.showToastMessage(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT);
            }
        }, _signUpEntity);
    }

    private void requestUserRegister(String _signUpEntity) {
        showProgDialog();
        Intro_WebHit_Post_SignUp intro_webHit_post_signUp = new Intro_WebHit_Post_SignUp();
        intro_webHit_post_signUp.postSignIn(getContext(), new IWebCallback() {
            @Override
            public void onWebResult(boolean isSuccess, String strMsg) {
                if (isSuccess) {
                    dismissProgDialog();
                    //Save user login data
                    AppConfig.getInstance().mUser.User_Id = Intro_WebHit_Post_SignUp.responseObject.getData().getUser().getId();
                    AppConfig.getInstance().mUser.Email = Intro_WebHit_Post_SignUp.responseObject.getData().getUser().getEmail();
                    AppConfig.getInstance().mUser.setLoggedIn(true);
                    AppConfig.getInstance().mUser.Authorization = Intro_WebHit_Post_SignUp.responseObject.getData().getUser().getAccessToken();
                    AppConfig.getInstance().mUser.GameCredits = 0+"";
                    AppConfig.getInstance().saveUserProfile();

                    if (Intro_WebHit_Post_SignUp.responseObject.getData().getUser().getEmail().equalsIgnoreCase("armoomragamesdev@gmail.com")) {
                        AppConfig.getInstance().mUser.setAdmin(true);
                        AppConfig.getInstance().saveUserProfile();
                        navtoAdminHomeFragment();
                    } else
                        navtoSignUpContFragment();

                } else {
                    dismissProgDialog();
                    CustomToast.showToastMessage(getActivity(), strMsg, Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onWebException(Exception ex) {
                dismissProgDialog();
                CustomToast.showToastMessage(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT);
//                Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT).show();
//                AppConfig.getInstance().showErrorMessage(getContext(), ex.toString());
            }
        }, _signUpEntity);
    }
    //endregion

    //region Navigation
    private void navtoSigninFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new SignInFragment();
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_SignInFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_SignInFragment);
        ft.hide(this);
        ft.commit();
    }

    private void navToForgotPassword() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new ForgotPasswordFragment();
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_ForgotPasswordFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_ForgotPasswordFragment);
        ft.hide(this);
        ft.commit();
    }

    private void navtoSignUpContFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new SignUpCompleteProfileFragment();
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_SignUpCompleteProfileFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_SignUpCompleteProfileFragment);
        ft.hide(this);
        ft.commit();
    }

    private void navtoAdminHomeFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new AdminHomeFragment();
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_AdminHomeFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_AdminHomeFragment);
        ft.hide(this);
        ft.commit();
    }

    //endregion

}