package com.armoomragames.denketa.IntroAuxilaries.Admin.PromoCode;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.armoomragames.denketa.IntroActivity;
import com.armoomragames.denketa.IntroAuxilaries.WebServices.Intro_WebHit_Get_All_Promos;
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.IAdapterCallback;
import com.armoomragames.denketa.Utils.IBadgeUpdateListener;
import com.armoomragames.denketa.Utils.IWebCallback;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class PromoCodesFragment extends Fragment implements View.OnClickListener {
    FloatingActionButton fab;
    ArrayList<DModel_Promo> lst_Promo;
    RelativeLayout rlToolbar, rlBack, rlCross;
    Dialog dialog;
    IBadgeUpdateListener mBadgeUpdateListener;
    PromoLsvAdapter adapter;
    ListView lsvPromo;
    private Dialog progressDialog;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_promo_codes, container, false);

        init();
        bindViews(frg);
        requestGet_All_Results();
        return frg;
    }

    private void navtoAddPromoFragment(boolean isUpdate, String startDate, String endDate, String promoName, String discount, String redemption, String id_promo) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new AddPromoFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("key_is_update", isUpdate);
        bundle.putString("key_startDate", startDate);
        bundle.putString("key_endDate", endDate);
        bundle.putString("key_promoName", promoName);
        bundle.putString("key_redemption", redemption);
        bundle.putString("key_discount", discount);
        bundle.putString("key_id_promo", id_promo);

        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_AddPromoFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_AddPromoFragment);
        frag.setArguments(bundle);
        ft.hide(this);
        ft.commit();
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


    private void requestGet_All_Results() {

        showProgDialog();
        Intro_WebHit_Get_All_Promos intro_webHit_get_all_promos = new Intro_WebHit_Get_All_Promos();

        intro_webHit_get_all_promos.getPromo(new IWebCallback() {
            @Override
            public void onWebResult(boolean isSuccess, String strMsg) {
                dismissProgDialog();
                Log.d("LOG_AS", "onSuccess: " + isSuccess);
                Log.d("LOG_AS", "onSuccess: " + strMsg);
                if (isSuccess) {
                    Log.d("LOG_AS", "onSuccess:1 " + strMsg);
                    Log.d("LOG_AS", "onSuccess:2 " + Intro_WebHit_Get_All_Promos.responseObject.getData().get(0).getPromoCode());
                    if (Intro_WebHit_Get_All_Promos.responseObject != null &&
                            Intro_WebHit_Get_All_Promos.responseObject.getData() != null
                            && Intro_WebHit_Get_All_Promos.responseObject.getData().size() > 0) {
                        Log.d("LOG_AS", "onSuccess:2 " + strMsg);

                        for (int i = 0; i < Intro_WebHit_Get_All_Promos.responseObject.getData().size(); i++) {
                            Log.d("LOG_AS", "onSuccess:2 " + Intro_WebHit_Get_All_Promos.responseObject.getData().get(i).getPromoCode());
                        }


                        for (int i = 0; i < Intro_WebHit_Get_All_Promos.responseObject.getData().size(); i++) {
                            lst_Promo.add(new DModel_Promo(
                                    Intro_WebHit_Get_All_Promos.responseObject.getData().get(i).getId(),
                                    Intro_WebHit_Get_All_Promos.responseObject.getData().get(i).getDiscount(),
                                    Intro_WebHit_Get_All_Promos.responseObject.getData().get(i).getEndDate(),
                                    Intro_WebHit_Get_All_Promos.responseObject.getData().get(i).getPromoCode(),
                                    Intro_WebHit_Get_All_Promos.responseObject.getData().get(i).getStartDate(),
                                    Intro_WebHit_Get_All_Promos.responseObject.getData().get(i).getStatus(),
                                    Intro_WebHit_Get_All_Promos.responseObject.getData().get(i).getUpdatedTime(),
                                    Intro_WebHit_Get_All_Promos.responseObject.getData().get(i).getRedemption() + ""

                            ));

                        }


                        if (adapter == null) {
                            adapter = new PromoLsvAdapter((IAdapterCallback) (eventId, position) -> {
                                switch (eventId) {
                                    case IAdapterCallback.EVENT_A:
                                        navtoAddPromoFragment(
                                                true,
                                                lst_Promo.get(position).getStartDate() + "",
                                                lst_Promo.get(position).getEndDate() + "",
                                                lst_Promo.get(position).getPromoCode() + "",
                                                lst_Promo.get(position).getDiscount() + "",
                                                lst_Promo.get(position).getRedumption() + "",
                                                lst_Promo.get(position).getId() + ""
                                        );

                                        break;
                                }

                            }, getActivity(), lst_Promo);
                            lsvPromo.setAdapter(adapter);

                        } else {
                            adapter.notifyDataSetChanged();
                        }

                        Log.d("LOG_AS", "onSuccess: end ");

                    }

                } else {
                    Toast.makeText(getContext(), strMsg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onWebException(Exception ex) {
                dismissProgDialog();
                Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

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
        setToolbar();
        lst_Promo = new ArrayList<>();
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
//        rlToolbar.setVisibility(View.VISIBLE);
        fab = frg.findViewById(R.id.fab);
        fab.setOnClickListener(this);
        lsvPromo = frg.findViewById(R.id.frg_lsvPromo);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.frg_add_promo_edt_save:
                break;
            case R.id.act_intro_lay_toolbar_rlBack:
                getActivity().onBackPressed();
                break;
            case R.id.act_intro_lay_toolbar_rlCross:
                ((IntroActivity) getActivity()).navToPreSignInVAFragment();
                break;
            case R.id.fab:
                navtoAddPromoFragment(false, "", "", "", "", "", "");
                break;
        }
    }

}
