package com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.Results;

import android.app.Dialog;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.armoomragames.denketa.AppConfig;
import com.armoomragames.denketa.IntroActivity;
import com.armoomragames.denketa.IntroAuxilaries.WebServices.Intro_WebHit_Get_All_Results;
import com.armoomragames.denketa.IntroAuxilaries.WebServices.Intro_WebHit_Post_Contactus;
import com.armoomragames.denketa.IntroAuxilaries.WebServices.Intro_WebHit_Post_Results;
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.CustomToast;
import com.armoomragames.denketa.Utils.IBadgeUpdateListener;
import com.armoomragames.denketa.Utils.IWebCallback;
import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class RateAppFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private static final String[] paths = {"0", "1", "2", "3", "4", "5"};
    Spinner spinner;
    RelativeLayout rlToolbar, rlBack, rlCross, rl_leave_coment, rl_comnt_sent;
    String danetka_id = "";
    String danetka_name = "";
    TextView edtTime;
    EditText edtInvestigator, frg_make_edtAnswer, edtMaster, txv_invest_num;
    TextView edtUsed;
    LinearLayout llSaveResult, llSavedResults, lltxv_enter_coment;
    IBadgeUpdateListener mBadgeUpdateListener;
    Bundle bundle;
    RatingBar simpleRatingBar;
    ImageView imv_send;
    String formattedDate = "";
    String StartTimeDate = "";
    long hours, minutes, seconds;
    int selected = 0;
    int size = 0;
    private Dialog progressDialog;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_rate_app, container, false);


        init();
        bindViews(frg);

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss", Locale.getDefault());
        formattedDate = df.format(c);


        SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
        try {
            Date dateStartTimeDate = format.parse(StartTimeDate);
            Date dateformattedDate = format.parse(formattedDate);
            long diff = dateformattedDate.getTime() - dateStartTimeDate.getTime();

            long timeInSeconds = diff / 1000;

            hours = timeInSeconds / 3600;
            timeInSeconds = timeInSeconds - (hours * 3600);
            minutes = timeInSeconds / 60;
            timeInSeconds = timeInSeconds - (minutes * 60);
            seconds = timeInSeconds;

            String str_hour = "00";
            String str_min = "00";
            String str_sec = "00";

            if (hours == 0) str_hour = "00";
            else if (hours == 1) str_hour = "01";
            else if (hours == 2) str_hour = "02";
            else if (hours == 3) str_hour = "03";
            else if (hours == 4) str_hour = "04";
            else if (hours == 5) str_hour = "05";
            else if (hours == 6) str_hour = "06";
            else if (hours == 7) str_hour = "07";
            else if (hours == 8) str_hour = "08";
            else if (hours == 9) str_hour = "09";


            if (minutes == 0) str_min = "00";
            else if (minutes == 1) str_min = "01";
            else if (minutes == 2) str_min = "02";
            else if (minutes == 3) str_min = "03";
            else if (minutes == 4) str_min = "04";
            else if (minutes == 5) str_min = "05";
            else if (minutes == 6) str_min = "06";
            else if (minutes == 7) str_min = "07";
            else if (minutes == 8) str_min = "08";
            else if (minutes == 9) str_min = "09";


            if (seconds == 0) str_sec = "00";
            else if (seconds == 1) str_sec = "01";
            else if (seconds == 2) str_sec = "02";
            else if (seconds == 3) str_sec = "03";
            else if (seconds == 4) str_sec = "04";
            else if (seconds == 5) str_sec = "05";
            else if (seconds == 6) str_sec = "06";
            else if (seconds == 7) str_sec = "07";
            else if (seconds == 8) str_sec = "08";
            else if (seconds == 9) str_sec = "09";


            String f_sec, f_min, f_hour;
            if (seconds >= 10) f_sec = seconds + "";
            else f_sec = str_sec;

            if (minutes >= 10) f_min = minutes + "";
            else f_min = str_min;

            if (hours >= 10) f_hour = hours + "";
            else f_hour = str_hour;

            String hour_min_sec = f_hour + ":" + f_min + ":" + f_sec;


            Log.d("TIME", "hour_min_sec " + hour_min_sec);

            edtTime.setText("" + hour_min_sec);

        } catch (ParseException e) {

            Toast.makeText(getContext(), "TIme " + e.getMessage(), Toast.LENGTH_SHORT);
            e.printStackTrace();
        }

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

    private void init() {
        setToolbar();
        bundle = this.getArguments();
        if (bundle != null) {
            danetka_id = bundle.getString("key_danetka_id");
            danetka_name = bundle.getString("key_danetka_name");
            StartTimeDate = bundle.getString("key_danetka_formattedDate");

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
        simpleRatingBar = (RatingBar) frg.findViewById(R.id.simpleRatingBar);
        rlToolbar = frg.findViewById(R.id.act_intro_rl_toolbar);
        rlBack = frg.findViewById(R.id.act_intro_lay_toolbar_rlBack);
        rlCross = frg.findViewById(R.id.act_intro_lay_toolbar_rlCross);
        frg_make_edtAnswer = frg.findViewById(R.id.frg_make_edtAnswer);
        rl_comnt_sent = frg.findViewById(R.id.rl_comnt_sent);
        txv_invest_num = frg.findViewById(R.id.frg_my_results_txv_invest_num);
        llSavedResults = frg.findViewById(R.id.frg_my_results_ll_saved_results);
        llSaveResult = frg.findViewById(R.id.frg_my_results_ll_save_results);
        rl_leave_coment = frg.findViewById(R.id.rl_leave_coment);

        edtTime = frg.findViewById(R.id.frg_my_results_edt_time);
        edtMaster = frg.findViewById(R.id.frg_my_results_edt_master);
        edtInvestigator = frg.findViewById(R.id.frg_my_results_edt_invest);
        spinner = frg.findViewById(R.id.frg_signup_cmplt_spinr_gender);
        imv_send = frg.findViewById(R.id.imv_send);

        edtUsed = frg.findViewById(R.id.frg_signup_cmplt_spinr_txvGend);
        lltxv_enter_coment = frg.findViewById(R.id.txv_enter_coment);

        edtMaster.setText(AppConfig.getInstance().mUser.getName());

        rlBack.setOnClickListener(this);
        rlCross.setOnClickListener(this);

        imv_send.setOnClickListener(this);
        llSaveResult.setOnClickListener(this);
        rl_leave_coment.setOnClickListener(this);
        txv_invest_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if (s.toString().startsWith(" ")) {
                        txv_invest_num.setText("");
                    } else if (Integer.parseInt(txv_invest_num.getText().toString()) > 10 || Integer.parseInt(txv_invest_num.getText().toString()) < 1) {
                        txv_invest_num.setText("");
                    }
                } catch (Exception e) {
                }
            }
        });


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        requestGet_All_Results();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        selected = position;
        switch (position) {
            case 0:
                // Whatever you want to happen when the first item gets selected
                break;
            case 1:
                // Whatever you want to happen when the second item gets selected
                break;
            case 2:
                // Whatever you want to happen when the thrid item gets selected
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.act_intro_lay_toolbar_rlBack:
                ((IntroActivity) getActivity()).navToPreSignInVAFragment();
                break;
            case R.id.act_intro_lay_toolbar_rlCross:
                ((IntroActivity) getActivity()).navToPreSignInVAFragment();

                break;
//            case R.id.imv_send:
//                lltxv_enter_coment.setVisibility(View.GONE);
//                break;


            case R.id.frg_my_results_ll_save_results:

                AppConfig.getInstance().closeKeyboard(getActivity());
                if (!edtInvestigator.getText().toString().equals("") && !edtMaster.getText().toString().equals("")
                ) {

                    Date c = Calendar.getInstance().getTime();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    String formattedDate = df.format(c);
                    JsonObject jsonObject = new JsonObject();

                    jsonObject.addProperty("investigatorName", edtInvestigator.getText().toString());
                    jsonObject.addProperty("riglettosUsed", selected + "");
                    jsonObject.addProperty("time",minutes + "");
                    jsonObject.addProperty("investegorNumber", txv_invest_num.getText().toString());
                    jsonObject.addProperty("masterName", edtMaster.getText().toString());
                    jsonObject.addProperty("date", formattedDate);
                    jsonObject.addProperty("masterId", AppConfig.getInstance().mUser.getUser_Id());
                    jsonObject.addProperty("danetkaId", danetka_id);
                    requestContactUs(jsonObject.toString());


                } else
                    CustomToast.showToastMessage(getActivity(), "Please fill all fields", Toast.LENGTH_LONG);
                break;
            case R.id.rl_leave_coment:

                AppConfig.getInstance().closeKeyboard(getActivity());
                if (!frg_make_edtAnswer.getText().toString().equals("")) {
                    showProgDialog();
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("email", AppConfig.getInstance().mUser.getEmail());
                    jsonObject.addProperty("text", "Danetka – " + danetka_name + " - " + simpleRatingBar.getRating() + " stars - " + frg_make_edtAnswer.getText().toString());
                    requestComment(jsonObject.toString());
                } else
                    CustomToast.showToastMessage(getActivity(), "Please enter your comments", Toast.LENGTH_LONG);
                break;
        }
    }

    private void requestComment(String _signUpEntity) {

        Intro_WebHit_Post_Contactus intro_webHit_post_contactus = new Intro_WebHit_Post_Contactus();
        intro_webHit_post_contactus.postContact(getContext(), new IWebCallback() {
            @Override
            public void onWebResult(boolean isSuccess, String strMsg) {
                if (isSuccess) {
                    dismissProgDialog();
                    rl_comnt_sent.setVisibility(View.VISIBLE);
                    rl_leave_coment.setVisibility(View.GONE);
                    lltxv_enter_coment.setVisibility(View.GONE);
//                    CustomToast.showToastMessage(getActivity(),"Comment sent!", Toast.LENGTH_SHORT);
                    frg_make_edtAnswer.setText("");
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

    private void requestContactUs(String _signUpEntity) {
        llSavedResults.setVisibility(View.VISIBLE);
        llSaveResult.setVisibility(View.GONE);
        showProgDialog();
        Intro_WebHit_Post_Results intro_webHit_post_results = new Intro_WebHit_Post_Results();
        intro_webHit_post_results.postResults(getContext(), new IWebCallback() {
            @Override
            public void onWebResult(boolean isSuccess, String strMsg) {
                if (isSuccess) {
                    dismissProgDialog();
                    CustomToast.showToastMessage(getActivity(), "Success! Result Added", Toast.LENGTH_SHORT);

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


    private void requestGet_All_Results() {

        showProgDialog();
        Intro_WebHit_Get_All_Results intro_webHit_get_all_results = new Intro_WebHit_Get_All_Results();

        intro_webHit_get_all_results.getResults(new IWebCallback() {
            @Override
            public void onWebResult(boolean isSuccess, String strMsg) {
                dismissProgDialog();
                if (isSuccess) {
                    if (Intro_WebHit_Get_All_Results.responseObject != null &&
                            Intro_WebHit_Get_All_Results.responseObject.getData() != null) {
                        size = Intro_WebHit_Get_All_Results.responseObject.getData().size();

                        txv_invest_num.setText((size + 1) + "");
                    }

                } else {

                    size = 1;
                    txv_invest_num.setText((size) + "");
                }
            }

            @Override
            public void onWebException(Exception ex) {
                dismissProgDialog();
                size = 1;
                txv_invest_num.setText((size) + "");
            }
        }, Integer.parseInt(danetka_id));

    }

}
