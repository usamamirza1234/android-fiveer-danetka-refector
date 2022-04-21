package com.armoomragames.denketa.IntroAuxilaries.SettingsAuxillaries;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.armoomragames.denketa.AppConfig;
import com.armoomragames.denketa.IntroActivity;
import com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.Results.MyProfileFragment;
import com.armoomragames.denketa.IntroAuxilaries.WebServices.Intro_WebHit_Post_AddUserProfile;
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.IBadgeUpdateListener;
import com.armoomragames.denketa.Utils.IWebCallback;
import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class SignUpCompleteProfileFragment extends Fragment implements View.OnClickListener {
    public static String[] country = new String[]{"NOT SPECIFIED", "Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra", "Angola", "Anguilla",

            "Antarctica", "Antigua and Barbuda", "Argentina", "Armenia", "Aruba", "Australia", "Austria",

            "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium",

            "Belize", "Benin", "Bermuda", "Bhutan", "Bolivia", "Bosnia and Herzegovina", "Botswana",

            "Brazil", "British Indian Ocean Territory", "British Virgin Islands", "Brunei", "Bulgaria",

            "Burkina Faso", "Burma (Myanmar)", "Burundi", "Cambodia", "Cameroon", "Canada", "Cape Verde",

            "Cayman Islands", "Central African Republic", "Chad", "Chile", "China", "Christmas Island",

            "Cocos (Keeling) Islands", "Colombia", "Comoros", "Cook Islands", "Costa Rica",

            "Croatia", "Cuba", "Cyprus", "Czech Republic", "Democratic Republic of the Congo",

            "Denmark", "Djibouti", "Dominica", "Dominican Republic",

            "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia",

            "Ethiopia", "Falkland Islands", "Faroe Islands", "Fiji", "Finland", "France", "French Polynesia",

            "Gabon", "Gambia", "Gaza Strip", "Georgia", "Germany", "Ghana", "Gibraltar", "Greece",

            "Greenland", "Grenada", "Guam", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana",

            "Haiti", "Holy See (Vatican City)", "Honduras", "Hong Kong", "Hungary", "Iceland", "India",

            "Indonesia", "Iran", "Iraq", "Ireland", "Isle of Man", "Israel", "Italy", "Ivory Coast", "Jamaica",

            "Japan", "Jersey", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Kosovo", "Kuwait",

            "Kyrgyzstan", "Laos", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein",

            "Lithuania", "Luxembourg", "Macau", "Macedonia", "Madagascar", "Malawi", "Malaysia",

            "Maldives", "Mali", "Malta", "Marshall Islands", "Mauritania", "Mauritius", "Mayotte", "Mexico",

            "Micronesia", "Moldova", "Monaco", "Mongolia", "Montenegro", "Montserrat", "Morocco",

            "Mozambique", "Namibia", "Nauru", "Nepal", "Netherlands", "Netherlands Antilles", "New Caledonia",

            "New Zealand", "Nicaragua", "Niger", "Nigeria", "Niue", "Norfolk Island", "North Korea",

            "Northern Mariana Islands", "Norway", "Oman", "Pakistan", "Palau", "Panama",

            "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Pitcairn Islands", "Poland",

            "Portugal", "Puerto Rico", "Qatar", "Republic of the Congo", "Romania", "Russia", "Rwanda",

            "Saint Barthelemy", "Saint Helena", "Saint Kitts and Nevis", "Saint Lucia", "Saint Martin",

            "Saint Pierre and Miquelon", "Saint Vincent and the Grenadines", "Samoa", "San Marino",

            "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Serbia", "Seychelles", "Sierra Leone",

            "Singapore", "Slovakia", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Korea",

            "Spain", "Sri Lanka", "Sudan", "Suriname", "Swaziland", "Sweden", "Switzerland",

            "Syria", "Taiwan", "Tajikistan", "Tanzania", "Thailand", "Timor-Leste", "Togo", "Tokelau",

            "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Turks and Caicos Islands",

            "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States", "Uruguay", "US Virgin Islands", "Uzbekistan", "Vanuatu", "Venezuela", "Vietnam",

            "Wallis and Futuna", "West Bank", "Yemen", "Zambia", "Zimbabwe"};
    RelativeLayout rlToolbar, rlBack, rlCross;
    Spinner spinnerSort = null;
    Spinner spinnerSort1 = null;
    TextView txv_Nationality, txv_Gend, txvSignout, txv_dob;
    RelativeLayout rlGetStarted;
    Calendar calendar;
    DatePickerDialog.OnDateSetListener date;
    EditText edtName, edtEmail;
    IBadgeUpdateListener mBadgeUpdateListener;
    Calendar todayCalender;
    //    List<String> lstNationalities;
    List<String> lstGender;
    private Dialog progressDialog;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_signup_complete, container, false);


        init();
        bindViews(frg);
        initializeDate();
        edtEmail.setText(AppConfig.getInstance().mUser.getEmail());

        if (AppConfig.getInstance().mUser.Name.isEmpty()) {
            String email = AppConfig.getInstance().mUser.getEmail();
            String[] For_split_email = email.split("[@]");
            edtName.setText(For_split_email[0] + "");
        } else edtName.setText(AppConfig.getInstance().mUser.getName());
        if (!AppConfig.getInstance().mUser.getNationality().isEmpty())
            txv_Nationality.setText(AppConfig.getInstance().mUser.getNationality());
        if (!AppConfig.getInstance().mUser.getGender().isEmpty()) {
            if (AppConfig.getInstance().mUser.getGender().equals("other"))
                txv_Gend.setText("NOT SPECIFIED");
            else txv_Gend.setText(AppConfig.getInstance().mUser.getGender());
        }
        if (AppConfig.getInstance().mUser.getDOB().isEmpty()) {
            txv_dob.setText(AppConfig.getInstance().getInstallDate());
        } else
            txv_dob.setText(AppConfig.getInstance().mUser.getDOB());


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
        calendar = Calendar.getInstance();


        setToolbar();


//        lstNationalities = new ArrayList<>();
//
//        lstNationalities.add("German");
//        lstNationalities.add("US");
//        lstNationalities.add("Select Nationality");

        lstGender = new ArrayList<>();
        lstGender.add("NOT SPECIFIED");
        lstGender.add("Male");
        lstGender.add("Female");
        lstGender.add("Select Gender");

//        lstNationalities.add("U");
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
        spinnerSort = frg.findViewById(R.id.act_main_spinr_city);
        spinnerSort1 = frg.findViewById(R.id.frg_signup_cmplt_spinr_gender);

        txvSignout = frg.findViewById(R.id.fg_signup_complete_txvSignOut);
        rlGetStarted = frg.findViewById(R.id.fg_signup_complete_rlRegister);

        edtName = frg.findViewById(R.id.frg_profile_edtname);
        edtEmail = frg.findViewById(R.id.frg_profile_edtEmail);
        txv_dob = frg.findViewById(R.id.frg_profile_edtdob);

        txv_Nationality = frg.findViewById(R.id.frg_profile_edtNationality);
        txv_Gend = frg.findViewById(R.id.frg_signup_cmplt_spinr_txvGend);

        rlGetStarted.setOnClickListener(this);
        txvSignout.setOnClickListener(this);
        txv_dob.setOnClickListener(this);


        SpinnerAdpter nationalitySpinner = new SpinnerAdpter(getContext(), Arrays.asList(country));
        spinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                int Pos = Integer.parseInt(selectedItem);
                txv_Nationality.setText(country[Pos]);

            } // to close the onItemSelected

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerSort.setAdapter(nationalitySpinner);
        spinnerSort.setSelection(nationalitySpinner.getCount());


        SpinnerAdpter nationalitySpinner1 = new SpinnerAdpter(getContext(), lstGender);
        spinnerSort1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                int Pos = Integer.parseInt(selectedItem);
                txv_Gend.setText(lstGender.get(Pos));

            } // to close the onItemSelected

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerSort1.setAdapter(nationalitySpinner1);
        spinnerSort1.setSelection(nationalitySpinner1.getCount());

        editTextWatchers();


        for (int i = 0; i < lstGender.size(); i++) {
            if (lstGender.get(i).equalsIgnoreCase(AppConfig.getInstance().mUser.getGender())) {
                spinnerSort1.setSelection(i);
            } else spinnerSort1.setSelection(0);
        }


        for (int i = 0; i < country.length; i++) {
            if (country[i].equalsIgnoreCase(AppConfig.getInstance().mUser.getNationality())) {
                spinnerSort.setSelection(i);
            } else spinnerSort.setSelection(0);
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
        edtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().startsWith(" ")) {
                    edtName.setText("");
                }
            }
        });
        txv_dob.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().startsWith(" ")) {
                    txv_dob.setText("");
                }
            }
        });
        txv_Nationality.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().startsWith(" ")) {
                    txv_Nationality.setText("");
                }
            }
        });
        txv_Gend.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().startsWith(" ")) {
                    txv_Gend.setText("");
                }
            }
        });

    }

    private void closeKeyboard() {
        AppConfig.getInstance().closeKeyboard(getActivity());

    }

    private boolean checkDobErrorCondition() {
//        if (edtDOB.getText().toString().equalsIgnoreCase("Date of Birth")) {
        if (txv_dob.getText().toString().isEmpty()) {
//            AppConfig.getInstance().showErrorMessage(getContext(), "Date of birth is not selected");
            Toast.makeText(getActivity(), "Date of birth is not selected", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean checkNameErrorCondition() {
        if (edtName.getText().toString().isEmpty()) {
//            AppConfig.getInstance().showErrorMessage(getContext(), "Empty name field");
            Toast.makeText(getActivity(), "Empty Name Feild", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean checkCityErrorCheck() {
        if (txv_Nationality.getText().toString().isEmpty()) {
//        if (edtNationality.getText().toString().equalsIgnoreCase("Select City")) {
//            AppConfig.getInstance().showErrorMessage(getContext(), "City is not selected");
            Toast.makeText(getActivity(), "Country is not selected", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean checkGenderCheck() {
//        if (edtGender.getText().toString().equalsIgnoreCase("Select Gender")) {
        if (txv_Gend.getText().toString().equalsIgnoreCase(lstGender.get(lstGender.size() - 1))) {
//            AppConfig.getInstance().showErrorMessage(getContext(), "City is not selected");
            Toast.makeText(getActivity(), "Gender is not selected", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
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

    private boolean checkNationality() {
//        if (edtGender.getText().toString().equalsIgnoreCase("Select Gender")) {
        if (txv_Nationality.getText().toString().equalsIgnoreCase(country[(country.length - 1)])) {
//            AppConfig.getInstance().showErrorMessage(getContext(), "City is not selected");
            Toast.makeText(getActivity(), "Nationality is not selected", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private void checkErrorConditions() {
        if (checkCityErrorCheck() && checkNameErrorCondition() && checkDobErrorCondition() && checkGenderCheck() && checkNationality()) {

            closeKeyboard();
            JsonObject jsonObject = new JsonObject();
//            jsonObject.addProperty("email", edtEmail.getText().toString());
            jsonObject.addProperty("name", edtName.getText().toString());
            jsonObject.addProperty("userName", edtEmail.getText().toString());
            jsonObject.addProperty("dateOfBirth", txv_dob.getText().toString());

            if (txv_Gend.getText().toString().equals("NOT SPECIFIED"))
                jsonObject.addProperty("gender", "other");
            else jsonObject.addProperty("gender", txv_Gend.getText().toString().toLowerCase());


            jsonObject.addProperty("nationality", txv_Nationality.getText().toString().toLowerCase());

            requestUserProfile(jsonObject.toString());

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

    private void requestUserProfile(String _signUpEntity) {
        showProgDialog();
        Intro_WebHit_Post_AddUserProfile intro_webHit_post_addUserProfile = new Intro_WebHit_Post_AddUserProfile();
        intro_webHit_post_addUserProfile.postAddProfile(getContext(), new IWebCallback() {
            @Override
            public void onWebResult(boolean isSuccess, String strMsg) {
                dismissProgDialog();
                if (isSuccess) {

                    AppConfig.getInstance().mUser.Name = Intro_WebHit_Post_AddUserProfile.responseObject.getData().getName();
                    AppConfig.getInstance().mUser.DOB = Intro_WebHit_Post_AddUserProfile.responseObject.getData().getDateOfBirth();
                    AppConfig.getInstance().mUser.Gender = Intro_WebHit_Post_AddUserProfile.responseObject.getData().getGender();
                    AppConfig.getInstance().mUser.Nationality = Intro_WebHit_Post_AddUserProfile.responseObject.getData().getNationality();

                    AppConfig.getInstance().saveUserProfile();

                    navToMYProfilePassword();
                } else {
                    Toast.makeText(getActivity(), strMsg, Toast.LENGTH_SHORT).show();
//                    AppConfig.getInstance().showErrorMessage(getContext(), strMsg);
                }
            }

            @Override
            public void onWebException(Exception ex) {
                dismissProgDialog();
                Toast.makeText(getActivity(), "Enter Valid Date of birth", Toast.LENGTH_SHORT).show();
//                AppConfig.getInstance().showErrorMessage(getContext(), ex.toString());
            }
        }, _signUpEntity);
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
            case R.id.fg_signup_complete_rlRegister:
                checkErrorConditions();
                break;
            case R.id.fg_signup_complete_txvSignOut:
                AppConfig.getInstance().navtoLogin();
                break;
            case R.id.frg_profile_edtdob:
                showCalender();

                break;
        }
    }

    private void showCalender() {
//        new SingleDateAndTimePickerDialog.Builder(getContext())
//                .bottomSheet()
//                .curved()
//                .displayMinutes(false)
//                .displayHours(false)
//                .displayDays(false)
//                .displayMonth(true)
//                .displayYears(true).mainColor(Color.BLACK)
//                .displayDaysOfMonth(true).listener(new SingleDateAndTimePickerDialog.Listener() {
//            @Override
//            public void onDateSelected(Date date) {
//                DateFormat format = new SimpleDateFormat("dd MMMM, yyyy");
//                String datee = format.parse(Date.parse( date.toString()));
//
//                txvDob.setText(date.toString());
////                if (date.after(yesterday()))
////                {
////                    goalDate =datee;
////                    txvCalender.setText(datee);
////                    btnNext.setEnabled(true);
////                }
//            }
//        }).defaultDate(calendar.getTime()).display();
////
////

        new DatePickerDialog(getContext(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, date, calendar
                .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();

//        String myFormat = "dd-MM-yyyy"; //In which you need put here
//        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
//        txv_dob.setText(sdf.format(calendar.getTime()));
    }

    private void initializeDate() {
        todayCalender = Calendar.getInstance(Locale.ENGLISH);

        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
//                rlUpdate.setClickable(true);
//                rlUpdate.setEnabled(true);

                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "yyyy-MM-dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                Log.d("LOG_AS", "calendar " + calendar.getTime());
                txv_dob.setText(sdf.format(calendar.getTime()));
//                if (todayCalender.after(calendar)) {
//
//                    txv_dob.setText(sdf.format(calendar.getTime()));
//
//                }
            }

        };


    }

    private void navToMYProfilePassword() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new MyProfileFragment();
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_MyProfileFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_MyProfileFragment);
        ft.hide(this);
        ft.commit();
    }
}
