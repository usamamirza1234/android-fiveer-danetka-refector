package com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.GameSession;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import com.armoomragames.denketa.IntroActivity;
import com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.BundleDiscountFragment;
import com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.DModel_MyDenketa;
import com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.PaymentDetailFragment;
import com.armoomragames.denketa.IntroAuxilaries.WebServices.Intro_WebHit_Get_More_Danektas;
import com.armoomragames.denketa.IntroAuxilaries.WebServices.Intro_WebHit_Post_AddUserDanetkas;
import com.armoomragames.denketa.IntroAuxilaries.WebServices.Intro_WebHit_Post_Played;
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.CustomToast;
import com.armoomragames.denketa.Utils.IBadgeUpdateListener;
import com.armoomragames.denketa.Utils.IWebCallback;
import com.armoomragames.denketa.Utils.JustifyTextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class QuestionFragment extends Fragment implements View.OnClickListener {
    ArrayList<DModel_MyDenketa> lst_MyDenketa;
    RelativeLayout rlToolbar, rlBack, rlCross;
    LinearLayout llSeeAnswer;
    RelativeLayout rlMaster;
    LinearLayout llPaynow;
    LinearLayout llBundleDiscount;
    IBadgeUpdateListener mBadgeUpdateListener;
    Bundle bundle;
    String danetka_Image, danetkaID;
    int position = 0;

    boolean isInvestigator = false;
    boolean isMoreDanetka = false;
    TextView txvDanetkaName;
    TextView txvQuestion;
    ImageView img;
    Dialog progressDialog = null; // Context, this, etc.

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_question, container, false);

        init();
        bindViews(frg);
        setData();


        return frg;
    }

    private void setData() {
        try {
            txvDanetkaName.setText(lst_MyDenketa.get(position).getStrName() + "");
            txvQuestion.setText(lst_MyDenketa.get(position).getQuestion() + "");
            danetka_Image = "http://18.119.55.236:2000/images/" + lst_MyDenketa.get(position).getStrImage();

            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.ic_logo)
                    .error(R.drawable.ic_logo)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH)
                    .dontAnimate()
                    .dontTransform();

            Glide.with(getContext())
                    .load(danetka_Image)
                    .apply(options)
                    .into(img);


            if (!isInvestigator) {
                if (!isMoreDanetka) {
                    llPaynow.setVisibility(View.GONE);
                    llBundleDiscount.setVisibility(View.GONE);
                    llSeeAnswer.setVisibility(View.VISIBLE);
                } else {
                    llPaynow.setVisibility(View.VISIBLE);
                    llBundleDiscount.setVisibility(View.VISIBLE);
                    llSeeAnswer.setVisibility(View.VISIBLE);
                }
            } else {
                llPaynow.setVisibility(View.GONE);
                llBundleDiscount.setVisibility(View.GONE);
                llSeeAnswer.setVisibility(View.GONE);
            }
        } catch (Exception e) {

        }

    }

    void init() {
        bundle = this.getArguments();
        if (bundle != null) {
            position = bundle.getInt("key_danetka_position");
            isInvestigator = bundle.getBoolean("key_danetka_is_investigator", false);
            isMoreDanetka = bundle.getBoolean("key_danetka_is_more_danetka", false);
            danetkaID = bundle.getString("key_danetka_is_more_danetka_danetkaID");
            lst_MyDenketa = bundle.getParcelableArrayList("list");

        }
    }

    private void bindViews(View frg) {

        llSeeAnswer = frg.findViewById(R.id.frg_denketa_question_llSeeAnswer);
        txvDanetkaName = frg.findViewById(R.id.frg_my_results_txv_danetkaname);
        txvQuestion = frg.findViewById(R.id.txvQuestion);
        llPaynow = frg.findViewById(R.id.frg_denketa_question_llBuyNow);
        llBundleDiscount = frg.findViewById(R.id.frg_denketa_question_llBundleDiscount);
        rlToolbar = frg.findViewById(R.id.act_intro_rl_toolbar);
        rlBack = frg.findViewById(R.id.act_intro_lay_toolbar_rlBack);
        img = frg.findViewById(R.id.img);
//        rlCross = frg.findViewById(R.id.act_intro_lay_toolbar_rlCross);

        rlBack.setOnClickListener(this);
//        rlCross.setOnClickListener(this);

        llSeeAnswer.setOnClickListener(this);
        llPaynow.setOnClickListener(this);
        llBundleDiscount.setOnClickListener(this);


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
            case R.id.frg_denketa_question_llSeeAnswer:
                if (!isMoreDanetka && !isInvestigator)
                    openDialog();
                else if (isMoreDanetka && !isInvestigator) {
                    openDialogCredits();
                } else
                    navToDenketaAnswerFragment();

                break;

            case R.id.frg_denketa_question_llBundleDiscount:
                if (AppConfig.getInstance().mUser.isLoggedIn()) {
                    bundle.putInt("key_danetka_position", position);
                    bundle.putBoolean("key_danetka_is_investigator", isInvestigator);
                    bundle.putBoolean("key_danetka_is_more_danetka", isMoreDanetka);
                    ((IntroActivity) getActivity()).navtoMainActivity(position,isMoreDanetka,isInvestigator,false);
                } else ((IntroActivity) getActivity()).navToSigninFragment();
                break;
            case R.id.frg_denketa_question_llBuyNow:
                if (AppConfig.getInstance().mUser.isLoggedIn()) {
                    bundle.putInt("key_danetka_position", position);
                    bundle.putBoolean("key_danetka_is_investigator", isInvestigator);
                    bundle.putBoolean("key_danetka_is_more_danetka", isMoreDanetka);
                    ((IntroActivity) getActivity()).navtoMainActivity(position,isMoreDanetka,isInvestigator,true);
                    //navToPaymentDetailFragment

                } else ((IntroActivity) getActivity()).navToSigninFragment();

                break;

            case R.id.rl_popup_parent:
                dismissProgDialog();
                navToDenketaAnswerFragment();
                break;

            case R.id.txv_dialoge_yes:
                dismissProgDialog();

                int GC = Integer.parseInt(AppConfig.getInstance().mUser.getGameCredits());

                if (GC != 0) {
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("danetkasId", danetkaID);
                    requestAddUserDanetkas(jsonObject.toString());
                } else {
                    popUpNoCredits();
                }
                break;
            case R.id.txv_dialoge_no:
                dismissProgDialog();
                break;

        }
    }

    private void requestAddUserDanetkas(String _signUpEntity) {
        showProgDialog();
        Intro_WebHit_Post_AddUserDanetkas intro_webHit_post_addUserDanetkas = new Intro_WebHit_Post_AddUserDanetkas();
        intro_webHit_post_addUserDanetkas.postAddUserDanetkas(getActivity(), new IWebCallback() {
            @Override
            public void onWebResult(boolean isSuccess, String strMsg) {
                if (isSuccess) {
                    dismissProgDialog();
                    ((IntroActivity) getActivity()).navToPreSignInVAFragment();
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

    private void navToDenketaAnswerFragment() {
        FragmentManager fm = getFragmentManager();
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
        ft.hide(this);
        ft.commit();
    }

    private void navToBundleDiscountFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new BundleDiscountFragment();
//        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
//                R.anim.enter_from_left, R.anim.exit_to_right);//not required
        Bundle bundle = new Bundle();

        bundle.putInt("key_danetka_position", position);
        bundle.putBoolean("key_danetka_is_investigator", isInvestigator);
        bundle.putBoolean("key_danetka_is_more_danetka", isMoreDanetka);

        frag.setArguments(bundle);
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_BundleDiscountFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_BundleDiscountFragment);
        ft.hide(this);
        ft.commit();
    }

    private void navToPaymentDetailFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new PaymentDetailFragment();
//        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
//                R.anim.enter_from_left, R.anim.exit_to_right);//not required
        Bundle bundle = new Bundle();
        bundle.putBoolean("key_is_coming_from_bundle", false);
        bundle.putString("key_danetka_danetkaID", "" + Intro_WebHit_Get_More_Danektas.responseObject.getData().getListing().get(position).getId());
        bundle.putString("key_danetka_sub_total", "1");
        bundle.putString("key_danetka_total", "1.00");
        bundle.putString("key_danetka_number", "1");
        bundle.putString("key_danetka_discount", "0.01");
        bundle.putInt("key_danetka_position", position);
        bundle.putBoolean("key_danetka_is_investigator", isInvestigator);
        bundle.putBoolean("key_danetka_is_more_danetka", isMoreDanetka);
        frag.setArguments(bundle);
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_PaymentDetailFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_PaymentDetailFragment);
        ft.hide(this);
        ft.commit();
    }

    public void openDialog() {
        progressDialog = new Dialog(getActivity(), R.style.AppTheme);
//        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.popup_dark)));
        progressDialog.setContentView(R.layout.dialog_master);
        rlMaster = progressDialog.findViewById(R.id.rl_popup_parent);
        rlMaster.setOnClickListener(this);
        if (AppConfig.getInstance().mUser.isLoggedIn())
            requestPlayed(lst_MyDenketa.get(position).getDanetkaIDPlayed());
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }


    private void requestPlayed(String _signUpEntity) {

        Intro_WebHit_Post_Played intro_webHit_post_played = new Intro_WebHit_Post_Played();
        intro_webHit_post_played.postPlayed(getContext(), new IWebCallback() {
            @Override
            public void onWebResult(boolean isSuccess, String strMsg) {
            }

            @Override
            public void onWebException(Exception ex) {
                dismissProgDialog();
            }
        }, _signUpEntity);
    }

    private void dismissProgDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    public void openDialogCredits() {


        progressDialog = new Dialog(getActivity(), R.style.AppTheme);
//        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.popup_dark)));
        progressDialog.setContentView(R.layout.dialog_unclock_danetka);
        TextView txvDetails = progressDialog.findViewById(R.id.dailog_txvDetails);
        TextView txv_dialoge_yes = progressDialog.findViewById(R.id.txv_dialoge_yes);
        TextView txv_dialoge_no = progressDialog.findViewById(R.id.txv_dialoge_no);
        txv_dialoge_yes.setOnClickListener(this);
        txv_dialoge_no.setOnClickListener(this);
        int GC = Integer.parseInt(AppConfig.getInstance().mUser.getGameCredits());

//        if (GC != 0)
        txvDetails.setText("Unlocking this Danetka \n costs 1 Game Credit. \n You have " + AppConfig.getInstance().mUser.getGameCredits() + " Game Credits. \n DO YOU WANT TO PROCEED?");
//        else {
//            txvDetails.setText("You don’t have enough\n  Game Credits.");
//            txv_dialoge_yes.setVisibility(View.GONE);
//            txv_dialoge_no.setVisibility(View.GONE);
//        }

        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.setCancelable(true);

        progressDialog.show();
    }


    private void popUpNoCredits() {
        progressDialog = new Dialog(getActivity(), R.style.AppTheme);
//        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.popup_dark)));
        progressDialog.setContentView(R.layout.dialog_unclock_danetka);
        TextView txvDetails = progressDialog.findViewById(R.id.dailog_txvDetails);
        TextView txv_dialoge_yes = progressDialog.findViewById(R.id.txv_dialoge_yes);
        TextView txv_dialoge_no = progressDialog.findViewById(R.id.txv_dialoge_no);
        txv_dialoge_yes.setOnClickListener(this);
        txv_dialoge_no.setOnClickListener(this);

        txvDetails.setText("You don’t have enough\n  Game Credits.");
        txv_dialoge_yes.setVisibility(View.GONE);
        txv_dialoge_no.setVisibility(View.GONE);
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.setCancelable(true);

        progressDialog.show();
    }

}
