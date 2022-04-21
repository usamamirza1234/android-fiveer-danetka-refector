package com.armoomragames.denketa.IntroAuxilaries.Admin.PromoCode;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.armoomragames.denketa.AppConfig;
import com.armoomragames.denketa.IntroActivity;
import com.armoomragames.denketa.IntroAuxilaries.WebServices.Intro_WebHit_Delete_Promo;
import com.armoomragames.denketa.IntroAuxilaries.WebServices.Intro_WebHit_Post_AddPromo;
import com.armoomragames.denketa.IntroAuxilaries.WebServices.Intro_WebHit_Post_UpdatePromo;
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.CustomToast;
import com.armoomragames.denketa.Utils.IBadgeUpdateListener;
import com.armoomragames.denketa.Utils.IWebCallback;
import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddPromoFragment extends Fragment implements View.OnClickListener {


    private final boolean isStarttime = false;
    private final boolean isEndtime = false;
    Calendar calendar;
    DatePickerDialog.OnDateSetListener date;
    Calendar todayCalender;
    JsonObject jsonObject;
    RelativeLayout rlToolbar, rlBack, rlCross;
    RelativeLayout rlDel, rlUpdate, rlsave;
    EditText edtPromo;
    TextView edtValidFrom, edtValidTill, edtDiscount, edtRedemption;
    Dialog dialog;
    IBadgeUpdateListener mBadgeUpdateListener;
    boolean isUpdate;
    String startDate, endDate, promoName, discount, redemption, id_promo;
    private boolean isValidFrom = false, isValidTill = false;
    private long min_date;
    private Dialog progressDialog;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_add_promo, container, false);

        init();
        bindViews(frg);
        setDate();

        return frg;
    }

    private void setDate() {
        if (isUpdate) {
            rlsave.setVisibility(View.GONE);
            rlUpdate.setVisibility(View.VISIBLE);
            rlDel.setVisibility(View.VISIBLE);
            edtPromo.setText(promoName + "");
            edtValidFrom.setText(startDate + "");
            edtValidTill.setText(endDate + "");
            edtDiscount.setText(discount + "");
            edtRedemption.setText(redemption + "");
        } else {
            rlsave.setVisibility(View.VISIBLE);
            rlUpdate.setVisibility(View.GONE);
            rlDel.setVisibility(View.GONE);
        }
    }

    void setToolbar() {

        try {
            mBadgeUpdateListener = (IBadgeUpdateListener) getActivity();
        } catch (ClassCastException castException) {
            castException.printStackTrace(); // The activity does not implement the listener
        }
        if (getActivity() != null && isAdded()) {
            mBadgeUpdateListener.setToolbarState(AppConstt.INTRO_ToolbarStates.TOOLBAR_HIDDEN);

        }

    }

    void init() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            isUpdate = bundle.getBoolean("key_is_update", false);
            startDate = bundle.getString("key_startDate");
            endDate = bundle.getString("key_endDate");
            promoName = bundle.getString("key_promoName");
            redemption = bundle.getString("key_redemption");
            discount = bundle.getString("key_discount");
            id_promo = bundle.getString("key_id_promo");

        }


        setToolbar();
        calendar = Calendar.getInstance();
        initializeDate();
        min_date = calendar.getTimeInMillis();
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

        edtPromo = frg.findViewById(R.id.frg_add_promo_edt_promo);
        edtValidFrom = frg.findViewById(R.id.frg_add_promo_edt_valid_from);
        edtValidTill = frg.findViewById(R.id.frg_add_promo_edt_valid_till);
        edtDiscount = frg.findViewById(R.id.frg_add_promo_edt_discount);
        edtRedemption = frg.findViewById(R.id.frg_add_promo_edt_redemption);
        rlsave = frg.findViewById(R.id.frg_add_promo_edt_save);
        rlUpdate = frg.findViewById(R.id.frg_add_promo_edt_update);
        rlDel = frg.findViewById(R.id.frg_add_promo_edt_del);

        edtValidFrom.setOnClickListener(this);
        edtValidTill.setOnClickListener(this);
        rlsave.setOnClickListener(this);
        rlBack.setOnClickListener(this);
        rlCross.setOnClickListener(this);
        rlDel.setOnClickListener(this);
        rlUpdate.setOnClickListener(this);
//        rlToolbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.frg_add_promo_edt_valid_from:
                isValidFrom = true;
                isValidTill = false;
                showCalender().show();
                break;
            case R.id.frg_add_promo_edt_valid_till:
                isValidTill = true;
                isValidFrom = false;
                showCalender().show();
                break;
            case R.id.frg_add_promo_edt_save:
                AppConfig.getInstance().closeKeyboard(getActivity());
                jsonObject = new JsonObject();
                jsonObject.addProperty("promoCode", edtPromo.getText().toString());
                jsonObject.addProperty("discount", edtDiscount.getText().toString());
                jsonObject.addProperty("redemption", edtRedemption.getText().toString());
                jsonObject.addProperty("startDate", edtValidFrom.getText().toString());
                jsonObject.addProperty("endDate", edtValidTill.getText().toString());
                requestAddPromo(jsonObject.toString());
                break;

            case R.id.frg_add_promo_edt_update:

                AppConfig.getInstance().closeKeyboard(getActivity());

                requestUpdatePromo(edtPromo.getText().toString(), edtDiscount.getText().toString(), edtRedemption.getText().toString(),
                        edtValidFrom.getText().toString(), edtValidTill.getText().toString());
                break;
            case R.id.frg_add_promo_edt_del:
                requestDeletePromo(id_promo);
                break;

            case R.id.act_intro_lay_toolbar_rlBack:
                getActivity().onBackPressed();
                break;
            case R.id.act_intro_lay_toolbar_rlCross:
                ((IntroActivity) getActivity()).navToPreSignInVAFragment();
                break;
        }
    }

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


    private void requestAddPromo(String _signUpEntity) {
        showProgDialog();
        Intro_WebHit_Post_AddPromo intro_webHit_post_addPromo = new Intro_WebHit_Post_AddPromo();
        intro_webHit_post_addPromo.postPromo(getContext(), new IWebCallback() {
            @Override
            public void onWebResult(boolean isSuccess, String strMsg) {
                if (isSuccess) {
                    dismissProgDialog();
                    CustomToast.showToastMessage(getActivity(), strMsg, Toast.LENGTH_SHORT);
                } else {

                    dismissProgDialog();
                    CustomToast.showToastMessage(getActivity(), strMsg, Toast.LENGTH_SHORT);
//                    Toast.makeText(getActivity(), strMsg, Toast.LENGTH_SHORT).show();
//                    AppConfig.getInstance().showErrorMessage(getContext(), strMsg);
                }
            }

            @Override
            public void onWebException(Exception ex) {
                Log.d("LOG_AS", "postSignIn: Exception" + ex.getMessage());
                CustomToast.showToastMessage(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT);
//                Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT).show();
//                AppConfig.getInstance().showErrorMessage(getContext(), ex.toString());
            }
        }, _signUpEntity);
    }

    private void requestUpdatePromo(String promoName, String discount, String redemption, String startDate, String endDate) {
        showProgDialog();
        Intro_WebHit_Post_UpdatePromo intro_webHit_post_updatePromo = new Intro_WebHit_Post_UpdatePromo();
        intro_webHit_post_updatePromo.postUpdatePromo(getContext(), new IWebCallback() {
            @Override
            public void onWebResult(boolean isSuccess, String strMsg) {
                if (isSuccess) {
                    dismissProgDialog();
                    CustomToast.showToastMessage(getActivity(), "Promo is Updated", Toast.LENGTH_SHORT);
                } else {

                    dismissProgDialog();
                    CustomToast.showToastMessage(getActivity(), strMsg, Toast.LENGTH_SHORT);
//                    Toast.makeText(getActivity(), strMsg, Toast.LENGTH_SHORT).show();
//                    AppConfig.getInstance().showErrorMessage(getContext(), strMsg);
                }
            }

            @Override
            public void onWebException(Exception ex) {
                Log.d("LOG_AS", "postSignIn: Exception" + ex.getMessage());
                CustomToast.showToastMessage(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT);
//                Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT).show();
//                AppConfig.getInstance().showErrorMessage(getContext(), ex.toString());
            }
        }, promoName, discount, redemption, startDate, endDate, id_promo);
    }

    private void requestDeletePromo(String _id) {
        Intro_WebHit_Delete_Promo intro_webHit_delete_promo = new Intro_WebHit_Delete_Promo();
        intro_webHit_delete_promo.deletePromo(getContext(), new IWebCallback() {
            @Override
            public void onWebResult(boolean isSuccess, String strMsg) {
                if (isSuccess) {
                    dismissProgDialog();
                    CustomToast.showToastMessage(getActivity(), "Promo is deleted", Toast.LENGTH_SHORT);
                    getActivity().onBackPressed();
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
        }, _id);
    }

    private DatePickerDialog showCalender() {
        DatePickerDialog dpd = new DatePickerDialog(getContext(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, date, calendar
                .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dpd.getDatePicker().setMinDate(min_date);
        return dpd;

    }

    private void initializeDate() {
        todayCalender = Calendar.getInstance(Locale.ENGLISH);

        date = (view, year, monthOfYear, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            if (isValidFrom) setValidFrom();
            else if (isValidTill) setValidTill();


        };
    }


    public void setValidFrom() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        edtValidFrom.setText(sdf.format(calendar.getTime()));
        isValidFrom = false;
    }


    public void setValidTill() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        edtValidTill.setText(sdf.format(calendar.getTime()));
        isValidTill = false;
    }
}
