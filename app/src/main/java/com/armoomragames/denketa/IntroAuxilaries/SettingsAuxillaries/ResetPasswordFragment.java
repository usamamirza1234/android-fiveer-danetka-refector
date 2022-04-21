package com.armoomragames.denketa.IntroAuxilaries.SettingsAuxillaries;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.armoomragames.denketa.AppConfig;
import com.armoomragames.denketa.IntroActivity;
import com.armoomragames.denketa.IntroAuxilaries.WebServices.Intro_WebHit_Post_VerifyAndUpdate;
import com.armoomragames.denketa.IntroAuxilaries.WebServices.Intro_WebHit_Post_forgotPassword;
import com.armoomragames.denketa.MainActivity;
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.CustomToast;
import com.armoomragames.denketa.Utils.IBadgeUpdateListener;
import com.armoomragames.denketa.Utils.IWebCallback;
import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;

public class ResetPasswordFragment extends Fragment implements View.OnClickListener {

    RelativeLayout rlToolbar, rlBack, rlCross;
    LinearLayout llConfirm, llConfirmed;
    IBadgeUpdateListener mBadgeUpdateListener;
    EditText edtOTP, edtPassword, edtCnfirmPassword;
    String Email="";
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_reset_password, container, false);

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
            mBadgeUpdateListener.setToolbarState(AppConstt.INTRO_ToolbarStates.TOOLBAR_VISIBLE);
        }

    }

    void init() {
        setToolbar();
        Bundle bundle = getArguments();
        if (bundle!=null)
        {

            Email = bundle.getString("key_email");
        }
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
        llConfirm = frg.findViewById(R.id.frg_restPass_llConfirm);
        llConfirmed = frg.findViewById(R.id.frg_restPass_llConfirmed);
        edtPassword = frg.findViewById(R.id.frg_signup_edtPassword);
        edtCnfirmPassword = frg.findViewById(R.id.frg_signup_edtCnfirmPassword);
        edtOTP = frg.findViewById(R.id.frg_signup_edtotp);

        llConfirm.setOnClickListener(this);
        llConfirmed.setOnClickListener(this);

        llConfirm.setVisibility(View.VISIBLE);
        llConfirmed.setVisibility(View.GONE);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.act_intro_lay_toolbar_rlBack:
                ((IntroActivity)getActivity()).  onBackPressed();

                break;
            case R.id.act_intro_lay_toolbar_rlCross:
                ((IntroActivity)getActivity()). navToPreSignInVAFragment();

                break;
            case R.id.frg_restPass_llConfirm:

                checkErrorConditions();
                break;
        }
    }





    private void checkErrorConditions() {


        if (checkEmailErrorCondition() && checkPassErrorCondition() && checkConfrmPassErrorCondition() && comaparePassErrorCondition()) {


            closeKeyboard();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("email", Email.toString());
            jsonObject.addProperty("newPassword", edtPassword.getText().toString());
            jsonObject.addProperty("otpCode", edtOTP.getText().toString());
            requestUserSigin(jsonObject.toString());

        }
    }


    private Dialog progressDialog;

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
    private void requestUserSigin(String _signUpEntity) {
        showProgDialog();
        Intro_WebHit_Post_VerifyAndUpdate intro_webHit_post_verifyAndUpdate = new Intro_WebHit_Post_VerifyAndUpdate();
        intro_webHit_post_verifyAndUpdate.postForgotPassword(getContext(), new IWebCallback() {
            @Override
            public void onWebResult(boolean isSuccess, String strMsg) {
                if (isSuccess) {
                    dismissProgDialog();
                    CustomToast.showToastMessage(getActivity(), strMsg, Toast.LENGTH_SHORT);

                    llConfirm.setVisibility(View.GONE);
                    llConfirmed.setVisibility(View.VISIBLE);

                } else {

                    dismissProgDialog();
                    CustomToast.showToastMessage(getActivity(), strMsg, Toast.LENGTH_SHORT);

                }
            }

            @Override
            public void onWebException(Exception ex) {
                Log.d("LOG_AS", "postSignIn: Exception" + ex.getMessage());
                CustomToast.showToastMessage(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT);

            }
        }, _signUpEntity);
    }

    private void closeKeyboard() {
        AppConfig.getInstance().closeKeyboard(getActivity());

    }

    private boolean checkEmailErrorCondition() {
        if (edtOTP.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "Empty Email Field", Toast.LENGTH_SHORT).show();
//            AppConfig.getInstance().showErrorMessage(getContext(), "Empty email field");
            return false;
        }  else {
            return true;
        }
    }

    private boolean comaparePassErrorCondition() {
        if (edtPassword.getText().toString().equals(edtCnfirmPassword.getText().toString())) {
            return true;
        } else {
//            AppConfig.getInstance().showErrorMessage(getContext(), "The passwords entered do not match");
            Toast.makeText(getActivity(), "The passwords entered do not match", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean checkConfrmPassErrorCondition() {
        if (edtCnfirmPassword.getText().toString().isEmpty()) {
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
}
