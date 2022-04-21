package com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.Results;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.armoomragames.denketa.AppConfig;
import com.armoomragames.denketa.IntroActivity;
import com.armoomragames.denketa.IntroAuxilaries.WebServices.Intro_WebHit_Post_Update_Results;
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.CustomToast;
import com.armoomragames.denketa.Utils.IWebCallback;
import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EditResultsFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private static final String[] paths = {"0", "1", "2", "3", "4", "5"};
    RelativeLayout rlToolbar, rlBack, rlCross;
    Bundle bundle;
    String danetka_name = "";
    int danetka_id =0;
    String invest_name = "";
    String d_id = "";
    String invest_time = "";
    TextView txvDanetkaName;
    Spinner spinner;
    TextView txv_invest_num;
    EditText edtInvestigator, edtTime, edtMaster;
    LinearLayout llSaveResult, llSavedResults;
    int selected = 0;
    int id_ = 0;
    int invest_count = 0;
    int selection = 0;
    private Dialog progressDialog;
    private String Strtime;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_edit_results, container, false);

        init();
        bindViewss(frg);

        setStates();

        return frg;
    }

    private void setStates() {
        txvDanetkaName.setText(danetka_name);
        edtInvestigator.setText(invest_name);
        edtTime.setText(invest_time);
        edtMaster.setText(AppConfig.getInstance().mUser.getName());
    }

    private void init() {
        bundle = this.getArguments();
        if (bundle != null) {
            danetka_name = bundle.getString("key_danetka_name");
            danetka_id = bundle.getInt("key_danetka_id");
            d_id = bundle.getString("key_danetka_invst_danetka_id");
            invest_name = bundle.getString("key_danetka_invst_name");
            invest_time = bundle.getString("key_danetka_time");
            invest_count = bundle.getInt("key_danetka_size");
            selection = bundle.getInt("key_danetka_selection");
        }
    }

    private void bindViewss(View frg) {
        rlToolbar = frg.findViewById(R.id.act_intro_rl_toolbar);
        rlBack = frg.findViewById(R.id.act_intro_lay_toolbar_rlBack);
        rlCross = frg.findViewById(R.id.act_intro_lay_toolbar_rlCross);

        txvDanetkaName = frg.findViewById(R.id.frg_my_results_txv_danetkaname);
        spinner = frg.findViewById(R.id.frg_signup_cmplt_spinr_gender);

        llSavedResults = frg.findViewById(R.id.frg_my_results_ll_saved_results);
        llSaveResult = frg.findViewById(R.id.frg_my_results_ll_save_results);


        txv_invest_num = frg.findViewById(R.id.frg_my_results_txv_invest_num);
        edtTime = frg.findViewById(R.id.frg_my_results_edt_time);
        edtMaster = frg.findViewById(R.id.frg_my_results_edt_master);
        edtInvestigator = frg.findViewById(R.id.frg_my_results_edt_invest);
        txv_invest_num.setText("" + invest_count);

        rlBack.setOnClickListener(this);
        rlCross.setOnClickListener(this);

        llSaveResult.setOnClickListener(this);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        spinner.setSelection(selection);
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
                getActivity().onBackPressed();

                break;
            case R.id.act_intro_lay_toolbar_rlCross:
                ((IntroActivity) getActivity()).navToPreSignInVAFragment();
                break;

            case R.id.frg_my_results_ll_save_results:

                AppConfig.getInstance().closeKeyboard(getActivity());

                boolean isNumb = false;
                try {
                    int min = Integer.parseInt(edtTime.getText().toString());
                    isNumb = true;
                } catch (Exception e) {
                    isNumb = false;

                }

                if (isNumb){
                    if (!edtInvestigator.getText().toString().equals("") && !edtMaster.getText().toString().equals("")
                            && !edtTime.getText().toString().equals("")) {

                        Date c = Calendar.getInstance().getTime();
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        String formattedDate = df.format(c);


                        double _date = 0;
                        String _hour = "00:";
                        String _min = "00:";
                        String _sec = "00";
                        String time = "";
                        try {
                            _date = Double.parseDouble(edtTime.getText().toString());
                            if (_date >= 60) {
                                double dhour = _date / 60;
                                int hour = (int) Math.round(dhour);
                                double dmin = (dhour - hour) * 60;
                                int __min = (int) Math.round(dmin);
                                int sec = (int) Math.round(dmin - __min);

                                if (hour < 10) {
                                    if (hour == 0) _hour = "00:";
                                    else if (hour == 1) _hour = "01:";
                                    else if (hour == 2) _hour = "02:";
                                    else if (hour == 3) _hour = "03:";
                                    else if (hour == 4) _hour = "04:";
                                    else if (hour == 5) _hour = "05:";
                                    else if (hour == 6) _hour = "06:";
                                    else if (hour == 7) _hour = "07:";
                                    else if (hour == 8) _hour = "08:";
                                    else if (hour == 9) _hour = "09:";

                                }

                                if (__min < 10) {
                                    if (__min == 0) _min = "00:";
                                    else if (__min == 1) _min = "01:";
                                    else if (__min == 2) _min = "02:";
                                    else if (__min == 3) _min = "03:";
                                    else if (__min == 4) _min = "04:";
                                    else if (__min == 5) _min = "05:";
                                    else if (__min == 6) _min = "06:";
                                    else if (__min == 7) _min = "07:";
                                    else if (__min == 8) _min = "08:";
                                    else if (__min == 9) _min = "09:";

                                }

                                if (sec == 0) {
                                    _sec = "00";

                                } else _sec = sec * 60 + "";


                                time = _hour + __min + ":"  + _sec;
                                Strtime = time;
                            } else if (_date < 10 && _date > 0) {
                                if (_date == 0) _min = "00:";
                                else if (_date == 1) _min = "01:";
                                else if (_date == 2) _min = "02:";
                                else if (_date == 3) _min = "03:";
                                else if (_date == 4) _min = "04:";
                                else if (_date == 5) _min = "05:";
                                else if (_date == 6) _min = "06:";
                                else if (_date == 7) _min = "07:";
                                else if (_date == 8) _min = "08:";
                                else if (_date == 9) _min = "09:";
                                time = _hour + _min + _sec;
                                Strtime = time;
                            } else {
                                int min = (int) _date;
                                time = _hour + min + ":" + _sec;
                                Strtime = time;
                            }
                        } catch (Exception e) {
                            time = _hour + _min + _sec;
                            Strtime = time;
                        }


                        try {
                            JsonObject jsonObject = new JsonObject();
                            jsonObject.addProperty("investigatorName", edtInvestigator.getText().toString());
                            jsonObject.addProperty("riglettosUsed", selected + "");
                            jsonObject.addProperty("time", Strtime);
                            jsonObject.addProperty("investegorNumber", txv_invest_num.getText().toString());
                            jsonObject.addProperty("masterName", edtMaster.getText().toString());
                            jsonObject.addProperty("date", formattedDate);
                            jsonObject.addProperty("masterId", AppConfig.getInstance().mUser.getUser_Id());
                            jsonObject.addProperty("danetkaId", d_id);
                            requestUpdatedResult(jsonObject.toString(), Integer.parseInt(d_id));
                        }
                        catch (Exception e)
                        {

                        }



                    } else
                        CustomToast.showToastMessage(getActivity(), "Please fill all fields", Toast.LENGTH_LONG);

                }else
                    CustomToast.showToastMessage(getActivity(), "Enter time in minutes", Toast.LENGTH_LONG);




                break;
        }
    }

    private void requestUpdatedResult(String toString, int danetka_id) {
        showProgDialog();
        Intro_WebHit_Post_Update_Results intro_webHit_post_update_results = new Intro_WebHit_Post_Update_Results();
        intro_webHit_post_update_results.postUpdateResults(getContext(), new IWebCallback() {
            @Override
            public void onWebResult(boolean isSuccess, String strMsg) {
                if (isSuccess) {
                    dismissProgDialog();
                    llSavedResults.setVisibility(View.VISIBLE);
                    llSaveResult.setVisibility(View.GONE);
//                    CustomToast.showToastMessage(getActivity(), "Success! Updated Added", Toast.LENGTH_SHORT);
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
        }, toString, danetka_id);
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
}
