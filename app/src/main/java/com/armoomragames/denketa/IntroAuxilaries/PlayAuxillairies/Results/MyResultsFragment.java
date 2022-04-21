package com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.Results;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.armoomragames.denketa.AppConfig;
import com.armoomragames.denketa.IntroActivity;
import com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.Results.AddResultsFragment;
import com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.Results.DModelResults;
import com.armoomragames.denketa.IntroAuxilaries.ResultsAdapter;
import com.armoomragames.denketa.IntroAuxilaries.WebServices.Intro_WebHit_Get_Results;
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.IAdapterCallback;
import com.armoomragames.denketa.Utils.IWebPaginationCallback;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MyResultsFragment extends Fragment implements View.OnClickListener, IWebPaginationCallback, AbsListView.OnScrollListener {

    RelativeLayout rlToolbar, rlBack, rlCross;
    Bundle bundle;
    String danetka_name = "";
    String danetka_id = "1";
    TextView txvDanetkaName;
    LinearLayout llAddResult, llNoResults;
    ImageView imvAddResults;
    boolean isAlreadyFetching = false;
    private int nFirstVisibleItem, nVisibleItemCount, nTotalItemCount, nScrollState, nErrorMsgShown;
    private boolean isLoadingMore = false;
    ResultsAdapter resultsAdapter = null;
    ListView lsvResults;
    ArrayList<DModelResults> lst_results;
    Intro_WebHit_Get_Results intro_webHit_get_results;
    private Dialog progressDialog;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_my_results, container, false);

        init();
        bindViewss(frg);

        setStates();


        return frg;
    }

    private void setStates() {
        txvDanetkaName.setText(danetka_name);

    }

    private void init() {
        bundle = this.getArguments();
        if (bundle != null) {
            danetka_name = bundle.getString("key_danetka_name");
            danetka_id = bundle.getString("key_danetka_id");
        }

        lst_results = new ArrayList<>();


        nFirstVisibleItem = 0;
        nVisibleItemCount = 0;
        nTotalItemCount = 0;
        nScrollState = 0;
        nErrorMsgShown = 0;
        isLoadingMore = false;

        requestResult();
    }

    public void requestResult() {
        isAlreadyFetching = true;
        showProgDialog();
        intro_webHit_get_results = new Intro_WebHit_Get_Results();

        Intro_WebHit_Get_Results.mPaginationInfo.currIndex = AppConstt.PAGINATION_START_INDEX;
        Intro_WebHit_Get_Results.responseObject = null;
        intro_webHit_get_results.getCategory(this,
                Intro_WebHit_Get_Results.mPaginationInfo.currIndex,Integer.parseInt(danetka_id));
    }

    private void bindViewss(View frg) {
        rlToolbar = frg.findViewById(R.id.act_intro_rl_toolbar);
        rlBack = frg.findViewById(R.id.act_intro_lay_toolbar_rlBack);
        rlCross = frg.findViewById(R.id.act_intro_lay_toolbar_rlCross);

        txvDanetkaName = frg.findViewById(R.id.frg_my_results_txv_danetkaname);


        lsvResults = frg.findViewById(R.id.frg_lsv_results);
        llNoResults = frg.findViewById(R.id.frg_my_results_ll_NoResults);

        imvAddResults = frg.findViewById(R.id.frg_my_results_imv_AddResults);
        llAddResult = frg.findViewById(R.id.frg_my_results_ll_AddResults);


        rlBack.setOnClickListener(this);
        rlCross.setOnClickListener(this);
        imvAddResults.setOnClickListener(this);


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
            case R.id.frg_my_results_imv_AddResults:
                navToAddResultFragment(txvDanetkaName.getText().toString(), danetka_id.toString(), (lst_results.size()+1));
                break;


        }
    }

    private void navToAddResultFragment(String name, String id, int _size) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new AddResultsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("key_danetka_name", name);
        bundle.putString("key_danetka_id",  id);
        bundle.putInt("key_danetka_size",  _size);
        frag.setArguments(bundle);
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_AddResultFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_AddResultFragment);
        ft.hide(this);
        ft.commit();
    }
    private void navToEditResultsFragment(String name, int id, int _size,String time, String invest,int selection,String danetka_id) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new EditResultsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("key_danetka_name", name);
        bundle.putInt("key_danetka_id",  id);
        bundle.putString("key_danetka_invst_name",  invest);
        bundle.putString("key_danetka_invst_danetka_id",  danetka_id);
        bundle.putString("key_danetka_time",  time);
        bundle.putInt("key_danetka_size",  _size);
        bundle.putInt("key_danetka_selection",  selection);
        frag.setArguments(bundle);
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_EditResultsFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_EditResultsFragment);
        ft.hide(this);
        ft.commit();
    }

    private void populateAllDanektasData(boolean isSuccess, String strMsg) {

        dismissProgDialog();

        isAlreadyFetching = false;
        if (getActivity() != null && isAdded())
            if (isSuccess) {

                if (Intro_WebHit_Get_Results.responseObject != null &&
                        Intro_WebHit_Get_Results.responseObject.getData() != null &&
                        Intro_WebHit_Get_Results.responseObject.getData().size() > 0) {

//                    txvNoData.setVisibility(View.GONE);
                    llNoResults.setVisibility(View.GONE);
                    for (int i = 0; i <Intro_WebHit_Get_Results .responseObject.getData().size(); i++) {
                        lst_results.add(new DModelResults(
                                (i+1)+"",
                                Intro_WebHit_Get_Results.responseObject.getData().get(i).getInvestigatorName(),
                                Intro_WebHit_Get_Results.responseObject.getData().get(i).getDate(),
                                Intro_WebHit_Get_Results.responseObject.getData().get(i).getTime(),
                                (Intro_WebHit_Get_Results.responseObject.getData().get(i).getRiglettosUsed()+""),
                                (Intro_WebHit_Get_Results.responseObject.getData().get(i).getId()),
                                (Intro_WebHit_Get_Results.responseObject.getData().get(i).getInvestegorNumber()),
                                (Intro_WebHit_Get_Results.responseObject.getData().get(i).getMasterName())
                                )
                        );

                    }

                    if (resultsAdapter == null) {
                        resultsAdapter = new ResultsAdapter( getActivity(), lst_results, (eventId, position) -> {
                            switch (eventId) {
                                case IAdapterCallback.EVENT_A:

                                    navToEditResultsFragment(
                                            txvDanetkaName.getText().toString(),
                                            lst_results.get(position).getID(),
                                            (position+1),
                                            lst_results.get(position).getTime(),
                                            lst_results.get(position).getInvestigator(),
                                            Integer.parseInt(lst_results.get(position).getRegiltor_used()),
                                            danetka_id
                                    );
                                    break;
                            }

                        });
                        lsvResults.setAdapter(resultsAdapter);
                        lsvResults.setOnScrollListener(this);
                    } else {
                        resultsAdapter.notifyDataSetChanged();
                    }
                } else {
                    if (Intro_WebHit_Get_Results.mPaginationInfo.currIndex == 1) {
////                        lsvMedicines.setVisibility(View.GONE);
//                        txvNoData.setVisibility(View.VISIBLE);
                        llNoResults.setVisibility(View.VISIBLE);
////                        imvNoData.setVisibility(View.VISIBLE);
                        lsvResults.setOnScrollListener(null);
                    }
                }
            } else {
                if (Intro_WebHit_Get_Results.mPaginationInfo.currIndex == 1) {
////                    lsvMedicines.setVisibility(View.GONE);
//                    txvNoData.setVisibility(View.VISIBLE);
////                    imvNoData.setVisibility(View.VISIBLE);
                    llNoResults.setVisibility(View.VISIBLE);
                    lsvResults.setOnScrollListener(null);
                }
            }
    }
    //region Api and population more danetka


    private void updateDenketaList(boolean isSuccess, boolean isCompleted, String errorMsg) {
        isLoadingMore = false;
//        llListItemLoader.setVisibility(View.GONE);
//        if (progressDilogue != null) {
//            progressDilogue.stopiOSLoader();
//        }
        dismissProgDialog();

        if (getActivity() != null && isAdded())//check whether it is attached to an activity
            if (isSuccess) {
                if (isCompleted) {
                    Intro_WebHit_Get_Results.mPaginationInfo.isCompleted = true;
                } else {
                    populateAllDanektasData(isSuccess, errorMsg);
                }
            } else if (nErrorMsgShown++ < AppConstt.LIMIT_PAGINATION_ERROR) {
//                CustomToast.showToastMessage(getActivity(), errorMsg, Toast.LENGTH_SHORT, false);
            }
    }


    @Override
    public void onWebInitialResult(boolean isSuccess, String strMsg) {
        populateAllDanektasData(isSuccess, strMsg);
    }

    @Override
    public void onWebSuccessiveResult(boolean isSuccess, boolean isCompleted, String strMsg) {
//        updateDenketaList(isSuccess, isCompleted, strMsg);
    }

    @Override
    public void onWebInitialException(Exception ex) {
        populateAllDanektasData(false, AppConfig.getInstance().getNetworkExceptionMessage(ex.toString()));
    }

    @Override
    public void onWebSuccessiveException(Exception ex) {
        updateDenketaList(false, false, AppConfig.getInstance().getNetworkExceptionMessage(ex.getMessage()));
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {
        this.nScrollState = i;
        this.isScrollCompleted();
    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {
        this.nFirstVisibleItem = i;
        this.nVisibleItemCount = i1;
        this.nTotalItemCount = i2;
    }

    private void isScrollCompleted() {
        if (this.nVisibleItemCount > 0 && this.nScrollState == SCROLL_STATE_IDLE &&
                this.nTotalItemCount == (nFirstVisibleItem + nVisibleItemCount)) {
            /*** In this way I detect if there's been a scroll which has completed ***/
            if (!isLoadingMore && !Intro_WebHit_Get_Results.mPaginationInfo.isCompleted) {
                isLoadingMore = true;
//                llListItemLoader.setVisibility(View.VISIBLE);

                intro_webHit_get_results.getCategory(this,
                        Intro_WebHit_Get_Results.mPaginationInfo.currIndex + 1,Integer.parseInt(danetka_id));
            }
        }
    }


    //region progdialog


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
    //endregion
}
